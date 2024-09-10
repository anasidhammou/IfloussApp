package com.edulab.e_commerceapp.Class;

public class historiquedemande {

    public int id;
    public String montantcumule;
    public String typepayemenet;
    public String infopaye;


    public historiquedemande() {
    }

    public historiquedemande(int id, String montantcumule, String typepayemenet, String infopaye) {
        this.id = id;
        this.montantcumule = montantcumule;
        this.typepayemenet = typepayemenet;
        this.infopaye = infopaye;
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String montantcumule() {
        return montantcumule;
    }

    public void setMontantcumule(String montantcumule) {
        this.montantcumule = montantcumule;
    }

    public String typepayemenet() {
        return typepayemenet;
    }

    public void setTypepayemenet(String typepayemenet) {
        this.typepayemenet = typepayemenet;
    }

    public String infopaye() {
        return infopaye;
    }

    public void setInfopaye(String infopaye) {
        this.infopaye = infopaye;
    }
}
