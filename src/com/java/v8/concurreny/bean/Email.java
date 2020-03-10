package com.java.v8.concurreny.bean;

public class Email{

    private String id;

    public Email() {

    }

    public Email(String id){
        this.id = id;
    }

    public String getId() {return this.id;}
    public void setId(String id){this.id = id;}

    @Override
    public String toString() {
        return "Email[id:" + this.id + "]";
    }
}