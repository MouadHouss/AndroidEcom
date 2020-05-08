package com.example.mye_commerceapplication.Model;

public class Products {
    String pid;
    String price;
    String pname;
    String description;
    String category;
    String phonenumber;
    String image;



    public Products() {
    }

    public Products(String productID, String price, String name, String description,
                    String category,String phonenumber,String image) {
        this.pid = productID;
        this.price = price;
        this.pname = name;
        this.description = description;
        this.category = category;
        this.phonenumber=phonenumber;
        this.image=image;

    }

    public String getPid() {
        return pid;
    }
    public void setPid(String productID) {
        this.pid = productID;
    }


    public String getPrice() {
        return price; }
    public void setPrice(String price) {
        this.price = price;
    }


    public String getPname() {
        return pname;
    }
    public void setPname(String name) {
        this.pname = name;
    }



    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }



    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }



    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }


    public String getPhonenumber() {
        return phonenumber;
    }
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
