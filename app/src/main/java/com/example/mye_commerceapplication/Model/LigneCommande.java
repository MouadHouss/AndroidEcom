package com.example.mye_commerceapplication.Model;

public class LigneCommande {
    private String idLignCommande;
    private String date;
    private String productId;
    private String quantity;
    private String telNumber;

    public LigneCommande() {
    }

    public LigneCommande(String idLignCommande, String date, String productId, String quantity, String telNumber) {
        this.idLignCommande = idLignCommande;
        this.date = date;
        this.productId = productId;
        this.quantity = quantity;
        this.telNumber = telNumber;
    }

    public String getIdLignCommande() {
        return idLignCommande;
    }

    public void setIdLignCommande(String idLignCommande) {
        this.idLignCommande = idLignCommande;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
