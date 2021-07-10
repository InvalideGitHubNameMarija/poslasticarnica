/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.radnik;

import domen.GenericEntity;
import domen.Radnik;
import sesija.SesijaKlijenti;
import so.AbstractGenericOperation;

/**
 *
 * @author Sony
 */
public class LogOutSO extends AbstractGenericOperation {
     GenericEntity object = null;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Radnik)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        SesijaKlijenti.getInstanca().removeRadnik(entity);
    }

    public GenericEntity getObject() {
        return object;
    }
}
