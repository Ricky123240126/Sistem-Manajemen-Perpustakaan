/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.DaoKategori;
import model.buku.Kategori;

import java.util.List;
/**
 *
 * @author Lenovo
 */
public class KategoriController {
    private DaoKategori daoKategori;

    public KategoriController() {
        daoKategori = new DaoKategori();
    }

    // TAMBAH KATEGORI
    public boolean tambahKategori(String nama) {

        if (nama == null || nama.isEmpty()) {
            System.out.println("Nama kategori tidak boleh kosong.");
            return false;
        }

        List<Kategori> daftarKategori = daoKategori.cariSemua();

        for (Kategori kategori : daftarKategori) {

            if (kategori.getNama().equalsIgnoreCase(nama)) {
                System.out.println("Kategori sudah ada.");
                return false;
            }
        }

        Kategori kategori = new Kategori(nama);

        daoKategori.simpan(kategori);

        return true;
    }

    // UPDATE KATEGORI
    public boolean perbaruiKategori(int id, String nama) {

        Kategori kategoriLama = daoKategori.cariById(id);

        if (kategoriLama == null) {
            System.out.println("Kategori tidak ditemukan.");
            return false;
        }

        if (nama == null || nama.isEmpty()) {
            System.out.println("Nama kategori tidak boleh kosong.");
            return false;
        }

        List<Kategori> daftarKategori = daoKategori.cariSemua();

        for (Kategori kategori : daftarKategori) {

            if (kategori.getNama().equalsIgnoreCase(nama)
                    && kategori.getId() != id) {

                System.out.println("Kategori sudah digunakan.");
                return false;
            }
        }

        Kategori kategori = new Kategori(id, nama);

        daoKategori.perbarui(kategori);

        return true;
    }

    // HAPUS KATEGORI
    public boolean hapusKategori(int id) {

        Kategori kategori = daoKategori.cariById(id);

        if (kategori == null) {
            System.out.println("Kategori tidak ditemukan.");
            return false;
        }

        daoKategori.hapus(id);

        return true;
    }

    // CARI BERDASARKAN ID
    public Kategori cariKategori(int id) {
        return daoKategori.cariById(id);
    }

    // AMBIL SEMUA KATEGORI
    public List<Kategori> ambilSemuaKategori() {
        return daoKategori.cariSemua();
    }
}
