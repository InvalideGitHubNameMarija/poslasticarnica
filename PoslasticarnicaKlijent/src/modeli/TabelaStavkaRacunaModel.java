/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Radnik;
import domen.StavkaRacuna;
import form.GlavnaForma;
import form.IzmeneRacuneForm;
import form.RacunForm;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import kontroler.NoviRacunKontroler;
import model.RacunModel;
import kontroler.IzmenaRacunaKontroler;
import sesija.Sesija;
import util.FormMode;
import util.StavkaStatus;

/**
 *
 * @author Sony
 */
public class TabelaStavkaRacunaModel extends AbstractTableModel {

    NoviRacunKontroler nk;
    IzmenaRacunaKontroler ik;
    private ArrayList<StavkaRacuna> stavke;
    private RacunForm view;
    private GlavnaForma mf;

    //ovo
    FormMode fm;
    private String[] header = {"Redni broj", "Naziv kolača", "Količina", "Cena (Bez PDV)", "Cena (Sa PDV)", "Status"};

    //ovo 2
    private Class[] columnClasses = new Class[]{Integer.class, String.class, Integer.class, Float.class, Float.class, StavkaStatus.class};
    private ArrayList<StavkaRacuna> stareStavke = new ArrayList<>();

    public TabelaStavkaRacunaModel(NoviRacunKontroler nk,IzmenaRacunaKontroler ik) {
        stavke = new ArrayList<>();
        view = new RacunForm(mf, true);
        this.nk = nk;
        this.ik=ik;
         //ovo
    }

    @Override
    public int getRowCount() {
        if (stavke.isEmpty()) {
            return 0;
        }
        return stavke.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        StavkaRacuna stavka = stavke.get(row);
        switch (col) {
            case 0:
                return ++row;
            case 1:
                return stavka.getKolac().getNaziv();
            case 2:
                return stavka.getKolicina();
            case 3:
                return stavka.getVrednostStavkeBezPdv();
            case 4:
                return stavka.getVrednostStavkeSaPdv();
            case 5:
                return stavka.getStatus();
            default:
                return "N/A";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (nk!=null) {
            StavkaRacuna stavka = stavke.get(rowIndex);
            switch (columnIndex) {
                case 2:
                    int kolicina = (int) aValue;
                    if (kolicina < 0) {
                        return;
                    }
                    stavka.setKolicina(kolicina);
                case 4:
                    stavka.setVrednostStavkeSaPdv(stavka.getKolicina() * stavka.getKolac().getCenaSaPdv());
                    fireTableDataChanged();
            }
            nk.osvezi();
        } else if (ik!=null) {
            StavkaRacuna stavka1 = stavke.get(rowIndex);
            StavkaRacuna stavka = new StavkaRacuna(stavke.get(rowIndex).getStavkaRacunaID(), stavke.get(rowIndex).getKolicina(),
                    stavke.get(rowIndex).getVrednostStavkeSaPdv(), stavke.get(rowIndex).getVrednostStavkeBezPdv(), stavke.get(rowIndex).getIznosPdv(),
                    stavke.get(rowIndex).getRacun(), stavke.get(rowIndex).getKolac(), stavke.get(rowIndex).getStatus());

            if (!stareStavke.contains(stavka)) {
                stareStavke.add(stavka);
            }
            StavkaStatus status = StavkaStatus.NEIZMENJENA;
            switch (columnIndex) {
                case 2:
                    int kolicina = (int) aValue;
                    if (kolicina <= 0) {
                        ik.obavestiKolicinu();
                        return;
                    }
                    if (stavka1.getStatus().equals(StavkaStatus.IZMENJENA)) {
                        ik.obavesti();
                        return;
                    }
                    if (ik.menjanje() == true) {
                        stavka1.setKolicina(kolicina);
                        status = StavkaStatus.U_PROCESU_MENJANJA;
                    } else {
                        status = StavkaStatus.NEIZMENJENA;
                    }
                case 3:
                    stavka1.setVrednostStavkeBezPdv(stavka1.getKolicina() * stavka1.getKolac().getCenaBezPdv());
                case 4:
                    stavka1.setVrednostStavkeSaPdv(stavka1.getKolicina() * stavka1.getKolac().getCenaSaPdv());
                    fireTableDataChanged();
                case 5:
                    stavka1.setStatus(status);
            }
            ik.osvezi();
            ik.osvezi(stavka1.getRacun().getRacunID());
        }
    }

    @Override
    public String getColumnName(int col) {
        return header[col];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            return true;
        }
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 2) {
            return Integer.class;
        }
        return Object.class;
    }

    public ArrayList<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(ArrayList<StavkaRacuna> stavke) {
        this.stavke = stavke;
        fireTableDataChanged();
        if(nk!=null)
        nk.osvezi();
    }

    public void add(StavkaRacuna b) {

        stavke.add(b);
        fireTableDataChanged();
    }

    public void remove(int selectedRow) {
        stavke.remove(selectedRow);
        fireTableDataChanged();
    }
}
