package com.mycompany.Modelo;

import com.mycompany.Otros.Atleta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO {

    private Connection conexion;

    public AtletaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener una lista de atletas
    public List<Atleta> obtenerAtletas() {
        List<Atleta> atletas = new ArrayList<>();
        String consulta = "SELECT "
                + "a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, "
                + "COUNT(DISTINCT cj.id_juego_olimpico) AS JuegosOlimpicosParticipados, "
                + "MIN(jo.agno_celebracion) AS PrimerJuegoOlimpico, "
                + "SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS Oro, "
                + "SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS Plata, "
                + "SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS Bronce, "
                + "SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS TotalMedallas "
                + "FROM atleta a "
                + "JOIN region_atleta ra ON a.id_atleta = ra.id_atleta "
                + "JOIN region r ON ra.id_region = r.id_region "
                + "JOIN competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta "
                + "LEFT JOIN competidor_de_evento cde ON cj.id_competidores = cde.id_competidor "
                + "LEFT JOIN juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico "
                + "GROUP BY a.id_atleta, a.nombre_completo, r.nombre_region, r.noc "
                + "ORDER BY a.nombre_completo";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                Atleta atleta = new Atleta();
                atleta.setId(rs.getInt("id_atleta")); 
                atleta.setNombreCompleto(rs.getString("nombre_completo"));
                atleta.setRegion(rs.getString("nombre_region"));
                atleta.setCodigoRegion(rs.getString("noc"));
                atleta.setJuegosOlimpicosParticipados(rs.getInt("JuegosOlimpicosParticipados"));
                atleta.setPrimerJuegoOlimpico(rs.getInt("PrimerJuegoOlimpico"));
                atleta.setOro(rs.getInt("Oro"));
                atleta.setPlata(rs.getInt("Plata"));
                atleta.setBronce(rs.getInt("Bronce"));
                atleta.setTotalMedallas(rs.getInt("TotalMedallas"));

                atletas.add(atleta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atletas;
    }

    // Método para obtener un atleta específico por su ID
    public Atleta obtenerAtletaPorId(int idAtleta) {
        Atleta atleta = null;
        String consulta = "SELECT "
                + "a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, "
                + "COUNT(DISTINCT cj.id_juego_olimpico) AS JuegosOlimpicosParticipados, "
                + "MIN(jo.agno_celebracion) AS PrimerJuegoOlimpico, "
                + "SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS Oro, "
                + "SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS Plata, "
                + "SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS Bronce, "
                + "SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS TotalMedallas "
                + "FROM atleta a "
                + "JOIN region_atleta ra ON a.id_atleta = ra.id_atleta "
                + "JOIN region r ON ra.id_region = r.id_region "
                + "JOIN competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta "
                + "LEFT JOIN competidor_de_evento cde ON cj.id_competidores = cde.id_competidor "
                + "LEFT JOIN juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico "
                + "WHERE a.id_atleta = ? "
                + "GROUP BY a.id_atleta, a.nombre_completo, r.nombre_region, r.noc";

        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setInt(1, idAtleta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                atleta = new Atleta();
                atleta.setId(rs.getInt("id_atleta")); // Asignar el id del atleta
                atleta.setNombreCompleto(rs.getString("nombre_completo"));
                atleta.setRegion(rs.getString("nombre_region"));
                atleta.setCodigoRegion(rs.getString("noc"));
                atleta.setJuegosOlimpicosParticipados(rs.getInt("JuegosOlimpicosParticipados"));
                atleta.setPrimerJuegoOlimpico(rs.getInt("PrimerJuegoOlimpico"));
                atleta.setOro(rs.getInt("Oro"));
                atleta.setPlata(rs.getInt("Plata"));
                atleta.setBronce(rs.getInt("Bronce"));
                atleta.setTotalMedallas(rs.getInt("TotalMedallas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atleta;
    }

    public void insertarAtleta(String nombre, String genero, float altura, String nombreRegion, int idRegion) {
        // Primero obtener el ID del nuevo atleta
        int nuevoId = obtenerUltimoId() + 1;
    
        // Consultas SQL para insertar en las tablas correspondientes
        String sqlAtleta = "INSERT INTO atleta (id_atleta, nombre_completo, altura, genero) VALUES (?, ?, ?, ?)";
        String sqlRegionAtleta = "INSERT INTO region_atleta (id_atleta, id_region) VALUES (?, ?)";
        String sqlCompetidorJuegoOlimpico = "INSERT INTO competidor_juego_olimpico (id_atleta, id_juego_olimpico) VALUES (?, ?)";
    
        try {
            // Comenzamos una transacción
            conexion.setAutoCommit(false);
    
            // Insertamos en la tabla atleta
            try (PreparedStatement pstAtleta = conexion.prepareStatement(sqlAtleta)) {
                pstAtleta.setInt(1, nuevoId);
                pstAtleta.setString(2, nombre);
                pstAtleta.setFloat(3, altura);
                pstAtleta.setString(4, genero);
                pstAtleta.executeUpdate();
            }
    
            // Insertamos en la tabla region_atleta
            try (PreparedStatement pstRegionAtleta = conexion.prepareStatement(sqlRegionAtleta)) {
                pstRegionAtleta.setInt(1, nuevoId);
                pstRegionAtleta.setInt(2, idRegion); // Asumimos que el idRegion es proporcionado
                pstRegionAtleta.executeUpdate();
            }
    
            // Insertamos en la tabla competidor_juego_olimpico con un juego olímpico por defecto (id_juego_olimpico = 1)
            try (PreparedStatement pstCompetidor = conexion.prepareStatement(sqlCompetidorJuegoOlimpico)) {
                pstCompetidor.setInt(1, nuevoId); // Insertar el atleta
                pstCompetidor.setInt(2, 1); // Asumimos que el id_juego_olimpico por defecto es 1
                pstCompetidor.executeUpdate();
            }
    
            // Commit de la transacción
            conexion.commit();
    
        } catch (SQLException e) {
            // En caso de error, hacer rollback
            try {
                conexion.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Restauramos el autocommit para que otras transacciones puedan ejecutarse normalmente
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    private int obtenerUltimoId() {
        String sql = "SELECT MAX(id_atleta) FROM atleta";
        int ultimoId = 0;

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                ultimoId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ultimoId;
    }

    public void eliminarAtletaConTransaccion(int idAtleta) {
        String deleteAtleta = "DELETE FROM atleta WHERE id_atleta = ?";
    
        try {
            conexion.setAutoCommit(false);
    
            try (PreparedStatement stmtAtleta = conexion.prepareStatement(deleteAtleta)) {
                stmtAtleta.setInt(1, idAtleta);
                stmtAtleta.executeUpdate();
            }
    
            conexion.commit();
    
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean editarAtleta(Atleta atleta) {
        String sql = "UPDATE atleta "
                + "SET nombre_completo = ?, "
                + "genero = ?, "
                + "altura = ? "
                + "WHERE id_atleta = ?";
        boolean success = false;

        try (PreparedStatement pst = conexion.prepareStatement(sql)) {
            // Asignar los valores a los parámetros de la consulta
            pst.setString(1, atleta.getNombreCompleto());
            pst.setString(2, String.valueOf(atleta.getGenero())); // Asumiendo que 'genero' es un char (ej. 'M' o 'F')
            pst.setFloat(3, atleta.getAltura());
            pst.setInt(4, atleta.getId());

            // Ejecutar la actualización
            int rowsAffected = pst.executeUpdate();

            // Si al menos una fila fue afectada, la actualización fue exitosa
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }


    public Atleta obtenerAtletaPorNombre(String nombreCompleto) {
        Atleta atleta = null;
        String consulta = "SELECT "
                + "a.id_atleta, a.nombre_completo, r.nombre_region, r.noc, "
                + "COUNT(DISTINCT cj.id_juego_olimpico) AS JuegosOlimpicosParticipados, "
                + "MIN(jo.agno_celebracion) AS PrimerJuegoOlimpico, "
                + "SUM(CASE WHEN cde.id_puesto = 1 THEN 1 ELSE 0 END) AS Oro, "
                + "SUM(CASE WHEN cde.id_puesto = 2 THEN 1 ELSE 0 END) AS Plata, "
                + "SUM(CASE WHEN cde.id_puesto = 3 THEN 1 ELSE 0 END) AS Bronce, "
                + "SUM(CASE WHEN cde.id_puesto IN (1, 2, 3) THEN 1 ELSE 0 END) AS TotalMedallas "
                + "FROM atleta a "
                + "JOIN region_atleta ra ON a.id_atleta = ra.id_atleta "
                + "JOIN region r ON ra.id_region = r.id_region "
                + "JOIN competidor_juego_olimpico cj ON a.id_atleta = cj.id_atleta "
                + "LEFT JOIN competidor_de_evento cde ON cj.id_competidores = cde.id_competidor "
                + "LEFT JOIN juego_olimpico jo ON cj.id_juego_olimpico = jo.id_juego_olimpico "
                + "WHERE a.nombre_completo = ? "
                + "GROUP BY a.id_atleta, a.nombre_completo, r.nombre_region, r.noc "
                + "ORDER BY a.nombre_completo";
    
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setString(1, nombreCompleto); // Asignamos el nombre del atleta a la consulta
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    atleta = new Atleta();
                    atleta.setId(rs.getInt("id_atleta"));
                    atleta.setNombreCompleto(rs.getString("nombre_completo"));
                    atleta.setRegion(rs.getString("nombre_region"));
                    atleta.setCodigoRegion(rs.getString("noc"));
                    atleta.setJuegosOlimpicosParticipados(rs.getInt("JuegosOlimpicosParticipados"));
                    atleta.setPrimerJuegoOlimpico(rs.getInt("PrimerJuegoOlimpico"));
                    atleta.setOro(rs.getInt("Oro"));
                    atleta.setPlata(rs.getInt("Plata"));
                    atleta.setBronce(rs.getInt("Bronce"));
                    atleta.setTotalMedallas(rs.getInt("TotalMedallas"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return atleta; // Retorna el atleta encontrado o null si no se encuentra
    }
    
}
