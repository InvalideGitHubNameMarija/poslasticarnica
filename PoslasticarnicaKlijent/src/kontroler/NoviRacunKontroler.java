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
import form.RacunForm;
import form.GlavnaForma;
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
import javax.swing.RowFilter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.RacunModel;
import modeli.TabelaKolacModel;
import modeli.TabelaStavkaRacunaModel;
import sesija.Sesija;
import util.StavkaStatus;

/**
 *
 * @author Sony
 */
public class NoviRacunKontroler {

    private GlavnaForma mf;
    private RacunModel model;
    private RacunForm view;
    //private Racun izabraniRacun;
    ArrayList<StavkaRacuna> stara = new ArrayList<>();
    ArrayList<Kolac> kolaci;

    public NoviRacunKontroler(GlavnaForma mf, Racun izabraniRacun) {
        this.mf = mf;
        model = new RacunModel();
        view = new RacunForm(mf, true);
        kolaci = new ArrayList<>();
        //this.izabraniRacun = izabraniRacun;
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date d = new Date();
            srediTabele();
            view.getBtnSacuvaj().setEnabled(true);
            view.getTxtDatumIzdavanja().setText(sdf.format(d));
            view.addNewSaveListener(new SaveListener());
            view.addNewAddListener(new AddListener());
            view.addNewDeleteListener(new DeleteListener());
            view.addNewPriceListener(new PriceListener());
            //view.addNewEditListener(new EditListener());
            view.addNewFilterListener(new FilterListener());

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setResizable(false);
            view.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(NoviRacunKontroler.class.getName()).log(Level.SEVERE, null, ex);
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

    private void srediTabele() {
        try {
            kolaci = model.vratiKolace();
            TabelaKolacModel tkm = new TabelaKolacModel(kolaci);
            view.getTabelaKolaci().setModel(tkm);
            TableColumnModel tcm = view.getTabelaKolaci().getColumnModel();
            view.getTabelaStavke().getColumnModel().getColumn(0).setPreferredWidth(5);
            tcm.getColumn(0).setPreferredWidth(3);
            tcm.getColumn(2).setPreferredWidth(250);
            tcm.getColumn(3).setPreferredWidth(8);
            tcm.getColumn(4).setPreferredWidth(8);

            TabelaStavkaRacunaModel tm = new TabelaStavkaRacunaModel(this,null);
            view.getTabelaStavke().setModel(tm);

        } catch (Exception ex) {
            Logger.getLogger(NoviRacunKontroler.class.getName()).log(Level.SEVERE, null, ex);
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

    public void vratiStaruKolicinu(ArrayList<StavkaRacuna> stareStavke) {
        stara = stareStavke;

    }

    /*public void osvezi(int racunID) {
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
       // Date datum = izabraniRacun.getDatum();
       // izabraniRacun = new Racun(racunID, datum, ukupnoSaPDV, ukupnoBezPDV, iznosPDV, false, radnik, stavke);
    }*/


    private class FilterListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            filtriraj(view.getTxtFilter().getText());
        }

        private void filtriraj(String text) {
            TableModel tm = view.getTabelaKolaci().getModel();
            TableRowSorter<TableModel> trs = new TableRowSorter<>(tm);

            view.getTabelaKolaci().setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(text));
        }
    }

    //save listener for Racun
    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date datum = sdf.parse(view.getTxtDatumIzdavanja().getText());
                double ukupnoSaPDV = Double.parseDouble(view.getTxtCenaSaPDV().getText());
                TabelaStavkaRacunaModel mt = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
                ArrayList<StavkaRacuna> list = mt.getStavke();
                if (list.isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Racun mora imati barem jednu stavku");
                    return;
                }

                Radnik radnik = Sesija.getInstance().getTrenutniRadnik();

                double ukupnoBezPDV = 0;
                for (StavkaRacuna stavkaRacuna : list) {
                    ukupnoBezPDV += stavkaRacuna.getVrednostStavkeBezPdv();
                }

                double iznosPDV = ukupnoSaPDV - ukupnoBezPDV;

