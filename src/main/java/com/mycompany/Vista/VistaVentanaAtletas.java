package com.mycompany.Vista;

import com.mycompany.Otros.Atleta;
import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class VistaVentanaAtletas extends javax.swing.JFrame {

    public VistaVentanaAtletas() {
        initComponents();
        this.setSize(1200, 700); // Cambiar tamaño de la ventana
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Centrar la ventana
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar correctamente
        this.setResizable(false); // Ventana no redimensionable
    }

    public void actualizarTablaAtletas(List<Atleta> atletas) {
        String[] columnNames = {
                "Nombre Completo", "Región", "Código Región",
                "Juegos Olímpicos Participados", "Primer Juego Olímpico",
                "Oro", "Plata", "Bronce", "Total Medallas"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnNames, 0);

        for (Atleta atleta : atletas) {
            modelo.addRow(new Object[]{
                    atleta.getNombreCompleto(),
                    atleta.getRegion(),
                    atleta.getCodigoRegion(),
                    atleta.getJuegosOlimpicosParticipados(),
                    atleta.getPrimerJuegoOlimpico(),
                    atleta.getOro(),
                    atleta.getPlata(),
                    atleta.getBronce(),
                    atleta.getTotalMedallas()
            });
        }

        jTableAtletas.setModel(modelo);
    }

    public Atleta getAtletaSeleccionado() {
        int filaSeleccionada = jTableAtletas.getSelectedRow();
        if (filaSeleccionada != -1) {
            return new Atleta(
                    (String) jTableAtletas.getValueAt(filaSeleccionada, 0), // Nombre completo
                    (String) jTableAtletas.getValueAt(filaSeleccionada, 1), // Región
                    (String) jTableAtletas.getValueAt(filaSeleccionada, 2), // Código región
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 3), // Juegos Olímpicos Participados
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 4), // Primer Juego Olímpico
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 5), // Oro
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 6), // Plata
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 7), // Bronce
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 8)  // Total Medallas
            );
        }
        return null;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public boolean confirmarAccion(String mensaje) {
        int opcion = JOptionPane.showConfirmDialog(this, mensaje, "Confirmación", JOptionPane.YES_NO_OPTION);
        return opcion == JOptionPane.YES_OPTION;
    }

    public String getTextoBusqueda() {
        return jTextFieldAtletas.getText().trim();
    }

    public void limpiarTextoBusqueda() {
        jTextFieldAtletas.setText("");
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
        setTitle("Gestión de Atletas");

        // Establece un Layout adecuado si es necesario
        this.setLayout(new BorderLayout()); // O cualquier otro Layout adecuado para tus necesidades
        jTableAtletas.setRowHeight(30); // Altura de las filas para mejor visibilidad
        jScrollPane1.setViewportView(jTableAtletas);

        jButtonBuscarAtletas.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonBuscarAtletas.setText("Buscar");

        jButtonAtras.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonAtras.setText("Atrás");

        jButtonEditar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonEditar.setText("Editar");

        jButtonRegistrar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonRegistrar.setText("Registrar");

        jButtonEliminar.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande
        jButtonEliminar.setText("Eliminar");

        jTextFieldAtletas.setFont(new java.awt.Font("Arial", 1, 18)); // Texto más grande

        // Aquí puedes mantener el diseño auto-generado del resto de componentes
        // Agregar componentes al contenedor
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(jScrollPane1, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(jButtonBuscarAtletas);
        botonesPanel.add(jButtonAtras);
        botonesPanel.add(jButtonEditar);
        botonesPanel.add(jButtonRegistrar);
        botonesPanel.add(jButtonEliminar);
        panel.add(botonesPanel, BorderLayout.SOUTH);

        this.add(panel); // Agrega el panel a la ventana principal
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
