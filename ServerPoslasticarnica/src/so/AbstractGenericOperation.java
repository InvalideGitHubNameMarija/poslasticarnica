 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import skladiste.IBrokerBaze;
import skladiste.baza.BrokerBaze;
import skladiste.baza.konekcija.Konekcija;

/**
 *
 * @author Sony
 */
public abstract class AbstractGenericOperation {

    protected IBrokerBaze databaseBroker;

    public AbstractGenericOperation() {
        databaseBroker = new BrokerBaze();
    }

    public final void templateExecute(Object entity) throws Exception {
        try {
            validate(entity);
            startTransaction();
            execute(entity);
            commitTransaction();
        } catch (Exception ex) {
            rollbackTransaction();
            throw ex;
        }
    }
    //operacion specific
    protected abstract void validate(Object entity) throws Exception;

    protected abstract void execute(Object entity) throws Exception;

    private void startTransaction() throws Exception {
        Konekcija.getInstance().getConnection().setAutoCommit(false);
    }

    private void commitTransaction() throws Exception {
        Konekcija.getInstance().commit();
    }

    private void rollbackTransaction() throws Exception {
        Konekcija.getInstance().rollback();
    }
}