/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.transaksi;

/**
 *
 * @author Carbon
 */
public class Transaksi {
    // 1. Atribut
    private int id;
    private int idBuku;
    private int idAnggota;
    private String tanggalPinjam; 
    private String tanggalJatuhTempo;
    private String tanggalKembali; // Bisa null jika belum dikembalikan
    private String status; // 'dipinjam', 'dikembalikan', 'terlambat'
    private int denda;

    // 2. Constructor Kosong
    public Transaksi() {
    }

    // 3. Constructor untuk Insert Data Baru (Peminjaman)
    // ID Auto Increment, tanggal kembali masih kosong/null, denda awal 0
    public Transaksi(int idBuku, int idAnggota, String tanggalPinjam, String tanggalJatuhTempo, String status) {
        this.idBuku = idBuku;
        this.idAnggota = idAnggota;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.status = status;
        this.denda = 0; 
    }

    // 4. Constructor Penuh (Untuk menampung hasil query SELECT)
    public Transaksi(int id, int idBuku, int idAnggota, String tanggalPinjam, 
                     String tanggalJatuhTempo, String tanggalKembali, String status, int denda) {
        this.id = id;
        this.idBuku = idBuku;
        this.idAnggota = idAnggota;
        this.tanggalPinjam = tanggalPinjam;
        this.tanggalJatuhTempo = tanggalJatuhTempo;
        this.tanggalKembali = tanggalKembali;
        this.status = status;
        this.denda = denda;
    }

    // 5. Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBuku() {
        return idBuku;
    }

    public void setIdBuku(int idBuku) {
        this.idBuku = idBuku;
    }

    public int getIdAnggota() {
        return idAnggota;
    }

    public void setIdAnggota(int idAnggota) {
        this.idAnggota = idAnggota;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public void setTanggalPinjam(String tanggalPinjam) {
        this.tanggalPinjam = tanggalPinjam;
    }

    public String getTanggalJatuhTempo() {
        return tanggalJatuhTempo;
    }

    public void setTanggalJatuhTempo(String tanggalJatuhTempo) {
        this.tanggalJatuhTempo = tanggalJatuhTempo;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(String tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDenda() {
        return denda;
    }

    public void setDenda(int denda) {
        this.denda = denda;
    }
}
