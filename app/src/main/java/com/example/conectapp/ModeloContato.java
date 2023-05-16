package com.example.conectapp;

public class ModeloContato {
    private String id, foto, nome, numero, email, bio, adicionadoTimeStamp, atualizadoTimeStamp;

    public ModeloContato(String id, String foto, String nome, String numero, String email, String bio, String adicionadoTimeStamp, String atualizadoTimeStamp) {
        this.id = id;
        this.foto = foto;
        this.nome = nome;
        this.numero = numero;
        this.email = email;
        this.bio = bio;
        this.adicionadoTimeStamp = adicionadoTimeStamp;
        this.atualizadoTimeStamp = atualizadoTimeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAdicionadoTimeStamp() {
        return adicionadoTimeStamp;
    }

    public void setAdicionadoTimeStamp(String adicionadoTimeStamp) {
        this.adicionadoTimeStamp = adicionadoTimeStamp;
    }

    public String getAtualizadoTimeStamp() {
        return atualizadoTimeStamp;
    }

    public void setAtualizadoTimeStamp(String atualizadoTimeStamp) {
        this.atualizadoTimeStamp = atualizadoTimeStamp;
    }
}
