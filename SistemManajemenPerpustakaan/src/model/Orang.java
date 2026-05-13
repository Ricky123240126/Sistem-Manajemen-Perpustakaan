/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Carbon
 */
public abstract class Orang {
    private int id;
    private String nama;
    private String email;
    private String noTelepon;
    
    public Orang (){}
    
    public Orang (int id, String nama, String email, String noTelepon){
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
    }
    
    public Orang (String nama, String email, String noTelepon){
        this.nama = nama;
        this.email = email;
        this.noTelepon = noTelepon;
    }
    
    public abstract String getRole();
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public String getNama(){
        return nama;
    }
    public void setNama(String nama){
        this.nama = nama;
    }
    
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getNoTelp(){
        return noTelepon;
    }
    public void setNoTelp(){
        this.noTelepon = noTelepon;
    }
    
    @Override
    public String toString() {
        return "Orang{id=" + id + ", nama='" + nama + "'}";
    }
}
