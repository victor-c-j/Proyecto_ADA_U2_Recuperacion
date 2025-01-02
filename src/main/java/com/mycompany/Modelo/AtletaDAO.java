package com.mycompany.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Colorado Jimenez Victor
 */
public class AtletaDAO {

    private Connection conexion;

    public AtletaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<String[]> obtenerAtletas() {
        List<String[]> atletas = new ArrayList<>();
        String consulta = "SELECT " +
                "a.nombre_completo AS \"Atleta\", " +
                "r.nombre_region AS \"Región\", " +
                "CONCAT('(', r.noc, ')') AS \"Código Región\", " +
                "COUNT(DISTINCT cj.id_juego_olimpico) AS \"Juegos Olímpicos Participados\", " +
                "MIN(jo.agno_celebracion) AS \"Primer Juego Olímpico\", " +
                "cj.edad AS \"Edad en el Último Juego\", " +
                "COUNT(*) AS \"Total de Participaciones\", " +
                "SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS \"Oro\", " +
                "SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS \"Plata\", " +
                "SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS \"Bronce\", " +
                "SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS \"Total Medallas\" " +
                "FROM atleta a " +
                "JOIN region_atleta ra ON a.id_atleta = ra.id_atleta " +
                "JOIN region r ON ra.id_region = r.id_region " +
                "JOIN competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta " +
                "LEFT JOIN competidor_de_evento cde ON cj.id_competidores = cde.id_competidor " +
                "LEFT JOIN juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico " +
                "GROUP BY a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, cj.edad " +
                "ORDER BY a.nombre_completo";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                String atleta = rs.getString("Atleta");
                String region = rs.getString("Región");
                String codigoRegion = rs.getString("Código Región");
                String juegosOlimpicos = String.valueOf(rs.getInt("Juegos Olímpicos Participados"));
                String primerJuegoOlimpico = String.valueOf(rs.getInt("Primer Juego Olímpico"));
                String edad = String.valueOf(rs.getInt("Edad en el Último Juego"));
                String totalParticipaciones = String.valueOf(rs.getInt("Total de Participaciones"));
                String oro = String.valueOf(rs.getInt("Oro"));
                String plata = String.valueOf(rs.getInt("Plata"));
                String bronce = String.valueOf(rs.getInt("Bronce"));
                String totalMedallas = String.valueOf(rs.getInt("Total Medallas"));

                // Añadimos los datos obtenidos en un array
                atletas.add(new String[]{atleta, region + " (" + codigoRegion + ")", juegosOlimpicos, 
                                         primerJuegoOlimpico, edad, totalParticipaciones, 
                                         "Oro: " + oro + ", Plata: " + plata + ", Bronce: " + bronce + ", Total: " + totalMedallas});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atletas;
    }

