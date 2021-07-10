/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sony
 */
public class GlavnaForma extends javax.swing.JFrame {

    /**
     * Creates new form MainForm
     */
    public GlavnaForma() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPrijavljeni = new javax.swing.JLabel();
        panelOSoftveru = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnOdjaviSe = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuKolac = new javax.swing.JMenu();
        jMenuItemUnos = new javax.swing.JMenuItem();
        jMenuItemPretraga = new javax.swing.JMenuItem();
        jMenuInvoice = new javax.swing.JMenu();
        jMenuItemNoviRacun = new javax.swing.JMenuItem();
        jMenuItemPretragaRacun = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenu();
        jMenuItemProcitajVise = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Poslastičarnica");

        lblPrijavljeni.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblPrijavljeni.setText("Prijavljeni radnik:");

        panelOSoftveru.setBorder(javax.swing.BorderFactory.createTitledBorder("O softveru"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Softver Klijent Poslastičarnica je napravljen u razvojnom okruženju NetBeans ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("IDE 8.2 RC i omogućava CRUD operacije nad kolačima, kao i kreiranje");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Softver je moguće prilagoditi različitim poslastičarnicama.");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("pretragu i izmenu računa od strane radnika koji je ulogovan u aplikaciju.");

        javax.swing.GroupLayout panelOSoftveruLayout = new javax.swing.GroupLayout(panelOSoftveru);
        panelOSoftveru.setLayout(panelOSoftveruLayout);
        panelOSoftveruLayout.setHorizontalGroup(
            panelOSoftveruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOSoftveruLayout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(panelOSoftveruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelOSoftveruLayout.setVerticalGroup(
            panelOSoftveruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOSoftveruLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(34, 34, 34))
        );

        btnOdjaviSe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnOdjaviSe.setText("Odjavi se");

        jMenuKolac.setText("Kolač");

        jMenuItemUnos.setText("Unos");
        jMenuItemUnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemUnosActionPerformed(evt);
            }
        });
        jMenuKolac.add(jMenuItemUnos);

        jMenuItemPretraga.setText("Pretraga");
        jMenuKolac.add(jMenuItemPretraga);

        jMenuBar1.add(jMenuKolac);

        jMenuInvoice.setText("Račun");

        jMenuItemNoviRacun.setText("Novi račun");
        jMenuInvoice.add(jMenuItemNoviRacun);

        jMenuItemPretragaRacun.setText("Pretraga");
        jMenuItemPretragaRacun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPretragaRacunActionPerformed(evt);
            }
        });
        jMenuInvoice.add(jMenuItemPretragaRacun);

        jMenuBar1.add(jMenuInvoice);

        jMenuAbout.setText("O softveru");

        jMenuItemProcitajVise.setText("Pročitaj više");
        jMenuAbout.add(jMenuItemProcitajVise);

        jMenuBar1.add(jMenuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPrijavljeni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnOdjaviSe, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(233, 233, 233))
            .addGroup(layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(panelOSoftveru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(121, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrijavljeni, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOdjaviSe))
                .addGap(81, 81, 81)
                .addComponent(panelOSoftveru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemPretragaRacunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPretragaRacunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemPretragaRacunActionPerformed

    private void jMenuItemUnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemUnosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemUnosActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOdjaviSe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenuAbout;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuInvoice;
    private javax.swing.JMenuItem jMenuItemNoviRacun;
    private javax.swing.JMenuItem jMenuItemPretraga;
    private javax.swing.JMenuItem jMenuItemPretragaRacun;
    private javax.swing.JMenuItem jMenuItemProcitajVise;
    private javax.swing.JMenuItem jMenuItemUnos;
    private javax.swing.JMenu jMenuKolac;
    private javax.swing.JLabel lblPrijavljeni;
    private javax.swing.JPanel panelOSoftveru;
    // End of variables declaration//GEN-END:variables

    public JLabel getLblPrijavljeni() {
        return lblPrijavljeni;
    }

    public void addNewCakeListener(ActionListener newCakeListener) {
        jMenuItemUnos.addActionListener(newCakeListener);
    }

    public void addNewSearchCakeListener(ActionListener newSearchCakeListener) {
        jMenuItemPretraga.addActionListener(newSearchCakeListener);
    }

    public void addNewInvoiceListener(ActionListener newInvoiceListener) {
        jMenuItemNoviRacun.addActionListener(newInvoiceListener);
    }

    public void addNewSearchInvoiceListener(ActionListener newSearchInvoiceListener) {
        jMenuItemPretragaRacun.addActionListener(newSearchInvoiceListener);
    }
    public void addNewAboutSoftwareListener(ActionListener newAboutListener){
        jMenuItemProcitajVise.addActionListener(newAboutListener);
    }
    public void addLogOutListener(ActionListener newLogOutListener){
        btnOdjaviSe.addActionListener(newLogOutListener);
    }

    public JPanel getPanelOSoftveru() {
        return panelOSoftveru;
    }
    
}
