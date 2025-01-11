package com.mycompany.Controlador;

import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Colorado Jimenez Victor
 */
public class ControladorVentanaJuegos implements ActionListener {

    VistaVentanaJuegos vvj;
    Conexion c;

    public ControladorVentanaJuegos(VistaVentanaJuegos vvj, Conexion c) {
        this.vvj = vvj;
        this.c = c;
        asociarListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {

            case "Buscar":
                //Logica de busqueda en la tabla
                System.out.println(" buscar");
                break;
            case "Atras":
                VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
                ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
                vvj.dispose();
                break;
        }
    }

    public void asociarListeners(ActionListener listener) {
        vvj.jButtonAtras.addActionListener(listener);
        vvj.jButtonBuscarJuegos.addActionListener(listener);
    }
}
