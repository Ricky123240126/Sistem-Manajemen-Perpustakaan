/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.pengguna.*;
import model.Orang;
import utilitas.Connector;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class DaoPengguna extends DaoInduk<Orang>{
    private Connection koneksi;
 
    public DaoPengguna() {
        this.koneksi = Connector.dapatkanKoneksi();
    }
 
    // Login — kembalikan Admin atau Pustakawan sesuai role
    public Orang login(String username, String password) {
        String sql = "SELECT * FROM pengguna WHERE username = ? AND password = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return petakanHasil(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal melakukan login: " + e.getMessage());
        }
        return null; // login gagal
    }
 
    @Override
    public void simpan(Orang pengguna) {
        String sql = "INSERT INTO pengguna (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            if (pengguna instanceof Admin admin) {
                ps.setString(1, admin.getUsername());
                ps.setString(2, admin.getPassword());
                ps.setString(3, "admin");
            } else if (pengguna instanceof Pustakawan pustakawan) {
                ps.setString(1, pustakawan.getUsername());
                ps.setString(2, pustakawan.getPassword());
                ps.setString(3, "pustakawan");
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan pengguna: " + e.getMessage());
        }
    }
 
    @Override
    public void perbarui(Orang pengguna) {
        String sql = "UPDATE pengguna SET password = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            if (pengguna instanceof Admin admin) {
                ps.setString(1, admin.getPassword());
            } else if (pengguna instanceof Pustakawan pustakawan) {
                ps.setString(1, pustakawan.getPassword());
            }
            ps.setInt(2, pengguna.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui pengguna: " + e.getMessage());
        }
    }
 
    @Override
    public void hapus(int id) {
        String sql = "DELETE FROM pengguna WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus pengguna: " + e.getMessage());
        }
    }
 
    @Override
    public Orang cariById(int id) {
        String sql = "SELECT * FROM pengguna WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return petakanHasil(rs);
        } catch (SQLException e) {
            System.err.println("Gagal mencari pengguna: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Orang> cariSemua() {
        List<Orang> daftarPengguna = new ArrayList<>();
        String sql = "SELECT * FROM pengguna ORDER BY username";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarPengguna.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil semua pengguna: " + e.getMessage());
        }
        return daftarPengguna;
    }
 
    public boolean usernameSudahAda(String username) {
        String sql = "SELECT COUNT(*) FROM pengguna WHERE username = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Gagal memeriksa username: " + e.getMessage());
        }
        return false;
    }
 
    private Orang petakanHasil(ResultSet rs) throws SQLException {
        int id         = rs.getInt("id");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role     = rs.getString("role");
 
        if (role.equals("admin")) {
            Admin admin = new Admin();
            admin.setId(id);
            admin.setUsername(username);
            admin.setPassword(password);
            return admin;
        } else {
            Pustakawan pustakawan = new Pustakawan();
            pustakawan.setId(id);
            pustakawan.setUsername(username);
            pustakawan.setPassword(password);
            return pustakawan;
        }
    }
}
