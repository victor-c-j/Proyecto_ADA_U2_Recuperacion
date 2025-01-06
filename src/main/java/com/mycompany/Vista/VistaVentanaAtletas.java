package com.mycompany.Vista;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa la vista para gestionar atletas.
 */
public class VistaVentanaAtletas extends javax.swing.JFrame {

    public VistaVentanaAtletas() {
        initComponents();
        this.setSize(1200, 700); // Cambiar tamaño de la ventana
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Centrar la ventana
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar correctamente
        this.setResizable(false); // Ventana no redimensionable
    }

    /**
     * Método para actualizar la tabla de atletas con nuevos datos.
     *
     * @param datos Lista de arreglos de String que contienen los datos de los atletas.
     */
    public void actualizarTablaAtletas(List<String[]> datos) {
        // Definir los nombres de las columnas
        String[] columnNames = {
            "Nombre Completo", "Región", "Código Región",
            "Juegos Olímpicos Participados", "Primer Juego Olímpico",
            "Edad Último Juego", "Total Participaciones", "Medallas"
        };

        // Crear el modelo de la tabla con los nuevos datos
        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);

        // Añadir las filas al modelo
        for (String[] fila : datos) {
            modelo.addRow(fila);
        }

        // Establecer el modelo en la tabla
        jTableAtletas.setModel(modelo);
    }

    /**
     * Obtiene el texto ingresado en el campo de búsqueda.
     *
     * @return Texto de búsqueda ingresado.
     */
    public String getTextoBusqueda() {
        return jTextFieldAtletas.getText().trim();
    }

    /**
     * Limpia el texto ingresado en el campo de búsqueda.
     */
    public void limpiarTextoBusqueda() {
        jTextFieldAtletas.setText("");
    }

    /**
     * Obtiene el atleta seleccionado de la tabla.
     *
     * @return Un arreglo de Strings con los datos del atleta seleccionado, o null si no hay selección.
     */
    public String[] getAtletaSeleccionado() {
        int filaSeleccionada = jTableAtletas.getSelectedRow();
        if (filaSeleccionada != -1) {
            int columnas = jTableAtletas.getColumnCount();
            String[] datosAtleta = new String[columnas];
            for (int i = 0; i < columnas; i++) {
                datosAtleta[i] = (String) jTableAtletas.getValueAt(filaSeleccionada, i);
            }
            return datosAtleta;
        }
        return null;
    }

    /**
     * Muestra un mensaje al usuario.
     *
     * @param mensaje Mensaje a mostrar.
     */
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    /**
     * Confirma con el usuario si desea realizar una acción específica.
     *
     * @param mensaje Mensaje de confirmación.
     * @return true si el usuario confirma, false de lo contrario.
     */
    public boolean confirmarAccion(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    // Método auto-generado por el diseñador de interfaz gráfica para inicializar componentes
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableAtletas = new javax.swing.JTable();
        jTextFieldAtletas = new javax.swing.JTextField();
        jButtonBuscarAtletas = new javax.swing.JButton();
        jButtonAtras = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonRegistrar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableAtletas.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{},
            new String[]{
                "Nombre Completo", "Región", "Código Región",
                "Juegos Olímpicos Participados", "Primer Juego Olímpico",
                "Edad Último Juego", "Total Participaciones", "Medallas"
            }
        ));
        jTableAtletas.setRowHeight(30); // Altura de las filas para mejor visibilidad
        jScrollPane1.setViewportView(jTableAtletas);

        jButtonBuscarAtletas.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonBuscarAtletas.setText("Buscar");

        jButtonAtras.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonAtras.setText("Atrás");
        jButtonAtras.setActionCommand("Atras");

        jButtonEditar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonEditar.setText("Editar");

        jButtonRegistrar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonRegistrar.setText("Registrar");

        jButtonEliminar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonEliminar.setText("Eliminar");

        jTextFieldAtletas.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1140, javax.swing.GroupLayout.PREFERRED_SIZE) // Tabla expandida
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jTextFieldAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE) // Campo de búsqueda más grande
                            .addGap(30, 30, 30)
                            .addComponent(jButtonBuscarAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButtonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jButtonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE) // Altura del campo de búsqueda
                        .addComponent(jButtonBuscarAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonAtras, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(30, 30, 30)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE) // Altura de la tabla
                    .addGap(30, 30, 30))
        );

        pack();
    }

    // Variables de la interfaz gráfica
    public javax.swing.JButton jButtonAtras;
    public javax.swing.JButton jButtonBuscarAtletas;
    public javax.swing.JButton jButtonEditar;
    public javax.swing.JButton jButtonEliminar;
    public javax.swing.JButton jButtonRegistrar;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableAtletas;
    public javax.swing.JTextField jTextFieldAtletas;
}
