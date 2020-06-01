package com.example.mye_commerceapplication.Model;

public class Shopping {

    private String date,idLignCommande,productId,quantity,status,telNumberClient,telNumberVendeur,longitude,latitude;

    public Shopping() {
    }

    public Shopping(String date, String idLignCommande, String productId, String quantity, String status, String telNumberClient, String telNumberVendeur, String longitude, String latitude) {
        this.date = date;
        this.idLignCommande = idLignCommande;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.telNumberClient = telNumberClient;
        this.telNumberVendeur = telNumberVendeur;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdLignCommande() {
        return idLignCommande;
    }

    public void setIdLignCommande(String idLignCommande) {
        this.idLignCommande = idLignCommande;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelNumberClient() {
        return telNumberClient;
    }

    public void setTelNumberClient(String telNumberClient) {
        this.telNumberClient = telNumberClient;
    }

    public String getTelNumberVendeur() {
        return telNumberVendeur;
    }

    public void setTelNumberVendeur(String telNumberVendeur) {
        this.telNumberVendeur = telNumberVendeur;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "date='" + date + '\'' +
                ", idLignCommande='" + idLignCommande + '\'' +
                ", productId='" + productId + '\'' +
                ", quantity='" + quantity + '\'' +
                ", status='" + status + '\'' +
                ", telNumberClient='" + telNumberClient + '\'' +
                ", telNumberVendeur='" + telNumberVendeur + '\'' +
                ", longitude="+longitude+"\n"+
                ", latitude="+latitude+"\n"+
                '}';
    }
}
