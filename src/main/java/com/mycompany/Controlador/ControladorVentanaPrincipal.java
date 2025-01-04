package com.mycompany.Controlador;

import com.mycompany.Modelo.*;
import com.mycompany.Vista.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Colorado Jimenez Victor
 */
public class ControladorVentanaPrincipal implements ActionListener {

    VistaVentanaPrincipal vvp;
    Conexion c;

    public ControladorVentanaPrincipal(VistaVentanaPrincipal vvp, Conexion c) {
        this.vvp = vvp;
        this.c = c;
        asociarListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Atletas":
                VistaVentanaAtletas vva = new VistaVentanaAtletas();
                vva.setVisible(true);
                ControladorVentanaAtletas cva = new ControladorVentanaAtletas(vva, c);
                vvp.dispose();
                break;
            case "Deportes":
                VistaVentanaDeportes vvd = new VistaVentanaDeportes(new DeporteDAO(c.getConexion()));
                vvd.setVisible(true);
                ControladorVentanaDeportes cvd = new ControladorVentanaDeportes(vvd, c);
                vvp.dispose();
                break;
            case "Juegos":
                /*VistaVentanaJuegos vvj = new VistaVentanaJuegos(new JuegoOlimpicoDAO(c.getConexion()));
                vvj.setVisible(true);
                ControladorVentanaJuegos cvj = new ControladorVentanaJuegos(vvj, c);*/
                JOptionPane.showMessageDialog(null, "La vista hacia los Juegos Olímpicos está temporalmente deshabilitada.",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
                //vvp.dispose();
                break;
        }
    }

    public void asociarListeners(ActionListener listener) {
        vvp.jButtonAtletas.addActionListener(listener);
        vvp.jButtonDeportes.addActionListener(listener);
        vvp.jButtonJuegos.addActionListener(listener);
    }
}
