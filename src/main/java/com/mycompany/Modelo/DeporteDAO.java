package com.mycompany.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeporteDAO {

    private Connection conexion;

    public DeporteDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Obtener la lista de deportes
    public List<String[]> obtenerDeportes() {
        List<String[]> deportes = new ArrayList<>();
        String consulta = "SELECT id_deporte, nombre_deporte FROM deporte"; // Se seleccionan id_deporte y nombre_deporte

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                String idDeporte = String.valueOf(rs.getInt("id_deporte"));
                String nombreDeporte = rs.getString("nombre_deporte");
                deportes.add(new String[]{idDeporte, nombreDeporte}); // Devolvemos un array con id y nombre
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportes;
    }

    // Registrar un nuevo deporte
    public void insertarDeporte(String nombreDeporte) {
        String query = "INSERT INTO deporte (nombre_deporte) VALUES (?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, nombreDeporte);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un deporte y todos sus elementos asociados
    public void eliminarDeporte(int idDeporte) {
        try {
            conexion.setAutoCommit(false); // Inicia la transacción

            // Eliminar competidores asociados a los eventos del deporte
            String deleteCompetidoresQuery
                    = "DELETE FROM competidor_de_evento WHERE id_evento IN (SELECT id_evento FROM evento WHERE id_deporte = ?)";
            try (PreparedStatement deleteCompetidoresStmt = conexion.prepareStatement(deleteCompetidoresQuery)) {
                deleteCompetidoresStmt.setInt(1, idDeporte);
                deleteCompetidoresStmt.executeUpdate();
            }

            // Eliminar jueces asociados a los eventos del deporte
            String deleteJuecesQuery
                    = "DELETE FROM juez WHERE id_evento IN (SELECT id_evento FROM evento WHERE id_deporte = ?)";
            try (PreparedStatement deleteJuecesStmt = conexion.prepareStatement(deleteJuecesQuery)) {
                deleteJuecesStmt.setInt(1, idDeporte);
                deleteJuecesStmt.executeUpdate();
            }

            // Eliminar eventos asociados al deporte
            String deleteEventosQuery = "DELETE FROM evento WHERE id_deporte = ?";
            try (PreparedStatement deleteEventosStmt = conexion.prepareStatement(deleteEventosQuery)) {
                deleteEventosStmt.setInt(1, idDeporte);
                deleteEventosStmt.executeUpdate();
            }

            // Finalmente, eliminar el deporte
            String deleteDeporteQuery = "DELETE FROM deporte WHERE id_deporte = ?";
            try (PreparedStatement deleteDeporteStmt = conexion.prepareStatement(deleteDeporteQuery)) {
                deleteDeporteStmt.setInt(1, idDeporte);
                deleteDeporteStmt.executeUpdate();
            }

            // Confirmar la transacción
            conexion.commit();
            System.out.println("Deporte y elementos asociados eliminados con éxito.");

        } catch (SQLException e) {
            try {
                // Si ocurre un error, revertir la transacción
                conexion.rollback();
                System.err.println("Error al eliminar el deporte. Se ha revertido la transacción.");
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conexion.setAutoCommit(true); // Reactivar el auto-commit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Editar un deporte
    public void actualizarDeporte(int idDeporte, String nuevoNombre) {
        String query = "UPDATE deporte SET nombre_deporte = ? WHERE id_deporte = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(query)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setInt(2, idDeporte);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Buscar deporte por nombre
    public List<String> buscarDeportePorNombre(String nombre) {
        List<String> deportes = new ArrayList<>();
        String consulta = "SELECT id_deporte, nombre_deporte FROM deporte WHERE nombre_deporte LIKE ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(consulta)) {
            pstmt.setString(1, "%" + nombre + "%"); // Hacemos una búsqueda con LIKE
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                deportes.add(rs.getInt("id_deporte") + " - " + rs.getString("nombre_deporte"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportes;
    }
}
