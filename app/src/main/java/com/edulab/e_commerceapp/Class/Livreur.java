package com.edulab.e_commerceapp.Class;

public  class Livreur {

   public String prenom, nom, email, pass, confpass, phonewhatsapp, type, ville,photopersonnel;

   public Livreur(){

   }

   public Livreur(String prenom, String nom, String email, String pass, String confpass, String phonewhatsapp, String type, String ville, String photopersonnel) {
      this.prenom = prenom;
      this.nom = nom;
      this.email = email;
      this.pass = pass;
      this.confpass = confpass;
      this.phonewhatsapp = phonewhatsapp;
      this.type = type;
      this.ville = ville;
      this.photopersonnel = photopersonnel;
   }
}
