package com.mycompany.Vista;

import com.mycompany.Otros.Atleta;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
            modelo.addRow(new Object[] {
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
                    (Integer) jTableAtletas.getValueAt(filaSeleccionada, 8) // Total Medallas
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
    
        // Establecer el Layout principal de la ventana
        this.setLayout(new BorderLayout());
    
        // Configuración de la tabla
        jTableAtletas.setRowHeight(30);
    
        // Pintar el encabezado de la tabla de un color azulado
        jTableAtletas.getTableHeader().setBackground(new java.awt.Color(173, 216, 230)); // Color azulado claro
    
        jScrollPane1.setViewportView(jTableAtletas);
    
        // Configuración de los botones
        jButtonBuscarAtletas.setFont(new java.awt.Font("Arial", 1, 18));
        jButtonBuscarAtletas.setText("Buscar");
        jButtonBuscarAtletas.setBackground(java.awt.Color.BLACK); // Fondo negro
        jButtonBuscarAtletas.setForeground(java.awt.Color.WHITE); // Texto blanco
    
        jButtonAtras.setFont(new java.awt.Font("Arial", 1, 18));
        jButtonAtras.setText("Atrás");
        jButtonAtras.setBackground(java.awt.Color.BLACK); // Fondo negro
        jButtonAtras.setForeground(java.awt.Color.WHITE); // Texto blanco
    
        jButtonEditar.setFont(new java.awt.Font("Arial", 1, 18));
        jButtonEditar.setText("Editar");
        jButtonEditar.setBackground(java.awt.Color.BLACK); // Fondo negro
        jButtonEditar.setForeground(java.awt.Color.WHITE); // Texto blanco
    
        jButtonRegistrar.setFont(new java.awt.Font("Arial", 1, 18));
        jButtonRegistrar.setText("Registrar");
        jButtonRegistrar.setBackground(java.awt.Color.BLACK); // Fondo negro
        jButtonRegistrar.setForeground(java.awt.Color.WHITE); // Texto blanco
    
        jButtonEliminar.setFont(new java.awt.Font("Arial", 1, 18));
        jButtonEliminar.setText("Eliminar");
        jButtonEliminar.setBackground(java.awt.Color.BLACK); // Fondo negro
        jButtonEliminar.setForeground(java.awt.Color.WHITE); // Texto blanco
    
        jTextFieldAtletas.setFont(new java.awt.Font("Arial", 1, 18));
    
        // Crear un panel con FlowLayout para alinear todos los elementos horizontalmente
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.LEFT)); // Alinea todos los componentes a la izquierda
    
        // Añadir el JTextField a la izquierda
        jTextFieldAtletas.setPreferredSize(new java.awt.Dimension(500, 40)); // Tamaño del JTextField
        panelInferior.add(jTextFieldAtletas);
    
        // Añadir el botón de búsqueda
        panelInferior.add(jButtonBuscarAtletas);
    
        // Añadir los otros botones
        panelInferior.add(jButtonAtras);
        panelInferior.add(jButtonEditar);
        panelInferior.add(jButtonRegistrar);
        panelInferior.add(jButtonEliminar);
    
        // Añadir el panel inferior a la ventana
        this.add(jScrollPane1, BorderLayout.CENTER); // La tabla en el centro
        this.add(panelInferior, BorderLayout.SOUTH); // El panel con todos los componentes alineados en la parte inferior
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
