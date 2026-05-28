/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.DaoBuku;
import model.buku.Buku;

import java.util.List;
/**
 *
 * @author Lenovo
 */
public class BukuController {
    private DaoBuku daoBuku;

    public BukuController() {
        daoBuku = new DaoBuku();
    }

    // TAMBAH BUKU
    public boolean tambahBuku(String judul, String pengarang, String isbn,
                              int idKategori, int jumlahStok) {

        if (judul == null || judul.isEmpty()) {
            System.out.println("Judul buku tidak boleh kosong.");
            return false;
        }

        if (pengarang == null || pengarang.isEmpty()) {
            System.out.println("Pengarang tidak boleh kosong.");
            return false;
        }

        if (isbn == null || isbn.isEmpty()) {
            System.out.println("ISBN tidak boleh kosong.");
            return false;
        }

        if (jumlahStok <= 0) {
            System.out.println("Jumlah stok harus lebih dari 0.");
            return false;
        }

        if (daoBuku.isbnSudahAda(isbn)) {
            System.out.println("ISBN sudah digunakan.");
            return false;
        }

        Buku buku = new Buku(
                idKategori,
                jumlahStok,
                jumlahStok,
                judul,
                pengarang,
                isbn
        );

        daoBuku.simpan(buku);

        return true;
    }

    // UPDATE BUKU
    public boolean perbaruiBuku(int id, String judul, String pengarang,
                                String isbn, int idKategori,
                                int jumlahStok, int stokTersedia) {

        Buku bukuLama = daoBuku.cariById(id);

        if (bukuLama == null) {
            System.out.println("Buku tidak ditemukan.");
            return false;
        }

        if (judul == null || judul.isEmpty()) {
            System.out.println("Judul buku tidak boleh kosong.");
            return false;
        }

        if (pengarang == null || pengarang.isEmpty()) {
            System.out.println("Pengarang tidak boleh kosong.");
            return false;
        }

        if (isbn == null || isbn.isEmpty()) {
            System.out.println("ISBN tidak boleh kosong.");
            return false;
        }

        if (!bukuLama.getIsbn().equals(isbn) && daoBuku.isbnSudahAda(isbn)) {
            System.out.println("ISBN sudah digunakan.");
            return false;
        }

        if (jumlahStok < 0 || stokTersedia < 0) {
            System.out.println("Stok tidak boleh negatif.");
            return false;
        }

        Buku buku = new Buku(
                id,
                idKategori,
                jumlahStok,
                stokTersedia,
                judul,
                pengarang,
                isbn
        );

        daoBuku.perbarui(buku);

        return true;
    }

    // HAPUS BUKU
    public boolean hapusBuku(int id) {

        Buku buku = daoBuku.cariById(id);

        if (buku == null) {
            System.out.println("Buku tidak ditemukan.");
            return false;
        }

        daoBuku.hapus(id);

        return true;
    }

    // CARI BERDASARKAN ID
    public Buku cariBuku(int id) {
        return daoBuku.cariById(id);
    }

    // CARI DENGAN KATA KUNCI
    public List<Buku> cariBuku(String kataKunci) {

        if (kataKunci == null || kataKunci.isEmpty()) {
            return daoBuku.cariSemua();
        }

        return daoBuku.cariDenganKataKunci(kataKunci);
    }

    // AMBIL SEMUA BUKU
    public List<Buku> ambilSemuaBuku() {
        return daoBuku.cariSemua();
    }

    // CEK STOK TERSEDIA
    public boolean stokTersedia(int idBuku) {

        Buku buku = daoBuku.cariById(idBuku);

        if (buku == null) {
            return false;
        }

        return buku.getStokTersedia() > 0;
    }

    // KURANGI STOK
    public void kurangiStok(int idBuku) {
        daoBuku.kurangiStok(idBuku);
    }

    // TAMBAH STOK
    public void tambahStok(int idBuku) {
        daoBuku.tambahStok(idBuku);
    }
}
