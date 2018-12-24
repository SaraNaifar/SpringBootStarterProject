package com.ecommerce.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("MonFiltreDynamique")
public class Product {
    private int id ;
    private String nom;
    private int prix;
    private int prixAchat;

    //Constructeur sans argument
    public Product() {
    }
    //Constructeur pour les test
    public Product(int id , String nom, int prix, int prixAchat){
        this.id=id;
        this.nom=nom;
        this.prix=prix;
        this.prixAchat=prixAchat;
    }
    //Setters et Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prix=" + prix +
                '}';
    }
}
