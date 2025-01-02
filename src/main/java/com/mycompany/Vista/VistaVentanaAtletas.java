package com.mycompany.Vista;

import com.mycompany.Modelo.AtletaDAO;
import com.mycompany.Modelo.TablaAbstracta;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VistaVentanaAtletas extends javax.swing.JFrame {

    AtletaDAO a1;

    public VistaVentanaAtletas(AtletaDAO a1) {
        initComponents();
        this.a1 = a1;
        this.setVisible(true);
        this.setLocationRelativeTo(null); // Centrar la ventana
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cerrar correctamente
        this.setResizable(false); // Ventana no redimensionable

        // Cargar los datos de la consulta en la tabla
        cargarDatosAtletas();
    }

    // Método para cargar los datos de la consulta a la tabla
    public void cargarDatosAtletas() {
        // Realiza la consulta para obtener los datos
       List<String[]> atletas = a1.obtenerAtletas();// Método que ejecuta la consulta SQL
        
        // Definir los nombres de las columnas
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Atleta");
        columnNames.add("Región");
        columnNames.add("Código Región");
        columnNames.add("Juegos Olímpicos Participados");
        columnNames.add("Primer Juego Olímpico");
        columnNames.add("Edad en el Último Juego");
        columnNames.add("Total de Participaciones");
        columnNames.add("Oro");
        columnNames.add("Plata");
        columnNames.add("Bronce");
        columnNames.add("Total Medallas");

        // Crear el objeto TablaAbstracta con los datos y las columnas
        TablaAbstracta tablaModelo = new TablaAbstracta(atletas, columnNames);
        
        // Asignar el modelo de la tabla a jTableAtletas
        jTableAtletas.setModel(tablaModelo);
    }

    // Método para filtrar los datos en la tabla según el texto de búsqueda
    public void filtrarTabla(String busqueda) {
        // Llamar al método de consulta con filtro de búsqueda
        List<String[]> atletas = a1.obtenerDatosAtletasConsultaConFiltro(busqueda);
        
        // Nombres de las columnas
        List<String> columnNames = new ArrayList<>();
        columnNames.add("Atleta");
        columnNames.add("Región");
        columnNames.add("Código Región");
        columnNames.add("Juegos Olímpicos Participados");
        columnNames.add("Primer Juego Olímpico");
        columnNames.add("Edad en el Último Juego");
        columnNames.add("Total de Participaciones");
        columnNames.add("Oro");
        columnNames.add("Plata");
        columnNames.add("Bronce");
        columnNames.add("Total Medallas");

        // Crear el modelo de tabla con los datos filtrados
        TablaAbstracta tablaModelo = new TablaAbstracta(atletas, columnNames);

        // Asignar el modelo filtrado a la tabla
        jTableAtletas.setModel(tablaModelo);
    }

    // Método para obtener el atleta seleccionado de la tabla
    public String[] getAtletaSeleccionado() {
        int filaSeleccionada = jTableAtletas.getSelectedRow();
        if (filaSeleccionada != -1) {
            String atleta = (String) jTableAtletas.getValueAt(filaSeleccionada, 0);
            String region = (String) jTableAtletas.getValueAt(filaSeleccionada, 1);
            String codigoRegion = (String) jTableAtletas.getValueAt(filaSeleccionada, 2);
            return new String[]{atleta, region, codigoRegion};  // Retorna solo el nombre del atleta y región como ejemplo
        }
        return null;
    }

    // Método para inicializar componentes del JFrame
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
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableAtletas);

        jButtonBuscarAtletas.setText("Buscar");

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
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonBuscarAtletas)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonEditar)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonRegistrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAtras)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAtletas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscarAtletas)
                    .addComponent(jButtonAtras)
                    .addComponent(jButtonEditar)
                    .addComponent(jButtonRegistrar)
                    .addComponent(jButtonEliminar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
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
