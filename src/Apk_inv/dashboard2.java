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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import java.sql.*;

/**
 *
 * @author Agung
 */
public class dashboard2 extends javax.swing.JFrame {

    /**
     * Creates new form dashboard2
     */
    Apk_inv.koneksi koneksinya = new Apk_inv.koneksi();
    Apk_inv.SesiPemilik PemilikSession = new Apk_inv.SesiPemilik();
    Apk_inv.SesiAdmin AdminSession = new Apk_inv.SesiAdmin();
    public dashboard2() {
        initComponents();
        this.setLocationRelativeTo(null);
        set_nama();
        tampilbrgkeluar();
        tampilbrgmsk();
        tampilbrgretur();
    }
    
    public void set_nama(){
        lbl_bangke.setText(PemilikSession.GetU_nama_user());
        bbs.setText(PemilikSession.GetU_jenis_akses());
    }
    
    public void tampilbrgkeluar(){
        String nonota, idbrg, nmbrg, nmrtostr, katbrg, hrgbl, hrgjl, spek, stok, nourut, tgl_keluar;
        int nmr=0;
        try {
            Object [] baris = {"No","Nomor Nota", "Tanggal Keluar","No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual", "Jumlah Keluar"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brgkeluar.setModel(modeltbbrg);
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT detail_transaksi_barangklr.no_urut_keluar, detail_transaksi_barangklr.no_nota"
                    + ", barang.id_barang as idbarang, barang.nama_barang as namabarang"
                    + ", barang.harga_jual as hargajual, transaksi_barang_keluar.tgl_keluar as tglkeluar"
                    + ", barang.stok as stock, detail_transaksi_barangklr.jumlah_brgkeluar"
                    + " FROM detail_transaksi_barangklr join barang ON "
                    + "barang.id_barang = detail_transaksi_barangklr.id_barang join transaksi_barang_keluar ON"
                    + " transaksi_barang_keluar.no_nota = detail_transaksi_barangklr.no_nota "
                    + "ORDER by detail_transaksi_barangklr.no_urut_keluar desc limit 5";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brgkeluar.getColumnModel().getColumn(0).setPreferredWidth(5);
                nonota=hasil.getString("no_nota");
                tgl_keluar=hasil.getString("tglkeluar");
                nourut=hasil.getString("no_urut_keluar");
                idbrg=hasil.getString("idbarang");
                nmbrg=hasil.getString("namabarang");
                hrgjl=hasil.getString("hargajual");
                stok=hasil.getString("jumlah_brgkeluar");
                String data[]={nmrtostr,nonota,tgl_keluar,nourut,idbrg,nmbrg,hrgjl,stok};   
                modeltbbrg.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }
    
    public void tampilbrgretur(){
        String nonota, id_brg, nm_brg, kd_retur, hrg_jual, keterangan, nmrtostr, tgl_retur,jmlh;
        int nmr =0;
        try {
            Object [] baris = {"No","Kode Return", "No Nota","Id Barang","Nama Barang",
                "Harga Jual","Jumlah Kembali","Tanggal Retur", "Keterangan"};
            DefaultTableModel modeltbbrgreturjual;
            modeltbbrgreturjual = new DefaultTableModel(null, baris);
            tabel_returjual.setModel(modeltbbrgreturjual);
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT detail_transaksi_return.no_urut_kembali,detail_transaksi_return.kd_return"
                    + ", transaksi_return.no_nota as nomernota, barang.id_barang as idbarang"
                    + ", barang.nama_barang as namabarang, detail_transaksi_return.harga_jual"
                    + ", detail_transaksi_return.jumlah_brgkembali, transaksi_return.tgl_return as tglkembali"
                    + ", transaksi_return.keterangan as ket"
                    + " FROM detail_transaksi_return JOIN barang ON "
                    + "barang.id_barang = detail_transaksi_return.id_barang JOIN transaksi_return ON"
                    + " transaksi_return.kd_return = detail_transaksi_return.kd_return"
                    + " ORDER by detail_transaksi_return.no_urut_kembali desc limit 5";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_returjual.getColumnModel().getColumn(0).setPreferredWidth(5);
                kd_retur=hasil.getString("kd_return");
                nonota=hasil.getString("nomernota");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_jual=hasil.getString("harga_jual");
                jmlh=hasil.getString("jumlah_brgkembali");
                tgl_retur=hasil.getString("tglkembali");
                keterangan=hasil.getString("ket");
                String data[]={nmrtostr,kd_retur,nonota,id_brg,nm_brg,hrg_jual,jmlh
                ,tgl_retur,keterangan};   
                modeltbbrgreturjual.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }
    
    public void tampilbrgmsk(){
        String nofaktur, id_brg, nm_brg, no_urut, hrg_beli, stok, nmrtostr, tgl_msk;
        int nmr =0;
        try {
            Object [] baris = {"No","Nomer Faktur", "Tanggal Masuk","No Urut Masuk","Id Barang",
                "Nama Barang","Harga Beli", "Jumlah Masuk"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_brgmasuk.setModel(modeltbbrg);
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT detail_transaksi_barangmsk.no_urut, detail_transaksi_barangmsk.no_fak"
                    + ", barang.id_barang as idbarang, barang.nama_barang as namabarang"
                    + ", barang.harga_beli as hargabeli, transaksi_barang_masuk.tgl_brgmsk as tglmasuk"
                    + ", barang.stok as stock, detail_transaksi_barangmsk.jumlah_brgmsk"
                    + " FROM detail_transaksi_barangmsk join barang ON "
                    + "barang.id_barang = detail_transaksi_barangmsk.id_barang join transaksi_barang_masuk ON"
                    + " transaksi_barang_masuk.no_fak = detail_transaksi_barangmsk.no_fak "
                    + "ORDER by detail_transaksi_barangmsk.no_urut desc limit 5";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next())
            {
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_brgmasuk.getColumnModel().getColumn(0).setPreferredWidth(5);
                nofaktur=hasil.getString("no_fak");
                tgl_msk=hasil.getString("tglmasuk");
                no_urut=hasil.getString("no_urut");
                id_brg=hasil.getString("idbarang");
                nm_brg=hasil.getString("namabarang");
                hrg_beli=hasil.getString("hargabeli");
                stok=hasil.getString("jumlah_brgmsk");
                String data[]={nmrtostr,nofaktur,tgl_msk,no_urut,id_brg,nm_brg,hrg_beli,stok};   
                modeltbbrg.addRow(data);
            }  
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
     }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lbl_bangke = new javax.swing.JLabel();
        bbs = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_transaksi = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_laporan = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_brg = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabel_brgkeluar = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabel_returjual = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabel_brgmasuk = new javax.swing.JTable();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/man.png"))); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 130));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel2.setText("SISTEM PERSEDIAAN BARANG");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 560, 50));

        lbl_bangke.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbl_bangke.setText("-");
        jPanel1.add(lbl_bangke, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 70, 20));

        bbs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bbs.setText("-");
        jPanel1.add(bbs, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 70, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 150));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 20));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Transaksi");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 80, 50));

