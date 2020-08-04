package com.javaint.gui;

import com.javaint.servicios.GestorAplicaciones;
import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFAplicacion extends JDialog {
    private JPanel JPAplicacion;
    private JPanel JPDatos;
    private JPanel JPBotones;
    private JButton JBCancelar;
    private JButton JBRegistrar;
    private JTextField JTNombreApp;
    private JTextField JTPrecio;
    private final GestorAplicaciones gestorApps;
    private final GestorUsuarios gestorUsuarios;

    public JFAplicacion(GestorAplicaciones gestorA, GestorUsuarios gestorU) {
        this.gestorApps = gestorA;
        this.gestorUsuarios = gestorU;
        setContentPane(JPAplicacion);
        this.setSize(300, 150);
        this.setResizable(false);
        this.setModal(true);
        this.setTitle("Registro Aplicación");
        this.setLocationRelativeTo(null);
        this.pack();
        JBRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuffer str = validarTiposDatos();
                if (str.length() > 0) {
                    JOptionPane.showMessageDialog(null, str, "Validación", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (!gestorApps.existeApp(gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), JTNombreApp.getText())) {
                        boolean var = gestorApps.crearAplicacion(gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), JTNombreApp.getText(), JTPrecio.getText());
                        if (var) {
                            JOptionPane.showMessageDialog(null, "Se registro correctamente la aplicación: " + JTNombreApp.getText(), "Exito", JOptionPane.INFORMATION_MESSAGE);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al crear la compra!", "Validación", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Ya posee la existe la aplicacion: " + JTNombreApp.getText(), "Información", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                }
            }
        });
        JBCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private StringBuffer validarTiposDatos() {
        StringBuffer resp = new StringBuffer();
        if (JTNombreApp.getText().isEmpty())
            return resp.append("El campo nombre no puede ser vacio.\n");
        if (JTPrecio.getText().isEmpty())
            return resp.append("El campo precio nombre no puede ser vacio.\n");
        if (!JTPrecio.getText().matches("^\\d{0,8}(\\.\\d{1,4})?$"))
            return resp.append("El campo precio tiene un formato invalido.\n");
        return resp;
    }
}
