/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Kolac;
import form.GlavnaForma;
import form.PretragaKolacaForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.KolacModel;
import modeli.TabelaKolacModel;
import util.FormMode;

/**
 *
 * @author Sony
 */
public class PretragaKolacaKontroler {

    private GlavnaForma mf;
    KolacModel model;
    PretragaKolacaForm view;
    ArrayList<Kolac> kolaci;

    public PretragaKolacaKontroler(GlavnaForma mf) {
        this.mf = mf;
        this.model = new KolacModel();
        this.view = new PretragaKolacaForm(mf, true);
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);

        popuniTabeluKolac();
        view.addNewSelectListener(new SelectListener());
        view.addNewFilterListener(new FilterListener());

        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }

    public void popuniTabeluKolac() {
        try {
            kolaci = model.vratiKolace();
            TabelaKolacModel modelKolac = new TabelaKolacModel(kolaci);
            view.getTblKolac().setModel(modelKolac);
            TableColumnModel tcm = view.getTblKolac().getColumnModel();
            tcm.getColumn(0).setPreferredWidth(3);
            tcm.getColumn(2).setPreferredWidth(250);
            tcm.getColumn(3).setPreferredWidth(8);
            tcm.getColumn(4).setPreferredWidth(8);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Nema kolača. Server je pao!", "Greška!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Nema kolača.", "Greška!", JOptionPane.ERROR_MESSAGE);
            view.dispose();
        }
    }

    private class SelectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = view.getTblKolac().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Morate selektovati kolač!", "Upozorenje!", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            TabelaKolacModel tmp = (TabelaKolacModel) view.getTblKolac().getModel();
            int id = (int) view.getTblKolac().getValueAt(row, 0);
            for (Kolac kolac : kolaci) {
                if (kolac.getKolacID() == id) {
                    Kolac kolac2=kolac;
                    new KolacKontroler(mf, FormMode.VIEW, kolac2);
                    popuniTabeluKolac();
                    return;
                }
            }
        }
    }

    private class FilterListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            filtriraj(view.getTxtFilter().getText());
        }
        
        private void filtriraj(String text) {
            TableModel tm = view.getTblKolac().getModel();
            TableRowSorter<TableModel> trs = new TableRowSorter<>(tm);

            view.getTblKolac().setRowSorter(trs);
            trs.setRowFilter(RowFilter.regexFilter(text));
        }
    }
}
