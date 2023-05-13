package com.mirea.kt.android2023;

public class Products {

    private String ID;
    private String title;
    private String quantitu;
    private String isBuy;
    private String conteinerID;

    public Products(String ID, String title, String quantitu, String isBuy, String conteinerID) {
        this.ID = ID;
        this.title = title;
        this.quantitu = quantitu;
        this.isBuy = isBuy;
        this.conteinerID = conteinerID;
    }

    public Products(String title, String quantitu, String isBuy, String conteinerID) {
        this.title = title;
        this.quantitu = quantitu;
        this.isBuy = isBuy;
        this.conteinerID = conteinerID;
    }

    public  Products(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuantitu() {
        return quantitu;
    }

    public void setQuantitu(String quantitu) {
        this.quantitu = quantitu;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getConteinerID() {
        return conteinerID;
    }

    public void setConteinerID(String conteinerID) {
        this.conteinerID = conteinerID;
    }
}
