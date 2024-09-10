package com.edulab.e_commerceapp.Class;

public class userInfo {
    public String photo;
    public String nomuser;
    public String emailuser;
    public String phoneuser;
    public String rib;
    public String banque;

    public userInfo() {
    }


    public userInfo(String photo, String nomuser, String emailuser, String phoneuser, String rib, String banque) {
        this.photo = photo;
        this.nomuser = nomuser;
        this.emailuser = emailuser;
        this.phoneuser = phoneuser;
        this.rib = rib;
        this.banque = banque;
    }

    public String photo() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String nomuser() {
        return nomuser;
    }

    public void setNomuser(String nomuser) {
        this.nomuser = nomuser;
    }

    public String emailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public String phoneuser() {
        return phoneuser;
    }

    public void setPhoneuser(String phoneuser) {
        this.phoneuser = phoneuser;
    }

    public String rib() {
        return rib;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String banque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }
}
