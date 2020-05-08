package com.example.mye_commerceapplication.Prevalent;

import android.widget.TextView;

import com.example.mye_commerceapplication.Model.LigneCommande;
import com.example.mye_commerceapplication.Model.Product;
import com.example.mye_commerceapplication.Model.Users;

public class Prevalent {
public static Users currentOnlineUser;

    public static String searchedword;
    public static Product product;
    public static TextView numberOfCommandText;
    public static int numberOfCommands=0;
    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";
    public static LigneCommande ligneCommande;

}
