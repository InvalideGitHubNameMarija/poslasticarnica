/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kolac;

import domen.Kolac;
import so.AbstractGenericOperation;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class IzmeniKolacSO extends AbstractGenericOperation {

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Kolac)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        databaseBroker.izmeni((GenericEntity) entity);
    }
}
