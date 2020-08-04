package com.javaint.gui;

import com.javaint.entidades.Aplicacion;
import com.javaint.entidades.Calificacion;
import com.javaint.servicios.GestorAplicaciones;
import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class JFCalificar extends JDialog {
    private JPanel JPCalificar;
    private JLabel JLAppNom;
    private JPanel JPDatos;
    private JComboBox JCBValidacion;
    private JPanel JPBotones;
    private JButton confirmarButton;
    private JButton volverButton;
    private final GestorAplicaciones gestorApps;
    private final GestorUsuarios gestorUsuarios;
    private final Aplicacion aplicacion;

    public JFCalificar(Aplicacion app, GestorAplicaciones gestorA, GestorUsuarios gestorU) {
        this.gestorApps = gestorA;
        this.gestorUsuarios = gestorU;
        this.aplicacion = app;
        inicializarCampos();
        setContentPane(JPCalificar);
        this.setSize(300, 200);
        this.setResizable(false);
        this.setTitle("Clasificar");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               boolean var= gestorApps.clasificarApp(aplicacion.getIdApp(), gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), (Calificacion)JCBValidacion.getSelectedItem());
               if(var) {
                   JOptionPane.showMessageDialog(null, "Se califico con exito la app.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                   dispose();
               }else{
                   JOptionPane.showMessageDialog(null, "Error al calificar la aplicación!", "Validación", JOptionPane.ERROR_MESSAGE);
               }
            }
        });
    }

    private void inicializarCampos() {
        List<Calificacion> calificaciones = gestorApps.obtenerCalificaciones();
        ComboBoxModel model = new DefaultComboBoxModel(calificaciones.toArray());
        JCBValidacion.setModel(model);
        Calificacion calificacionActual=gestorApps.obtenerCalificacion(gestorUsuarios.getUserLog().getUsuario().getIdUsuario(), aplicacion.getIdApp());
        JCBValidacion.getModel().setSelectedItem(calificacionActual);
        JLAppNom.setText(aplicacion.getNombre());

    }


}
