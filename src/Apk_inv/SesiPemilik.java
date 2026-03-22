/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;

/**
 *
 * @author Agung
 */
public class SesiPemilik {
    private static String sp_id_user;
    private static String sp_nama_user;
    private static String sp_password;
    private static String sp_jenis_akses;
    
     public static String GetU_id_user(){
        return sp_id_user;
    }
    
    public static void SetU_id_user(String sp_id_user){
        SesiPemilik.sp_id_user = sp_id_user; 
    }
    
    public static String GetU_nama_user(){
        return sp_nama_user;
    }
    
    public static void SetU_nama_user(String sp_nama_user){
        SesiPemilik.sp_nama_user = sp_nama_user; 
    }
    
     public static String GetU_password(){
        return sp_password;
    }
    
    public static void SetU_password(String sp_password){
        SesiPemilik.sp_password = sp_password; 
    }
     public static String GetU_jenis_akses(){
        return sp_jenis_akses;
    }
    
    public static void SetU_jenis_akses(String sp_jenis_akses){
        SesiPemilik.sp_jenis_akses = sp_jenis_akses; 
    }
}
