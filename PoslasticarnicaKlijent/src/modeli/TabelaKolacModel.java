/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modeli;

import domen.Kolac;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sony
 */
public class TabelaKolacModel extends AbstractTableModel {

    ArrayList<Kolac> kolaci;
    String[] header = new String[]{"ID", "Naziv", "Opis", "Cena(bez PDV)", "Cena (sa PDV)"};
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public TabelaKolacModel() {
    }

    public TabelaKolacModel(ArrayList<Kolac> kolaci) {
        this.kolaci = kolaci;
    }

    @Override
    public int getRowCount() {
        return kolaci.size();
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
        Kolac kolac = kolaci.get(row);
        switch (col) {
            case 0:
                return kolac.getKolacID();
            case 1:
                return kolac.getNaziv();

            case 2:
                return kolac.getOpis();
            case 3:
                return kolac.getCenaBezPdv();

            case 4:
                return kolac.getCenaSaPdv();
            default:
                return "N/A";
        }
    }

    public void setKolaci(ArrayList<Kolac> kolaci) {
        
        this.kolaci = kolaci;
    }

    public ArrayList<Kolac> getKolaci() {
        return kolaci;
    }
}
