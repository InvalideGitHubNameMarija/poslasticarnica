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

/**
 *
 * @author Sony
 */
public class Kolac implements Serializable, GenericEntity {

    private int kolacID;
    private String naziv;
    private String opis;
    private double cenaSaPdv;
    private double cenaBezPdv;
    private PDVStopa pdv;

    public Kolac() {
    }

    public Kolac(int kolacID, String naziv, String opis, double cenaSaPdv, double cenaBezPdv, PDVStopa pdv) {
        this.kolacID = kolacID;
        this.naziv = naziv;
        this.opis = opis;
        this.cenaSaPdv = cenaSaPdv;
        this.cenaBezPdv = cenaBezPdv;
        this.pdv = pdv;
    }

    Kolac(int kolacID) {
        this.kolacID=kolacID;
    }

    public int getKolacID() {
        return kolacID;
    }

    public void setKolacID(int kolacID) {
        this.kolacID = kolacID;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCenaSaPdv() {
        return cenaSaPdv;
    }

    public void setCenaSaPdv(double cenaSaPdv) {
        this.cenaSaPdv = cenaSaPdv;
    }

    public double getCenaBezPdv() {
        return cenaBezPdv;
    }

    public void setCenaBezPdv(double cenaBezPdv) {
        this.cenaBezPdv = cenaBezPdv;
    }

    public PDVStopa getPdv() {
        return pdv;
    }

    public void setPdv(PDVStopa pdv) {
        this.pdv = pdv;
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.kolacID;
        hash = 89 * hash + Objects.hashCode(this.naziv);
        hash = 89 * hash + Objects.hashCode(this.opis);
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.cenaSaPdv) ^ (Double.doubleToLongBits(this.cenaSaPdv) >>> 32));
        hash = 89 * hash + (int) (Double.doubleToLongBits(this.cenaBezPdv) ^ (Double.doubleToLongBits(this.cenaBezPdv) >>> 32));
        hash = 89 * hash + Objects.hashCode(this.pdv);
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
        final Kolac other = (Kolac) obj;
        if (this.kolacID != other.kolacID) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "kolac";
    }

    @Override
    public String getAttributes() {
        return "naziv, opis, cena_sa_pdv, cena_bez_pdv, stopa_id";
    }

    @Override
    public String getValues() {
        return "'" + naziv + "', '" + opis + "', " + cenaSaPdv + ", " + cenaBezPdv + ", " + pdv.getStopaID();
    }

    @Override
    public String getSelectContidion() {
         return "kolac_id=" + kolacID;
    }

    @Override
    public String getDeleteContidion() {
         return "kolac_id=" + kolacID;
    }

    @Override
    public String setAttributes() {
        return "naziv='" + naziv + "',opis='" + opis + "',cena_sa_pdv=" + cenaSaPdv + ",cena_bez_pdv=" + cenaBezPdv + ",stopa_id=" + pdv.getStopaID();
    }

    @Override
    public List<GenericEntity> getList(ResultSet resultSet) throws Exception {
        List<GenericEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            int kolacID = resultSet.getInt("kolac_id");
            String naziv = resultSet.getString("naziv");
            String opis = resultSet.getString("opis");
            Double cenaSaPdv = resultSet.getDouble("cena_sa_pdv");
            Double cenaBezPdv = resultSet.getDouble("cena_bez_pdv");
            PDVStopa pdv = new PDVStopa(resultSet.getInt("stopa_id"));
            list.add(new Kolac(kolacID, naziv, opis, cenaSaPdv, cenaBezPdv, pdv));
        }
        return list;
    }

    @Override
    public String getUpdateCondition() {
        return "kolac_id=" + kolacID;
    }

   
}
