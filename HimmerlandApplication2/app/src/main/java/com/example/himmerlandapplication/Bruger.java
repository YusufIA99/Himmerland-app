package com.example.himmerlandapplication;

public class Bruger {

    public String fornavn;
    public String efternavn;
    public String email;

    public Bruger()
    {
    }

    public Bruger(String fornavn, String efternavn, String email)
    {
        this.fornavn = fornavn;
        this.efternavn = efternavn;
        this.email = email;
    }

    public String getFornavn(){
        return fornavn;
    }

    public String getEfternavn() {
        return efternavn;
    }

    public String getEmail(){
        return email;
    }
}
