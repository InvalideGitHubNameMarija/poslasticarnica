/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Racun;
import form.GlavnaForma;
import form.PretragaRacunaForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.RacunModel;
import modeli.TabelaRacunModel;

/**
 *
 * @author Sony
 */
public class PretragaRacunaKontroler {

    private GlavnaForma mf;
    private PretragaRacunaForm view;
    private RacunModel model;
    private ArrayList<Racun> racuni;
    private Racun izabraniRacun;
    PretragaRacunaKontroler prk;

    public PretragaRacunaKontroler(GlavnaForma mf) {
        this.mf = mf;
        view = new PretragaRacunaForm(mf, true);
        model = new RacunModel();
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        try {
            racuni = model.vratiRacune();
            TabelaRacunModel tm = new TabelaRacunModel(racuni);
            view.getTabelaRacun().setModel(tm);
            view.getTabelaRacun().getColumnModel().getColumn(0).setPreferredWidth(20);
            view.addNewPretragaListener(new PretragaListener());
            view.addDetailsListener(new DetailListener());
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            view.setResizable(false);
            view.setVisible(true);

        } catch (Exception ex) {
            Logger.getLogger(PretragaRacunaKontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class PretragaListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            //filtriranje
            String pretraga = view.getTxtFilter().getText();
            TableModel tm = view.getTabelaRacun().getModel();
            TableRowSorter<TableModel> trs = new TableRowSorter<>(tm);

            view.getTabelaRacun().setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(pretraga));
        }
    }

    private class DetailListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTabelaRacun().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Morate izabrati račun koji zelite da izmenite.", "Obaveštenje!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            Racun izabraniRacun = new Racun();
            int id = (int) view.getTabelaRacun().getValueAt(row, 0);
            for (Racun racun : racuni) {
                if (racun.getRacunID() == id) {
                    izabraniRacun = racun;
                }
            }
            try {
                new IzmenaRacunaKontroler(mf, izabraniRacun);
                TabelaRacunModel tm = (TabelaRacunModel) view.getTabelaRacun().getModel();
                racuni = model.vratiRacune();
                tm.setRacuni(racuni);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Sistem nije izmenio račun.", "Greška!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
