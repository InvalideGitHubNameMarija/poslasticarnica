/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import form.MainForm;
import form.PodesavanjaForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import util.Konstante;
import util.PodesavanjaLoader;

/**
 *
 * @author Sony
 */
public class PodesavanjaKontroler {

    PodesavanjaForm view;
    private MainForm mf;

    public PodesavanjaKontroler(MainForm mainform) {
        this.mf = mainform;
        this.view = new PodesavanjaForm(mf, true);
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.addSettingsListener(new SettingsPortAndDatabaseListener());
        view.setResizable(false);
        view.setVisible(true);

    }

    private class SettingsPortAndDatabaseListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String url = view.getTxtUrlBaze().getText().trim();
            String username = view.getTxtUsernameBaze().getText().trim();
            String password = String.valueOf(view.getTxtPassword().getPassword());
            String port = view.getTxtBrojPorta().getText().trim();

           if(url.isEmpty() || username.isEmpty() || port.isEmpty()){
                JOptionPane.showMessageDialog(view, "Sva polja u podešavanjima moraju biti popunjena (sifra - opciono)!" , "Upozorenje!" , JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            try {
                Integer.parseInt(port);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Port mora biti broj! Pokušajte ponovo!", "Upozorenje!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PodesavanjaLoader.getInstance().setProperty(Konstante.URL, url);
            PodesavanjaLoader.getInstance().setProperty(Konstante.USERNAME, username);
            PodesavanjaLoader.getInstance().setProperty(Konstante.PASSWORD, password);
            PodesavanjaLoader.getInstance().setProperty(Konstante.PORT, port);

            try {
                PodesavanjaLoader.getInstance().saveProperties();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Property nisu sačuvani! Pokušajte ponovo!", "Upozorenje!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(view, "Properties su uspešno sačuvani!","Obaveštenje",JOptionPane.INFORMATION_MESSAGE);
            view.dispose();

        }
    }
}
