/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sony
 */
public class Racun implements Serializable, GenericEntity {

    private int racunID;
    private Date datum;
    private double ukupnoSaPdv;
    private double ukupnoBezPdv;
    private double iznosPDV;
    private boolean storniran;
    private Radnik radnik;
    private ArrayList<StavkaRacuna> stavke;

    public Racun() {
    }

    public Racun(int racunID, Date datum, double ukupnoSaPdv, double ukupnoBezPdv, double iznosPDV, boolean storniran, Radnik radnik, ArrayList<StavkaRacuna> stavke) {
        this.racunID = racunID;
        this.datum = datum;
        this.ukupnoSaPdv = ukupnoSaPdv;
        this.ukupnoBezPdv = ukupnoBezPdv;
        this.iznosPDV = iznosPDV;
        this.storniran = storniran;
        this.radnik = radnik;
        this.stavke = stavke;
    }

    public Racun(int racunID) {
        this.racunID = racunID;
    }

    public int getRacunID() {
        return racunID;
    }

    public void setRacunID(int racunID) {
        this.racunID = racunID;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public double getUkupnoSaPdv() {
        return ukupnoSaPdv;
    }

    public void setUkupnoSaPdv(double ukupnoSaPdv) {
        this.ukupnoSaPdv = ukupnoSaPdv;
    }

    public double getUkupnoBezPdv() {
        return ukupnoBezPdv;
    }

    public void setUkupnoBezPdv(double ukupnoBezPdv) {
        this.ukupnoBezPdv = ukupnoBezPdv;
    }

    public double getIznosPDV() {
        return iznosPDV;
    }

    public void setIznosPDV(double iznosPDV) {
        this.iznosPDV = iznosPDV;
    }

    public boolean isStorniran() {
        return storniran;
    }

    public void setStorniran(boolean storniran) {
        this.storniran = storniran;
    }

    public Radnik getRadnik() {
        return radnik;
    }

    public void setRadnik(Radnik radnik) {
        this.radnik = radnik;
    }

    public ArrayList<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(ArrayList<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return "Racun{" + "racunID=" + racunID + ", datum=" + datum + ", ukupnoSaPdv=" + ukupnoSaPdv + ", ukupnoBezPdv=" + ukupnoBezPdv + ", iznosPDV=" + iznosPDV + ", storniran=" + storniran + ", radnik=" + radnik + ", stavke=" + stavke + '}';
    }

    @Override
    public String getTableName() {
        return "racun";
    }

    @Override
    public String getAttributes() {
        return "datum, ukupno_sa_pdv, ukupno_bez_pdv, iznos_pdv, storniran, radnik_id";
    }

    @Override
    public String getValues() {
        return "'" + new java.sql.Date(datum.getTime()) + "', " + ukupnoSaPdv + ", " + ukupnoBezPdv + ", " + iznosPDV + ", " + storniran + ", " + radnik.getRadnikID();
    }

    @Override
    public String getSelectContidion() {
        return "racun_id=" + racunID;
    }

    @Override
    public String getDeleteContidion() {
        return "racun_id=" + racunID;
    }

    @Override
    public String getUpdateCondition() {
        return "racun_id=" + racunID;
    }

    @Override
    public String setAttributes() {
        return "datum='" + new java.sql.Date(datum.getTime()) + "', ukupno_sa_pdv=" + ukupnoSaPdv + ", ukupno_bez_pdv=" + ukupnoBezPdv + ", iznos_pdv=" + iznosPDV + ", storniran=" + storniran;
    }

    @Override
    public List<GenericEntity> getList(ResultSet rs) throws Exception {
        List<GenericEntity> list = new ArrayList<>();

        while (rs.next()) {
            Racun r = new Racun();
            r.setRacunID(rs.getInt("racun_id"));
            r.setDatum(rs.getDate("datum"));
            r.setUkupnoSaPdv(rs.getFloat("ukupno_sa_pdv"));
            r.setUkupnoBezPdv(rs.getFloat("ukupno_bez_pdv"));
            r.setIznosPDV(rs.getFloat("iznos_pdv"));
            r.setStorniran(rs.getBoolean("storniran"));
            r.setRadnik(new Radnik(rs.getInt("radnik_id")));

            list.add(r);
        }
        return list;
    }

}
