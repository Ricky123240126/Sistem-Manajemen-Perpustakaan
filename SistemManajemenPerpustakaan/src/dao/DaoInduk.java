/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.util.List;

/**
 *
 * @author Carbon
 */
public abstract class DaoInduk<T>{
    public abstract void simpan(T entitas);
    public abstract void perbarui(T entitas);
    public abstract void hapus(int id);
    public abstract T cariById(int id);
    public abstract List<T> cariSemua();
}
