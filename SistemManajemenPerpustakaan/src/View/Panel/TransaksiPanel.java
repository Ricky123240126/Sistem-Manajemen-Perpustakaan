/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view.panel;

import controller.AnggotaController;
import controller.BukuController;
import controller.TransaksiController;

import model.anggota.Anggota;
import model.buku.Buku;
import model.transaksi.Transaksi;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class TransaksiPanel extends JPanel {

    private TransaksiController transaksiController;
    private BukuController bukuController;
    private AnggotaController anggotaController;

    // TAB PEMINJAMAN
    private JComboBox<Integer> cbBuku;
    private JComboBox<Integer> cbAnggota;
    private JButton btnPinjam;

    // TAB PENGEMBALIAN
    private JTable tableDipinjam;
    private DefaultTableModel modelDipinjam;
    private JButton btnKembalikan;

    // TAB RIWAYAT
    private JTable tableRiwayat;
    private DefaultTableModel modelRiwayat;

    public TransaksiPanel() {
        transaksiController = new TransaksiController();
        bukuController = new BukuController();
        anggotaController = new AnggotaController();

        initComponents();

        loadComboBox();
        loadDipinjam();
        loadRiwayat();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("Data Transaksi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Peminjaman", createPeminjamanPanel());
        tabbedPane.addTab("Pengembalian", createPengembalianPanel());
        tabbedPane.addTab("Riwayat", createRiwayatPanel());

        add(lblTitle, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);
    }

    // TAB PEMINJAMAN

    private JPanel createPeminjamanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cbBuku = new JComboBox<>();
        cbAnggota = new JComboBox<>();
        btnPinjam = new JButton("Pinjam Buku");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID Buku"), gbc);

        gbc.gridx = 1;
        panel.add(cbBuku, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("ID Anggota"), gbc);

        gbc.gridx = 1;
        panel.add(cbAnggota, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(btnPinjam, gbc);

        btnPinjam.addActionListener(e -> pinjamBuku());

        return panel;
    }

    // TAB PENGEMBALIAN
    private JPanel createPengembalianPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {
                "ID",
                "ID Buku",
                "ID Anggota",
                "Jatuh Tempo",
                "Status"
        };

        modelDipinjam = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableDipinjam = new JTable(modelDipinjam);
        tableDipinjam.setRowHeight(28);

        btnKembalikan = new JButton("Kembalikan Buku");
        btnKembalikan.addActionListener(e -> kembalikanBuku());

        panel.add(new JScrollPane(tableDipinjam), BorderLayout.CENTER);
        panel.add(btnKembalikan, BorderLayout.SOUTH);

        return panel;
    }

    // TAB RIWAYAT
    private JPanel createRiwayatPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] kolom = {
                "ID",
                "ID Buku",
                "ID Anggota",
                "Tgl Pinjam",
                "Jatuh Tempo",
                "Tgl Kembali",
                "Status",
                "Denda"
        };

        modelRiwayat = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRiwayat = new JTable(modelRiwayat);
        tableRiwayat.setRowHeight(28);

        panel.add(new JScrollPane(tableRiwayat), BorderLayout.CENTER);

        return panel;
    }

    // LOAD DATA
    private void loadComboBox() {
        cbBuku.removeAllItems();
        cbAnggota.removeAllItems();

        List<Buku> daftarBuku = bukuController.ambilSemuaBuku();

        for (Buku buku : daftarBuku) {
            cbBuku.addItem(buku.getId());
        }

        List<Anggota> daftarAnggota = anggotaController.ambilSemuaAnggota();

        for (Anggota anggota : daftarAnggota) {
            cbAnggota.addItem(anggota.getId());
        }
    }

    private void loadDipinjam() {
        modelDipinjam.setRowCount(0);

        List<Transaksi> daftar = transaksiController.ambilTransaksiDipinjam();

        for (Transaksi t : daftar) {
            modelDipinjam.addRow(new Object[]{
                    t.getId(),
                    t.getIdBuku(),
                    t.getIdAnggota(),
                    t.getTanggalJatuhTempo(),
                    t.getStatus()
            });
        }
    }

    private void loadRiwayat() {
        modelRiwayat.setRowCount(0);

        List<Transaksi> daftar = transaksiController.ambilSemuaTransaksi();

        for (Transaksi t : daftar) {
            modelRiwayat.addRow(new Object[]{
                    t.getId(),
                    t.getIdBuku(),
                    t.getIdAnggota(),
                    t.getTanggalPinjam(),
                    t.getTanggalJatuhTempo(),
                    t.getTanggalKembali(),
                    t.getStatus(),
                    t.getDenda()
            });
        }
    }

    // PEMINJAMAN
    private void pinjamBuku() {
        try {
            int idBuku = (Integer) cbBuku.getSelectedItem();
            int idAnggota = (Integer) cbAnggota.getSelectedItem();

            boolean berhasil = transaksiController.pinjamBuku(idBuku, idAnggota);

            if (berhasil) {
                JOptionPane.showMessageDialog(this, "Peminjaman berhasil");

                loadDipinjam();
                loadRiwayat();
            } else {
                JOptionPane.showMessageDialog(this, "Peminjaman gagal");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    // PENGEMBALIAN
    private void kembalikanBuku() {
        int row = tableDipinjam.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih transaksi terlebih dahulu");
            return;
        }

        int idTransaksi = Integer.parseInt(
                modelDipinjam.getValueAt(row, 0).toString()
        );

        boolean berhasil = transaksiController.kembalikanBuku(idTransaksi);

        if (berhasil) {
            JOptionPane.showMessageDialog(this, "Buku berhasil dikembalikan");

            loadDipinjam();
            loadRiwayat();
        } else {
            JOptionPane.showMessageDialog(this, "Pengembalian gagal");
        }
    }
    public void refreshData() {
    loadComboBox();
    loadDipinjam();
    loadRiwayat();
    }
}
