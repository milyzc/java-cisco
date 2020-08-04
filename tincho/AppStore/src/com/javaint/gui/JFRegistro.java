package com.javaint.gui;

import com.javaint.servicios.GestorUsuarios;
import jdk.nashorn.internal.scripts.JD;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFRegistro extends JDialog {
    private JButton btnCancelar;
    private JButton btnRegistro;
    private JPasswordField txtPassword;
    private JTextField txtApeNom;
    private JTextField txtDni;
    private JTextField txtUsuario;
    private JTextField txtEmail;
    private JPanel panelRegistro;

    private GestorUsuarios gestorUsuario;

    public JFRegistro(GestorUsuarios gestor, boolean editar) {
        this.gestorUsuario = gestor;
        setContentPane(panelRegistro);
        this.setSize(500, 300);
        this.setTitle(editar ? "Editar" : "Registro");
        this.btnRegistro.setText(editar ? "Editar" : "Registro");
        if (editar) {
            this.setModal(true);
            cargarUsuario();
        }
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editar) {
                    new JFPrincipal(gestor).setVisible(true);
                } else {
                    new JFInicioSession().setVisible(true);
                }
                dispose();
            }
        });
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (txtApeNom.getText().isEmpty() ||
                        txtDni.getText().isEmpty() ||
                        txtUsuario.getText().isEmpty() ||
                        txtEmail.getText().isEmpty() ||
                        String.valueOf(txtPassword.getPassword()).isEmpty()
                ) {
                    JOptionPane.showMessageDialog(null, "Hay campos que no pueden quedar vacíos!", "Validación", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!editar && gestorUsuario.existeMail(txtEmail.getText())) {
                        JOptionPane.showMessageDialog(null, "Email existente!", "Validación", JOptionPane.ERROR_MESSAGE);
                    } else {
                        boolean var = editar ? gestorUsuario.editarUsuario(gestor.getUserLog().getUsuario().getIdUsuario(),
                                txtApeNom.getText(),
                                txtDni.getText(),
                                txtEmail.getText(),
                                txtUsuario.getText(),
                                String.valueOf(txtPassword.getPassword()))
                                : gestorUsuario.registrarUsuario(txtApeNom.getText(),
                                txtDni.getText(),
                                txtEmail.getText(),
                                txtUsuario.getText(),
                                String.valueOf(txtPassword.getPassword()));
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Se actualizo el usuario con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                            new JFInicioSession().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar el usuario!", "Validación", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    private void cargarUsuario() {
        txtApeNom.setText(gestorUsuario.getUserLog().getApellidoNombre());
        txtDni.setText(gestorUsuario.getUserLog().getDni());
        txtEmail.setText(gestorUsuario.getUserLog().getEmail());
        txtUsuario.setText(gestorUsuario.getUserLog().getUsuario().getNombre());
    }

}
