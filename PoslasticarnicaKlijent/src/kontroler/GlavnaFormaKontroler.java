/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import form.GlavnaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.LogInOutModel;
import sesija.Sesija;
import util.FormMode;

/**
 *
 * @author Sony
 */
public class GlavnaFormaKontroler {

    GlavnaForma view;
    LogInOutModel model;

    public GlavnaFormaKontroler() {
        view = new GlavnaForma();
        model = new LogInOutModel();
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        setTrenutniRadnik();
        view.getPanelOSoftveru().setVisible(false);
        view.addNewCakeListener(new NewCakeListener());
        view.addNewSearchCakeListener(new SearchCakeListener());
        view.addNewInvoiceListener(new InvoiceListener());
        view.addNewSearchInvoiceListener(new SearchInvoiceListener());
        view.addNewAboutSoftwareListener(new AboutListener());
        view.addLogOutListener(new LogOutListener());
        view.setExtendedState(JFrame.MAXIMIZED_BOTH);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }

    private class LogOutListener implements ActionListener {

        public LogOutListener() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            
            int pitanje = JOptionPane.showConfirmDialog(view, "Da li želite da se odjavite?","Izbor",JOptionPane.YES_NO_CANCEL_OPTION);
            if (pitanje == JOptionPane.YES_OPTION) {
                try {
                    boolean uspeh = model.odjaviRadnika(Sesija.getInstance().getTrenutniRadnik());
                    if (uspeh == true) {
                        JOptionPane.showMessageDialog(view, "Uspešno ste se odjavili!", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                        view.dispose();
                    } else {
                        JOptionPane.showMessageDialog(view, "Neuspešno ste se odjavili!", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Sistem ne može da Vas izloguje!", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private class AboutListener implements ActionListener {

        public AboutListener() {
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            //view.getPanelOSoftveru().setVisible(true);
            new AboutKontroler(view);
        }
    }

    private class NewCakeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new KolacKontroler(view, FormMode.NEW, null);
        }
    }

    private void setTrenutniRadnik() {
        String radnik = Sesija.getInstance().getTrenutniRadnik().getIme() + " " + Sesija.getInstance().getTrenutniRadnik().getPrezime();
        view.getLblPrijavljeni().setText("Trenutno prijavljeni radnik: " + radnik);
    }

    private class SearchCakeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new PretragaKolacaKontroler(view);
        }
    }

    private class InvoiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new NoviRacunKontroler(view,null);
        }
    }

    private class SearchInvoiceListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new PretragaRacunaKontroler(view);
        }
    }
}
