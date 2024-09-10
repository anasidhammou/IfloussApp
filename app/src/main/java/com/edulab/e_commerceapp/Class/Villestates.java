package com.edulab.e_commerceapp.Class;

public class Villestates {

   public String photo;
   public String nomville;
   public String nbrpieces;


   public Villestates() {
   }

   public Villestates(String photo, String nomville, String nbrpieces) {
      this.photo = photo;
      this.nomville = nomville;
      this.nbrpieces = nbrpieces;
   }

   public String photo() {
      return photo;
   }

   public void setPhoto(String photo) {
      this.photo = photo;
   }

   public String nomville() {
      return nomville;
   }

   public void setNomville(String nomville) {
      this.nomville = nomville;
   }

   public String nbrpieces() {
      return nbrpieces;
   }

   public void setNbrpieces(String nbrpieces) {
      this.nbrpieces = nbrpieces;
   }
}
