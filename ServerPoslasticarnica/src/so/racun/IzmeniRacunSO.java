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
import util.StavkaStatus;

/**
 *
 * @author Sony
 */
public class IzmeniRacunSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Racun)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {

        databaseBroker.izmeni((GenericEntity) entity);
        Racun racun = (Racun) entity;
        for (StavkaRacuna sr : racun.getStavke()) {
            if (sr.getStatus() == StavkaStatus.U_PROCESU_MENJANJA) {
                sr.setStatus(StavkaStatus.IZMENJENA);
                databaseBroker.izmeni((GenericEntity) sr);
            } 
        }
    }
}
