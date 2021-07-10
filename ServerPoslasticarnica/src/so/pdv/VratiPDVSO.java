/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.pdv;

import domen.PDVStopa;
import java.util.List;
import so.AbstractGenericOperation;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class VratiPDVSO extends AbstractGenericOperation {

    private List<GenericEntity> list;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof PDVStopa)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        list = databaseBroker.vratiSve((GenericEntity) new PDVStopa());
    }

    public List<GenericEntity> getList() {
        return list;
    }
}