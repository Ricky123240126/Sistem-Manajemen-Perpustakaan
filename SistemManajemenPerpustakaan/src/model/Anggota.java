/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Carbon
 */
public class Anggota extends Orang{
    private String tipeKeanggotaan;
    private String tanggalGabung;
    private String status;
    
    public Anggota(){
        super();
    }
    
    public Anggota(int id, String nama, String email, String noTelepon, 
                   String tipeKeanggotaan, String tanggalGabung, String status){
        super(id, nama, email, noTelepon);
        this.tipeKeanggotaan = tipeKeanggotaan;
        this.tanggalGabung = tanggalGabung;
        this.status = status;
    }
    
    public Anggota(String nama, String email, String noTelepon, 
                   String tipeKeanggotaan, String tanggalGabung, String status){
        super(nama, email, noTelepon);
        this.tipeKeanggotaan = tipeKeanggotaan;
        this.tanggalGabung = tanggalGabung;
        this.status = "aktif";
    }
    
    @Override
    public String getRole() {
        return "Anggota"; 
    }

    // Getter & Setter
    public String getTipeKeanggotaan() { 
        return tipeKeanggotaan; 
    }
    public void setTipeKeanggotaan(String tipeKeanggotaan) {
        this.tipeKeanggotaan = tipeKeanggotaan; 
    }

    public String getTanggalGabung() {
        return tanggalGabung; 
    }
    public void setTanggalGabung(String tanggalGabung) {
        this.tanggalGabung = tanggalGabung; 
    }

    public String getStatus() {
        return status; 
    }
    public void setStatus(String status) {
        this.status = status; 
    }
}
