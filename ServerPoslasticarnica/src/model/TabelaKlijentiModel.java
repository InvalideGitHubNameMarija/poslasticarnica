/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import domen.Radnik;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Sony
 */
public class TabelaKlijentiModel extends AbstractTableModel{
    

    ArrayList<Radnik> radnici=new ArrayList<>();
    String kolone[]={"Ime","Prezime","Username","JMBG"};
    
    public TabelaKlijentiModel(ArrayList<Radnik> radnici) {
        this.radnici = radnici;
    }
    
    
    @Override
    public int getRowCount() {
        return radnici.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Radnik radnik=radnici.get(rowIndex);
        switch(columnIndex){
            case 0: return radnik.getIme();
            case 1: return radnik.getPrezime();
            case 2: return radnik.getUsername();
            case 3: return radnik.getJmbg();
            default: return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

//    public void addRadnik(Radnik radnik) {
//        for (Radnik radnik1 : radnici) {
//            if(!radnici.contains(radnik))
//                radnici.add(radnik);
//            
//        }
//        fireTableDataChanged();
//    }

    public void setPodaci(ArrayList<Radnik> radnici) {
        this.radnici=radnici;
        fireTableDataChanged();
    }
    
}
