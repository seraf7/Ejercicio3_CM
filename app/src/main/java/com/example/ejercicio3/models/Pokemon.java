package com.example.ejercicio3.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Pokemon implements Serializable {
    private long id;
    private String nombre;
    private String url;
    private  String urlImg;
    private int expBase;
    private int altura;
    private int peso;
    private ArrayList<String> tipo;

    //Constructor generico
    public Pokemon(long id, String nombre, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.tipo = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public int getExpBase() {
        return expBase;
    }

    public void setExpBase(int expBase) {
        this.expBase = expBase;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getTipo(int i) {
        if(i < tipo.size()){
            return tipo.get(i);
        }
        return "";
    }

    public int getTipos(){
        return this.tipo.size();
    }

    public String getTipo(){
        String r = "";
        for(int i=0; i< tipo.size(); i++){
            r += " " + this.getTipo(i);
        }
        return r;
    }

    public void addTipo(String tipo) {
        this.tipo.add(tipo);
    }
}
