package com.edulab.e_commerceapp.Class;

public class commande {

   public String nomProduit, quantité, typePayement, villeLivraison, Total, etat;


   public commande(String nomProduit, String quantité, String typePayement, String villeLivraison, String total, String etat) {
      this.nomProduit = nomProduit;
      this.quantité = quantité;
      this.typePayement = typePayement;
      this.villeLivraison = villeLivraison;
      Total = total;
      this.etat = etat;
   }
}
