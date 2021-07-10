/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Kolac;
import domen.Racun;
import domen.Radnik;
import domen.StavkaRacuna;
import form.GlavnaForma;
import form.IzmeneRacuneForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.RacunModel;
import modeli.TabelaStavkaRacunaModel;
import sesija.Sesija;
import util.FormMode;
import util.StavkaStatus;

/**
 *
 * @author Sony
 */
public class IzmenaRacunaKontroler {

    private GlavnaForma mf;
    private RacunModel model;
    private IzmeneRacuneForm view;
    private Racun izabraniRacun;
    ArrayList<Kolac> kolaci;

    public IzmenaRacunaKontroler(GlavnaForma mf, Racun izabraniRacun) {
        this.mf = mf;
        model = new RacunModel();
        view = new IzmeneRacuneForm(mf, true);
        kolaci = new ArrayList<>();
        this.izabraniRacun = izabraniRacun;
        if (this.izabraniRacun != null) {
            proveriStorniranost();
        }
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date d = new Date();
            srediTabelu();

            view.getTxtCenaSaPDV().setText(izabraniRacun.getUkupnoSaPdv() + "");
            view.addNewStornirajListener(new StornirajListener());
            view.addNewEditListener(new EditListener());
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setResizable(false);
            view.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(IzmenaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void osvezi() {
        TabelaStavkaRacunaModel mt = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
        double cenaRacuna = 0;
        ArrayList<StavkaRacuna> stavke = mt.getStavke();
        for (StavkaRacuna stavkaRacuna : stavke) {
            cenaRacuna += stavkaRacuna.getVrednostStavkeSaPdv();
        }
        view.getTxtCenaSaPDV().setText(cenaRacuna + "");
    }

    private void srediTabelu() {
        try {
            TabelaStavkaRacunaModel tm = new TabelaStavkaRacunaModel(null, this);
            view.getTabelaStavke().setModel(tm);
            tm.setStavke(izabraniRacun.getStavke());
        } catch (Exception ex) {
            Logger.getLogger(IzmenaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obavestiKolicinu() {
        JOptionPane.showMessageDialog(view, "Količina mora biti BROJ veći od 0!");
    }

    public void obavesti() {
        JOptionPane.showMessageDialog(view, "Stavka je već promenjena. Nije moguće menjati stavku 2 puta.");
    }

    public boolean menjanje() {
        int odg = JOptionPane.showConfirmDialog(view, "Da li zelite da izmenite stavku?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
        if (odg == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    public void osvezi(int racunID) {
        double ukupnoSaPDV = 0;
        double ukupnoBezPDV = 0;
        double iznosPDV = 0;

        TabelaStavkaRacunaModel mt = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
        ArrayList<StavkaRacuna> stavke = mt.getStavke();
        for (StavkaRacuna stavkaRacuna : stavke) {
            ukupnoSaPDV += stavkaRacuna.getVrednostStavkeSaPdv();
            ukupnoBezPDV += stavkaRacuna.getVrednostStavkeBezPdv();
        }
        iznosPDV = ukupnoSaPDV - ukupnoBezPDV;
        Radnik radnik = Sesija.getInstance().getTrenutniRadnik();
        Date datum = izabraniRacun.getDatum();
        izabraniRacun = new Racun(racunID, datum, ukupnoSaPDV, ukupnoBezPDV, iznosPDV, false, radnik, stavke);
    }

    private void proveriStorniranost() {
        if (izabraniRacun.isStorniran()) {

            JOptionPane.showMessageDialog(view, "Izabrani račun je već storniran. Nije moguće izmeniti ga.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
            view.getTabelaStavke().setEnabled(false);
            view.getBtnizmeni().setEnabled(false);
            view.getBtnStorniraj().setEnabled(false);

        } else {
            view.getTabelaStavke().setEnabled(true);

            view.getBtnizmeni().setEnabled(true);
            view.getBtnStorniraj().setVisible(true);
        }
    }

    private class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            azurirajIzmene(izabraniRacun);
        }

        private void azurirajIzmene(Racun izabraniRacun) {
            try {
                Radnik radnik = Sesija.getInstance().getTrenutniRadnik();

                TabelaStavkaRacunaModel tm = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
                izabraniRacun.setStavke(tm.getStavke());

                ArrayList<StavkaRacuna> noveStavke = izabraniRacun.getStavke();
                for (StavkaRacuna novo : noveStavke) {

                    if (view.getTabelaStavke().isEditing()) {
                        JOptionPane.showMessageDialog(view, "Sistem ne menja jer  kolcina nije broj veci od 0.");
                        return;
                    }

                    if (novo.getKolicina() > 0) {
                        int odgovor = JOptionPane.showConfirmDialog(view, "Da li želite da izmenite račun?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
                        if (odgovor == JOptionPane.YES_OPTION) {

                            model.izmeniRacun(izabraniRacun, view);

                            view.dispose();
                            return;

                        }
                    } else {
                        JOptionPane.showMessageDialog(view, "Količina mora biti BROJ veći od nule!", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(IzmenaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(view, "Količina mora biti broj!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                Logger.getLogger(IzmenaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(IzmenaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(view, "Račun nije izmenjen.", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private class StornirajListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int odg = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da želite da stornirate račun?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
            if (odg == JOptionPane.YES_OPTION) {

                if (izabraniRacun.isStorniran()) {
                    JOptionPane.showMessageDialog(view, "Račun je storniran.\nNe možete ga stornirati ponovo.", "Obaveštenje!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                ArrayList<StavkaRacuna> stavke = new ArrayList<>();
                for (StavkaRacuna sr : izabraniRacun.getStavke()) {
                    stavke.add(sr);
                }
                try {
                    izabraniRacun.setStorniran(true);
                    izabraniRacun.setDatum(izabraniRacun.getDatum());
                    izabraniRacun.setUkupnoSaPdv(0);
                    izabraniRacun.setUkupnoBezPdv(0);
                    izabraniRacun.setIznosPDV(0);
                    model.stornirajRacun(izabraniRacun);

                    JOptionPane.showMessageDialog(null, "Sistem je stornirao račun.", "Uspesno!", JOptionPane.INFORMATION_MESSAGE);
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Sistem nije stornirao račun.", "Greska!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class PriceListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            obradaCene();
        }
    }

    private void obradaCene() {
        if (izabraniRacun != null && !izabraniRacun.isStorniran()) {
            double cena = 0;
            for (StavkaRacuna stavkaRacuna : izabraniRacun.getStavke()) {
                cena = stavkaRacuna.getVrednostStavkeSaPdv();
            }
            view.getTxtCenaSaPDV().setText(cena + "");
        }

    }
}
