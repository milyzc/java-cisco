package com.javaint.gui;

import com.javaint.entidades.Aplicacion;
import com.javaint.servicios.GestorAplicaciones;
import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFComprar extends JDialog {
    private JPanel JPCompras;
    private JPanel JPBotones;
    private JButton cancelarButton;
    private JButton comprarButton;
    private JPanel JPDatos;
    private JLabel JLAppNom;
    private JLabel JLPrecio;
    private JLabel JLValoracion;
    private final Aplicacion app;
    private final GestorAplicaciones gestorApps;
    private final GestorUsuarios gestorUsuarios;

    public JFComprar(Aplicacion aplicacion, GestorAplicaciones gestorA, GestorUsuarios gestorU) {
        this.app = aplicacion;
        this.gestorApps = gestorA;
        this.gestorUsuarios = gestorU;
        setContentPane(JPCompras);
        this.setSize(300, 200);
        this.setResizable(false);
        this.setTitle("Comprar");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        IniciarApp();
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!gestorApps.existeCompra(gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), app.getIdApp())) {
                    boolean var = gestorApps.comprarAplicacion(gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), app.getIdApp());
                    if (var) {
                        JOptionPane.showMessageDialog(null, "Se proceso correctamente la compra de la aplicación: " + app.getNombre(), "Exito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al procesar la compra!", "Validación", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya posee la aplicación: " + app.getNombre(), "Información", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void IniciarApp() {
        JLAppNom.setText(app.getNombre());
        JLPrecio.setText(String.valueOf(app.getPrecio()));
        JLValoracion.setText("5");
    }
}
