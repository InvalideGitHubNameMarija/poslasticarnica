/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Radnik;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sesija.Sesija;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;

/**
 *
 * @author Sony
 */
public class LogInOutModel {

    public Radnik prijaviRadnika(String username, String password) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_PRIJAVA);

        Radnik r = new Radnik();
        r.setUsername(username);
        r.setPassword(password);

        request.setData(r);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());

        Response response = (Response) in.readObject();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (Radnik) response.getData();
        } else if (response.getStatus() == ResponseStatus.ERROR && response.getError() == null) {
            return null;
        } else {
            throw (Exception) response.getError();
        }
    }

    public boolean odjaviRadnika(Radnik radnik) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.OPERATION_ODJAVA);

        request.setData(radnik);

        ObjectOutputStream oos = new ObjectOutputStream(Sesija.getInstance().getSocket().getOutputStream());
        oos.writeObject(request);
        ObjectInputStream in = new ObjectInputStream(Sesija.getInstance().getSocket().getInputStream());

        Response response = (Response) in.readObject();
        if (response.getStatus() == ResponseStatus.SUCCESS) {
            return (boolean) response.getData();
        } else {
            throw (Exception) response.getError();
        }
    }
}
