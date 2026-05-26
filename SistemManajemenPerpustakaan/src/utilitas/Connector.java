/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitas;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carbon
 */
public class Connector {
    private static final String URL      = "jdbc:mysql://localhost:3306/perpustakaan_db";
    private static final String PENGGUNA = "root";
    private static final String SANDI    = "";
 
    private static Connection koneksi = null;
 
    // constructor private agar tidak bisa di-instantiate (Singleton)
    private Connector() {}
 
    public static Connection dapatkanKoneksi() {
        try {
            if (koneksi == null || koneksi.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(URL, PENGGUNA, SANDI);
                System.out.println("Koneksi database berhasil.");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Gagal terhubung ke database: " + e.getMessage());
        }
        return koneksi;
    }
 
    public static void tutupKoneksi() {
        try {
            if (koneksi != null && !koneksi.isClosed()) {
                koneksi.close();
                System.out.println("Koneksi database ditutup.");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
