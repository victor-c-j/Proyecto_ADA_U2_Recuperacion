package com.mycompany.Modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TablaAbstracta extends AbstractTableModel {
    private List<String[]> data;
    private List<String> columns;  // Cambié List<String[]> a List<String> para las columnas

    public TablaAbstracta(List<String[]> data, List<String> columns) {
        this.data = data;
        this.columns = columns;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column);  // Ahora retorna el nombre de la columna como un String
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }
}
