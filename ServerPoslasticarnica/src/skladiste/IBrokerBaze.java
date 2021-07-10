/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste;

import java.util.List;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public interface IBrokerBaze {

    GenericEntity vratiPoId(GenericEntity entity) throws Exception;

    List<GenericEntity> vratiSve(GenericEntity entity) throws Exception;

    int sacuvaj(GenericEntity entity) throws Exception;

    void obrisi(GenericEntity entity) throws Exception;

    void izmeni(GenericEntity entity) throws Exception;

    List<GenericEntity> vratiPoUslovu(GenericEntity entity) throws Exception;
}
