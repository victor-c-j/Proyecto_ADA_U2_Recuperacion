package com.mycompany.Controlador;

import com.mycompany.Otros.Atleta;
import com.mycompany.Modelo.AtletaDAO;
import com.mycompany.Modelo.Conexion;
import com.mycompany.Vista.VistaVentanaAtletas;
import com.mycompany.Vista.VistaVentanaPrincipal;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
                pedirValoracionAntesDeSalir();
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
            List<Atleta> atletasFiltrados = atletaDAO.obtenerAtletasPorNombre(busqueda); // Cambia esto según tus necesidades
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
            Atleta atletaAux = atletaDAO.obtenerAtletaPorNombre(atletaSeleccionado.getNombreCompleto());
            
            try {
                float nuevaAltura = Float.parseFloat(nuevoAlturaStr);
                atletaSeleccionado.setNombreCompleto(nuevoNombre);
                atletaSeleccionado.setGenero(nuevoGenero);
                atletaSeleccionado.setAltura(nuevaAltura);
                atletaSeleccionado.setId(atletaAux.getId());

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
        try {
            // Solicitamos la información del atleta
            String nombre = JOptionPane.showInputDialog(vva, "Nombre Completo:");
            if (nombre == null || nombre.trim().isEmpty()) {
                vva.mostrarMensaje("Error: El nombre completo es obligatorio.");
                return; // Salimos del método si el campo está vacío
            }
    
            String genero = JOptionPane.showInputDialog(vva, "Género:");
            if (genero == null || genero.trim().isEmpty()) {
                vva.mostrarMensaje("Error: El género es obligatorio.");
                return; // Salimos del método si el campo está vacío
            }
    
            String alturaStr = JOptionPane.showInputDialog(vva, "Altura (en metros):");
            if (alturaStr == null || alturaStr.trim().isEmpty()) {
                vva.mostrarMensaje("Error: La altura es obligatoria.");
                return; // Salimos del método si el campo está vacío
            }
    
            String nombreRegion = JOptionPane.showInputDialog(vva, "Nombre región:");
            if (nombreRegion == null || nombreRegion.trim().isEmpty()) {
                vva.mostrarMensaje("Error: El nombre de la región es obligatorio.");
                return; // Salimos del método si el campo está vacío
            }
    
            String noc = JOptionPane.showInputDialog(vva, "Abreviatura (noc) región:");
            if (noc == null || noc.trim().isEmpty()) {
                vva.mostrarMensaje("Error: La abreviatura de la región (noc) es obligatoria.");
                return; // Salimos del método si el campo está vacío
            }
    
            // Generamos el id de la región
            int idRegion = atletaDAO.ultimoIdRegion() + 1;
    
            // Intentamos convertir la altura a float
            float altura = Float.parseFloat(alturaStr);
    
            // Insertamos el atleta en la base de datos
            atletaDAO.insertarAtleta(nombre, genero, altura, nombreRegion, noc, idRegion);
            cargarDatosAtletas();
            vva.mostrarMensaje("Atleta registrado con éxito.");
    
        } catch (NumberFormatException e) {
            vva.mostrarMensaje("Error: Altura no válida.");
        } catch (NullPointerException e) {
            vva.mostrarMensaje("Error: No se seleccionó ningún valor en los campos.");
        } catch (Exception e) {
            vva.mostrarMensaje("Ocurrió un error inesperado: " + e.getMessage());
        }
    }
    
    /**
     * Método que muestra la encuesta de satisfacción antes de cerrar la ventana.
     */
    private void pedirValoracionAntesDeSalir() {
        int opcion = JOptionPane.showOptionDialog(vva, "¿Te gustaría valorar la aplicación?", "Valoración",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (opcion == JOptionPane.YES_OPTION) {
            // Pedir la puntuación y el comentario
            String puntuacionStr = JOptionPane.showInputDialog(vva, "Puntuación (1-5):");
            String comentario = JOptionPane.showInputDialog(vva, "Comentario (200 caracteres máximo):");

            try {
                int puntuacion = Integer.parseInt(puntuacionStr);
                if (puntuacion < 1 || puntuacion > 5) {
                    vva.mostrarMensaje("Error: La puntuación debe estar entre 1 y 5.");
                    return;
                }

                if (comentario.length() > 200) {
                    vva.mostrarMensaje("Error: El comentario debe tener máximo 200 caracteres.");
                    return;
                }

                // Guardar la valoración en un archivo
                guardarValoracion(puntuacion, comentario);
            } catch (NumberFormatException e) {
                vva.mostrarMensaje("Error: La puntuación debe ser un número válido.");
            }

            // Proceder a cerrar la ventana
            VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
            ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
            vva.dispose();
        } else {
            // Si el usuario elige "Más tarde", simplemente cerrar la ventana
            VistaVentanaPrincipal vvp = new VistaVentanaPrincipal();
            ControladorVentanaPrincipal cvp = new ControladorVentanaPrincipal(vvp, c);
            vva.dispose();
        }
    }

    /**
     * Guarda la valoración en un archivo txt.
     */
    private void guardarValoracion(int puntuacion, String comentario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("valoraciones.txt", true))) {
            writer.write("Puntuación: " + puntuacion + "/5\n");
            writer.write("Comentario: " + comentario + "\n");
            writer.write("------------\n");
        } catch (IOException e) {
            vva.mostrarMensaje("Error al guardar la valoración.");
        }
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
