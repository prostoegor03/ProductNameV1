package com.mirea.kt.android2023;

public class Notes {

    private String ID;
    private String Title;
    private String Content;

    private  String Type;

    public Notes(String title, String content) {
        Title = title;
        Content = content;
    }

    public Notes(String ID, String title, String content, String type) {
        this.ID = ID;
        Title = title;
        Content = content;
        Type = type;
    }

    public Notes(String ID, String title, String content) {
        this.ID = ID;
        Title = title;
        Content = content;
    }

    public  Notes(){

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
