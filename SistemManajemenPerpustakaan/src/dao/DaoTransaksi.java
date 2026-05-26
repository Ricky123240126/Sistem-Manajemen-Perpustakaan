/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import model.transaksi.Transaksi;
import utilitas.Connector;
 
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Carbon
 */
public class DaoTransaksi extends DaoInduk<Transaksi>{
        private Connection koneksi;
 
    public DaoTransaksi() {
        this.koneksi = Connector.dapatkanKoneksi();
    }
 
    @Override
    public void simpan(Transaksi transaksi) {
        String sql = "INSERT INTO transaksi (id_buku, id_anggota, tanggal_pinjam, tanggal_jatuh_tempo, status, denda) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, transaksi.getIdBuku());
            ps.setInt(2, transaksi.getIdAnggota());
            ps.setDate(3, Date.valueOf(transaksi.getTanggalPinjam()));
            ps.setDate(4, Date.valueOf(transaksi.getTanggalJatuhTempo()));
            ps.setString(5, transaksi.getStatus());
            ps.setInt(6, transaksi.getDenda());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan transaksi: " + e.getMessage());
        }
    }
 
    @Override
    public void perbarui(Transaksi transaksi) {
        String sql = "UPDATE transaksi SET tanggal_kembali = ?, status = ?, denda = ? WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setDate(1, transaksi.getTanggalKembali() != null
                ? Date.valueOf(transaksi.getTanggalKembali()) : null);
            ps.setString(2, transaksi.getStatus());
            ps.setInt(3, transaksi.getDenda());
            ps.setInt(4, transaksi.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui transaksi: " + e.getMessage());
        }
    }
 
    @Override
    public void hapus(int id) {
        String sql = "DELETE FROM transaksi WHERE id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Gagal menghapus transaksi: " + e.getMessage());
        }
    }
 
    @Override
    public Transaksi cariById(int id) {
        String sql = "SELECT t.*, b.judul AS judul_buku, a.nama AS nama_anggota "
                   + "FROM transaksi t "
                   + "JOIN buku b ON t.id_buku = b.id "
                   + "JOIN anggota a ON t.id_anggota = a.id "
                   + "WHERE t.id = ?";
        try (PreparedStatement ps = koneksi.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return petakanHasil(rs);
        } catch (SQLException e) {
            System.err.println("Gagal mencari transaksi: " + e.getMessage());
        }
        return null;
    }
 
    @Override
    public List<Transaksi> cariSemua() {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT t.*, b.judul AS judul_buku, a.nama AS nama_anggota "
                   + "FROM transaksi t "
                   + "JOIN buku b ON t.id_buku = b.id "
                   + "JOIN anggota a ON t.id_anggota = a.id "
                   + "ORDER BY t.tanggal_pinjam DESC";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarTransaksi.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil semua transaksi: " + e.getMessage());
        }
        return daftarTransaksi;
    }
 
    public List<Transaksi> cariYangDipinjam() {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT t.*, b.judul AS judul_buku, a.nama AS nama_anggota "
                   + "FROM transaksi t "
                   + "JOIN buku b ON t.id_buku = b.id "
                   + "JOIN anggota a ON t.id_anggota = a.id "
                   + "WHERE t.status = 'dipinjam' ORDER BY t.tanggal_jatuh_tempo";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarTransaksi.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil transaksi dipinjam: " + e.getMessage());
        }
        return daftarTransaksi;
    }
 
    public List<Transaksi> cariYangTerlambat() {
        List<Transaksi> daftarTransaksi = new ArrayList<>();
        String sql = "SELECT t.*, b.judul AS judul_buku, a.nama AS nama_anggota "
                   + "FROM transaksi t "
                   + "JOIN buku b ON t.id_buku = b.id "
                   + "JOIN anggota a ON t.id_anggota = a.id "
                   + "WHERE t.status = 'dipinjam' AND t.tanggal_jatuh_tempo < CURDATE()";
        try (Statement st = koneksi.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                daftarTransaksi.add(petakanHasil(rs));
            }
        } catch (SQLException e) {
            System.err.println("Gagal mengambil transaksi terlambat: " + e.getMessage());
        }
        return daftarTransaksi;
    }
 
    public void perbaruiStatusTerlambat() {
        String sql = "UPDATE transaksi SET status = 'terlambat' "
                   + "WHERE status = 'dipinjam' AND tanggal_jatuh_tempo < CURDATE()";
        try (Statement st = koneksi.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Gagal memperbarui status terlambat: " + e.getMessage());
        }
    }
 
    private Transaksi petakanHasil(ResultSet rs) throws SQLException {
        Transaksi transaksi = new Transaksi();
        transaksi.setId(rs.getInt("id"));
        transaksi.setIdBuku(rs.getInt("id_buku"));
        transaksi.setIdAnggota(rs.getInt("id_anggota"));
        transaksi.setTanggalPinjam(rs.getString("tanggal_pinjam"));
        transaksi.setTanggalJatuhTempo(rs.getString("tanggal_jatuh_tempo"));
        transaksi.setTanggalKembali(rs.getString("tanggal_kembali"));
        transaksi.setStatus(rs.getString("status"));
        transaksi.setDenda(rs.getInt("denda"));
        return transaksi;
    }
}
