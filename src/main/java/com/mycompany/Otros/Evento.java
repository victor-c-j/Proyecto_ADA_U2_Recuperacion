package com.mycompany.Otros;

/**
 *
 * @author Colorado Jimenez Victor
 */
public class Evento {
    private int idEvento;
    private int idDeporte;
    private int idJuegoOlimpico;
    private String nombreEvento;

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
}
