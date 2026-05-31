/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.panel;

import controller.BukuController;
import controller.KategoriController;

import model.buku.Buku;
import model.buku.Kategori;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class BukuPanel extends JPanel {

    private BukuController bukuController;
    private KategoriController kategoriController;

    private JTable tableBuku;
    private DefaultTableModel tableModel;

    private JTextField txtJudul;
    private JTextField txtPengarang;
    private JTextField txtISBN;
    private JTextField txtJumlahStok;
    private JTextField txtCari;

    private JComboBox<Kategori> cbKategori;

    private JButton btnTambah;
    private JButton btnEdit;
    private JButton btnHapus;
    private JButton btnReset;
    private JButton btnCari;
    private JButton btnRefresh;

    private int selectedId = -1;

    public BukuPanel() {
        bukuController = new BukuController();
        kategoriController = new KategoriController();

        initComponents();

        loadKategori();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 250, 252));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // JUDUL
        JLabel lblTitle = new JLabel("Data Buku");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // FORM PANEL
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Buku"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtJudul = new JTextField(20);
        txtPengarang = new JTextField(20);
        txtISBN = new JTextField(20);
        txtJumlahStok = new JTextField(20);

        cbKategori = new JComboBox<>();

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Judul"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtJudul, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Pengarang"), gbc);

        gbc.gridx = 3;
        formPanel.add(txtPengarang, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("ISBN"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtISBN, gbc);

        gbc.gridx = 2;
        formPanel.add(new JLabel("Kategori"), gbc);

        gbc.gridx = 3;
        formPanel.add(cbKategori, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Jumlah Stok"), gbc);

        gbc.gridx = 1;
        formPanel.add(txtJumlahStok, gbc);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Simpan Perubahan");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnReset);

        // SEARCH PANEL
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
                "Judul",
                "Pengarang",
                "ISBN",
                "Jumlah Stok",
                "Stok Tersedia"
        };

        tableModel = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableBuku = new JTable(tableModel);
        tableBuku.setRowHeight(28);

        JScrollPane scrollPane = new JScrollPane(tableBuku);

        // TOP PANEL
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
        btnRefresh.addActionListener(e -> loadData());
        btnCari.addActionListener(e -> cariData());

        tableBuku.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });
    }

    private void loadKategori() {
        cbKategori.removeAllItems();

        List<Kategori> daftar = kategoriController.ambilSemuaKategori();

        for (Kategori kategori : daftar) {
            cbKategori.addItem(kategori);
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);

        List<Buku> daftar = bukuController.ambilSemuaBuku();

        for (Buku buku : daftar) {
            tableModel.addRow(new Object[]{
                    buku.getId(),
                    buku.getJudul(),
                    buku.getPengarang(),
                    buku.getIsbn(),
                    buku.getJumlahStok(),
                    buku.getStokTersedia()
            });
        }
    }

    private void cariData() {
        tableModel.setRowCount(0);

        List<Buku> daftar = bukuController.cariBuku(txtCari.getText());

        for (Buku buku : daftar) {
            tableModel.addRow(new Object[]{
                    buku.getId(),
                    buku.getJudul(),
                    buku.getPengarang(),
                    buku.getIsbn(),
                    buku.getJumlahStok(),
                    buku.getStokTersedia()
            });
        }
    }

    private void tambahData() {
        try {
            Kategori kategori = (Kategori) cbKategori.getSelectedItem();

            boolean berhasil = bukuController.tambahBuku(
                    txtJudul.getText(),
                    txtPengarang.getText(),
                    txtISBN.getText(),
                    kategori.getId(),
                    Integer.parseInt(txtJumlahStok.getText())
            );

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");

                loadData();
                clearForm();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid.");
        }
    }

    private void editData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }

        try {
            Kategori kategori = (Kategori) cbKategori.getSelectedItem();

            Buku bukuLama = bukuController.cariBuku(selectedId);

            boolean berhasil = bukuController.perbaruiBuku(
                    selectedId,
                    txtJudul.getText(),
                    txtPengarang.getText(),
                    txtISBN.getText(),
                    kategori.getId(),
                    Integer.parseInt(txtJumlahStok.getText()),
                    bukuLama.getStokTersedia()
            );

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");

                loadData();
                clearForm();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid.");
        }
    }

    private void hapusData() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Hapus buku ini ?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            if (bukuController.hapusBuku(selectedId)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");

                loadData();
                clearForm();
            }
        }
    }

    private void isiFormDariTabel() {
        int row = tableBuku.getSelectedRow();

        if (row == -1) return;

        selectedId = Integer.parseInt(
                tableModel.getValueAt(row, 0).toString()
        );

        Buku buku = bukuController.cariBuku(selectedId);

        txtJudul.setText(buku.getJudul());
        txtPengarang.setText(buku.getPengarang());
        txtISBN.setText(buku.getIsbn());
        txtJumlahStok.setText(String.valueOf(buku.getJumlahStok()));

        for (int i = 0; i < cbKategori.getItemCount(); i++) {
            if (cbKategori.getItemAt(i).getId() == buku.getIdKategori()) {
                cbKategori.setSelectedIndex(i);
                break;
            }
        }
    }

    private void clearForm() {
        selectedId = -1;

        txtJudul.setText("");
        txtPengarang.setText("");
        txtISBN.setText("");
        txtJumlahStok.setText("");
        txtCari.setText("");

        if (cbKategori.getItemCount() > 0) {
            cbKategori.setSelectedIndex(0);
        }

        tableBuku.clearSelection();
    }
    public void refreshData(){
        loadKategori();
        loadData();
    }
}