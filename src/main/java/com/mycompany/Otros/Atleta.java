package com.mycompany.Otros;

public class Atleta {
    private String nombreCompleto;
    private String region;
    private String codigoRegion;
    private int juegosOlimpicosParticipados;
    private int primerJuegoOlimpico;
    private int oro;
    private int plata;
    private int bronce;
    private int totalMedallas;
    
    private String genero;
    private float altura;
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    // Constructor por defecto
    public Atleta() {

    }

    //Constructor con todos los parámetros
    public Atleta(String nombreCompleto, String region, String codigoRegion, int juegosOlimpicosParticipados, 
                  int primerJuegoOlimpico, int oro, int plata, int bronce, int totalMedallas) {
        this.nombreCompleto = nombreCompleto;
        this.region = region;
        this.codigoRegion = codigoRegion;
        this.juegosOlimpicosParticipados = juegosOlimpicosParticipados;
        this.primerJuegoOlimpico = primerJuegoOlimpico;
        this.oro = oro;
        this.plata = plata;
        this.bronce = bronce;
        this.totalMedallas = totalMedallas;
    }

    // Getters y Setters
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

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public int getPlata() {
        return plata;
    }

    public void setPlata(int plata) {
        this.plata = plata;
    }

    public int getBronce() {
        return bronce;
    }

    public void setBronce(int bronce) {
        this.bronce = bronce;
    }

    public int getTotalMedallas() {
        return totalMedallas;
    }

    public void setTotalMedallas(int totalMedallas) {
        this.totalMedallas = totalMedallas;
    }

    @Override
    public String toString() {
        return "Atleta{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", region='" + region + '\'' +
                ", codigoRegion='" + codigoRegion + '\'' +
                ", juegosOlimpicosParticipados=" + juegosOlimpicosParticipados +
                ", primerJuegoOlimpico=" + primerJuegoOlimpico +
                ", oro=" + oro +
                ", plata=" + plata +
                ", bronce=" + bronce +
                ", totalMedallas=" + totalMedallas +
                '}';
    }
}
