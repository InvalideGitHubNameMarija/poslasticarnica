/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Racun;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sony
 */
public class TabelaRacunModel extends AbstractTableModel {

    ArrayList<Racun> racuni = new ArrayList<>();
    String[] header = new String[]{"ID", "Datum", "Ukupno (bez PDV)", "Iznos PDV", "Ukupno (sa PDV)", "Storniran", "Radnik"};
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public TabelaRacunModel(ArrayList<Racun> racuni) {
        this.racuni = racuni;
    }

    @Override
    public int getRowCount() {
        if (racuni == null) {
            return 0;
        }
        return racuni.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public String getColumnName(int col) {
        return header[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Racun r = racuni.get(row);
        switch (col) {
            case 0:
                return r.getRacunID();
            case 1:
                return sdf.format(r.getDatum());
            case 2:
                return r.getUkupnoBezPdv();
            case 3:
                return r.getIznosPDV();
            case 4:
                return r.getUkupnoSaPdv();
            case 5:
                if(r.isStorniran()==true) return "Storniran";
                return "Nije storniran";
            case 6:
                return r.getRadnik().getIme() + " " + r.getRadnik().getPrezime();
            default:
                return "N/A";
        }
    }

    public ArrayList<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(ArrayList<Racun> racuni) {
        this.racuni = racuni;
        fireTableDataChanged();
    }
}
