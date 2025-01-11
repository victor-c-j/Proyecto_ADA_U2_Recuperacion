/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyecto_ada_u2_recuperacion;

import com.mycompany.Controlador.ControladorVentanaPrincipal;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.VistaVentanaPrincipal;

/**
 *
 * @author usuario
 */
public class Proyecto_ADA_U2_Recuperacion {

    public static void main(String[] args) {
        Conexion c = new Conexion();
        VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
        ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
    }
}
