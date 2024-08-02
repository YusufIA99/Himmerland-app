package com.example.himmerlandapplication.models;

public class ChatMessage {

    public String message;
    public String fornavn;
    public String efternavn;
    public String dateTime;

    public ChatMessage(){}

    public ChatMessage(String message, String fornavn, String efternavn, String dateTime) {
        this.message = message;
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFornavn() {
        return fornavn;
    }

    public void setFornavn(String fornavn) {
        this.fornavn = fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public void setEfternavn(String efternavn) {
        this.efternavn = efternavn;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
