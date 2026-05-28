/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.DaoAnggota;
import model.anggota.Anggota;

import java.time.LocalDate;
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class AnggotaController {
    private DaoAnggota daoAnggota;

    public AnggotaController() {
        daoAnggota = new DaoAnggota();
    }

    // TAMBAH ANGGOTA
    public boolean tambahAnggota(String nama, String email,
                                 String noTelepon,
                                 String tipeKeanggotaan) {

        if (nama == null || nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return false;
        }

        if (email == null || email.isEmpty()) {
            System.out.println("Email tidak boleh kosong.");
            return false;
        }

        if (noTelepon == null || noTelepon.isEmpty()) {
            System.out.println("Nomor telepon tidak boleh kosong.");
            return false;
        }

        if (tipeKeanggotaan == null || tipeKeanggotaan.isEmpty()) {
            System.out.println("Tipe keanggotaan tidak boleh kosong.");
            return false;
        }

        if (daoAnggota.emailSudahAda(email)) {
            System.out.println("Email sudah digunakan.");
            return false;
        }

        String tanggalGabung = LocalDate.now().toString();

        Anggota anggota = new Anggota(
                nama,
                email,
                noTelepon,
                tipeKeanggotaan,
                tanggalGabung,
                "aktif"
        );

        daoAnggota.simpan(anggota);

        return true;
    }

    // UPDATE ANGGOTA
    public boolean perbaruiAnggota(int id, String nama,
                                   String email,
                                   String noTelepon,
                                   String tipeKeanggotaan,
                                   String status) {

        Anggota anggotaLama = daoAnggota.cariById(id);

        if (anggotaLama == null) {
            System.out.println("Anggota tidak ditemukan.");
            return false;
        }

        if (nama == null || nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return false;
        }

        if (email == null || email.isEmpty()) {
            System.out.println("Email tidak boleh kosong.");
            return false;
        }

        if (noTelepon == null || noTelepon.isEmpty()) {
            System.out.println("Nomor telepon tidak boleh kosong.");
            return false;
        }

        if (tipeKeanggotaan == null || tipeKeanggotaan.isEmpty()) {
            System.out.println("Tipe keanggotaan tidak boleh kosong.");
            return false;
        }

        if (!anggotaLama.getEmail().equals(email)
                && daoAnggota.emailSudahAda(email)) {

            System.out.println("Email sudah digunakan.");
            return false;
        }

        Anggota anggota = new Anggota(
                id,
                nama,
                email,
                noTelepon,
                tipeKeanggotaan,
                anggotaLama.getTanggalGabung(),
                status
        );

        daoAnggota.perbarui(anggota);

        return true;
    }

    // HAPUS ANGGOTA
    public boolean hapusAnggota(int id) {

        Anggota anggota = daoAnggota.cariById(id);

        if (anggota == null) {
            System.out.println("Anggota tidak ditemukan.");
            return false;
        }

        int pinjamanAktif = daoAnggota.hitungPinjamanAktif(id);

        if (pinjamanAktif > 0) {
            System.out.println("Anggota masih memiliki pinjaman aktif.");
            return false;
        }

        daoAnggota.hapus(id);

        return true;
    }

    // CARI BERDASARKAN ID
    public Anggota cariAnggota(int id) {
        return daoAnggota.cariById(id);
    }

    // CARI DENGAN KATA KUNCI
    public List<Anggota> cariAnggota(String kataKunci) {

        if (kataKunci == null || kataKunci.isEmpty()) {
            return daoAnggota.cariSemua();
        }

        return daoAnggota.cariDenganKataKunci(kataKunci);
    }

    // AMBIL SEMUA ANGGOTA
    public List<Anggota> ambilSemuaAnggota() {
        return daoAnggota.cariSemua();
    }

    // HITUNG PINJAMAN AKTIF
    public int hitungPinjamanAktif(int idAnggota) {
        return daoAnggota.hitungPinjamanAktif(idAnggota);
    }
}
