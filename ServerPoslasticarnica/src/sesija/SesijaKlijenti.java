/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesija;

import domen.GenericEntity;
import domen.Radnik;
import java.util.ArrayList;

/**
 *
 * @author Sony
 */
public class SesijaKlijenti {

    private static SesijaKlijenti instanca;
    ArrayList<Radnik> radnici;

    public SesijaKlijenti() {
        radnici = new ArrayList<>();
    }

    public static SesijaKlijenti getInstanca() {
        if (instanca == null) {
            instanca = new SesijaKlijenti();
        }
        return instanca;
    }

    public void dodajRadnika(GenericEntity objekat) {
        if (objekat instanceof Radnik) {
            if (!radnici.contains(objekat)) {
                radnici.add((Radnik) objekat);
            }
        }
    }

    public ArrayList<Radnik> getRadnici() {
        return radnici;
    }

    public void removeRadnik(Object entity) {
        try {
            if (entity instanceof Radnik) {
                for (Radnik radnik : radnici) {
                    if (radnici.contains(entity)) {
                        radnici.remove(entity);
                    }
                }
            }
        } catch (Exception e) {
        }

    }

    public void isprazniRadnike() {
        radnici=new ArrayList<>();
    }

}
