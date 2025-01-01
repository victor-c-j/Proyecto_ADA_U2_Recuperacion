package com.mycompany.Controlador;

import com.mycompany.Modelo.AtletaDAO;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author usuario
 */
public class ControladorVentanaAtletas implements ActionListener {

    VistaVentanaAtletas vva;
    Conexion c;
    AtletaDAO atletaDAO;

    public ControladorVentanaAtletas(VistaVentanaAtletas vva, Conexion c) {
        this.vva = vva;
        this.c = c;

        // Inicializar AtletaDAO con la conexión proporcionada
        this.atletaDAO = new AtletaDAO(c.getConexion());

        // Asociar los listeners a los botones
        asociarListeners(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Buscar":
                String busqueda = vva.jTextFieldAtletas.getText().trim();
                vva.filtrarTabla(busqueda);
                break;

            case "Atras":
                VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
                ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
                vva.dispose();
                break;

            case "Eliminar":
                // Obtener el atleta seleccionado desde la tabla (usamos la primera columna que tiene el ID)
                int filaSeleccionada = vva.jTableAtletas.getSelectedRow();
                if (filaSeleccionada != -1) {
                    // Obtener el ID del atleta desde la primera columna de la tabla
                    String idAtletaStr = (String) vva.jTableAtletas.getValueAt(filaSeleccionada, 0); // Primera columna tiene el ID
                    int idAtleta = Integer.parseInt(idAtletaStr);

                    // Confirmar eliminación
                    int opcion = JOptionPane.showConfirmDialog(vva, "¿Está seguro de que desea eliminar al atleta con ID " + idAtleta + "?", "Confirmación", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        // Eliminar el atleta utilizando el ID
                        atletaDAO.eliminarAtletaConTransaccion(idAtleta);
                        vva.cargarDatosAtletas();  // Recargar los datos después de eliminar
                    }
                } else {
                    JOptionPane.showMessageDialog(vva, "Por favor, seleccione un atleta.");
                }
                break;

            case "Editar":
                // Obtener el atleta seleccionado
                String[] atletaParaEditar = vva.getAtletaSeleccionado();
                if (atletaParaEditar != null) {
                    // Obtener el ID del atleta desde la primera columna de la tabla (index 0)
                    int idAtleta = Integer.parseInt(atletaParaEditar[0]);

                    // Pedir el nuevo nombre
                    String nuevoNombre = JOptionPane.showInputDialog(vva, "Nuevo nombre completo:", atletaParaEditar[1]);

                    // Si el nombre no es null, pedir los otros campos para editar
                    if (nuevoNombre != null) {
                        String nuevoGenero = JOptionPane.showInputDialog(vva, "Nuevo género:", atletaParaEditar[2]);
                        String nuevaAlturaStr = JOptionPane.showInputDialog(vva, "Nueva altura:", atletaParaEditar[3]);
                        float nuevaAltura = Float.parseFloat(nuevaAlturaStr);

                        if (nuevoGenero != null && nuevaAlturaStr != null && nuevoNombre != null) {
                            // Actualizar el atleta con el nuevo nombre, género y altura
                            atletaDAO.actualizarAtleta(idAtleta, nuevoNombre, nuevoGenero, nuevaAltura);
                            vva.cargarDatosAtletas();  // Recargar los datos después de editar
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vva, "Por favor, seleccione un atleta.");
                }
                break;

            case "Registrar":
                // Abrir ventana para registrar un nuevo atleta (puedes pedir los datos con JOptionPane o un formulario)
                String nombre = JOptionPane.showInputDialog(vva, "Nombre Completo:");
                String genero = JOptionPane.showInputDialog(vva, "Género:");
                String alturaStr = JOptionPane.showInputDialog(vva, "Altura:");
                float altura = Float.parseFloat(alturaStr);

                if (nombre != null && genero != null && alturaStr != null) {
                    // Registrar el nuevo atleta
                    atletaDAO.insertarAtleta(nombre, genero, altura);
                    vva.cargarDatosAtletas();  // Recargar los datos después de registrar
                }
                break;
        }
    }

    public void asociarListeners(ActionListener listener) {
        vva.jButtonBuscarAtletas.addActionListener(listener);
        vva.jButtonAtras.addActionListener(listener);
        vva.jButtonEditar.addActionListener(listener);
        vva.jButtonRegistrar.addActionListener(listener);
        vva.jButtonEliminar.addActionListener(listener);
    }
}
