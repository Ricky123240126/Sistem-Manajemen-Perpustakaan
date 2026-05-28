/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import dao.DaoPengguna; 
import model.Orang; 
import model.pengguna.Admin; 
import model.pengguna.Pustakawan; 
import java.util.List;
/**
 *
 * @author Lenovo
 */
public class PenggunaController {
    private DaoPengguna daoPengguna;

    public PenggunaController() {
        daoPengguna = new DaoPengguna();
    }

    // LOGIN
    public Orang login(String username, String password) {

        if (username == null || username.isEmpty()) {
            System.out.println("Username tidak boleh kosong.");
            return null;
        }

        if (password == null || password.isEmpty()) {
            System.out.println("Password tidak boleh kosong.");
            return null;
        }

        return daoPengguna.login(username, password);
    }

    // TAMBAH ADMIN
    public boolean tambahAdmin(String username, String password) {

        if (username == null || username.isEmpty()) {
            System.out.println("Username tidak boleh kosong.");
            return false;
        }

        if (password == null || password.isEmpty()) {
            System.out.println("Password tidak boleh kosong.");
            return false;
        }

        if (daoPengguna.usernameSudahAda(username)) {
            System.out.println("Username sudah digunakan.");
            return false;
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        daoPengguna.simpan(admin);

        return true;
    }

    // TAMBAH PUSTAKAWAN
    public boolean tambahPustakawan(String username, String password) {

        if (username == null || username.isEmpty()) {
            System.out.println("Username tidak boleh kosong.");
            return false;
        }

        if (password == null || password.isEmpty()) {
            System.out.println("Password tidak boleh kosong.");
            return false;
        }

        if (daoPengguna.usernameSudahAda(username)) {
            System.out.println("Username sudah digunakan.");
            return false;
        }

        Pustakawan pustakawan = new Pustakawan();
        pustakawan.setUsername(username);
        pustakawan.setPassword(password);

        daoPengguna.simpan(pustakawan);

        return true;
    }

    // UPDATE PASSWORD
    public boolean ubahPassword(Orang pengguna, String passwordBaru) {

        if (passwordBaru == null || passwordBaru.isEmpty()) {
            System.out.println("Password baru tidak boleh kosong.");
            return false;
        }

        if (pengguna instanceof Admin admin) {
            admin.setPassword(passwordBaru);
            daoPengguna.perbarui(admin);

        } else if (pengguna instanceof Pustakawan pustakawan) {
            pustakawan.setPassword(passwordBaru);
            daoPengguna.perbarui(pustakawan);
        }

        return true;
    }

    // HAPUS PENGGUNA
    public boolean hapusPengguna(int id) {

        Orang pengguna = daoPengguna.cariById(id);

        if (pengguna == null) {
            System.out.println("Pengguna tidak ditemukan.");
            return false;
        }

        daoPengguna.hapus(id);

        return true;
    }

    // CARI BERDASARKAN ID
    public Orang cariPengguna(int id) {
        return daoPengguna.cariById(id);
    }

    // AMBIL SEMUA PENGGUNA
    public List<Orang> ambilSemuaPengguna() {
        return daoPengguna.cariSemua();
    }
}
