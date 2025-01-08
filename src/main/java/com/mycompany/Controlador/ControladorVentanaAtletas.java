package com.mycompany.Controlador;

import com.mycompany.Otros.Atleta;
import com.mycompany.Modelo.AtletaDAO;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.VistaVentanaAtletas;
import com.mycompany.Vista.VistaVentanaPrincipal;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Controlador para la ventana de gestión de atletas.
 */
public class ControladorVentanaAtletas implements ActionListener {

    private final VistaVentanaAtletas vva;
    private final Conexion c;
    private final AtletaDAO atletaDAO;

    public ControladorVentanaAtletas(VistaVentanaAtletas vva, Conexion c) {
        this.vva = vva;
        this.c = c;
        this.atletaDAO = new AtletaDAO(c.getConexion());

        // Asociar los listeners a los botones
        asociarListeners(this);

        // Cargar datos iniciales en la tabla
        cargarDatosAtletas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "Buscar":
                filtrarAtletas();
                break;

            case "Atrás":
                irAtras();
                break;

            case "Eliminar":
                eliminarAtleta();
                break;

            case "Editar":
                editarAtleta();
                break;

            case "Registrar":
                registrarAtleta();
                break;
        }
    }

    /**
     * Carga los datos de los atletas desde el DAO y los pasa a la vista.
     */
    private void cargarDatosAtletas() {
        List<Atleta> atletas = atletaDAO.obtenerAtletas();
        vva.actualizarTablaAtletas(atletas);
    }

    /**
     * Filtra los atletas con base en el texto ingresado en el campo de búsqueda.
     */
    private void filtrarAtletas() {
        String busqueda = vva.getTextoBusqueda();
        if (!busqueda.isEmpty()) {
            List<Atleta> atletasFiltrados = atletaDAO.obtenerAtletas(); // Cambia esto según tus necesidades
            vva.actualizarTablaAtletas(atletasFiltrados);
        } else {
            cargarDatosAtletas();
        }
    }

    /**
     * Elimina el atleta seleccionado de la tabla después de confirmar la acción.
     */
    private void eliminarAtleta() {
        Atleta atletaSeleccionado = atletaDAO.obtenerAtletaPorNombre(vva.getAtletaSeleccionado().getNombreCompleto());
        if (atletaSeleccionado != null) {
            boolean confirmado = vva.confirmarAccion("¿Está seguro de que desea eliminar al atleta " + atletaSeleccionado.getNombreCompleto() + "?");
            if (confirmado) {
                atletaDAO.eliminarAtletaConTransaccion(atletaSeleccionado.getId());
                cargarDatosAtletas();
                vva.mostrarMensaje("Atleta eliminado con éxito.");
            }
        } else {
            vva.mostrarMensaje("Por favor, seleccione un atleta.");
        }
    }

    /**
     * Permite editar los datos del atleta seleccionado.
     */
    private void editarAtleta() {
        Atleta atletaSeleccionado = vva.getAtletaSeleccionado();
        if (atletaSeleccionado != null) {
            String nuevoNombre = JOptionPane.showInputDialog(vva, "Nuevo nombre completo:", atletaSeleccionado.getNombreCompleto());
            String nuevoGenero = JOptionPane.showInputDialog(vva, "Nuevo género:", atletaSeleccionado.getGenero());
            String nuevoAlturaStr = JOptionPane.showInputDialog(vva, "Nueva altura:", atletaSeleccionado.getAltura());

            try {
                float nuevaAltura = Float.parseFloat(nuevoAlturaStr);
                atletaSeleccionado.setNombreCompleto(nuevoNombre);
                atletaSeleccionado.setGenero(nuevoGenero);
                atletaSeleccionado.setAltura(nuevaAltura);

                boolean actualizado = atletaDAO.editarAtleta(atletaSeleccionado);
                if (actualizado) {
                    cargarDatosAtletas();
                    vva.mostrarMensaje("Atleta actualizado con éxito.");
                } else {
                    vva.mostrarMensaje("Error: No se pudo actualizar el atleta.");
                }
            } catch (NumberFormatException e) {
                vva.mostrarMensaje("La altura debe ser un número válido.");
            }
        } else {
            vva.mostrarMensaje("Por favor, seleccione un atleta.");
        }
    }

    /**
     * Registra un nuevo atleta en el sistema.
     */
    private void registrarAtleta() {
        String nombre = JOptionPane.showInputDialog(vva, "Nombre Completo:");
        String genero = JOptionPane.showInputDialog(vva, "Género:");
        String alturaStr = JOptionPane.showInputDialog(vva, "Altura (en metros):");
        String nombreRegion = JOptionPane.showInputDialog(vva, "Nombre region: ");
        String noc = JOptionPane.showInputDialog(vva, "Abreviatura(noc) region: ");
        int idRegion = atletaDAO.ultimoIdRegion() + 1;
        try {
            float altura = Float.parseFloat(alturaStr);


            atletaDAO.insertarAtleta(nombre, genero, altura, nombreRegion, noc, idRegion);
            cargarDatosAtletas();
            vva.mostrarMensaje("Atleta registrado con éxito.");
        } catch (NumberFormatException e) {
            vva.mostrarMensaje("Error: Altura no válida.");
        }
    }

    /**
     * Navega a la ventana principal y cierra la actual.
     */
    private void irAtras() {
        VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
        ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
        vva.dispose();
    }

    /**
     * Asocia los listeners a los botones de la vista.
     *
     * @param listener Listener para los eventos de acción.
     */
    public void asociarListeners(ActionListener listener) {
        vva.jButtonBuscarAtletas.addActionListener(listener);
        vva.jButtonAtras.addActionListener(listener);
        vva.jButtonEditar.addActionListener(listener);
        vva.jButtonRegistrar.addActionListener(listener);
        vva.jButtonEliminar.addActionListener(listener);
    }
}
