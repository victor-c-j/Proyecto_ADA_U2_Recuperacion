package com.mycompany.Otros;

/**
 *
 * @author Colorado Jimenez Victor
 */

public class JuegoOlimpico {
    private int idJuegoOlimpico;
    private int idCiudad;
    private String nombreJuegos;
    private int agnoCelebracion;
    private String estacion;

    public int getIdJuegoOlimpico() {
        return idJuegoOlimpico;
    }

    public void setIdJuegoOlimpico(int idJuegoOlimpico) {
        this.idJuegoOlimpico = idJuegoOlimpico;
    }

    public String getNombreJuegos() {
        return nombreJuegos;
    }

    public void setNombreJuegos(String nombreJuegos) {
        this.nombreJuegos = nombreJuegos;
    }

    public int getAgnoCelebracion() {
        return agnoCelebracion;
    }

    public void setAgnoCelebracion(int agnoCelebracion) {
        this.agnoCelebracion = agnoCelebracion;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }
}
