 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skladiste.baza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import skladiste.IBrokerBaze;
import skladiste.baza.konekcija.Konekcija;
import domen.GenericEntity;

/**
 *
 * @author Sony
 */
public class BrokerBaze implements IBrokerBaze {

    @Override
    public GenericEntity vratiPoId(GenericEntity entity) throws Exception {
        List<GenericEntity> list = null;
        try {
            Connection connection = Konekcija.getInstance().getConnection();
            //System.out.println("Uspostavljena je konekcija sa bazom.");
            String query = "select * from " + entity.getTableName() + " where " + entity.getSelectContidion();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            list = entity.getList(resultSet);

            statement.close();
            return list.get(0);
        } catch (Exception ex) {
            throw new Exception("Ne postoji!");
        }
    }

    @Override
    public List<GenericEntity> vratiSve(GenericEntity entity) throws Exception {
        List<GenericEntity> list = null;
        try {
            Connection connection = Konekcija.getInstance().getConnection();
           // System.out.println("Uspostavljena je konekcija na bazu.");
            String query = "select * from " + entity.getTableName();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = entity.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int sacuvaj(GenericEntity entity) throws Exception {
        try {
            String query;
            Connection connection = Konekcija.getInstance().getConnection();
            //System.out.println("Uspostavljena je konekcija sa bazom.");
            query = "insert into " + entity.getTableName() + "(" + entity.getAttributes() + ")" + " values(" + entity.getValues() + ")";
            //System.out.println(query);
            PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void obrisi(GenericEntity entity) throws Exception {
        try {
            String query;
            Connection connection = Konekcija.getInstance().getConnection();
            //System.out.println("Uspostavljena je konekcija sa bazom.");
            query = "delete from " + entity.getTableName() + " where " + entity.getDeleteContidion();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void izmeni(GenericEntity entity) throws Exception {
        try {
            String query;
            Connection connection = Konekcija.getInstance().getConnection();
            //System.out.println("Uspostavljena je konekcija sa bazom.");
            query = "update " + entity.getTableName() + " set " + entity.setAttributes() + " where " + entity.getUpdateCondition();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.commit();
            statement.close();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<GenericEntity> vratiPoUslovu(GenericEntity entity) throws Exception {
        List<GenericEntity> list = null;
        try {
            Connection connection = Konekcija.getInstance().getConnection();
            //System.out.println("Uspostavljena je konekcija na bazu.");
            String query = "select * from " + entity.getTableName() + " where " + entity.getSelectContidion();
            //System.out.println(query);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            list = entity.getList(resultSet);
            resultSet.close();
            statement.close();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }
}