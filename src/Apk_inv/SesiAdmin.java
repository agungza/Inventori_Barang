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
public class SesiAdmin {
    private static String sa_id_user;
    private static String sa_nama_user;
    private static String sa_password;
    private static String sa_jenis_akses;
    
     public static String GetU_id_user(){
        return sa_id_user;
    }
    
    public static void SetU_id_user(String sa_id_user){
        SesiAdmin.sa_id_user = sa_id_user; 
    }
    
    public static String GetU_nama_user(){
        return sa_nama_user;
    }
    
    public static void SetU_nama_user(String sa_nama_user){
        SesiAdmin.sa_nama_user = sa_nama_user; 
    }
    
     public static String GetU_password(){
        return sa_password;
    }
    
    public static void SetU_password(String sa_password){
        SesiAdmin.sa_password = sa_password; 
    }
     public static String GetU_jenis_akses(){
        return sa_jenis_akses;
    }
    
    public static void SetU_jenis_akses(String sa_jenis_akses){
        SesiAdmin.sa_jenis_akses = sa_jenis_akses; 
    }
}
