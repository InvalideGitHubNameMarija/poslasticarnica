/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Kolac;
import domen.Racun;
import form.IzmeneRacuneForm;
import form.RacunForm;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
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
public class RacunModel {

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

    public void sacuvajRacun(Racun racun) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_SACUVAJ_RACUN);
        request.setData(racun);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return;
        } else {
            throw (Exception) response.getError();
        }
    }

    public void izmeniRacun(Racun r, IzmeneRacuneForm racunForm) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_IZMENI_RACUN);
        request.setData(r);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            JOptionPane.showMessageDialog(racunForm, "Uspešno izmenjen račun.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
            //PretragaRacunaForm.dispose();
        } else {
            throw (Exception) response.getError();
        }

    }

    public void stornirajRacun(Racun racun) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_STORNIRAJ_RACUN);
        request.setData(racun);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);

        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) in.readObject();

        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return;
        } else {
            throw (Exception) response.getError();
        }
    }

    public ArrayList<Racun> vratiRacune() throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_VRATI_RACUNE);
        ObjectOutputStream outSocket = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        outSocket.writeObject(request);
        ObjectInputStream inSocket = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());
        Response response = (Response) inSocket.readObject();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (ArrayList<Racun>) response.getData();
        } else {
            throw (Exception) response.getError();
        }
    }
}
