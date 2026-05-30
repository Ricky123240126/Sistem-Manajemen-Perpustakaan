/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.panel;

import controller.PenggunaController;

import model.Orang;
import model.pengguna.Admin;
import model.pengguna.Pustakawan;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class PenggunaPanel extends JPanel {

    private PenggunaController penggunaController;

    private JTable tablePengguna;
    private DefaultTableModel tableModel;

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    private JComboBox<String> cbRole;

    private JButton btnTambah;
    private JButton btnUbahPassword;
    private JButton btnHapus;
    private JButton btnReset;

    private int selectedId = -1;

    public PenggunaPanel() {
        penggunaController = new PenggunaController();

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Data Pengguna");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pengguna"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUsername = new JTextField();
        txtPassword = new JPasswordField(15);

        cbRole = new JComboBox<>(new String[]{
                "admin",
                "pustakawan"
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Password"), gbc);

        gbc.gridx = 3;
        formPanel.add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Role"), gbc);

        gbc.gridx = 1;
        formPanel.add(cbRole, gbc);

        // BUTTON
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnTambah = new JButton("Tambah");
        btnUbahPassword = new JButton("Ubah Password");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUbahPassword);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnReset);

        // TABLE
        String[] kolom = {
                "ID",
                "Username",
                "Role"
        };

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablePengguna = new JTable(tableModel);
        tablePengguna.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(tablePengguna);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.add(lblTitle);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // EVENT
        btnTambah.addActionListener(e -> tambahData());
        btnUbahPassword.addActionListener(e -> ubahPassword());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> clearForm());

        tablePengguna.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiForm();
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<Orang> daftar = penggunaController.ambilSemuaPengguna();

        for (Orang orang : daftar) {

            String username;
            String role;

            if (orang instanceof Admin admin) {
                username = admin.getUsername();
                role = "Admin";
            } else {
                Pustakawan pustakawan = (Pustakawan) orang;

                username = pustakawan.getUsername();
                role = "Pustakawan";
            }

            tableModel.addRow(new Object[]{
                    orang.getId(),
                    username,
                    role
            });
        }
    }

    private void tambahData() {
        String username = txtUsername.getText();
        String password = String.valueOf(txtPassword.getPassword());

        boolean berhasil;

        if (cbRole.getSelectedItem().equals("admin")) {
            berhasil = penggunaController.tambahAdmin(username, password);
        } else {
            berhasil = penggunaController.tambahPustakawan(username, password);
        }

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Pengguna berhasil ditambahkan");

            loadData();
            clearForm();
        }
    }

    private void ubahPassword() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pengguna terlebih dahulu");
            return;
        }

        Orang pengguna = penggunaController.cariPengguna(selectedId);

        String passwordBaru = String.valueOf(txtPassword.getPassword());

        boolean berhasil =
                penggunaController.ubahPassword(
                        pengguna,
                        passwordBaru
                );

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Password berhasil diubah");

            loadData();
            clearForm();
        }
    }

    private void hapusData() {
        if (selectedId == -1) return;

        if (JOptionPane.showConfirmDialog(
                this,
                "Hapus pengguna?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            if (penggunaController.hapusPengguna(selectedId)) {
                JOptionPane.showMessageDialog(this, "Pengguna berhasil dihapus");

                loadData();
                clearForm();
            }
        }
    }

    private void isiForm() {
        int row = tablePengguna.getSelectedRow();

        if (row == -1) return;

        selectedId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        Orang pengguna = penggunaController.cariPengguna(selectedId);

        if (pengguna instanceof Admin admin) {

            txtUsername.setText(admin.getUsername());
            txtPassword.setText(admin.getPassword());
            cbRole.setSelectedItem("admin");

        } else {

            Pustakawan pustakawan = (Pustakawan) pengguna;

            txtUsername.setText(pustakawan.getUsername());
            txtPassword.setText(pustakawan.getPassword());
            cbRole.setSelectedItem("pustakawan");
        }
    }

    private void clearForm() {
        selectedId = -1;

        txtUsername.setText("");
        txtPassword.setText("");

        cbRole.setSelectedIndex(0);

        tablePengguna.clearSelection();
    }
}
