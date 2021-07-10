/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import form.AboutSoftwareForm;
import form.MainForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author Sony
 */
public class AboutKontroler {
        MainForm mf;
        AboutSoftwareForm view;

    public AboutKontroler(MainForm mf) {
        this.mf=mf;
        view=new AboutSoftwareForm(mf, true);
        view.addPovratakListener(new PovratakListener());
        srediFormu();
    }

    private void srediFormu() {
        view.setLocationRelativeTo(null);
        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        view.setResizable(false);
        view.setVisible(true);
    }  

    private  class PovratakListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
        }
    }
}
