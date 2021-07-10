/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.radnik;

import domen.Radnik;
import so.AbstractGenericOperation;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class LogInSO extends AbstractGenericOperation {

    GenericEntity object = null;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Radnik)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        object = databaseBroker.vratiPoId((GenericEntity) entity);
    }

    public GenericEntity getObject() {
        return object;
    }
    
}