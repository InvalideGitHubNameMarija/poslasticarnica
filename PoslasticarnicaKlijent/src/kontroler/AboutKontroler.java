/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kontroler;

import form.GlavnaForma;

/**
 *
 * @author Sony
 */
public class AboutKontroler {
    GlavnaForma view;

    public AboutKontroler(GlavnaForma view) {
        this.view = view;
        srediformu();
    }

    private void srediformu() {
        view.getPanelOSoftveru().setVisible(true);
    }
    
}
