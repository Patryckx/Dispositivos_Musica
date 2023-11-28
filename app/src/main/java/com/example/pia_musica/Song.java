package com.example.pia_musica;

public class Song {
    private String nombreCancion;
    private String banda;
    private int ano;

    public Song() {
        // Constructor vacío requerido para Firebase
    }

    public Song(String nombreCancion, String banda, int ano) {
        this.nombreCancion = nombreCancion;
        this.banda = banda;
        this.ano = ano;
    }

    public String getNombreCancion() {
        return nombreCancion;
    }

    public String getBanda() {
        return banda;
    }

    public int getAno() {
        return ano;
    }
}