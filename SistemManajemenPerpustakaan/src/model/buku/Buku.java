/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.buku;

/**
 *
 * @author Carbon
 */
public class Buku {
    // Menggunakan standar penamaan CamelCase dan menambahkan idKategori
    private int id, idKategori, jumlahStok, stokTersedia;
    private String judul, pengarang, isbn;
    
    // 1. Constructor Kosong
    public Buku(){
        
    }
    
    // 2. Constructor Penuh (Untuk select data dari database)
    public Buku(int id, int idKategori, int jumlahStok, int stokTersedia, String judul, String pengarang, String isbn){
        this.id = id;
        this.idKategori = idKategori;
        this.jumlahStok = jumlahStok;
        this.stokTersedia = stokTersedia;
        this.judul = judul;
        this.pengarang = pengarang;
        this.isbn = isbn;
    }
    
    // 3. Constructor Tanpa ID (Untuk insert data baru ke database, karena ID Auto Increment)
    public Buku(int idKategori, int jumlahStok, int stokTersedia, String judul, String pengarang, String isbn){
        this.idKategori = idKategori;
        this.jumlahStok = jumlahStok;
        this.stokTersedia = stokTersedia;
        this.judul = judul;
        this.pengarang = pengarang;
        this.isbn = isbn;
    }
    
    // 4. Getter dan Setter
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public int getIdKategori() {
        return idKategori;
    }
    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
    }

    public int getJumlahStok(){
        return jumlahStok;
    }
    public void setJumlahStok(int jumlahStok){
        this.jumlahStok = jumlahStok;
    }

    public int getStokTersedia(){
        return stokTersedia;
    }
    public void setStokTersedia(int stokTersedia){
        this.stokTersedia = stokTersedia;
    }

    public String getJudul(){
        return judul;
    }
    public void setJudul(String judul){
        this.judul = judul;
    }

    public String getPengarang(){
        return pengarang;
    }
    public void setPengarang(String pengarang){
        this.pengarang = pengarang;
    }

    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }
}
