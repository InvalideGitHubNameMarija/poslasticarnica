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

/**
 *
 * @author Sony
 */
public class Radnik implements Serializable, GenericEntity {

    private int radnikID;
    private String ime;
    private String prezime;
    private String jmbg;
    private String username;
    private String password;
    private Mesto mesto;

    public Radnik() {
    }

    public Radnik(int radnikID) {
        this.radnikID = radnikID;
    }

    public Radnik(int radnikID, String ime, String prezime, String jmbg, String username, String password, Mesto mesto) {
        this.radnikID = radnikID;
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
        this.username = username;
        this.password = password;
        this.mesto = mesto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRadnikID() {
        return radnikID;
    }

    public void setRadnikID(int radnikID) {
        this.radnikID = radnikID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Mesto getmesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public String getTableName() {
        return "radnik";
    }

    @Override
    public String getAttributes() {
        return "ime, prezime, jmbg, username, password, postanski_broj";
    }

    @Override
    public String getValues() {
        return "'" + ime + "', '" + prezime + "', '" + jmbg + "', '" + username + "', '" + password + "', " + mesto.getPostanskiBroj();
    }

    @Override
    public String getSelectContidion() {
        if (username != null) {
            return "username='" + username + "' and password='" + password + "'";
        } else {
            return "radnik_id=" + radnikID;
        }
    }

    @Override
    public String getDeleteContidion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateCondition() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GenericEntity> getList(ResultSet resultSet) throws Exception {
        List<GenericEntity> list = new ArrayList<>();
        while (resultSet.next()) {
            int radnikID = resultSet.getInt("radnik_id");
            String ime = resultSet.getString("ime");
            String prezime = resultSet.getString("prezime");
            String jmbg = resultSet.getString("jmbg");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            mesto = new Mesto(resultSet.getInt("postanskiBroj"));
            list.add(new Radnik(radnikID, ime, prezime, jmbg, username, password, mesto));
        }
        return list;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Radnik other = (Radnik) obj;
        if (this.radnikID != other.radnikID) {
            return false;
        }
        return true;
    }

}
