/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.pengguna;

import model.Orang;

/**
 *
 * @author Carbon
 */
public class Admin extends Orang{
    private String username;
    private String password;
 
    public Admin() {
        super();
    }
 
    public Admin(int id, String nama, String email, String noTelepon,
                      String username, String password) {
        super(id, nama, email, noTelepon);
        this.username = username;
        this.password = password;
    }
 
    public Admin(String nama, String email, String noTelepon,
                      String username, String password) {
        super(nama, email, noTelepon);
        this.username = username;
        this.password = password;
    }
 
    @Override
    public String getRole() {
        return "Admin"; 
    }
 
    public String getNamapengguna() {
        return username; 
    }
    public void setNamapengguna(String username) {
        this.username = username; 
    }
 
    public String getPassword() {
        return password; 
    }
    public void setPassword(String password) {
        this.password = password; 
    }
 
    @Override
    public String toString() {
        return "Pustakawan{id=" + getId() + ", nama='" + getNama() + "', namapengguna='" + username + "'}";
    }
}
