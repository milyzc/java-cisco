package com.javaint.gui;

import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class JFInicioSession extends JFrame {
    private JButton jbIngresar;
    private JPasswordField jpPass;
    private JTextField jtUsuario;
    private JLabel jlRegistrarse;
    private JPanel JPInicio;
    private GestorUsuarios gestor;

    public JFInicioSession() {
        super("Inicio sesión");
        gestor = new GestorUsuarios();
        setContentPane(JPInicio);
        this.setSize(250,150);
        this.jbIngresar.setForeground(Color.RED);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
        jbIngresar.addActionListener(new JFInicioSession.JBIngresarListener(this));
        jlRegistrarse.addMouseListener(new JFInicioSession.JLRegistrarseMouseListener(this));
    }

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
                        new JFPrincipal(gestor).setVisible(true);
                    }
                } catch (RuntimeException re) {
                    re.printStackTrace();
                    JOptionPane.showMessageDialog(parent, re.getMessage(), "Error al intentar acceder", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class JLRegistrarseMouseListener implements MouseListener {
        private JFrame parent;

        public JLRegistrarseMouseListener(JFrame parent) {
            this.parent = parent;
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            parent.dispose();
            new JFRegistro(gestor,false).setVisible(true);
        }
    }
}
