/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import domen.Kolac;
import domen.PDVStopa;
import form.KolacForm;
import form.GlavnaForma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.KolacModel;
import util.FormMode;

/**
 *
 * @author Sony
 */
public class KolacKontroler {

    private GlavnaForma mf;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    KolacModel model;
    KolacForm view;
    Kolac izabraniKolac = null;

    public KolacKontroler(GlavnaForma mf, FormMode mod, Kolac izabraniKolac) {
        this.mf = mf;
        this.izabraniKolac = izabraniKolac;
        this.model = new KolacModel();
        this.view = new KolacForm(mf, true);
        spremiFormu(mod);
    }

    private void spremiFormu(FormMode mod) {
        view.setLocationRelativeTo(null);
        switch (mod) {
            case NEW:
                view.getBtnSacuvaj().setVisible(true);
                view.getBtnIzmeni().setVisible(false);
                view.getBtnObrisi().setVisible(false);
                view.getBtnNoviKolac().setVisible(true);
                view.getBtnNoviKolac().setEnabled(false);
                popuniCombo();
                break;
            case VIEW:
                view.getBtnSacuvaj().setVisible(false);
                view.getBtnIzmeni().setVisible(true);
                view.getBtnObrisi().setVisible(true);
                view.getBtnNoviKolac().setVisible(false);
                popuniZaView();
                break;
            case EDIT:
                view.getBtnSacuvaj().setVisible(true);
                view.getBtnIzmeni().setVisible(false);
                view.getBtnObrisi().setVisible(false);
                view.getBtnNoviKolac().setVisible(false);
                popuniZaEdit();
                break;
        }
        view.addNewSaveListener(new SaveListener());
        view.addNewEditListener(new EditListener());
        view.addNewResetListener(new ResetListener());
        view.addNewDeleteListener(new DeleteListener());
        view.addNewComboListener(new ComboListener());
        view.addNewCenaListener(new CenaListener());

        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }

