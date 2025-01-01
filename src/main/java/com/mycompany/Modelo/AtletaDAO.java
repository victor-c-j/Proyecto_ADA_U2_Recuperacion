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
        String consulta = "SELECT * FROM atleta";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                String idAtleta = String.valueOf(rs.getInt("id_atleta"));
                String nombre = rs.getString("nombre_completo");
                String genero = rs.getString("genero");
                float altura = rs.getFloat("altura");
                atletas.add(new String[]{idAtleta, nombre, genero, String.valueOf(altura)});  // Devolvemos un array con todos los datos
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
