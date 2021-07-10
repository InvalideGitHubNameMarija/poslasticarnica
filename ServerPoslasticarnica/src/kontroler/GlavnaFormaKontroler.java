/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.GenericEntity;
import domen.Radnik;
import form.MainForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.TabelaKlijentiModel;
import server.Server;
import sesija.SesijaKlijenti;
import skladiste.baza.konekcija.Konekcija;
import util.Konstante;
import util.PodesavanjaLoader;

/**
 *
 * @author Sony
 */
public class GlavnaFormaKontroler {

    MainForm view;
    private Server server;
    private static GlavnaFormaKontroler instanca;

    public GlavnaFormaKontroler() {
        view = new MainForm();
        srediFormu();
    }

    public static GlavnaFormaKontroler getInstanca() {
        if (instanca == null) {
            instanca = new GlavnaFormaKontroler();
        }
        return instanca;
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        view.addNewStartServerListener(new StartServerListener());
        view.addNewStopServerListener(new StopServerListener());
        view.addNewSettingsListener(new SettingsListener());
        view.addNewAboutListener(new AboutListener());
        view.setResizable(false);
        view.setVisible(true);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.getBtnZaustavi().setEnabled(false);
        view.getTxtStatus().setText("Server još uvek nije pokrenut.");
        TabelaKlijentiModel tkm = new TabelaKlijentiModel(SesijaKlijenti.getInstanca().getRadnici());
        view.getTabelaKlijenti().setModel(tkm);
    }

    public void osveziTabelu() {
        TabelaKlijentiModel tkm = (TabelaKlijentiModel) view.getTabelaKlijenti().getModel();
        tkm.setPodaci(SesijaKlijenti.getInstanca().getRadnici());
    }

    private class StartServerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (server == null || !server.isAlive()) {
                try {
                    server = new Server(Integer.parseInt(PodesavanjaLoader.getInstance().getProperty(Konstante.PORT)));
                    server.start();

                    Konekcija.getInstance().getConnection();

                    view.getTxtStatus().setText("Server radi.");
                    JOptionPane.showMessageDialog(view, "Server je započeo rad.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    view.getBtnPokreni().setEnabled(false);
                    view.getBtnZaustavi().setEnabled(true);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Port " + PodesavanjaLoader.getInstance().getProperty(Konstante.PORT) + " je zauzet.", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(view, "Neispravni podaci o bazi!", "Greška", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("greska je: " + ex.getMessage());
                    try {
                        server.stopServer();
                        server = null;
                    } catch (IOException ex1) {
                        JOptionPane.showMessageDialog(view, "Neuspešno zatvaranje servera!", "Greška", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(view, "Server već radi.", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class StopServerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                server.stopServer();
                server = null;
                Konekcija.getInstance().closeConnection();
                JOptionPane.showMessageDialog(view, "Server je zaustavljen.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                view.getTxtStatus().setText("Server nije pokrenut.");
                view.getBtnPokreni().setEnabled(true);
                view.getBtnZaustavi().setEnabled(false);
                SesijaKlijenti.getInstanca().isprazniRadnike();
                osveziTabelu();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(view, "Neuspešno zaustavljanje servera.", "Greška", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(view, "Neuspešno zatvaranje baze za rad.", "Greška", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class SettingsListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new PodesavanjaKontroler(view);
        }
    }

    private class AboutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new AboutKontroler(view);
        }
    }
}