    private void popuniCombo() {
        try {
            List<PDVStopa> pdvList = model.vratiPDV();
            if (pdvList != null) {
                for (PDVStopa pdv : pdvList) {
                    view.getJComboPDV().addItem(pdv);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Greška prilikom učitavanja PDV-a.", "Greška!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void popuniZaView() {
        if (izabraniKolac != null) {
            view.getTxtID().setText(izabraniKolac.getKolacID() + "");
            view.getTxtNaziv().setText(izabraniKolac.getNaziv());
            view.getTxtOpis().setText(izabraniKolac.getOpis());
            view.getTxtCenaBezPDV().setText(izabraniKolac.getCenaBezPdv() + "");
            view.getTxtCenaSaPDV().setText(izabraniKolac.getCenaSaPdv() + "");
            popuniCombo();
            view.getJComboPDV().setSelectedIndex(izabraniKolac.getPdv().getStopaID() - 1);
            disableFields();
        }
    }

    private void popuniZaEdit() {
        if (izabraniKolac != null) {
            view.getTxtID().setText(izabraniKolac.getKolacID() + "");
            view.getTxtNaziv().setText(izabraniKolac.getNaziv());
            view.getTxtOpis().setText(izabraniKolac.getOpis());
            view.getTxtCenaBezPDV().setText(izabraniKolac.getCenaBezPdv() + "");
            view.getTxtCenaSaPDV().setText(izabraniKolac.getCenaSaPdv() + "");
            popuniCombo();
            view.getJComboPDV().setSelectedIndex(izabraniKolac.getPdv().getStopaID() - 1);

        }
    }

    private void disableFields() {
        view.getTxtID().setEnabled(false);
        view.getTxtNaziv().setEnabled(false);
        view.getTxtOpis().setEnabled(false);
        view.getTxtCenaBezPDV().setEnabled(false);
        view.getTxtCenaSaPDV().setEnabled(false);
        view.getJComboPDV().setEnabled(false);
    }

    private class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (view.getTxtID().getText().isEmpty()) {
                if (svePopunjeno()) {
                    int odg = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da zelite da sacuvate kolač?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (odg == JOptionPane.YES_OPTION) {
                        sacuvavanjeKolaca();
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Sva polja moraju biti popunjena.", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            } else {
                if (svePopunjeno()) {
                    int odg = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da zelite da izmenite kolač?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (odg == JOptionPane.YES_OPTION) {
                        menjanjeKolaca();
                        view.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Sva polja moraju biti popunjena.", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }

    }

    private void sacuvavanjeKolaca() {

        String naziv = view.getTxtNaziv().getText();
        String opis = view.getTxtOpis().getText();
        String cenaBezPDV = view.getTxtCenaBezPDV().getText();
        String cenaSaPDV = view.getTxtCenaSaPDV().getText();
        double parsiranaCenaBezPDV = -1;
        double parsiranaCenaSaPDV = -1;
        try {
            parsiranaCenaBezPDV = Double.parseDouble(cenaBezPDV);
            parsiranaCenaSaPDV = Double.parseDouble(cenaSaPDV);
            if (parsiranaCenaBezPDV <= 0 || parsiranaCenaSaPDV <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Greska prilikom unosa cene slovima. ");
            JOptionPane.showMessageDialog(view, "Morate uneti broj bez slova.", "Obaveštenje!", JOptionPane.INFORMATION_MESSAGE);
            return;
        } catch (Exception ex) {
            System.out.println("Greska prilikom unosa cene manje od 0 ");
            JOptionPane.showMessageDialog(view, "Cene moraju biti veće od 0.", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        PDVStopa pdv = (PDVStopa) view.getJComboPDV().getSelectedItem();
        Kolac k = new Kolac(-5, naziv, opis, parsiranaCenaSaPDV, parsiranaCenaBezPDV, pdv);
        try {
            k = model.sacuvajKolac(k);
        } catch (Exception ex) {
            Logger.getLogger(KolacForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(view, "Sistem ne može da zapamti kolač.", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        view.getTxtID().setText(k.getKolacID() + "");
        JOptionPane.showMessageDialog(view, "Sistem je zapamtio kolač.", "Obaveštenje!", JOptionPane.INFORMATION_MESSAGE);
        view.getBtnSacuvaj().setEnabled(false);
        view.getBtnNoviKolac().setEnabled(true);
    }

    private void menjanjeKolaca() {
        int id = Integer.parseInt(view.getTxtID().getText());
        String naziv = view.getTxtNaziv().getText();
        String opis = view.getTxtOpis().getText();

        String cenaBezPDV = view.getTxtCenaBezPDV().getText();
        String cenaSaPDV = view.getTxtCenaSaPDV().getText();
        double parsiranaCenaBezPDV = -1;
        double parsiranaCenaSaPDV = -1;
        try {
            parsiranaCenaBezPDV = Double.parseDouble(cenaBezPDV);
            parsiranaCenaSaPDV = Double.parseDouble(cenaSaPDV);
            if (parsiranaCenaBezPDV <= 0 || parsiranaCenaSaPDV <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Morate uneti broj bez slova.", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception ex) {
            Logger.getLogger(KolacForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(view, "Cene moraju biti veće od 0.", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        PDVStopa pdv = (PDVStopa) view.getJComboPDV().getSelectedItem();
        Kolac k = new Kolac(id, naziv, opis, parsiranaCenaSaPDV, parsiranaCenaBezPDV, pdv);

        try {
            model.izmeniKolac(k, view);
            JOptionPane.showMessageDialog(view, "Sistem je izmenio kolač.", "Obaveštenje!", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(Kolac.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(view, "Sistem ne može da izmeni kolač.", "Greška!", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    private boolean svePopunjeno() {
        if (view.getTxtNaziv().getText().isEmpty() || view.getTxtOpis().getText().isEmpty()
                || view.getTxtCenaBezPDV().getText().isEmpty() || view.getTxtCenaSaPDV().getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new KolacKontroler(mf, FormMode.EDIT, izabraniKolac);
            view.dispose();
        }
    }

    private class ResetListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            resetujPoljaForme();
            view.getBtnSacuvaj().setEnabled(true);
            view.getBtnNoviKolac().setEnabled(false);
        }

        private void resetujPoljaForme() {
            view.getTxtID().setText("");
            view.getTxtNaziv().setText("");
            view.getTxtOpis().setText("");
            view.getTxtCenaBezPDV().setText("");
            view.getTxtCenaSaPDV().setText("");
        }
    }

    private class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int odg = JOptionPane.showConfirmDialog(view, "Da li ste sigurni da želite da obrišete kolač?", "Izbor", JOptionPane.YES_NO_CANCEL_OPTION);
            if (odg == JOptionPane.YES_OPTION) {
                try {
                    String poruka = model.obrisiKolac(izabraniKolac);
                    JOptionPane.showMessageDialog(view, poruka);
                } catch (MySQLIntegrityConstraintViolationException exp) {
                    JOptionPane.showMessageDialog(view, "Sistem ne može da obriše kolač, jer se nalazi u stavkama postojećih računa.", "Upozorenje!", JOptionPane.ERROR_MESSAGE);
                    return;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Sistem ne može da obriše kolač.", "Upozorenje!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                view.dispose();
            }
        }
    }

    private class ComboListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            PDVStopa pdvStopa = (PDVStopa) view.getJComboPDV().getSelectedItem();
            if (!view.getTxtCenaBezPDV().getText().isEmpty()) {
                String cena = view.getTxtCenaBezPDV().getText();
                try {
                    int cenaBezPDV = Integer.parseInt(cena);
                    double pdv = pdvStopa.getPdv();
                    double cenaSaPDV = cenaBezPDV + (cenaBezPDV * pdv) / 100;
                    view.getTxtCenaSaPDV().setText(cenaSaPDV + "");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Unesite cenu brojevima.", "Greška!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class CenaListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            obradaCene();
        }

        private void obradaCene() {
            if (!view.getTxtCenaBezPDV().getText().isEmpty()) {
                String cena = view.getTxtCenaBezPDV().getText();
                try {
                    int cenaBezPDV = Integer.parseInt(cena);
                    if (cenaBezPDV <= 0) {
                        JOptionPane.showMessageDialog(view, "Cena mora biti broj veci od 0.", "GreŠka!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PDVStopa pdv = (PDVStopa) view.getJComboPDV().getSelectedItem();
                    double pdvStopa = pdv.getPdv();
                    double cenaSaPDV = cenaBezPDV + (cenaBezPDV * pdvStopa) / 100;
                    view.getTxtCenaSaPDV().setText(cenaSaPDV + "");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(view, "Unesite cenu brojevima.", "Greška!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
