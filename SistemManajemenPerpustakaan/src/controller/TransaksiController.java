/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.DaoAnggota;
import dao.DaoBuku;
import dao.DaoTransaksi;

import model.anggota.Anggota;
import model.buku.Buku;
import model.transaksi.Transaksi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class TransaksiController {
    private DaoTransaksi daoTransaksi;
    private DaoBuku daoBuku;
    private DaoAnggota daoAnggota;

    private final int MAKSIMAL_PINJAMAN = 3;
    private final int DENDA_PER_HARI = 1000;

    public TransaksiController() {
        daoTransaksi = new DaoTransaksi();
        daoBuku = new DaoBuku();
        daoAnggota = new DaoAnggota();
    }

    // PINJAM BUKU
    public boolean pinjamBuku(int idBuku, int idAnggota) {

        Buku buku = daoBuku.cariById(idBuku);

        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            return false;
        }

        if (buku.getStokTersedia() <= 0) {
            System.out.println("Stok buku tidak tersedia.");
            return false;
        }

        Anggota anggota = daoAnggota.cariById(idAnggota);

        if (anggota == null) {
            System.out.println("Anggota tidak ditemukan.");
            return false;
        }

        if (!anggota.getStatus().equalsIgnoreCase("aktif")) {
            System.out.println("Status anggota tidak aktif.");
            return false;
        }

        int jumlahPinjaman = daoAnggota.hitungPinjamanAktif(idAnggota);

        if (jumlahPinjaman >= MAKSIMAL_PINJAMAN) {
            System.out.println("Anggota telah mencapai batas pinjaman.");
            return false;
        }

        LocalDate tanggalPinjam = LocalDate.now();
        LocalDate tanggalJatuhTempo = tanggalPinjam.plusDays(7);

        Transaksi transaksi = new Transaksi(
                idBuku,
                idAnggota,
                tanggalPinjam.toString(),
                tanggalJatuhTempo.toString(),
                "dipinjam"
        );

        daoTransaksi.simpan(transaksi);

        daoBuku.kurangiStok(idBuku);

        return true;
    }

    // KEMBALIKAN BUKU
    public boolean kembalikanBuku(int idTransaksi) {

        Transaksi transaksi = daoTransaksi.cariById(idTransaksi);

        if (transaksi == null) {
            System.out.println("Transaksi tidak ditemukan.");
            return false;
        }

        if (transaksi.getStatus().equalsIgnoreCase("dikembalikan")) {
            System.out.println("Buku sudah dikembalikan.");
            return false;
        }

        LocalDate tanggalKembali = LocalDate.now();
        LocalDate tanggalJatuhTempo = LocalDate.parse(transaksi.getTanggalJatuhTempo());

        long terlambatHari = ChronoUnit.DAYS.between(
                tanggalJatuhTempo,
                tanggalKembali
        );

        int denda = 0;

        if (terlambatHari > 0) {
            denda = (int) terlambatHari * DENDA_PER_HARI;
        }

        transaksi.setTanggalKembali(tanggalKembali.toString());
        transaksi.setDenda(denda);
        transaksi.setStatus("dikembalikan");

        daoTransaksi.perbarui(transaksi);

        daoBuku.tambahStok(transaksi.getIdBuku());

        return true;
    }

    // UPDATE STATUS TERLAMBAT
    public void perbaruiStatusTerlambat() {
        daoTransaksi.perbaruiStatusTerlambat();
    }

    // CARI TRANSAKSI BERDASARKAN ID
    public Transaksi cariTransaksi(int id) {
        return daoTransaksi.cariById(id);
    }

    // AMBIL SEMUA TRANSAKSI
    public List<Transaksi> ambilSemuaTransaksi() {
        return daoTransaksi.cariSemua();
    }

    // AMBIL TRANSAKSI DIPINJAM
    public List<Transaksi> ambilTransaksiDipinjam() {
        return daoTransaksi.cariYangDipinjam();
    }

    // AMBIL TRANSAKSI TERLAMBAT
    public List<Transaksi> ambilTransaksiTerlambat() {
        return daoTransaksi.cariYangTerlambat();
    }

    // HITUNG DENDA
    public int hitungDenda(String tanggalJatuhTempo) {

        LocalDate jatuhTempo = LocalDate.parse(tanggalJatuhTempo);
        LocalDate hariIni = LocalDate.now();

        long selisihHari = ChronoUnit.DAYS.between(
                jatuhTempo,
                hariIni
        );

        if (selisihHari <= 0) {
            return 0;
        }

        return (int) selisihHari * DENDA_PER_HARI;
    }
}