        txt_transaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/wallet.png"))); // NOI18N
        txt_transaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_transaksiMouseClicked(evt);
            }
        });
        jPanel2.add(txt_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 70, 90));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Cetak Laporan");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 500, 110, 40));

        txt_laporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/cetak.png"))); // NOI18N
        txt_laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_laporanMouseClicked(evt);
            }
        });
        jPanel2.add(txt_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 420, 70, 70));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Barang");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 70, 40));

        txt_brg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/inventory.png"))); // NOI18N
        txt_brg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_brgMouseClicked(evt);
            }
        });
        jPanel2.add(txt_brg, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 70, 90));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 230, 610));

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 20));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/exit.png"))); // NOI18N
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 510, 120, 70));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Transaksi Terakhir Barang Return");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 350, 260, 20));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setText("Transaksi Terakhir Barang Masuk");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 260, 20));

        tabel_brgkeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomor Nota", "Tanggal Keluar", "No Urut Keluar", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah Keluar"
            }
        ));
        jScrollPane2.setViewportView(tabel_brgkeluar);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 810, 120));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Transaksi Terakhir Barang Keluar");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 260, 20));

        tabel_returjual.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Kode Return", "No Nota", "Id Barang", "Nama Barang", "Harga Jual", "Jumlah Keluar", "Tanggal Return", "Keterangan"
            }
        ));
        jScrollPane3.setViewportView(tabel_returjual);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 810, 120));

        tabel_brgmasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomer Faktur", "Tanggal Masuk", "No Urut Masuk", "Id Barang", "Nama Barang", "Harga Beli", "Jumlah Masuk"
            }
        ));
        jScrollPane4.setViewportView(tabel_brgmasuk);

        jPanel3.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 810, 120));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 910, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        new login().setVisible(true);
        dispose(); 
    }//GEN-LAST:event_jLabel6MouseClicked

    private void txt_transaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_transaksiMouseClicked
        new menu_transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_txt_transaksiMouseClicked

    private void txt_brgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_brgMouseClicked
        new barang_karyawan().setVisible(true);
        dispose();
    }//GEN-LAST:event_txt_brgMouseClicked

    private void txt_laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_laporanMouseClicked
        new Laporan_1(this, rootPaneCheckingEnabled).show();        
        dispose();
    }//GEN-LAST:event_txt_laporanMouseClicked

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
            java.util.logging.Logger.getLogger(dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboard2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboard2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel bbs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    public javax.swing.JLabel lbl_bangke;
    private javax.swing.JTable tabel_brgkeluar;
    private javax.swing.JTable tabel_brgmasuk;
    private javax.swing.JTable tabel_returjual;
    private javax.swing.JLabel txt_brg;
    private javax.swing.JLabel txt_laporan;
    private javax.swing.JLabel txt_transaksi;
    // End of variables declaration//GEN-END:variables
}
