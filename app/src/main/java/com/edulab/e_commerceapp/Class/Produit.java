package com.edulab.e_commerceapp.Class;

public class Produit {

    public String Nom_Entreprise, Nom_Produit, qte_Produit, PrixU_Produit, Photo_Produit,commision;

    public Produit() {
    }

    public Produit(String nom_Entreprise, String nom_Produit, String qte_Produit, String prixU_Produit, String photo_Produit, String commision) {
        Nom_Entreprise = nom_Entreprise;
        Nom_Produit = nom_Produit;
        this.qte_Produit = qte_Produit;
        PrixU_Produit = prixU_Produit;
        Photo_Produit = photo_Produit;
        this.commision = commision;
    }
}
