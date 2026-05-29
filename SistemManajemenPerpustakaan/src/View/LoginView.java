/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import controller.PenggunaController;
import model.Orang;
import model.pengguna.Admin;
import model.pengguna.Pustakawan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
/**
 *
 * @author Lenovo
 */
public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    private PenggunaController penggunaController;

    public LoginView() {
        penggunaController = new PenggunaController();

        initComponents();
        initFrame();
    }

    private void initFrame() {
        setTitle("Sistem Informasi Perpustakaan");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        // ==========================
        // PANEL KIRI
        // ==========================
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(30, 41, 59));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel lblIcon = new JLabel("📚");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        lblIcon.setForeground(Color.WHITE);
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("PERPUSTAKAAN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubTitle = new JLabel("Sistem Informasi");
        lblSubTitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubTitle.setForeground(Color.WHITE);
        lblSubTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubTitle2 = new JLabel("Perpustakaan");
        lblSubTitle2.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubTitle2.setForeground(Color.WHITE);
        lblSubTitle2.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(lblIcon);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(lblTitle);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(lblSubTitle);
        leftPanel.add(lblSubTitle2);
        leftPanel.add(Box.createVerticalGlue());

        // ==========================
        // PANEL KANAN
        // ==========================
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(50, 60, 50, 60));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel lblLogin = new JLabel("LOGIN");
        lblLogin.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(59, 130, 246));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnLogin.addActionListener(e -> prosesLogin());

        rightPanel.add(Box.createVerticalGlue());

        rightPanel.add(lblLogin);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        rightPanel.add(lblUsername);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightPanel.add(txtUsername);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(lblPassword);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        rightPanel.add(txtPassword);

        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        rightPanel.add(btnLogin);

        rightPanel.add(Box.createVerticalGlue());

        // ==========================
        // MAIN PANEL
        // ==========================
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel);
    }

    private void prosesLogin() {

        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword());

        Orang pengguna = penggunaController.login(username, password);

        if (pengguna == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username atau Password salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        JOptionPane.showMessageDialog(
                this,
                "Login Berhasil",
                "Informasi",
                JOptionPane.INFORMATION_MESSAGE
        );

        if (pengguna instanceof Admin) {

            new DashboardAdmin().setVisible(true);

        } else if (pengguna instanceof Pustakawan) {

            new DashboardPustakawan().setVisible(true);
        }

        dispose();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
