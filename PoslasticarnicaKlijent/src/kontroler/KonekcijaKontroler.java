/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import form.KonekcijaForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import sesija.Sesija;

/**
 *
 * @author Sony
 */
public class KonekcijaKontroler {

    KonekcijaForm view;

    public KonekcijaKontroler() {
        view = new KonekcijaForm();
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        view.addNewConnectListener(new ConnectListener());
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }

    private class ConnectListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Socket socket = new Socket(view.getTxtAdresa().getText(), Integer.parseInt(view.getTxtPort().getText()));
                Sesija.getInstance().setSocket(socket);
                JOptionPane.showMessageDialog(null, "Povezivanje uspešno!", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                view.dispose();
                new LoginKontroler();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Server nije operativan!", "Greška!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