                Racun racun = new Racun(-1, datum, ukupnoSaPDV, ukupnoBezPDV, iznosPDV, false, radnik, list);
                for (StavkaRacuna stavka : racun.getStavke()) {
                    stavka.setRacun(racun);
                }
                for (int i = 0; i < racun.getStavke().size(); i++) {
                    racun.getStavke().get(i).setStavkaRacunaID(i + 1);
                }
                try {
                    int odgovor = JOptionPane.showConfirmDialog(view, "Da li želite da sačuvate račun?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (odgovor == JOptionPane.YES_OPTION) {

                        model.sacuvajRacun(racun);
                        JOptionPane.showMessageDialog(null, "Sistem je sačuvao račun.", "Uspešno!", JOptionPane.INFORMATION_MESSAGE);
                        view.dispose();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Sistem nije sačuvao račun.", "Greška!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException ex) {
                Logger.getLogger(NoviRacunKontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //add listener for stavkaRacuna
    private class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                TabelaStavkaRacunaModel mt = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
                //Kolac k = (Kolac) view.getCmbKolaci().getSelectedItem();
                // TabelaKolacModel tkm = (TabelaKolacModel) view.getTabelaKolaci().getModel();
                int selectedRow = view.getTabelaKolaci().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Morate selektovati kolač", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Kolac k = new Kolac();
                TabelaKolacModel tkm = (TabelaKolacModel) view.getTabelaKolaci().getModel();

                int id = (int) view.getTabelaKolaci().getValueAt(selectedRow, 0);
                for (Kolac kolac : kolaci) {
                    if (kolac.getKolacID() == id) {
                        k = kolac;
                    }
                }
                if (view.getTxtKolicina().getText().isEmpty()) {
                    JOptionPane.showMessageDialog(view, "Morate uneti količinu");
                    return;
                }
                int kolicina = 0;
                try {
                    kolicina = Integer.parseInt(view.getTxtKolicina().getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Količina mora biti ceo broj, bez slova", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double vrednostBezPDV = k.getCenaBezPdv() * kolicina;
                double vrednostSaPDV = Double.parseDouble(view.getTxtCenaStavke().getText());
                double pdv = vrednostSaPDV - vrednostBezPDV;
                StavkaStatus status = null;
                StavkaRacuna stavka = new StavkaRacuna(Integer.MIN_VALUE, kolicina, vrednostSaPDV, vrednostBezPDV, pdv, null, k, status);
                stavka.setStatus(StavkaStatus.NEIZMENJENA);

                ArrayList<StavkaRacuna> stavke = mt.getStavke();
                if (kolicina > 0) {
                    if (stavke.contains(stavka)) {
                        for (StavkaRacuna stavkaRacuna : stavke) {
                            if (stavkaRacuna.getKolac().equals(stavka.getKolac())) {

                                if (stavkaRacuna.getStatus() != StavkaStatus.IZMENJENA) {
                                    int odg = JOptionPane.showConfirmDialog(view, "U računu već postoji stavka sa izabranim kolačem ("
                                            + stavkaRacuna.getKolac().getNaziv() + ") \n Da li želite da povećate količinu sa "
                                            + stavkaRacuna.getKolicina() + " na " + (stavkaRacuna.getKolicina() + kolicina), "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
                                    if (odg == JOptionPane.YES_OPTION) {
                                        stavkaRacuna.setKolicina(stavkaRacuna.getKolicina() + kolicina);
                                        stavkaRacuna.setVrednostStavkeBezPdv(stavkaRacuna.getKolac().getCenaBezPdv() * stavkaRacuna.getKolicina());
                                        stavkaRacuna.setVrednostStavkeSaPdv(stavkaRacuna.getKolac().getCenaSaPdv() * stavkaRacuna.getKolicina());
                                        mt.setStavke(stavke);

                                    } else {
                                        view.getTxtKolicina().setText("");
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(view, "Stavka je već izmenjena. Nije moguće promeniti je ponovo.");
                                }
                            }
                        }
                    } else {
                        mt.add(stavka);
                        double trenutnaCenaRacuna = Double.parseDouble(view.getTxtCenaSaPDV().getText());
                        view.getTxtCenaSaPDV().setText((trenutnaCenaRacuna + stavka.getVrednostStavkeSaPdv()) + "");
                        view.getTxtKolicina().setText("");

                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Kolicina mora biti veća od nule!", "Greška!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (Exception ex) {
                Logger.getLogger(NoviRacunKontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //delete listener for stavkaRacuna
    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TabelaStavkaRacunaModel mt = (TabelaStavkaRacunaModel) view.getTabelaStavke().getModel();
            int selectedRow = view.getTabelaStavke().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Morate selektovati stavku", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            StavkaRacuna stavka = mt.getStavke().get(selectedRow);
            double trenutnaCenaRacuna = Double.parseDouble(view.getTxtCenaSaPDV().getText());
            view.getTxtCenaSaPDV().setText((trenutnaCenaRacuna - stavka.getVrednostStavkeSaPdv()) + "");
            mt.remove(selectedRow);
        }
    }

    private class PriceListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            obradaCene();
        }
    }

    private void obradaCene() {
        if (!view.getTxtKolicina().getText().isEmpty()) {
            String quantity = view.getTxtKolicina().getText();
            try {
                int kolicina = Integer.parseInt(quantity);
                //Kolac k = (Kolac) view.getCmbKolaci().getSelectedItem();
                TabelaKolacModel tkm = (TabelaKolacModel) view.getTabelaKolaci().getModel();
                int selectedRow = view.getTabelaKolaci().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(view, "Morate selektovati kolač", "Upozorenje!", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Kolac k = new Kolac();
                int id = (int) view.getTabelaKolaci().getValueAt(selectedRow, 0);
                for (Kolac kolac : kolaci) {
                    if (kolac.getKolacID() == id) {
                        k = kolac;
                    }
                }
                double cenaStavke = kolicina * k.getCenaSaPdv();
                view.getTxtCenaStavke().setText(cenaStavke + "");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Unesite količinu brojevima.", "Greška!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
