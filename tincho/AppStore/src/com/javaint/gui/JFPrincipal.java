package com.javaint.gui;

import com.javaint.entidades.Aplicacion;
import com.javaint.servicios.GestorAplicaciones;
import com.javaint.servicios.GestorUsuarios;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JFPrincipal extends JFrame {
    private JPanel panelPrincipal;
    private JPanel JPBusqueda;
    private JPanel JPUsuario;
    private JPanel JPGrilla;
    private JPanel JPBotones;
    private JTextField JTBusqueda;
    private JButton JBBuscar;
    private JButton publicarAppButton;
    private JButton comprarAppButton;
    private JTable jtAplicaciones;
    private JLabel jlImagen;
    private JLabel jlNombreUsuario;
    private JLabel JLCerrarSesion;
    private JLabel JLMisCompras;
    private final GestorUsuarios gestorUsuarios;
    private final GestorAplicaciones gestorApps;
    private final AppTableModel tableModel;

    public JFPrincipal(final GestorUsuarios gestor) {
        this.gestorUsuarios = gestor;
        setContentPane(panelPrincipal);
        this.setSize(700, 400);
        this.setResizable(false);
        this.setTitle("Aplicaciones");
        this.setLocationRelativeTo(null);
        this.jlNombreUsuario.setText("Usuario: " + gestorUsuarios.getUserLog().getUsuario().getNombre());
        this.jlImagen.setText(null);
        this.jlImagen.setIcon(new ImageIcon(gestorUsuarios.getUserAvatar()));
        this.gestorApps = new GestorAplicaciones(gestorUsuarios.getUserLog().getUsuario());
        this.tableModel = new AppTableModel(gestorApps);
        this.jtAplicaciones.setModel(this.tableModel);

        comprarAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccionada = jtAplicaciones.getSelectedRow();
                if (filaSeleccionada != -1) {
                    Aplicacion app = tableModel.getAplicacion(filaSeleccionada);
                    new JFComprar(app, gestorApps, gestorUsuarios).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una aplicación!", "Compra", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        publicarAppButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new JFAplicacion(gestorApps, gestorUsuarios).setVisible(true);
            }
        });
        JBBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableModel.filtrar(JTBusqueda.getText());
                } catch (RuntimeException re) {
                    re.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Problema al buscar!");
                }
            }
        });
        jlNombreUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new JFRegistro(gestorUsuarios, true).setVisible(true);
            }
        });
        JLCerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                new JFInicioSession().setVisible(true);
            }
        });
        JLMisCompras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new JFMisCompras(gestorApps,gestorUsuarios).setVisible(true);
            }
        });
    }
}
