/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sesija;

import domen.Radnik;
import java.net.Socket;

/**
 *
 * @author Sony
 */
public class Sesija {

    private static Sesija instanca;
    private Radnik trenutniRadnik;
    private Socket socket;

    private Sesija() {
    }

    public static Sesija getInstance() {
        if (instanca == null) {
            instanca = new Sesija();
        }
        return instanca;
    }

    public Radnik getTrenutniRadnik() {
        return trenutniRadnik;
    }

    public void setTrenutniRadnik(Radnik trenutniRadnik) {
        this.trenutniRadnik = trenutniRadnik;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
