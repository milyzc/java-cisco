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
    private JPanel JPDatos;
    private JPanel JPBotones;

    private GestorUsuarios gestorUsuario;

    public JFRegistro(GestorUsuarios gestor, boolean editar) {
        this.gestorUsuario = gestor;
        setContentPane(panelRegistro);
        this.setSize(600, 250);
        this.setTitle(editar ? "Editar" : "Registro");
        this.btnRegistro.setText(editar ? "Editar" : "Registro");
        if (editar) {
            this.setModal(true);
            cargarUsuario();
        }
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.pack();
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
                StringBuffer str=validarTiposDatos();
                if (str.length()>0) {
                    JOptionPane.showMessageDialog(null, str, "Validación", JOptionPane.ERROR_MESSAGE);
                } else {
                   validarTiposDatos();
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
                            if (!editar)
                                new JFInicioSession().setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al registrar el usuario!", "Validación", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    private StringBuffer validarTiposDatos() {
        StringBuffer resp= new StringBuffer();
        if(txtApeNom.getText().isEmpty())
            return   resp.append("El campo apellido nombre no puede ser vacio.\n");
        if(!txtApeNom.getText().toUpperCase().matches("^[A-Za-z]*\\s()[A-Za-z]*$"))
            return   resp.append("El campo apellido nombre tiene un formato invalido.\n");
        if(txtDni.getText().isEmpty())
            return   resp.append("El campo dni no puede ser vacio.\n");
        if(!txtDni.getText().matches("^[0-9]*$"))
            return    resp.append("El campo dni tiene un formato invalido.\n");
        if(txtEmail.getText().isEmpty())
            return  resp.append("El campo email no puede ser vacio.\n");
        if(!txtEmail.getText().toUpperCase().matches("^([\\w\\.\\-]+)@([\\w\\-]+)((\\.(\\w){2,3})+)$"))
            return   resp.append("El campo email tiene un formato invalido.\n");
        if(txtUsuario.getText().isEmpty())
            return   resp.append("El campo usuario no puede ser vacio.\n");
        if(String.valueOf(txtPassword.getPassword()).isEmpty())
            return   resp.append("El campo password no puede ser vacio.\n");
       return resp;
    }

    private void cargarUsuario() {
        txtApeNom.setText(gestorUsuario.getUserLog().getApellidoNombre());
        txtDni.setText(gestorUsuario.getUserLog().getDni());
        txtEmail.setText(gestorUsuario.getUserLog().getEmail());
        txtUsuario.setText(gestorUsuario.getUserLog().getUsuario().getNombre());
    }

}
