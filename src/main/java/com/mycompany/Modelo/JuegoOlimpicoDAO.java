package com.mycompany.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Colorado Jimenez Victor
 */

public class JuegoOlimpicoDAO {
    private Connection conexion;

    public JuegoOlimpicoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public List<String> obtenerJuegosOlimpicos() {
        List<String> juegos = new ArrayList<>();
        String consulta = "SELECT nombre_juegos, agno_celebracion, estacion FROM juego_olimpico";

        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consulta)) {
            while (rs.next()) {
                String nombre = rs.getString("nombre_juegos");
                int agno = rs.getInt("agno_celebracion");
                String estacion = rs.getString("estacion");
                juegos.add(nombre + " (" + agno + ", " + estacion + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return juegos;
    }
}