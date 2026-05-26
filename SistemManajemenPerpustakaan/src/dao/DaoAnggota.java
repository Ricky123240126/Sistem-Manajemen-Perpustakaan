/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import utilitas.Connector;
import model.anggota.Anggota;
 
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class DaoAnggota extends DaoInduk<Anggota>{
        private Connection koneksi;
 
    public DaoAnggota() {
        this.koneksi = Connector.dapatkanKoneksi();
    }
 
    @Override
    public void simpan(Anggota anggota) {
        String sql = "INSERT INTO anggota (nama, email, no_telepon, tipe_keanggotaan, tanggal_gabung, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getEmail());
            ps.setString(3, anggota.getNoTelp());
            ps.setString(4, anggota.getTipeKeanggotaan());
            ps.setString(5, anggota.getTanggalGabung());
            ps.setString(6, anggota.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan anggota: " + e.getMessage());
        }
    }
 
    @Override
    public void perbarui(Anggota anggota) {
        String sql = "UPDATE anggota SET nama = ?, email = ?, no_telepon = ?, "
                   + "tipe_keanggotaan = ?, status = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, anggota.getNama());
            ps.setString(2, anggota.getEmail());
            ps.setString(3, anggota.getNoTelp());
            ps.setString(4, anggota.getTipeKeanggotaan());
            ps.setString(5, anggota.getStatus());
            ps.setInt(6, anggota.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui anggota: " + e.getMessage());
        }
    }
 
    @Override
    public void hapus(int id) {
        String sql = "DELETE FROM anggota WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus anggota: " + e.getMessage());
        }
    }
 
    @Override
    public Anggota cariById(int id) {
        String sql = "SELECT * FROM anggota WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return petakanHasil(rs);
        } catch (SQLException e) {
            System.err.println("Gagal mencari anggota: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Anggota> cariSemua() {
        List<Anggota> daftarAnggota = new ArrayList<>();
        String sql = "SELECT * FROM anggota ORDER BY nama";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarAnggota.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil semua anggota: " + e.getMessage());
        }
        return daftarAnggota;
    }
 
    public List<Anggota> cariDenganKataKunci(String kataKunci) {
        List<Anggota> daftarAnggota = new ArrayList<>();
        String sql = "SELECT * FROM anggota WHERE nama LIKE ? OR email LIKE ? OR no_telepon LIKE ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            String pola = "%" + kataKunci + "%";
            ps.setString(1, pola);
            ps.setString(2, pola);
            ps.setString(3, pola);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                daftarAnggota.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari anggota: " + e.getMessage());
        }
        return daftarAnggota;
    }
 
    public boolean emailSudahAda(String email) {
        String sql = "SELECT COUNT(*) FROM anggota WHERE email = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Gagal memeriksa email: " + e.getMessage());
        }
        return false;
    }
 
    public int hitungPinjamanAktif(int idAnggota) {
        String sql = "SELECT COUNT(*) FROM transaksi WHERE id_anggota = ? AND status = 'dipinjam'";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idAnggota);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.err.println("Gagal menghitung pinjaman aktif: " + e.getMessage());
        }
        return 0;
    }
 
    private Anggota petakanHasil(ResultSet rs) throws SQLException {
        return new Anggota(
            rs.getInt("id"),
            rs.getString("nama"),
            rs.getString("email"),
            rs.getString("no_telepon"),
            rs.getString("tipe_keanggotaan"),
            rs.getString("tanggal_gabung"),
            rs.getString("status")
        );
    }
}
