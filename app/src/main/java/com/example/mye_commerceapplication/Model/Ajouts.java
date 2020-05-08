package com.example.mye_commerceapplication.Model;

import java.util.Date;

public class Ajouts {
    int idAjout;
    String idProduit;
    String dateAjout;
    int quantity;

    public Ajouts() {
    }

    public Ajouts(int idAjout, String idProduit, String dateAjout, int quantity) {
        this.idAjout = idAjout;
        this.idProduit = idProduit;
        this.dateAjout = dateAjout;
        this.quantity = quantity;
    }

    public int getIdAjout() {
        return idAjout;
    }

    public void setIdAjout(int idAjout) {
        this.idAjout = idAjout;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(String dateAjout) {
        this.dateAjout = dateAjout;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
