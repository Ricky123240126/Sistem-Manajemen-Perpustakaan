/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.panel;

import controller.AnggotaController;
import model.anggota.Anggota;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class AnggotaPanel extends JPanel {

    private AnggotaController anggotaController;

    private JTable tableAnggota;
    private DefaultTableModel tableModel;

    private JTextField txtNama;
    private JTextField txtEmail;
    private JTextField txtTelepon;
    private JTextField txtCari;

    private JComboBox<String> cbTipe;
    private JComboBox<String> cbStatus;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnReset;
    private JButton btnCari;
    private JButton btnRefresh;

    private int selectedId = -1;

    public AnggotaPanel() {
        anggotaController = new AnggotaController();

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Data Anggota");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Anggota"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNama = new JTextField();
        txtEmail = new JTextField();
        txtTelepon = new JTextField();

        cbTipe = new JComboBox<>(new String[]{
                "Reguler",
                "Premium"
        });

        cbStatus = new JComboBox<>(new String[]{
                "aktif",
                "nonaktif"
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtNama, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Email"), gbc);

        gbc.gridx = 3;
        formPanel.add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Telepon"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtTelepon, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Tipe"), gbc);

        gbc.gridx = 3;
        formPanel.add(cbTipe, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Status"), gbc);

        gbc.gridx = 1;
        formPanel.add(cbStatus, gbc);

        // BUTTON
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Simpan Perubahan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnReset);

        // SEARCH
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtCari = new JTextField(20);

        btnCari = new JButton("Cari");
        btnRefresh = new JButton("Refresh");

        searchPanel.add(new JLabel("Pencarian"));
        searchPanel.add(txtCari);
        searchPanel.add(btnCari);
        searchPanel.add(btnRefresh);

        // TABLE
        String[] kolom = {
                "ID",
                "Nama",
                "Email",
                "Telepon",
                "Tipe",
                "Status"
        };

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableAnggota = new JTable(tableModel);
        tableAnggota.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(tableAnggota);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        topPanel.add(lblTitle);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);
        topPanel.add(searchPanel);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // EVENT
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> clearForm());
        btnCari.addActionListener(e -> cariData());
        btnRefresh.addActionListener(e -> loadData());

        tableAnggota.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiForm();
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<Anggota> daftar = anggotaController.ambilSemuaAnggota();

        for (Anggota a : daftar) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getNama(),
                    a.getEmail(),
                    a.getNoTelp(),
                    a.getTipeKeanggotaan(),
                    a.getStatus()
            });
        }
    }

    private void cariData() {
        tableModel.setRowCount(0);

        List<Anggota> daftar = anggotaController.cariAnggota(txtCari.getText());

        for (Anggota a : daftar) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getNama(),
                    a.getEmail(),
                    a.getNoTelp(),
                    a.getTipeKeanggotaan(),
                    a.getStatus()
            });
        }
    }

    private void tambahData() {
        boolean berhasil = anggotaController.tambahAnggota(
                txtNama.getText(),
                txtEmail.getText(),
                txtTelepon.getText(),
                cbTipe.getSelectedItem().toString()
        );

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");

            loadData();
            clearForm();
        }
    }

    private void editData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu");
            return;
        }

        boolean berhasil = anggotaController.perbaruiAnggota(
                selectedId,
                txtNama.getText(),
                txtEmail.getText(),
                txtTelepon.getText(),
                cbTipe.getSelectedItem().toString(),
                cbStatus.getSelectedItem().toString()
        );

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui");

            loadData();
            clearForm();
        }
    }

    private void hapusData() {
        if (selectedId == -1) return;

        if (JOptionPane.showConfirmDialog(
                this,
                "Hapus anggota?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {

            if (anggotaController.hapusAnggota(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus");

                loadData();
                clearForm();
            }
        }
    }

    private void isiForm() {
        int row = tableAnggota.getSelectedRow();

        if (row == -1) return;

        selectedId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        Anggota anggota = anggotaController.cariAnggota(selectedId);

        txtNama.setText(anggota.getNama());
        txtEmail.setText(anggota.getEmail());
        txtTelepon.setText(anggota.getNoTelp());

        cbTipe.setSelectedItem(anggota.getTipeKeanggotaan());
        cbStatus.setSelectedItem(anggota.getStatus());
    }

    private void clearForm() {
        selectedId = -1;

        txtNama.setText("");
        txtEmail.setText("");
        txtTelepon.setText("");
        txtCari.setText("");

        cbTipe.setSelectedIndex(0);
        cbStatus.setSelectedIndex(0);

        tableAnggota.clearSelection();
    }
}
