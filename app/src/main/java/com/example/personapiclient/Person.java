package com.example.personapiclient;

import java.io.Serializable;

public class Person implements Serializable {
    public int id;
    public String navn;
    public String addresse;
    public int hairFarve;
    public boolean favorit;
    public String tlf;
    public String programSprog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public int getHairFarve() {
        return hairFarve;
    }

    public void setHairFarve(int hairFarve) {
        this.hairFarve = hairFarve;
    }

    public boolean isFavorit() {
        return favorit;
    }

    public void setFavorit(boolean favorit) {
        this.favorit = favorit;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getProgramSprog() {
        return programSprog;
    }

    public void setProgramSprog(String programSprog) {
        this.programSprog = programSprog;
    }

    public Person(int id, String navn, String addresse, int hairFarve, boolean favorit, String tlf,
                  String programSprog) {
        super();
        this.id = id;
        this.navn = navn;
        this.addresse = addresse;
        this.hairFarve = hairFarve;
        this.favorit = favorit;
        this.tlf = tlf;
        this.programSprog = programSprog;
    }

    public Person() {
        // TODO Auto-generated constructor stub
    }


}

