/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import util.StavkaStatus;

/**
 *
 * @author Sony
 */
public class StavkaRacuna implements Serializable, GenericEntity {

    private int stavkaRacunaID;
    private int kolicina;
    private double vrednostStavkeSaPdv;
    private double vrednostStavkeBezPdv;
    private double iznosPdv;
    private Racun racun;
    private Kolac kolac;
    private StavkaStatus status;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int stavkaRacunaID, int kolicina, double vrednostStavkeSaPdv, double vrednostStavkeBezPdv, double iznosPdv, Racun racun, Kolac kolac, StavkaStatus status) {
        this.stavkaRacunaID = stavkaRacunaID;
        this.kolicina = kolicina;
        this.vrednostStavkeSaPdv = vrednostStavkeSaPdv;
        this.vrednostStavkeBezPdv = vrednostStavkeBezPdv;
        this.iznosPdv = iznosPdv;
        this.racun = racun;
        this.kolac = kolac;
        this.status = status;
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "stavkaRacunaID=" + stavkaRacunaID + ", kolicina=" + kolicina + ", vrednostStavkeSaPdv=" + vrednostStavkeSaPdv + ", vrednostStavkeBezPdv=" + vrednostStavkeBezPdv + ", iznosPdv=" + iznosPdv + ", racun=" + racun + ", kolac=" + kolac + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.stavkaRacunaID;
        hash = 29 * hash + this.kolicina;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.vrednostStavkeSaPdv) ^ (Double.doubleToLongBits(this.vrednostStavkeSaPdv) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.vrednostStavkeBezPdv) ^ (Double.doubleToLongBits(this.vrednostStavkeBezPdv) >>> 32));
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.iznosPdv) ^ (Double.doubleToLongBits(this.iznosPdv) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.racun);
        hash = 29 * hash + Objects.hashCode(this.kolac);
        hash = 29 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StavkaRacuna other = (StavkaRacuna) obj;
        if (!Objects.equals(this.kolac, other.kolac)) {
            return false;
        }
        return true;
    }

    public StavkaStatus getStatus() {
        return status;
    }

    public void setStatus(StavkaStatus status) {
        this.status = status;
    }

    public int getStavkaRacunaID() {
        return stavkaRacunaID;
    }

    public void setStavkaRacunaID(int stavkaRacunaID) {
        this.stavkaRacunaID = stavkaRacunaID;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getVrednostStavkeSaPdv() {
        return vrednostStavkeSaPdv;
    }

    public void setVrednostStavkeSaPdv(double vrednostStavkeSaPdv) {
        this.vrednostStavkeSaPdv = vrednostStavkeSaPdv;
    }

    public double getVrednostStavkeBezPdv() {
        return vrednostStavkeBezPdv;
    }

    public void setVrednostStavkeBezPdv(double vrednostStavkeBezPdv) {
        this.vrednostStavkeBezPdv = vrednostStavkeBezPdv;
    }

    public double getIznosPdv() {
        return iznosPdv;
    }

    public void setIznosPdv(double iznosPdv) {
        this.iznosPdv = iznosPdv;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public Kolac getKolac() {
        return kolac;
    }

    public void setKolac(Kolac kolac) {
        this.kolac = kolac;
    }

    @Override
    public String getTableName() {
        return "stavka_racuna";
    }

    @Override
    public String getAttributes() {
        return "stavka_racuna_id, racun_id, kolicina, vrednost_stavke_sa_pdv, vrednost_stavke_bez_pdv, iznos_pdv, kolac_id, status";
    }

    @Override
    public String getValues() {
        return stavkaRacunaID + ", " + racun.getRacunID() + ", " + kolicina + ", " + vrednostStavkeSaPdv + ", " + vrednostStavkeBezPdv + ", " + iznosPdv + ", " + kolac.getKolacID() + ", '" +status + "'";

    }

    @Override
    public String getSelectContidion() {
        if (racun != null) {
            return "racun_id= " + racun.getRacunID();
        } else {
            return "stavka_racuna_id = " + stavkaRacunaID + " and racun_id= " + racun.getRacunID();
        }
    }

    @Override
    public String getDeleteContidion() {
        return "stavka_racuna_id = " + stavkaRacunaID + " and racun_id= " + racun.getRacunID();
    }

    @Override
    public String getUpdateCondition() {
        return "stavka_racuna_id = " + stavkaRacunaID + " and racun_id= " + racun.getRacunID();
    }

    @Override
    public String setAttributes() {
        return "kolicina=" + kolicina + ", vrednost_stavke_sa_pdv=" + vrednostStavkeSaPdv + ", vrednost_stavke_bez_pdv=" + vrednostStavkeBezPdv + ", iznos_pdv=" + iznosPdv + ", kolac_id=" + kolac.getKolacID() +", status='"+status+"'";
    }

    @Override
    public List<GenericEntity> getList(ResultSet resultSet) throws Exception {
        List<GenericEntity> list = new ArrayList<>();

        while (resultSet.next()) {
            StavkaRacuna sr = new StavkaRacuna(
                    resultSet.getInt("stavka_racuna_id"),
                    resultSet.getInt("kolicina"),
                    resultSet.getDouble("vrednost_stavke_sa_pdv"),
                    resultSet.getDouble("vrednost_stavke_bez_pdv"),
                    resultSet.getDouble("iznos_pdv"),
                    new Racun(resultSet.getInt("racun_id")),
                    new Kolac(resultSet.getInt("kolac_id")), StavkaStatus.valueOf(resultSet.getString("status")));

            list.add(sr);
        }
        return list;
    }
}
