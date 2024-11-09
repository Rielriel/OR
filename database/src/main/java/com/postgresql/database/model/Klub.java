package com.postgresql.database.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Hrvatske_nogometne_lige_merge")

public class Klub {



    private String naziv_liga;
    private String rang;
    private String broj_klubova;
    private String krugovi;
    @Id
    private String naziv_klub;
    private String nadimak;
    private String naziv_stadion;
    private String mjesto;
    private int godina_osnutak;
    private String predsjednik;
    private String trener;
    private String navijači;
    private String boja;
    private int prvak_hrvatska;

    public Klub(){
    }

    public Klub(String naziv_liga, String rang, String broj_klubova, String krugovi, String naziv_klub, String nadimak, String naziv_stadion, String mjesto, int godina_osnutak, String predsjednik, String trener, String navijači, String boja, int prvak_hrvatska) {
        this.naziv_liga = naziv_liga;
        this.rang = rang;
        this.broj_klubova = broj_klubova;
        this.krugovi = krugovi;
        this.naziv_klub = naziv_klub;
        this.nadimak = nadimak;
        this.naziv_stadion = naziv_stadion;
        this.mjesto = mjesto;
        this.godina_osnutak = godina_osnutak;
        this.predsjednik = predsjednik;
        this.trener = trener;
        this.navijači = navijači;
        this.boja = boja;
        this.prvak_hrvatska = prvak_hrvatska;
    }


    public String getNaziv_liga() {
        return naziv_liga;
    }

    public void setNaziv_liga(String naziv_liga) {
        this.naziv_liga = naziv_liga;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public String getBroj_klubova() {
        return broj_klubova;
    }

    public void setBroj_klubova(String broj_klubova) {
        this.broj_klubova = broj_klubova;
    }

    public String getKrugovi() {
        return krugovi;
    }

    public void setKrugovi(String krugovi) {
        this.krugovi = krugovi;
    }

    public String getNaziv_klub() {
        return naziv_klub;
    }

    public void setNaziv_klub(String naziv_klub) {
        this.naziv_klub = naziv_klub;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void setNadimak(String nadimak) {
        this.nadimak = nadimak;
    }

    public String getNaziv_stadion() {
        return naziv_stadion;
    }

    public void setNaziv_stadion(String naziv_stadion) {
        this.naziv_stadion = naziv_stadion;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public int getGodina_osnutak() {
        return godina_osnutak;
    }

    public void setGodina_osnutak(int godina_osnutak) {
        this.godina_osnutak = godina_osnutak;
    }

    public String getPredsjednik() {
        return predsjednik;
    }

    public void setPredsjednik(String predsjednik) {
        this.predsjednik = predsjednik;
    }

    public String getTrener() {
        return trener;
    }

    public void setTrener(String trener) {
        this.trener = trener;
    }

    public String getNavijači() {
        return navijači;
    }

    public void setNavijači(String navijači) {
        this.navijači = navijači;
    }

    public String getBoja() {
        return boja;
    }

    public void setBoja(String boja) {
        this.boja = boja;
    }

    public int getPrvak_hrvatska() {
        return prvak_hrvatska;
    }

    public void setPrvak_hrvatska(int prvak_hrvatska) {
        this.prvak_hrvatska = prvak_hrvatska;
    }
}
