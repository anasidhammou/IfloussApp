package com.edulab.e_commerceapp.Class;

public class userpoint {
   public int Point;
   public String user;

   public userpoint() {
   }

   public userpoint(int point, String user) {
      Point = point;
      this.user = user;
   }

   public int Point() {
      return Point;
   }

   public void setPoint(int point) {
      Point = point;
   }

   public String user() {
      return user;
   }

   public void setUser(String user) {
      this.user = user;
   }
}
