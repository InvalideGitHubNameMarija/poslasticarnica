/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import domen.Kolac;
import domen.PDVStopa;
import form.KolacForm;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import sesija.Sesija;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author Sony
 */
public class KolacModel {

    public List<PDVStopa> vratiPDV() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_VRATI_PDV);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());

        Response response = (Response) in.readObject();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (List<PDVStopa>) response.getData();
        } else {
            throw (Exception) response.getError();
        }
    }

    public Kolac sacuvajKolac(Kolac k) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_SACUVAJ_KOLAC);
        request.setData(k);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            int index = (int) response.getData();
            k.setKolacID(index);
            return k;
        } else {
            throw (Exception) response.getError();
        }
    }

    public String obrisiKolac(Kolac izabraniKolac) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_OBRISI_KOLAC);
        request.setData(izabraniKolac);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return "Sistem je obrisao kolač.";
        } else {
            if(response.getError() instanceof SQLIntegrityConstraintViolationException){
                throw (SQLIntegrityConstraintViolationException)response.getError();
                
            }
            throw (Exception) response.getError();
        }
    }

    public void izmeniKolac(Kolac p, KolacForm kolacForm) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_IZMENI_KOLAC);
        request.setData(p);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
        } else {
            throw (Exception) response.getError();
        }
    }
    
    public ArrayList<Kolac> vratiKolace() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_VRATI_KOLACE);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (ArrayList<Kolac>) response.getData();
        } else {
            throw (Exception) response.getError();
        }
    }
}
