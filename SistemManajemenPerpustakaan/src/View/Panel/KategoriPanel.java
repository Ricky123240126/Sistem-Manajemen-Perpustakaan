/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.panel;

import controller.KategoriController;
import model.buku.Kategori;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class KategoriPanel extends JPanel {

    private KategoriController kategoriController;

    private JTable tableKategori;
    private DefaultTableModel tableModel;

    private JTextField txtNamaKategori;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnReset;

    private int selectedId = -1;

    public KategoriPanel() {
        kategoriController = new KategoriController();

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // JUDUL
        JLabel lblTitle = new JLabel("Data Kategori");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // FORM
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Kategori"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNamaKategori = new JTextField(30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Nama Kategori"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtNamaKategori, gbc);

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

        // TABLE
        String[] kolom = {
                "ID",
                "Nama Kategori"
        };

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKategori = new JTable(tableModel);
        tableKategori.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(tableKategori);

        // TOP PANEL
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
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnReset.addActionListener(e -> clearForm());

        tableKategori.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<Kategori> daftar = kategoriController.ambilSemuaKategori();

        for (Kategori kategori : daftar) {
            tableModel.addRow(new Object[]{
                    kategori.getId(),
                    kategori.getNama()
            });
        }
    }

    private void tambahData() {
        boolean berhasil = kategoriController.tambahKategori(
                txtNamaKategori.getText()
        );

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil ditambahkan.");

            loadData();
            clearForm();
        }
    }

    private void editData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }

        boolean berhasil = kategoriController.perbaruiKategori(
                selectedId,
                txtNamaKategori.getText()
        );

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Kategori berhasil diperbarui.");

            loadData();
            clearForm();
        }
    }

    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Hapus kategori ini ?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {

            boolean berhasil = kategoriController.hapusKategori(selectedId);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Kategori berhasil dihapus.");

                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Kategori gagal dihapus.\nKemungkinan masih digunakan oleh data buku."
                );
            }
        }
    }

    private void isiFormDariTabel() {
        int row = tableKategori.getSelectedRow();

        if (row == -1) return;

        selectedId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        Kategori kategori = kategoriController.cariKategori(selectedId);

        txtNamaKategori.setText(kategori.getNama());
    }

    private void clearForm() {
        selectedId = -1;

        txtNamaKategori.setText("");

        tableKategori.clearSelection();
    }
}