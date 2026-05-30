/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import View.Panel.*;
import view.panel.BukuPanel;
import view.panel.AnggotaPanel;
import view.panel.TransaksiPanel;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Lenovo
 */
public class DashboardPustakawan extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public DashboardPustakawan() {
        initComponents();

        setTitle("Dashboard Pustakawan");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // HEADER
        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(new Color(51, 65, 85));
        headerPanel.setPreferredSize(new Dimension(0, 60));

        JLabel lblTitle = new JLabel("  SISTEM INFORMASI PERPUSTAKAAN");

        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));

        headerPanel.add(lblTitle, BorderLayout.WEST);

        // SIDEBAR
        JPanel sidebarPanel = new JPanel();

        sidebarPanel.setBackground(new Color(30, 41, 59));
        sidebarPanel.setPreferredSize(new Dimension(220, 0));
        sidebarPanel.setLayout(new GridLayout(5, 1, 0, 5));

        JButton btnDashboard = buatMenuButton("Dashboard");
        JButton btnBuku = buatMenuButton("Buku");
        JButton btnAnggota = buatMenuButton("Anggota");
        JButton btnTransaksi = buatMenuButton("Transaksi");
        JButton btnLogout = buatMenuButton("Logout");

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(btnBuku);
        sidebarPanel.add(btnAnggota);
        sidebarPanel.add(btnTransaksi);
        sidebarPanel.add(btnLogout);

        // CONTENT PANEL
        cardLayout = new CardLayout();

        contentPanel = new JPanel(cardLayout);

        contentPanel.add(new DashboardHomePanel(), "DASHBOARD");
        contentPanel.add(new BukuPanel(), "BUKU");
        contentPanel.add(new AnggotaPanel(), "ANGGOTA");
        contentPanel.add(new TransaksiPanel(), "TRANSAKSI");

        // EVENT
        btnDashboard.addActionListener(
                e -> cardLayout.show(contentPanel, "DASHBOARD")
        );

        btnBuku.addActionListener(
                e -> cardLayout.show(contentPanel, "BUKU")
        );

        btnAnggota.addActionListener(
                e -> cardLayout.show(contentPanel, "ANGGOTA")
        );

        btnTransaksi.addActionListener(
                e -> cardLayout.show(contentPanel, "TRANSAKSI")
        );

        btnLogout.addActionListener(e -> {

            int konfirmasi = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (konfirmasi == JOptionPane.YES_OPTION) {
                new LoginView().setVisible(true);
                dispose();
            }
        });

        add(headerPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JButton buatMenuButton(String text) {
        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);

        button.setBackground(new Color(30, 41, 59));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        return button;
    }
}
