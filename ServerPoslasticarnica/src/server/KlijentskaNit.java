/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;
import transfer.Request;
import transfer.Response;
import util.Operation;
import util.ResponseStatus;
import domen.GenericEntity;
import kontroler.GlavnaFormaKontroler;
import sesija.SesijaKlijenti;

/**
 *
 * @author Sony
 */
public class KlijentskaNit extends Thread {

    private Socket socket;

    public KlijentskaNit(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            communication();
        } catch (Exception ex) {
            System.out.println("Klijent se otkačio!");
        }
    }

    private void communication() throws Exception {
        while (!isInterrupted()) {
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            Object object = inSocket.readObject();
            if (object instanceof Request) {
                Request request = (Request) object;
                Response response = handleRequest(request);
                sendResponse(socket, response);
            }
        }
    }

    private Response handleRequest(Request request) {
        int operation = request.getOperation();
        Response response = new Response();
        //System.out.println("Operacija: " + request.getOperation());

        switch (operation) {
            case Operation.OPERATION_PRIJAVA: {
                try {
                    GenericEntity objekat = Kontroler.getInstance().prijaviRadnika((GenericEntity) request.getData());

                    if (SesijaKlijenti.getInstanca().getRadnici().contains(objekat)) {
                        response.setStatus(ResponseStatus.ERROR);
                    } else {
                        response.setStatus(ResponseStatus.SUCCESS);
                        response.setData(objekat);
                        SesijaKlijenti.getInstanca().dodajRadnika(objekat);
                        GlavnaFormaKontroler.getInstanca().osveziTabelu();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_VRATI_PDV: {
                try {
                    List<GenericEntity> pdv = Kontroler.getInstance().vratiPDVStope();
                    response.setStatus(ResponseStatus.SUCCESS);
                    response.setData(pdv);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_SACUVAJ_KOLAC: {
                try {
                    int index = Kontroler.getInstance().sacuvajKolac((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                    response.setData(index);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_VRATI_KOLACE: {
                try {
                    List<GenericEntity> kolaci = Kontroler.getInstance().vratiKolace();
                    response.setStatus(ResponseStatus.SUCCESS);
                    response.setData(kolaci);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_OBRISI_KOLAC: {
                try {
                    Kontroler.getInstance().obrisiKolac((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                } catch (MySQLIntegrityConstraintViolationException exp) {
                    System.out.println("SQL EXCEPTION:" + exp.getMessage());
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(exp);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_IZMENI_KOLAC: {
                try {
                    Kontroler.getInstance().izmeniKolac((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_SACUVAJ_RACUN: {
                try {
                    Kontroler.getInstance().sacuvajRacun((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_VRATI_RACUNE: {
                try {
                    List<GenericEntity> racuni = Kontroler.getInstance().vratiRacune();
                    response.setStatus(ResponseStatus.SUCCESS);
                    response.setData(racuni);
                } catch (Exception ex) {
                    Logger.getLogger(KlijentskaNit.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_IZMENI_RACUN: {
                try {
                    Kontroler.getInstance().izmeniRacun((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_STORNIRAJ_RACUN: {
                try {
                    Kontroler.getInstance().stornirajRacun((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setError(ex);
                }
            }
            break;
            case Operation.OPERATION_ODJAVA: {
                try {
                    Kontroler.getInstance().odjaviRadnika((GenericEntity) request.getData());
                    response.setStatus(ResponseStatus.SUCCESS);
                    response.setData(true);
                    GlavnaFormaKontroler.getInstanca().osveziTabelu();

                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    response.setStatus(ResponseStatus.ERROR);
                    response.setData(false);
                    response.setError(ex);
                }
            }
            break;
        }
        return response;
    }

    private void sendResponse(Socket socket, Response response) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(response);
    }
}
