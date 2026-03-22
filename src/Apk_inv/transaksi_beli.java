/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;

import java.awt.Color;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author Agung
 */
public class transaksi_beli extends javax.swing.JFrame {

    /**
     * Creates new form transaksi_beli
     */
    
    Apk_inv.koneksi koneksinya = new Apk_inv.koneksi();
    Apk_inv.SesiPemilik PemilikSession = new Apk_inv.SesiPemilik();
    Apk_inv.SesiAdmin AdminSession = new Apk_inv.SesiAdmin();
    
    public transaksi_beli() {
        initComponents();
        pilsupplier();
        this.setLocationRelativeTo(null);
//        jtxt_idsupp.hide();
        jtxt_baristrpilih.hide();
        tombolkurang(false);
        tomboledit(false);
        set_namauser();
        hitungtotalpembeliansemua();
    }
    
    public void pilsupplier(){
        String nmsupp;
        int nmr=0;
        try {
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM supplier";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmsupp=hasil.getString("nama_supplier");
                jcb_pilsupp.addItem(nmsupp);
            }  
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
    
    public void penomorenauto(){
        int nomerawal = 1;
        int x = 1;
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menambah data transaksi?",
            "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            ;
        }
    }
    
    public void set_namauser(){
        jlbl_nmuser.setText(PemilikSession.GetU_nama_user());
        jlbl_setidusernya.setText(PemilikSession.GetU_id_user());
    }

    public void tambahbarang(){
        int nmr = 1;
        String nofaktur = jtxt_nofktur.getText();
        String idbrgnya = jtxt_idbrg.getText();
        String nmbrgnya = jlbl_idbrg.getText();
        int hrgbrg = Integer.valueOf(jtxt_hrgbelibrg.getText());
        int jmlhbrg = Integer.valueOf(jtxt_jmlh.getText());
        int totalbrgnya = Integer.valueOf(jtxt_total.getText());
        if(!"".equals(idbrgnya) && !"".equals(nmbrgnya) && !"".equals(jmlhbrg) && !"".equals(hrgbrg)
           && !"".equals(totalbrgnya) && !"".equals(nofaktur)){
                //untuk menambahkan barang ke tabel sementara
                    Object[] row = { idbrgnya, nmbrgnya, hrgbrg, jmlhbrg, totalbrgnya };
                    DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
                    model.addRow(row);
                    jtxt_idbrg.setText("");
                    jlbl_idbrg.setText("-");
                    jtxt_hrgbelibrg.setText("");
                    jtxt_jmlh.setText("");
                    jtxt_total.setText("");         
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
    }   

    public void kurangibarang(){
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            String baris= Integer.toString(tabel_brgmsk.getSelectedRow());
            jtxt_baristrpilih.setText(baris);
            DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
            int row = Integer.parseInt(jtxt_baristrpilih.getText());
            model.removeRow(row);
        }
    }
    
    public void caribarang(){
        String idbrgnya = jtxt_idbrg.getText();
        String nmbrgnya = jlbl_idbrg.getText();
        String hrgbrg = jtxt_hrgbelibrg.getText();
        String cari =jtxt_idbrg.getText();
        if(!idbrgnya.equals("")){
                //--------- Cek In jtable
            int idygsama = 0;
            DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if(idbrgnya.equals(tabel_brgmsk.getModel().getValueAt(i, 1).toString())){
                    idygsama = 1;
                }
            }
            if(idygsama == 0){
                try {
                    
                    Connection con = db_koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "Select nama_barang, harga_beli from barang where id_barang like '" + cari + "'" +
                        "or nama_barang like '%" + cari + "%' ";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                    if(hasil.next()){
                        idbrgnya=hasil.getString("nama_barang");
                        jlbl_idbrg.setText(idbrgnya);
                        hrgbrg=hasil.getString("harga_beli");
                        jtxt_hrgbelibrg.setText(hrgbrg);
                    }else{
                        JOptionPane.showMessageDialog(null, "Barang tidak ditemukan / belum ada di database.");
                        jtxt_idbrg.setText("");
                        jlbl_idbrg.setText("-");
                        jtxt_hrgbelibrg.setText("");
                        jtxt_jmlh.setText("");
                        jtxt_total.setText("");;
                    }
                    //konek.closekoneksi();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
            }else{
                JOptionPane.showMessageDialog(null, "Barang sudah pernah ditambahkan.");
                jtxt_idbrg.setText("");
            }
        }else{
            jtxt_idbrg.setText("");
            jlbl_idbrg.setText("-");
            jtxt_hrgbelibrg.setText(""); 
        }
    }
    
