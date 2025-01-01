package com.mycompany.Vista;

import com.mycompany.Modelo.DeporteDAO;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Colorado Jimenez Victor
 */
public class VistaVentanaDeportes extends javax.swing.JFrame {

    DeporteDAO d1;

    public VistaVentanaDeportes(DeporteDAO d1) {
        initComponents();
        this.d1 = d1;
        this.setVisible(true);
        this.setLocationRelativeTo(null);// Hacemos que esta ventana se genere en el medio de nuestra pantalla.
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);// Define que cuando se pulse el botón con la X en rojo, se cierre la ventana y se libere en memoria.
        this.setResizable(false);// La ventana mantendrá un tamaño fijo.
        cargarDatosDeportes();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDeportes = new javax.swing.JTable();
        jTextFieldDeportes = new javax.swing.JTextField();
        jButtonBuscarDeportes = new javax.swing.JButton();
        jButtonAtras = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonRegistrar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableDeportes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null}
                },
                new String[]{
                    "ID Deporte", "Nombre Deporte"
                }
        ));
        jScrollPane1.setViewportView(jTableDeportes);

        jButtonBuscarDeportes.setText("Buscar");

        jButtonAtras.setText("Atrás");
        jButtonAtras.setActionCommand("Atras");

        jButtonEditar.setText("Editar");

        jButtonRegistrar.setText("Registrar");

        jButtonEliminar.setText("Eliminar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jTextFieldDeportes)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButtonBuscarDeportes)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButtonEditar)
                                                .addGap(26, 26, 26)
                                                .addComponent(jButtonRegistrar)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButtonEliminar)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButtonAtras)))
                                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(28, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextFieldDeportes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonBuscarDeportes)
                                        .addComponent(jButtonAtras)
                                        .addComponent(jButtonEditar)
                                        .addComponent(jButtonRegistrar)
                                        .addComponent(jButtonEliminar))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    // Método para cargar los datos de los deportes
    public void cargarDatosDeportes() {
        List<String[]> deportes = d1.obtenerDeportes(); // Suponiendo que devuelve una lista de arreglos con id_deporte y nombre

        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Deporte");
        modelo.addColumn("Nombre Deporte");

        // Llenar la tabla con los datos
        for (String[] deporte : deportes) {
            modelo.addRow(deporte);
        }

        // Asignar el modelo de la tabla a jTableDeportes
        jTableDeportes.setModel(modelo);
    }

    // Método para filtrar los datos en la tabla según el texto de búsqueda
    public void filtrarTabla(String busqueda) {
        List<String[]> deportes = d1.obtenerDeportes();
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Deporte");
        modelo.addColumn("Nombre Deporte");

        // Filtrar los datos según la búsqueda
        for (String[] deporte : deportes) {
            if (deporte[1].toLowerCase().contains(busqueda.toLowerCase())) { // Buscar solo en el nombre
                modelo.addRow(deporte);
            }
        }

        // Asignar el modelo de la tabla filtrada
        jTableDeportes.setModel(modelo);
    }

    public String[] getDeporteSeleccionado() {
        int filaSeleccionada = jTableDeportes.getSelectedRow();
        if (filaSeleccionada != -1) { // Verificar que hay una fila seleccionada
            String idDeporte = jTableDeportes.getValueAt(filaSeleccionada, 0).toString();
            String nombreDeporte = jTableDeportes.getValueAt(filaSeleccionada, 1).toString();
            return new String[]{idDeporte, nombreDeporte}; // Devolver ID y nombre como array
        } else {
            return null; // No hay selección
        }
    }

    // Variables declaration - do not modify                     
    public javax.swing.JButton jButtonAtras;
    public javax.swing.JButton jButtonBuscarDeportes;
    public javax.swing.JButton jButtonEditar;
    public javax.swing.JButton jButtonEliminar;
    public javax.swing.JButton jButtonRegistrar;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTableDeportes;
    public javax.swing.JTextField jTextFieldDeportes;
    // End of variables declaration                   
}
