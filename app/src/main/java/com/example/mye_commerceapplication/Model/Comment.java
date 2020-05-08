package com.example.mye_commerceapplication.Model;

public class Comment {

    private String id;
    private String date;
    private String text;
    private String numClient;
    private String idroduit;

    public Comment(){ }

    public Comment(String id, String date, String text, String numClient, String idroduit) {
        this.id = id;
        this.date = date;
        this.text = text;
        this.numClient = numClient;
        this.idroduit = idroduit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumClient() {
        return numClient;
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    public String getIdroduit() {
        return idroduit;
    }

    public void setIdroduit(String idroduit) {
        this.idroduit = idroduit;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", text='" + text + '\'' +
                ", numClient='" + numClient + '\'' +
                ", idroduit='" + idroduit + '\'' +
                '}';
    }
}
