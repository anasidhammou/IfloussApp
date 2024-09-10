package com.edulab.e_commerceapp.Class;

public class User {

    public String prenom, nom, email, pass, confpass, phonewhatsapp, type;

    public User(){

    }

    public User(String prenom, String nom, String email, String pass, String confpass, String phonewhatsapp , String type) {
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.pass = pass;
        this.confpass = confpass;
        this.phonewhatsapp = phonewhatsapp;
        this.type = type;
    }
}
