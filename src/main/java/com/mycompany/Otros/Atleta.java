package com.mycompany.Otros;

/**
 * Clase Atleta que mapea los datos obtenidos de la base de datos mediante AtletaDAO.
 * @author Colorado Jimenez Victor
 */
public class Atleta {
    private int idAtleta;
    private String nombreCompleto;
    private String region;
    private String codigoRegion;
    private int juegosOlimpicosParticipados;
    private int primerJuegoOlimpico;
    private int edadUltimoJuego;
    private int totalParticipaciones;
    private int medallasOro;
    private int medallasPlata;
    private int medallasBronce;
    private int totalMedallas;

    // Getters y setters

    public int getIdAtleta() {
        return idAtleta;
    }

    public void setIdAtleta(int idAtleta) {
        this.idAtleta = idAtleta;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCodigoRegion() {
        return codigoRegion;
    }

    public void setCodigoRegion(String codigoRegion) {
        this.codigoRegion = codigoRegion;
    }

    public int getJuegosOlimpicosParticipados() {
        return juegosOlimpicosParticipados;
    }

    public void setJuegosOlimpicosParticipados(int juegosOlimpicosParticipados) {
        this.juegosOlimpicosParticipados = juegosOlimpicosParticipados;
    }

    public int getPrimerJuegoOlimpico() {
        return primerJuegoOlimpico;
    }

    public void setPrimerJuegoOlimpico(int primerJuegoOlimpico) {
        this.primerJuegoOlimpico = primerJuegoOlimpico;
    }

    public int getEdadUltimoJuego() {
        return edadUltimoJuego;
    }

    public void setEdadUltimoJuego(int edadUltimoJuego) {
        this.edadUltimoJuego = edadUltimoJuego;
    }

    public int getTotalParticipaciones() {
        return totalParticipaciones;
    }

    public void setTotalParticipaciones(int totalParticipaciones) {
        this.totalParticipaciones = totalParticipaciones;
    }

    public int getMedallasOro() {
        return medallasOro;
    }

    public void setMedallasOro(int medallasOro) {
        this.medallasOro = medallasOro;
    }

    public int getMedallasPlata() {
        return medallasPlata;
    }

    public void setMedallasPlata(int medallasPlata) {
        this.medallasPlata = medallasPlata;
    }

    public int getMedallasBronce() {
        return medallasBronce;
    }

    public void setMedallasBronce(int medallasBronce) {
        this.medallasBronce = medallasBronce;
    }

    public int getTotalMedallas() {
        return totalMedallas;
    }

    public void setTotalMedallas(int totalMedallas) {
        this.totalMedallas = totalMedallas;
    }
    
     public void setMedallas(String medallasCadena) {
        if (medallasCadena == null || medallasCadena.isEmpty()) {
            throw new IllegalArgumentException("La cadena de medallas no puede ser nula o vacía.");
        }
        try {
            String[] partes = medallasCadena.split(",");
            this.medallasOro = Integer.parseInt(partes[0].split(":")[1].trim());
            this.medallasPlata = Integer.parseInt(partes[1].split(":")[1].trim());
            this.medallasBronce = Integer.parseInt(partes[2].split(":")[1].trim());
            this.totalMedallas = Integer.parseInt(partes[3].split(":")[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de cadena de medallas inválido: " + medallasCadena, e);
        }
    }
}
