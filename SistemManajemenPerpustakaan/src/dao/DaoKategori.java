/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import utilitas.Connector;
import model.buku.Kategori;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class DaoKategori extends DaoInduk<Kategori>{
    private Connection koneksi;
 
    public DaoKategori() {
        this.koneksi = Connector.dapatkanKoneksi();
    }
 
    @Override
    public void simpan(Kategori kategori) {
        String sql = "INSERT INTO kategori (nama) VALUES (?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, kategori.getNama());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan kategori: " + e.getMessage());
        }
    }
 
    @Override
    public void perbarui(Kategori kategori) {
        String sql = "UPDATE kategori SET nama = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, kategori.getNama());
            ps.setInt(2, kategori.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui kategori: " + e.getMessage());
        }
    }
 
    @Override
    public void hapus(int id) {
        String sql = "DELETE FROM kategori WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus kategori: " + e.getMessage());
        }
    }
 
    @Override
    public Kategori cariById(int id) {
        String sql = "SELECT * FROM kategori WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return petakanHasil(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari kategori: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Kategori> cariSemua() {
        List<Kategori> daftarKategori = new ArrayList<>();
        String sql = "SELECT * FROM kategori ORDER BY nama";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarKategori.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil semua kategori: " + e.getMessage());
        }
        return daftarKategori;
    }
 
    private Kategori petakanHasil(ResultSet rs) throws SQLException {
        return new Kategori(
            rs.getInt("id"),
            rs.getString("nama")
        );
    }
}
