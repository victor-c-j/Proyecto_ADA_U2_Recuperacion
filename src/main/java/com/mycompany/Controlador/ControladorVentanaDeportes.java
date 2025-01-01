package com.mycompany.Controlador;

import com.mycompany.Modelo.DeporteDAO;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.VistaVentanaDeportes;
import com.mycompany.Vista.VistaVentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorVentanaDeportes implements ActionListener {

    private VistaVentanaDeportes vvd;
    private DeporteDAO deporteDAO;
    private Conexion c;

    public ControladorVentanaDeportes(VistaVentanaDeportes vvd, Conexion c) {
        this.vvd = vvd;
        this.c = c;
        this.deporteDAO = new DeporteDAO(c.getConexion());
        asociarListeners(this);
        vvd.cargarDatosDeportes(); // Cargar los datos iniciales
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Buscar":
                String nombreBusqueda = vvd.jTextFieldDeportes.getText();
                if (!nombreBusqueda.isEmpty()) {
                    vvd.filtrarTabla(nombreBusqueda); // Filtrar directamente en la vista
                } else {
                    vvd.cargarDatosDeportes(); // Si no hay texto, mostrar todos los deportes
                }
                break;

            case "Atras":
                VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
                ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
                vvd.dispose();
                break;

            case "Editar":
                String[] deporteParaEditar = vvd.getDeporteSeleccionado();
                if (deporteParaEditar != null && deporteParaEditar.length == 2) {
                    String nuevoNombre = JOptionPane.showInputDialog(vvd, "Nuevo nombre del deporte:", deporteParaEditar[1]);
                    if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
                        int idDeporte = Integer.parseInt(deporteParaEditar[0]);
                        deporteDAO.actualizarDeporte(idDeporte, nuevoNombre);
                        vvd.cargarDatosDeportes(); // Recargar los deportes después de editar
                    }
                } else {
                    JOptionPane.showMessageDialog(vvd, "Por favor, selecciona un deporte válido para editar.");
                }
                break;

            case "Registrar":
                String nuevoDeporte = JOptionPane.showInputDialog(vvd, "Ingrese el nombre del nuevo deporte:");
                if (nuevoDeporte != null && !nuevoDeporte.isEmpty()) {
                    deporteDAO.insertarDeporte(nuevoDeporte);
                    vvd.cargarDatosDeportes(); // Recargar la lista después de registrar
                } else {
                    JOptionPane.showMessageDialog(vvd, "El nombre del deporte no puede estar vacío.");
                }
                break;

            case "Eliminar":
                String[] deporteSeleccionado = vvd.getDeporteSeleccionado();
                if (deporteSeleccionado != null) {
                    int idDeporteEliminar = Integer.parseInt(deporteSeleccionado[0]);
                    
                    // Confirmación antes de eliminar
                    int confirmacion = JOptionPane.showConfirmDialog(
                        vvd, 
                        "¿Está seguro de que desea eliminar el deporte seleccionado? Esta acción no se puede deshacer.", 
                        "Confirmar Eliminación", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirmacion == JOptionPane.YES_OPTION) {
                        deporteDAO.eliminarDeporte(idDeporteEliminar);
                        vvd.cargarDatosDeportes(); // Recargar la lista después de eliminar
                        JOptionPane.showMessageDialog(vvd, "Deporte eliminado con éxito.");
                    }
                } else {
                    JOptionPane.showMessageDialog(vvd, "Por favor, selecciona un deporte para eliminar.");
                }
                break;
        }
    }

    public void asociarListeners(ActionListener listener) {
        vvd.jButtonAtras.addActionListener(listener);
        vvd.jButtonBuscarDeportes.addActionListener(listener);
        vvd.jButtonEditar.addActionListener(listener);
        vvd.jButtonRegistrar.addActionListener(listener);
        vvd.jButtonEliminar.addActionListener(listener);
    }
}
