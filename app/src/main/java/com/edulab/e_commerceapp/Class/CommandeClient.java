package com.edulab.e_commerceapp.Class;

public class CommandeClient {

   public String Nom_Entreprise, Nom_Produitn,Photo_Produit, Total, qte, Nom_Client, Phone_Client, Adresse_Client, Comment,Ville , etat, idClient,typepaiement;

   public CommandeClient() {
   }

   public CommandeClient(String nom_Entreprise, String nom_Produitn, String photo_Produit, String total, String qte, String nom_Client, String phone_Client, String adresse_Client, String comment, String ville, String etat, String idClient, String typepaiement) {
      Nom_Entreprise = nom_Entreprise;
      Nom_Produitn = nom_Produitn;
      Photo_Produit = photo_Produit;
      Total = total;
      this.qte = qte;
      Nom_Client = nom_Client;
      Phone_Client = phone_Client;
      Adresse_Client = adresse_Client;
      Comment = comment;
      Ville = ville;
      this.etat = etat;
      this.idClient = idClient;
      this.typepaiement = typepaiement;
   }
}