    public void namaheadertabel(){
        Object [] baris = {"Id Barang","Nama Barang", "Harga Beli",
            "Jumlah","Total"};
        DefaultTableModel modeltbbrg;
        modeltbbrg = new DefaultTableModel(null, baris);
        tabel_brgmsk.setModel(modeltbbrg);
    }
    
    public void hitunghargajumlah(){
        int jmlhbrg = Integer.parseInt(String.valueOf(jtxt_jmlh.getText()));
        int hrgbrg  = Integer.parseInt(jtxt_hrgbelibrg.getText());
        int total   = 0;
        if(!"".equals(jmlhbrg) && !"".equals(hrgbrg) )
            {
                total = (hrgbrg * jmlhbrg);
                String hasil = Integer.toString(total);
                jtxt_total.setText(hasil);
        
            }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
            }
    }
    
    public void tombolkurang(boolean a){
        jbttn_kurangi.setEnabled(a);
    }
    
    public void tomboledit(boolean a){
      // jbttn_edit.setEnabled(a);
    }
    
    public void kosongkantabel(){
        DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    public void hitungtotalpembeliansemua(){
        try {
            int rowsCount = tabel_brgmsk.getRowCount();
            int totalnya = 0;
            for(int i = 0; i < rowsCount; i++){
                totalnya = totalnya+Integer.parseInt(tabel_brgmsk.getValueAt(i, 4).toString());
                 set_pembeliantotal.setText(Integer.toString(totalnya));
            }       
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro disini " + e);
        }
        
    }
    
    public void simpanbarangmasuk(){
        String tglmasuk = jdt_tglbrgmsk.getDateFormatString();
        String setdatenya = "yyyy-MM-dd";
        SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
        String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglbrgmsk.getDate()));
        String idusernya = jlbl_setidusernya.getText();
        String nmusernya = jlbl_nmuser.getText();
        String idsupplier = jtxt_idsupp.getText();
        String nofaktur = jtxt_nofktur.getText();
        int hasiltotalhrgbeli = Integer.parseInt(set_pembeliantotal.getText()) ;
        String idbrg, kode, id_brgmsk;
        Integer stok, tidak_ditemukan, totalhrgabeli, kosong = 0;
        String totalharga,jumlah,hrgbli;
        
        DefaultTableModel model = (DefaultTableModel) tabel_brgmsk.getModel();
        int rowCount = model.getRowCount();
        if(!"".equals(tglmasuk) && !"".equals(idusernya) && !"".equals(nmusernya) 
                && !"".equals(nofaktur) && !"".equals(idsupplier)){
                //------- Memasukan pada tabel transaksi lihat [tabel barang_] dan mengeluarkan id terakhir
            try{
                Connection con = db_koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "INSERT INTO transaksi_barang_masuk(no_fak, id_supplier, id_user, tgl_brgmsk, total_bayar)VALUES('"
                + nofaktur + "', '" +  idsupplier+ "', '" + idusernya  + "', '"+dateinputnya+"', '" + hasiltotalhrgbeli + "')"; 
                sttment.executeUpdate(exec_sql);
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            
            try{
                Connection con = db_koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT MAX(no_fak) as max FROM transaksi_barang_masuk";
                ResultSet hasil = sttment.executeQuery(exec_sql); 
                while(hasil.next()) 
                    {
                        id_brgmsk = hasil.getString("max");
                    }
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }   
            for (int i = 0; i < rowCount; i++) {
                tidak_ditemukan = 0;
                stok = 0;
                idbrg = (tabel_brgmsk.getModel().getValueAt(i, 0).toString());
                hrgbli = (tabel_brgmsk.getModel().getValueAt(i, 2).toString());
                jumlah = (tabel_brgmsk.getModel().getValueAt(i, 3).toString());
                totalharga = (tabel_brgmsk.getModel().getValueAt(i, 4).toString());
                    //------- Menjumlahkan stok dengan data jumlah
                try {
                    Connection con = db_koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "SELECT stok FROM barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                   // sttment.executeUpdate(exec_sql);
                    while(hasil.next())
                        {
                            if (hasil.getRow() == 1){
                                stok = Integer.parseInt(jumlah) + hasil.getInt("stok");
                            }else {
                                tidak_ditemukan = 1;
                            }
                        }
                }catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
                if(tidak_ditemukan == 0){
                        //------- Mengupdate jumlah stok barang
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("UPDATE barang SET stok='" + stok 
                        + "' WHERE id_barang = '" + idbrg + "'");
                    }catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                        //------- Memasukan pada table transaksi detail
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("INSERT INTO detail_transaksi_barangmsk(id_barang, no_fak, "
                          + "harga_beli, jumlah_brgmsk, total_hargabeli) VALUES ('" 
                          + idbrg + "', '" + nofaktur+ "', '" + hrgbli + "', '" + Integer.parseInt(jumlah) + "', '" 
                          + Integer.parseInt(totalharga)+ "')");
                        kosong = 1;
                    }catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
//                    JOptionPane.showMessageDialog(null, "Sistem tidak menemukan barang dengan Id Barang = " + idbrg + " Gagal Disimpan");
                }
                    //------- Opsi jika terdapat barang yang belum satupun di masukan
                if(kosong == 0){
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("DELETE FROM  transaksi_barang_masuk WHERE no_fak = '" + nofaktur + "'");
                    }catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
                }
            }
            JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi");
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
            kosongkantabel();
            namaheadertabel();
            jdt_tglbrgmsk.setDateFormatString("");
            jtxt_nofktur.setText("");
            jtxt_baristrpilih.setText("");
            jtxt_idbrg.setText("");
            jtxt_idsupp.setText("");
            jtxt_jmlh.setText("");
            jtxt_hrgbelibrg.setText("");
            jtxt_total.setText("");
            set_pembeliantotal.setText("-");
            jcb_pilsupp.setSelectedItem("Silahkan Pilih");
    }
            
              


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxt_nofktur = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jcb_pilsupp = new javax.swing.JComboBox();
        jtxt_idsupp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdt_tglbrgmsk = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jlbl_setidusernya = new javax.swing.JLabel();
        jlbl_nmuser = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jtxt_idbrg = new javax.swing.JTextField();
        jbttn_cari = new javax.swing.JButton();
        jlbl_idbrg = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtxt_hrgbelibrg = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jtxt_jmlh = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxt_total = new javax.swing.JTextField();
        jbttn_tmbh = new javax.swing.JButton();
        jbttn_kurangi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_brgmsk = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        set_pembeliantotal = new javax.swing.JLabel();
        jbttn_clear = new javax.swing.JButton();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jbttn_simpan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Form Transaksi Beli Barang");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/back.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(177, 177, 177)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("No Faktur");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 103, 69, 27));

        jtxt_nofktur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_nofkturMouseReleased(evt);
            }
        });
        jtxt_nofktur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_nofkturKeyReleased(evt);
            }
        });
        jPanel2.add(jtxt_nofktur, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 106, 120, -1));

        jLabel3.setText("Nama Supplier");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 136, -1, 26));

        jcb_pilsupp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pilih" }));
        jcb_pilsupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcb_pilsuppActionPerformed(evt);
            }
        });
        jPanel2.add(jcb_pilsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 139, 120, -1));

        jtxt_idsupp.setEnabled(false);
        jPanel2.add(jtxt_idsupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 50, -1));

        jLabel4.setText("Tanggal Barang Masuk");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 186, 135, -1));
        jPanel2.add(jdt_tglbrgmsk, new org.netbeans.lib.awtextra.AbsoluteConstraints(149, 180, 120, -1));

        jLabel5.setText("ID USER");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 29, 97, -1));

        jlbl_setidusernya.setText("-");
        jPanel2.add(jlbl_setidusernya, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 29, 107, -1));

        jlbl_nmuser.setText("-");
        jPanel2.add(jlbl_nmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 61, 107, -1));

        jLabel6.setText("User Saat Ini");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 61, 97, -1));
        jPanel2.add(jtxt_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 110, 98, -1));

        jbttn_cari.setText("Cari Barang");
        jbttn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_cariActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, -1, -1));

        jlbl_idbrg.setText("-");
        jPanel2.add(jlbl_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 80, -1));

        jLabel8.setText("Nama Barang");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 80, -1));

        jLabel9.setText("Harga Beli");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 70, -1));

        jtxt_hrgbelibrg.setEnabled(false);
        jtxt_hrgbelibrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgbelibrgActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgbelibrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 190, 80, -1));

        jLabel10.setText("Jumlah");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 150, 60, -1));

        jtxt_jmlh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_jmlhActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_jmlh, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 150, 90, -1));

        jLabel11.setText("Total");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 190, 40, 20));

        jtxt_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtxt_totalMouseClicked(evt);
            }
        });
        jtxt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_totalActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 190, 90, -1));

        jbttn_tmbh.setText("+");
        jbttn_tmbh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_tmbhActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_tmbh, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, 50, -1));

        jbttn_kurangi.setText("-");
        jbttn_kurangi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_kurangiActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_kurangi, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 200, 50, -1));

        tabel_brgmsk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Beli", "Jumlah", "Total"
            }
        ));
        tabel_brgmsk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_brgmskMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_brgmsk);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 880, 150));

        jLabel12.setText("Total Harga");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 400, 70, -1));

        set_pembeliantotal.setText("-");
        jPanel2.add(set_pembeliantotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 400, 100, -1));

        jbttn_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/delete.png"))); // NOI18N
        jbttn_clear.setText("Hapus");
        jbttn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_clearActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 390, -1, 40));
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 40, -1));

        jbttn_simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/save.png"))); // NOI18N
        jbttn_simpan.setText("save");
        jbttn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbttn_simpanActionPerformed(evt);
            }
        });
        jPanel2.add(jbttn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 914, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jcb_pilsuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcb_pilsuppActionPerformed
        String nmsupp = jcb_pilsupp.getSelectedItem().toString();
            if(!nmsupp.equals("")){
                try {
                    Connection con = db_koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "SELECT id_supplier FROM supplier WHERE nama_supplier='"+nmsupp+"'";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                    if(hasil.next()){
                        jtxt_idsupp.setText(hasil.getString("id_supplier"));
                    }
                }catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                }
            }else{
                jtxt_idsupp.setText("");
            }
            if(nmsupp=="Silahkan Pilih"){
                jtxt_idsupp.setText("");
            }   
    }//GEN-LAST:event_jcb_pilsuppActionPerformed

    private void jbttn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_cariActionPerformed
        caribarang();
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
    }//GEN-LAST:event_jbttn_cariActionPerformed

    private void jtxt_hrgbelibrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgbelibrgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgbelibrgActionPerformed

    private void jtxt_jmlhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_jmlhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_jmlhActionPerformed

    private void jtxt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_totalActionPerformed

    private void jbttn_tmbhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_tmbhActionPerformed
        tambahbarang();
        tombolkurang(true);
        hitungtotalpembeliansemua();        
    }//GEN-LAST:event_jbttn_tmbhActionPerformed

    private void jbttn_kurangiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_kurangiActionPerformed
        kurangibarang();        // TODO add your handling code here:
    }//GEN-LAST:event_jbttn_kurangiActionPerformed

    private void jbttn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_simpanActionPerformed
        simpanbarangmasuk();
        jtxt_baristrpilih.setText("");
        jtxt_hrgbelibrg.setText("");
        jtxt_idbrg.setText("");
        jtxt_idsupp.setText("");
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
        jtxt_nofktur.setText("");
        jdt_tglbrgmsk.setCalendar(null);
        jcb_pilsupp.setSelectedItem("Silahkan pilih");
    }//GEN-LAST:event_jbttn_simpanActionPerformed

    private void jbttn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbttn_clearActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus semua baris, "  
                + "dan semua inputan ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
        kosongkantabel();
        jtxt_baristrpilih.setText("");
        jtxt_hrgbelibrg.setText("");
        jtxt_idbrg.setText("");
        jtxt_idsupp.setText("");
        jcb_pilsupp.setSelectedItem("Silahkan pilih");
        jtxt_jmlh.setText("");
        jtxt_total.setText("");
        jtxt_nofktur.setText("");
        jdt_tglbrgmsk.setCalendar(null);
        jdt_tglbrgmsk.cleanup();
        }
    }//GEN-LAST:event_jbttn_clearActionPerformed

    private void tabel_brgmskMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_brgmskMouseClicked
        DefaultTableModel dt_tbtransmsk= (DefaultTableModel)tabel_brgmsk.getModel();
        int selecttedrow = tabel_brgmsk.getSelectedRow();
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        jtxt_idbrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 0).toString());
        jlbl_idbrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 1).toString());
        jtxt_hrgbelibrg.setText(dt_tbtransmsk.getValueAt(selecttedrow, 2).toString());
        jtxt_jmlh.setText(dt_tbtransmsk.getValueAt(selecttedrow, 3).toString());
        jtxt_total.setText(dt_tbtransmsk.getValueAt(selecttedrow, 4).toString());
    }//GEN-LAST:event_tabel_brgmskMouseClicked

    private void jtxt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_totalMouseClicked
        hitunghargajumlah();
        tombolkurang(true);
    }//GEN-LAST:event_jtxt_totalMouseClicked

    private void jtxt_nofkturKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_nofkturKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_nofkturKeyReleased

    private void jtxt_nofkturMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_nofkturMouseReleased
        String faktursama = jtxt_nofktur.getText();
            try {
                Connection con = db_koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql = "SELECT no_fak FROM transaksi_barang_masuk WHERE no_fak = '"+faktursama+"'";
                ResultSet hasil = sttment.executeQuery(exec_sql);
                if(hasil.next())
                {
                    JOptionPane.showMessageDialog(null, "No faktur sudah ada di database");
                }
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
    }//GEN-LAST:event_jtxt_nofkturMouseReleased

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        new menu_transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_beli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbttn_cari;
    private javax.swing.JButton jbttn_clear;
    private javax.swing.JButton jbttn_kurangi;
    private javax.swing.JButton jbttn_simpan;
    private javax.swing.JButton jbttn_tmbh;
    private javax.swing.JComboBox jcb_pilsupp;
    private com.toedter.calendar.JDateChooser jdt_tglbrgmsk;
    private javax.swing.JLabel jlbl_idbrg;
    private javax.swing.JLabel jlbl_nmuser;
    private javax.swing.JLabel jlbl_setidusernya;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_hrgbelibrg;
    private javax.swing.JTextField jtxt_idbrg;
    private javax.swing.JTextField jtxt_idsupp;
    private javax.swing.JTextField jtxt_jmlh;
    private javax.swing.JTextField jtxt_nofktur;
    private javax.swing.JTextField jtxt_total;
    private javax.swing.JLabel set_pembeliantotal;
    private javax.swing.JTable tabel_brgmsk;
    // End of variables declaration//GEN-END:variables
}
