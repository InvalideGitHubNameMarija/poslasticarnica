/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domen;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author Sony
 */
public interface GenericEntity {

    public String getTableName();

    public String getAttributes();

    public String getValues();

    public String getSelectContidion();

    public String getDeleteContidion();

    public String getUpdateCondition();

    public String setAttributes();

    public List<GenericEntity> getList(ResultSet resultSet) throws Exception;
}