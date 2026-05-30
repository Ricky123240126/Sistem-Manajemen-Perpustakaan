/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Panel;
import controller.AnggotaController;
import controller.BukuController;
import controller.TransaksiController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
/**
 *
 * @author Lenovo
 */
public class DashboardHomePanel extends JPanel {

    private BukuController bukuController;
    private AnggotaController anggotaController;
    private TransaksiController transaksiController;

    public DashboardHomePanel() {
        bukuController = new BukuController();
        anggotaController = new AnggotaController();
        transaksiController = new TransaksiController();

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(248, 250, 252));

        // JUDUL
        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));

        // PANEL CARD
        JPanel cardPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        cardPanel.setBackground(new Color(248, 250, 252));

        int totalBuku = bukuController.ambilSemuaBuku().size();
        int totalAnggota = anggotaController.ambilSemuaAnggota().size();
        int totalDipinjam = transaksiController.ambilTransaksiDipinjam().size();
        int totalTerlambat = transaksiController.ambilTransaksiTerlambat().size();

        cardPanel.add(createCard("Total Buku", String.valueOf(totalBuku)));
        cardPanel.add(createCard("Total Anggota", String.valueOf(totalAnggota)));
        cardPanel.add(createCard("Dipinjam", String.valueOf(totalDipinjam)));
        cardPanel.add(createCard("Terlambat", String.valueOf(totalTerlambat)));

        // WELCOME PANEL
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBorder(new EmptyBorder(20, 0, 0, 0));
        welcomePanel.setBackground(new Color(248, 250, 252));

        JLabel lblWelcome = new JLabel(
                "<html>"
                        + "<h2>Selamat Datang di Sistem Informasi Perpustakaan</h2>"
                        + "<p>Gunakan menu di sebelah kiri untuk mengelola data perpustakaan.</p>"
                        + "</html>"
        );

        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        welcomePanel.add(lblWelcome, BorderLayout.CENTER);

        // TOP PANEL
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(248, 250, 252));

        topPanel.add(lblTitle, BorderLayout.NORTH);
        topPanel.add(cardPanel, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(welcomePanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());

        card.setBackground(Color.WHITE);

        card.setBorder(new LineBorder(
                new Color(220, 220, 220),
                1,
                true
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel lblValue = new JLabel(value);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 30));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }
}
