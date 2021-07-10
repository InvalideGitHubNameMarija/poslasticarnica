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
public class PDVStopa implements Serializable,GenericEntity {
    private int stopaID;
    private double pdv;

    public PDVStopa() {
    }

    public PDVStopa(int stopaID, double pdv) {
        this.stopaID = stopaID;
        this.pdv = pdv;
    }

    public PDVStopa(int stopaID) {
        this.stopaID=stopaID;
    }

    public int getStopaID() {
        return stopaID;
    }

    public void setStopaID(int stopaID) {
        this.stopaID = stopaID;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    @Override
    public String toString() {
       return getPdv() + " %";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + this.stopaID;
        hash = 43 * hash + (int) (Double.doubleToLongBits(this.pdv) ^ (Double.doubleToLongBits(this.pdv) >>> 32));
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
        final PDVStopa other = (PDVStopa) obj;
        if (this.stopaID != other.stopaID) {
            return false;
        }
        return true;
    }

 @Override
    public String getTableName() {
        return "pdv_stopa";
    }

    @Override
    public String getAttributes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSelectContidion() {
        return "stopa_id=" + stopaID;
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
            int stopaID = resultSet.getInt("stopa_id");
            Double pdv = resultSet.getDouble("pdv");
            list.add(new PDVStopa(stopaID, pdv));
        }
        return list;
    }
}