    // Actualizar un atleta
    public void actualizarAtleta(int idAtleta, String nombre, String genero, float altura) {
        String query = "UPDATE atleta SET nombre_completo = ?, genero = ?, altura = ? WHERE id_atleta = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, genero);
            stmt.setFloat(3, altura);
            stmt.setInt(4, idAtleta);  // Usamos el id_atleta en lugar del nombre
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   public void insertarAtleta(String nombre, String genero, float altura) {
    int nuevoId = obtenerUltimoId() + 1;  // Obtener el último ID y sumarle 1 para el nuevo ID
    String sql = "INSERT INTO Atleta (id_atleta, nombre_completo, genero, altura) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pst = conexion.prepareStatement(sql)) {
        pst.setInt(1, nuevoId);  // Establecer el nuevo ID
        pst.setString(2, nombre);  // Establecer el nombre
        pst.setString(3, genero);  // Establecer el género
        pst.setFloat(4, altura);   // Establecer la altura
        pst.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private int obtenerUltimoId() {
        String sql = "SELECT MAX(id_atleta) FROM Atleta";
        int ultimoId = 0;

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                ultimoId = rs.getInt(1);  // Obtener el valor del máximo ID
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ultimoId;
    }

    // Método auxiliar para obtener el id_atleta por su nombre (necesario para eliminar las referencias)
    public int obtenerIdAtletaPorNombre(String nombre) {
        String query = "SELECT id_atleta FROM atleta WHERE nombre_completo = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_atleta");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Si no se encuentra el atleta
    }

public List<String[]> obtenerDatosAtletasConsultaConFiltro(String busqueda) {
    List<String[]> atletas = new ArrayList<>();
    String consulta = "SELECT " +
            "a.nombre_completo AS 'Atleta', " +
            "r.nombre_region AS 'Región', " +
            "CONCAT('(', r.noc, ')') AS 'Código Región', " +
            "COUNT(DISTINCT cj.id_juego_olimpico) AS 'Juegos Olímpicos Participados', " +
            "MIN(jo.agno_celebracion) AS 'Primer Juego Olímpico', " +
            "cj.edad AS 'Edad en el Último Juego', " +
            "COUNT(*) AS 'Total de Participaciones', " +
            "SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS 'Oro', " +
            "SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS 'Plata', " +
            "SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS 'Bronce', " +
            "SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS 'Total Medallas' " +
            "FROM atleta a " +
            "JOIN region_atleta ra ON a.id_atleta = ra.id_atleta " +
            "JOIN region r ON ra.id_region = r.id_region " +
            "JOIN competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta " +
            "LEFT JOIN competidor_de_evento cde ON cj.id_competidores = cde.id_competidor " +
            "LEFT JOIN juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico " +
            "WHERE a.nombre_completo LIKE ? " +
            "GROUP BY a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, cj.edad " +
            "ORDER BY a.nombre_completo";

    try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
        stmt.setString(1, "%" + busqueda + "%");  // Filtrar por nombre de atleta

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Cambié List<Object> por String[] para que coincida con el tipo esperado
                String[] atleta = new String[11];  // Se crean 11 elementos para las 11 columnas
                atleta[0] = rs.getString("Atleta");
                atleta[1] = rs.getString("Región");
                atleta[2] = rs.getString("Código Región");
                atleta[3] = String.valueOf(rs.getInt("Juegos Olímpicos Participados"));
                atleta[4] = String.valueOf(rs.getInt("Primer Juego Olímpico"));
                atleta[5] = String.valueOf(rs.getInt("Edad en el Último Juego"));
                atleta[6] = String.valueOf(rs.getInt("Total de Participaciones"));
                atleta[7] = String.valueOf(rs.getInt("Oro"));
                atleta[8] = String.valueOf(rs.getInt("Plata"));
                atleta[9] = String.valueOf(rs.getInt("Bronce"));
                atleta[10] = String.valueOf(rs.getInt("Total Medallas"));

                // Añadimos el array de strings a la lista
                atletas.add(atleta);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return atletas;
}

    public void eliminarAtletaConTransaccion(int idAtleta) {
        String deleteCompetidorEvento = "DELETE FROM competidor_de_evento WHERE id_competidor IN (SELECT id_competidor FROM competidor_juego_olimpico WHERE id_atleta = ?)";
        String deleteCompetidorJuegoOlimpico = "DELETE FROM competidor_juego_olimpico WHERE id_atleta = ?";
        String deleteRegionAtleta = "DELETE FROM region_atleta WHERE id_atleta = ?";
        String deleteAtleta = "DELETE FROM atleta WHERE id_atleta = ?";

        try {
            // Iniciar transacción
            conexion.setAutoCommit(false);

            // Eliminar las filas relacionadas en competidor_de_evento
            try (PreparedStatement stmtEvento = conexion.prepareStatement(deleteCompetidorEvento)) {
                stmtEvento.setInt(1, idAtleta);
                stmtEvento.executeUpdate();
            }

            // Eliminar las filas relacionadas en competidor_juego_olimpico
            try (PreparedStatement stmtJuegoOlimpico = conexion.prepareStatement(deleteCompetidorJuegoOlimpico)) {
                stmtJuegoOlimpico.setInt(1, idAtleta);
                stmtJuegoOlimpico.executeUpdate();
            }

            // Eliminar las filas relacionadas en region_atleta
            try (PreparedStatement stmtRegionAtleta = conexion.prepareStatement(deleteRegionAtleta)) {
                stmtRegionAtleta.setInt(1, idAtleta);
                stmtRegionAtleta.executeUpdate();
            }

            // Eliminar al atleta
            try (PreparedStatement stmtAtleta = conexion.prepareStatement(deleteAtleta)) {
                stmtAtleta.setInt(1, idAtleta);
                stmtAtleta.executeUpdate();
            }

            // Confirmar transacción
            conexion.commit();

        } catch (SQLException e) {
            // En caso de error, revertir transacción
            try {
                conexion.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                // Restaurar el auto commit
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
