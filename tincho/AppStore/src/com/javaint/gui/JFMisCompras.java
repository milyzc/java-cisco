package com.javaint.gui;

import com.javaint.entidades.Aplicacion;
import com.javaint.servicios.GestorAplicaciones;
import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JFMisCompras extends JDialog{
    private JPanel JPMisCompras;
    private JPanel JPCompras;
    private JPanel JPBotones;
    private JButton JBCalificar;
    private JButton JBVolver;
    private JTable JTCompras;
    private final AppTableModel tableModel;
    private final GestorAplicaciones gestorApps;
    private final GestorUsuarios gestorUsuarios;
    public JFMisCompras(GestorAplicaciones gestorA, GestorUsuarios gestorU) {
        this.gestorApps=gestorA;
        this.gestorUsuarios=gestorU;
        setContentPane(JPMisCompras);
        this.setSize(700, 400);
        this.setResizable(false);
        this.setTitle("Compras");
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.tableModel = new AppTableModel(gestorApps,true);
        this.JTCompras.setModel(this.tableModel);
        JBCalificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = JTCompras.getSelectedRow();
                if (filaSeleccionada != -1) {
                    Aplicacion app = tableModel.getAplicacion(filaSeleccionada);
                    new JFCalificar(app, gestorApps, gestorUsuarios).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una aplicación!", "Compra", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        JBVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
