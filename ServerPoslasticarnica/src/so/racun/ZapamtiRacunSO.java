/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.racun;

import domen.Racun;
import domen.StavkaRacuna;
import so.AbstractGenericOperation;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class ZapamtiRacunSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Racun)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        Racun racun = (Racun) entity;
        int id = databaseBroker.sacuvaj((GenericEntity) racun);
        racun.setRacunID(id);
        for (StavkaRacuna stavkaRacuna : racun.getStavke()) {
            databaseBroker.sacuvaj((GenericEntity) stavkaRacuna);
        }
    }
}