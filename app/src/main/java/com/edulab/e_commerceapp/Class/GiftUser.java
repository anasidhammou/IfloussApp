package com.edulab.e_commerceapp.Class;

public class GiftUser {

   public String photogift;
   public String namegigt;
   public String pointgift;
   public String descriptiongift;


   public GiftUser() {
   }

   public GiftUser(String photogift, String namegigt, String pointgift, String descriptiongift) {
      this.photogift = photogift;
      this.namegigt = namegigt;
      this.pointgift = pointgift;
      this.descriptiongift = descriptiongift;
   }

   public String photogift() {
      return photogift;
   }

   public void setPhotogift(String photogift) {
      this.photogift = photogift;
   }

   public String namegigt() {
      return namegigt;
   }

   public void setNamegigt(String namegigt) {
      this.namegigt = namegigt;
   }

   public String pointgift() {
      return pointgift;
   }

   public void setPointgift(String pointgift) {
      this.pointgift = pointgift;
   }

   public String descriptiongift() {
      return descriptiongift;
   }

   public void setDescriptiongift(String descriptiongift) {
      this.descriptiongift = descriptiongift;
   }
}
