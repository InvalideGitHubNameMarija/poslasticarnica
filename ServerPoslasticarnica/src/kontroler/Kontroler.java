/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Kolac;
import domen.PDVStopa;
import domen.Racun;
import java.util.List;
import skladiste.IBrokerBaze;
import skladiste.baza.BrokerBaze;
import so.AbstractGenericOperation;
import so.pdv.VratiPDVSO;
import so.kolac.IzmeniKolacSO;
import so.kolac.ObrisiKolacSO;
import so.kolac.VratiKolaceSO;
import so.kolac.ZapamtiKolacSO;
import so.racun.IzmeniRacunSO;
import so.racun.StornirajRacunSO;
import so.racun.VratiRacuneSO;
import so.racun.ZapamtiRacunSO;
import so.radnik.LogInSO;
import domen.GenericEntity;
import so.radnik.LogOutSO;

/**
 *
 * @author Sony
 */
public class Kontroler {

    private static Kontroler instance;
    private IBrokerBaze brokerBaze;

    private Kontroler() {
        brokerBaze = new BrokerBaze();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public GenericEntity prijaviRadnika(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new LogInSO();
        so.templateExecute(entity);
        return ((LogInSO) so).getObject();
    }

    public List<GenericEntity> vratiPDVStope() throws Exception {
        AbstractGenericOperation so = new VratiPDVSO();
        so.templateExecute(new PDVStopa());
        return ((VratiPDVSO) so).getList();
    }

    public int sacuvajKolac(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new ZapamtiKolacSO();
        so.templateExecute(entity);
        return ((ZapamtiKolacSO) so).getIndex();
    }

    public List<GenericEntity> vratiKolace() throws Exception {
        AbstractGenericOperation so = new VratiKolaceSO();
        so.templateExecute(new Kolac());
        return ((VratiKolaceSO) so).getList();
    }

    public void obrisiKolac(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new ObrisiKolacSO();
        so.templateExecute(entity);
    }

    public void izmeniKolac(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new IzmeniKolacSO();
        so.templateExecute(entity);
    }

    public void sacuvajRacun(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new ZapamtiRacunSO();
        so.templateExecute(entity);
    }

    public List<GenericEntity> vratiRacune() throws Exception {
        AbstractGenericOperation so = new VratiRacuneSO();
        so.templateExecute(new Racun());
        return ((VratiRacuneSO) so).getList();
    }

    public void izmeniRacun(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new IzmeniRacunSO();
        so.templateExecute(entity);
    }

    public void stornirajRacun(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new StornirajRacunSO();
        so.templateExecute(entity);
    }

    public void odjaviRadnika(GenericEntity entity) throws Exception {
        AbstractGenericOperation so = new LogOutSO();
        so.templateExecute(entity);

    }

}
