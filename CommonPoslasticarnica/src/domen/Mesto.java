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
public class Mesto implements Serializable, GenericEntity {

    private int postanskiBroj;
    private String naziv;

    public Mesto() {
    }

    public Mesto(int postanskiBroj, String naziv) {
        this.postanskiBroj = postanskiBroj;
        this.naziv = naziv;
    }
    public Mesto(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
   

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return "Mesto{" + "postanskiBroj=" + postanskiBroj + ", naziv=" + naziv + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.postanskiBroj;
        hash = 61 * hash + Objects.hashCode(this.naziv);
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
        final Mesto other = (Mesto) obj;
        if (this.postanskiBroj != other.postanskiBroj) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "mesto";
    }

    @Override
    public String getAttributes() {
        return "naziv";
    }

    @Override
    public String getValues() {
        return "'" + naziv + "'";
    }

    @Override
    public String getSelectContidion() {
        return "postanskiBroj=" + postanskiBroj;
    }

    @Override
    public String getDeleteContidion() {
        return "postanskiBroj=" + postanskiBroj;
    }

    @Override
    public String getUpdateCondition() {
        return "postanskiBroj=" + postanskiBroj;
    }

    @Override
    public String setAttributes() {
        return "naziv='" + naziv + "'";
    }

    @Override
    public List<GenericEntity> getList(ResultSet resultSet) throws Exception {
        List<GenericEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            int postanskiBroj = resultSet.getInt("postanskiBroj");
            String naziv = resultSet.getString("naziv");
            list.add(new Mesto(postanskiBroj, naziv));
        }
        return list;
    }

}
