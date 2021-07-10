/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.racun;

import domen.Kolac;
import domen.Racun;
import domen.Radnik;
import domen.StavkaRacuna;
import java.util.ArrayList;
import java.util.List;
import so.AbstractGenericOperation;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class VratiRacuneSO extends AbstractGenericOperation {

    private List<GenericEntity> list;

    @Override
    protected void validate(Object entity) throws Exception {
        if (!(entity instanceof Racun)) {
            throw new Exception("Objekat nije validan!");
        }
    }

    @Override
    protected void execute(Object entity) throws Exception {
        list = databaseBroker.vratiSve((GenericEntity) new Racun());
        
        for (GenericEntity domenskiRacun : list) {
            Racun r = (Racun) domenskiRacun;
            
            r.setRadnik((Radnik) databaseBroker.vratiPoId((GenericEntity) r.getRadnik()));

            StavkaRacuna stavka = new StavkaRacuna();
            stavka.setRacun(r);
            List<GenericEntity> stavke = databaseBroker.vratiPoUslovu((GenericEntity) stavka);

            ArrayList<StavkaRacuna> stavkeRacuna = new ArrayList<>();
            for (GenericEntity domenskaStavka : stavke) {
                StavkaRacuna sr = (StavkaRacuna) domenskaStavka;
                sr.setRacun(r);
                sr.setKolac((Kolac) databaseBroker.vratiPoId((GenericEntity) sr.getKolac()));
                stavkeRacuna.add(sr);
            }
            r.setStavke(stavkeRacuna);
        }
    }
    

    public List<GenericEntity> getList() {
        return list;
    }
}