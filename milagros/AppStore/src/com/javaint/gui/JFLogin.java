/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javaint.gui;

import com.javaint.dao.UsuarioDao;
import com.javaint.dao.UsuarioDaoFile;
import com.javaint.servicios.GestorUsuarios;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author MARTIN
 */
public class JFLogin extends javax.swing.JFrame {

    private GestorUsuarios gestor;

    /**
     * Creates new form JFLogin
     */
    public JFLogin() {
        initComponents();
        gestor = new GestorUsuarios();
        this.jbIngresar.addActionListener(new JBIngresarListener(this));
        this.jbIngresar.setForeground(Color.RED);
        this.setResizable(false);
        this.setTitle("Login");
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtUsuario = new javax.swing.JTextField();
        jpPass = new javax.swing.JPasswordField();
        jbIngresar = new javax.swing.JButton();
        jbRegistrarUsuario = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Usuario:");

        jLabel2.setText("Clave:");

        jbIngresar.setText("Ingresar");
        jbIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbIngresarActionPerformed(evt);
            }
        });

        jbRegistrarUsuario.setText("Registrarse");
        jbRegistrarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbRegistrarUsuarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbRegistrarUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                        .addComponent(jbIngresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jtUsuario)
                            .addComponent(jpPass, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jpPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbIngresar)
                    .addComponent(jbRegistrarUsuario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbIngresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbIngresarActionPerformed

    private void jbRegistrarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbRegistrarUsuarioActionPerformed
        this.dispose();
        new JFRegistrarse().setVisible(true);
    }//GEN-LAST:event_jbRegistrarUsuarioActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jbIngresar;
    private javax.swing.JButton jbRegistrarUsuario;
    private javax.swing.JPasswordField jpPass;
    private javax.swing.JTextField jtUsuario;
    // End of variables declaration//GEN-END:variables

    class JBIngresarListener implements ActionListener {

        private JFrame parent;

        public JBIngresarListener(JFrame parent) {
            this.parent = parent;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            String user = jtUsuario.getText();
            String pass = String.valueOf(jpPass.getPassword());

            if (user.isEmpty()
                    || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y/o clave pueden quedar vacíos!", "Validación", JOptionPane.ERROR_MESSAGE);
            } else {
                try {

                    if (!gestor.login(user, pass)) {
                        JOptionPane.showMessageDialog(null, "Usuario y/o pass incorrectos!", "Login", JOptionPane.ERROR_MESSAGE);
                    } else {
                        parent.dispose();
                        new JFramePrincipal(gestor).setVisible(true);
                    }
                } catch (RuntimeException re) {
                    re.printStackTrace();
                    JOptionPane.showMessageDialog(parent, re.getMessage(), "Error al intentar acceder", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
