/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.buku.*;
import utilitas.Connector;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carbon
 */
public class DaoBuku extends DaoInduk<Buku>{
    private Connection koneksi;
 
    public DaoBuku() {
        this.koneksi = Connector.dapatkanKoneksi();
    }
 
    @Override
    public void simpan(Buku buku) {
        String sql = "INSERT INTO buku (judul, pengarang, isbn, id_kategori, jumlah_stok, stok_tersedia) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPengarang());
            ps.setString(3, buku.getIsbn());
            ps.setInt(4, buku.getIdKategori());
            ps.setInt(5, buku.getJumlahStok());
            ps.setInt(6, buku.getStokTersedia());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan buku: " + e.getMessage());
        }
    }
 
    @Override
    public void perbarui(Buku buku) {
        String sql = "UPDATE buku SET judul = ?, pengarang = ?, isbn = ?, "
                   + "id_kategori = ?, jumlah_stok = ?, stok_tersedia = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, buku.getJudul());
            ps.setString(2, buku.getPengarang());
            ps.setString(3, buku.getIsbn());
            ps.setInt(4, buku.getIdKategori());
            ps.setInt(5, buku.getJumlahStok());
            ps.setInt(6, buku.getStokTersedia());
            ps.setInt(7, buku.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui buku: " + e.getMessage());
        }
    }
 
    @Override
    public void hapus(int id) {
        String sql = "DELETE FROM buku WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus buku: " + e.getMessage());
        }
    }
 
    @Override
    public Buku cariById(int id) {
        String sql = "SELECT b.*, k.nama AS nama_kategori FROM buku b "
                   + "LEFT JOIN kategori k ON b.id_kategori = k.id WHERE b.id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return petakanHasil(rs);
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari buku: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Buku> cariSemua() {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama AS nama_kategori FROM buku b "
                   + "LEFT JOIN kategori k ON b.id_kategori = k.id ORDER BY b.judul";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarBuku.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil semua buku: " + e.getMessage());
        }
        return daftarBuku;
    }
 
    public List<Buku> cariDenganKataKunci(String kataKunci) {
        List<Buku> daftarBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama AS nama_kategori FROM buku b "
                   + "LEFT JOIN kategori k ON b.id_kategori = k.id "
                   + "WHERE b.judul LIKE ? OR b.pengarang LIKE ? OR b.isbn LIKE ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            String pola = "%" + kataKunci + "%";
            ps.setString(1, pola);
            ps.setString(2, pola);
            ps.setString(3, pola);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                daftarBuku.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mencari buku: " + e.getMessage());
        }
        return daftarBuku;
    }
 
    public boolean isbnSudahAda(String isbn) {
        String sql = "SELECT COUNT(*) FROM buku WHERE isbn = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.err.println("Gagal memeriksa ISBN: " + e.getMessage());
        }
        return false;
    }
 
    public void kurangiStok(int idBuku) {
        String sql = "UPDATE buku SET stok_tersedia = stok_tersedia - 1 WHERE id = ? AND stok_tersedia > 0";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal mengurangi stok: " + e.getMessage());
        }
    }
 
    public void tambahStok(int idBuku) {
        String sql = "UPDATE buku SET stok_tersedia = stok_tersedia + 1 WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, idBuku);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menambah stok: " + e.getMessage());
        }
    }
 
    private Buku petakanHasil(ResultSet rs) throws SQLException {
        Buku buku = new Buku();
        buku.setId(rs.getInt("id"));
        buku.setJudul(rs.getString("judul"));
        buku.setPengarang(rs.getString("pengarang"));
        buku.setIsbn(rs.getString("isbn"));
        buku.setIdKategori(rs.getInt("id_kategori"));
        buku.setJumlahStok(rs.getInt("jumlah_stok"));
        buku.setStokTersedia(rs.getInt("stok_tersedia"));
        return buku;
    }
}
