/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import domen.Radnik;
import form.LoginForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import model.LogInOutModel;
import sesija.Sesija;

/**
 *
 * @author Sony
 */
public class LoginKontroler {

    LoginForm view;
    LogInOutModel model;

    public LoginKontroler() {
        view = new LoginForm();
        model = new LogInOutModel();
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        view.addNewLoginListener(new LoginListener());
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getTxtUsername().getText();
            String password = new String(view.getTxtPassword().getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                try {
                    Radnik radnik = model.prijaviRadnika(username, password);
                    if (radnik == null) {
                        JOptionPane.showMessageDialog(view, "Radnik čiji je username " +username + " je već ulogovan u sistem", "Greška!", JOptionPane.INFORMATION_MESSAGE);
                        view.getTxtUsername().setText("");
                        view.getTxtPassword().setText("");
                        return;
                    }
                    JOptionPane.showMessageDialog(view, "Ulogovali ste se uspešno.", "Uspeh!", JOptionPane.INFORMATION_MESSAGE);
                    Sesija.getInstance().setTrenutniRadnik(radnik);
                    new GlavnaFormaKontroler();
                    view.dispose();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view, "Prijavljivanje neuspešno! Server je pao!", "Greška!", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Sistem ne može da pronađe radnika!", "Greška!", JOptionPane.ERROR_MESSAGE);
                    view.getTxtUsername().setText("");
                    view.getTxtPassword().setText("");
                }
            } else {
                JOptionPane.showMessageDialog(view, "Sva polja moraju biti popunjena!", "Greška!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
