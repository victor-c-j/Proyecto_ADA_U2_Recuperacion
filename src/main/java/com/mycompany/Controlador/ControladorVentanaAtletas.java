package com.mycompany.Controlador;

import com.mycompany.Otros.Atleta;
import com.mycompany.Modelo.AtletaDAO;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.*;
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

            case "Atras":
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
        List<String[]> datos = atletaDAO.obtenerAtletas();
        vva.actualizarTablaAtletas(datos);
    }

    /**
     * Filtra los atletas con base en el texto ingresado en el campo de búsqueda.
     */
    private void filtrarAtletas() {
        String busqueda = vva.getTextoBusqueda();
        if (!busqueda.isEmpty()) {
            List<String[]> datosFiltrados = atletaDAO.obtenerDatosAtletasConsultaConFiltro(busqueda);
            vva.actualizarTablaAtletas(datosFiltrados);
        } else {
            cargarDatosAtletas();
        }
    }

    /**
     * Elimina el atleta seleccionado de la tabla después de confirmar la acción.
     */
    private void eliminarAtleta() {
        String[] atletaSeleccionado = vva.getAtletaSeleccionado();
        if (atletaSeleccionado != null && atletaSeleccionado.length > 0) {
            String nombreAtleta = atletaSeleccionado[0];  // Suponiendo que el nombre está en la primera columna
            int idAtleta = atletaDAO.obtenerIdAtletaPorNombre(nombreAtleta);  // Obtener el ID por el nombre
            if (idAtleta != -1) {
                boolean confirmado = vva.confirmarAccion("¿Está seguro de que desea eliminar al atleta " + nombreAtleta + "?");
                if (confirmado) {
                    atletaDAO.eliminarAtletaConTransaccion(idAtleta);
                    cargarDatosAtletas();
                    vva.mostrarMensaje("Atleta eliminado con éxito.");
                }
            } else {
                vva.mostrarMensaje("Error: El atleta no se encuentra.");
            }
        } else {
            vva.mostrarMensaje("Por favor, seleccione un atleta.");
        }
    }
    

    /**
     * Permite editar los datos del atleta seleccionado.
     */
/**
 * Permite editar los datos del atleta seleccionado.
 */
private void editarAtleta() {
    String[] atletaSeleccionado = vva.getAtletaSeleccionado();
    if (atletaSeleccionado != null && atletaSeleccionado.length > 0) {
        String nombreAtleta = atletaSeleccionado[0];  // Nombre del atleta
        // Aquí ya no usamos las otras variables de la consulta ya que solo nos interesa el ID, nombre, género y altura
       
        // Obtener el ID del atleta
        int idAtleta = atletaDAO.obtenerIdAtletaPorNombre(nombreAtleta);
        
        if (idAtleta != -1) {
            // Pedir los nuevos datos al usuario
            String nuevoNombre = JOptionPane.showInputDialog(vva, "Nuevo nombre completo:", nombreAtleta);
            String nuevoGenero = JOptionPane.showInputDialog(vva, "Nuevo género:", "Masculino"); // Asumiendo un valor por defecto
            String nuevoAlturaStr = JOptionPane.showInputDialog(vva, "Nueva altura:", "0.0");
            Float nuevaAltura = null;
        
            // Validar que la altura sea un valor numérico
            try {
                nuevaAltura = Float.parseFloat(nuevoAlturaStr);
            } catch (NumberFormatException e) {
                vva.mostrarMensaje("La altura debe ser un número válido.");
                return;
            }
            
            // Validación de los datos ingresados
            if (nuevoNombre != null && nuevoGenero != null && nuevaAltura != null) {
                // Actualizar solo los campos requeridos: idAtleta, nombreCompleto, genero, altura
                boolean actualizoExitosamente = atletaDAO.actualizarAtleta(idAtleta, nuevoNombre, nuevoGenero, nuevaAltura);
                
                if (actualizoExitosamente) {
                    cargarDatosAtletas();
                    vva.mostrarMensaje("Atleta actualizado con éxito.");
                } else {
                    vva.mostrarMensaje("Error: No se pudo actualizar el atleta.");
                }
            } else {
                vva.mostrarMensaje("Los campos no pueden estar vacíos.");
            }
        } else {
            vva.mostrarMensaje("Error: El atleta no se encuentra.");
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

        // Validación de los campos de entrada
        if (nombre != null && genero != null && alturaStr != null) {
            try {
                float altura = Float.parseFloat(alturaStr);
                atletaDAO.insertarAtleta(nombre, genero, altura);
                cargarDatosAtletas();
                vva.mostrarMensaje("Atleta registrado con éxito.");
            } catch (NumberFormatException e) {
                vva.mostrarMensaje("Error: Altura no válida.");
            }
        } else {
            vva.mostrarMensaje("Todos los campos son obligatorios.");
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
