package com.mycompany.Vista;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author usuario
 */
public class VistaVentanaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form VistaVentanaPrincipal
     */
    public VistaVentanaPrincipal() {
        initComponents();
        this.setSize(1200, 700); // Cambiar tamaño de la ventana
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Ventana centrada en pantalla
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Liberar memoria al cerrar
        this.setResizable(false); // Evitar redimensionado

        // Configurar imagen de fondo en jLabelFondo
        setFondo();
    }

    /**
     * Método para configurar el fondo de jLabelFondo.
     */
    private void setFondo() {
        // Cargar la imagen original
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/Logo.png"));

        // Redimensionar la imagen para que se ajuste al tamaño deseado
        int width = 800; // Ancho deseado (proporcional al diseño)
        int height = 400; // Altura deseada
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon con la imagen escalada
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Asignar la imagen escalada al JLabel
        jLabelFondo.setIcon(scaledIcon);
        jLabelFondo.setText(""); // Eliminar texto del JLabel para mostrar solo la imagen
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabelBienvenida = new javax.swing.JLabel();
        jButtonDeportes = new javax.swing.JButton();
        jButtonAtletas = new javax.swing.JButton();
        jButtonJuegos = new javax.swing.JButton();
        jButtonMedallero = new javax.swing.JButton();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelBienvenida.setFont(new java.awt.Font("Arial", 1, 36)); // Texto más grande
        jLabelBienvenida.setText("Bienvenido");

        jButtonDeportes.setFont(new java.awt.Font("Arial", 1, 24)); // Botón más grande
        jButtonDeportes.setText("Deportes");

        jButtonAtletas.setFont(new java.awt.Font("Arial", 1, 24)); // Botón más grande
        jButtonAtletas.setText("Atletas");

        jButtonJuegos.setFont(new java.awt.Font("Arial", 1, 24)); // Botón más grande
        jButtonJuegos.setText("Juegos");

        jButtonMedallero.setFont(new java.awt.Font("Arial", 1, 24)); // Botón más grande
        jButtonMedallero.setText("Medallero");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(500, 500, 500) // Centrar el texto de bienvenida
                                                .addComponent(jLabelBienvenida))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(200, 200, 200) // Ajustar posición horizontal de los botones
                                                .addComponent(jButtonAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50) // Espacio entre botones
                                                .addComponent(jButtonDeportes, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addComponent(jButtonJuegos, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(50, 50, 50)
                                                .addComponent(jButtonMedallero, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(200, 200, 200) // Centrar la imagen
                                                .addComponent(jLabelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))) // Tamaño ajustado del JLabel
                                .addContainerGap(200, Short.MAX_VALUE)) // Bordes laterales
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30) // Espaciado inicial
                                .addComponent(jLabelBienvenida)
                                .addGap(50, 50, 50) // Espacio entre título y botones
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButtonAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonDeportes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonJuegos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonMedallero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(50, 50, 50) // Espacio entre botones y la imagen
                                .addComponent(jLabelFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE) // Tamaño del JLabel para la imagen
                                .addContainerGap(70, Short.MAX_VALUE)) // Espacio inferior
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify                     
    public javax.swing.JButton jButtonAtletas;
    public javax.swing.JButton jButtonDeportes;
    public javax.swing.JButton jButtonJuegos;
    public javax.swing.JButton jButtonMedallero;
    private javax.swing.JLabel jLabelBienvenida;
    private javax.swing.JLabel jLabelFondo;
    // End of variables declaration                   
}
