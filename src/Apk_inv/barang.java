/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import session.session_login;
import java.sql.SQLException;
import java.sql.*;

/**
 *
 * @author Agung
 */
public class barang extends javax.swing.JFrame {
    Connection conn = new db_koneksi().membukakoneksi();
    Statement stat;
    ResultSet res;
    DefaultTableModel tb;
    /**
     * Creates new form barang
     */
    public barang() {
        initComponents();
        kd_otomatis();
        showTable();
//        txt_idbarang.setEditable(false);
        this.setLocationRelativeTo(null);
        
    }
    
//    private void kd_otomatis(){
//        try {
//           // String sql = "SELECT MAX (RIGHT(id_barang,1)) AS NO FROM tb_barang DESC";
//            String sql = "select * from tb_barang order by id_barang desc";
//            stat = conn.createStatement();
//            res = stat.executeQuery(sql);
////            while (res.next()) {                
////               // int a = res.getInt(1);
////                txt_idbarang.setText("BRG-0" + 1);
////            }
//             
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(rootPane, e);
//        }
//    }
       private void kd_otomatis() {
        try {
            String sql = "SELECT MAX(right(id_barang,2)) FROM barang";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {
                int a = res.getInt(1);
                txt_idbarang.setText("BRG00" + Integer.toString(a + 1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    public void cariData(){
        String cari = txt_searchbarang.getText();
        try {
            stat = conn.createStatement();
            String sql = "SELECT * FROM `barang` WHERE `id_barang` = '"+cari+"';";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {                
                tb.addRow(new Object[]{
                res.getString("id_barang"),
                res.getString("nama_barang"),
                res.getString("harga_beli"),
                res.getString("harga_jual"),
                res.getString("stok")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan" +e.getMessage());
        }
     }
    
    public void showTable(){
        try {
            stat = conn.createStatement();
            tb = new DefaultTableModel();
            tb.addColumn("Id Barang");
            tb.addColumn("Nama Barang");
            tb.addColumn("Harga Beli");
            tb.addColumn("Harga Jual");
            tb.addColumn("Stok");
            tabel_barang.setModel(tb);
            
            String sql = "select * from barang";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {                
                tb.addRow(new Object[]{
                res.getString("id_barang"),
                res.getString("nama_barang"),
                res.getString("harga_beli"),
                res.getString("harga_jual"),
                res.getString("stok")});
            }
          
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan" +e.getMessage());
        }
    }
        
    public void simpanData() {
        try {
            String sql = "insert into barang (id_barang,nama_barang,harga_beli,harga_jual,stok) values ('"
                    +txt_idbarang.getText()+"','"
                    +txt_namabarang.getText()+"','"
                    +txt_hargabeli.getText()+"','"
                    +txt_hargajual.getText()+"','"
                    +txt_stok.getText()+"')";
            stat = conn.createStatement();
            stat.execute(sql);
            JOptionPane.showMessageDialog(null, "berhasil Tersimpan");
            
            showTable();
            kd_otomatis();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "data sudah tersedia");
        }
    }    
    
    public void editData() {
        try {
            String sql = "UPDATE barang SET " +"nama_barang= '"+ txt_namabarang.getText()
                                            + "', harga_beli= '" + txt_hargabeli.getText()
                                            + "', harga_jual= '" + txt_hargajual.getText() 
                                            + "', stok= '" + txt_stok.getText()+"' WHERE id_barang= '" +txt_idbarang.getText()+ "'" ;
            stat = conn.createStatement();
            stat.execute(sql);
           
            showTable();
             kd_otomatis();
            JOptionPane.showMessageDialog(rootPane, "berhasil Terubah");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "gagal simpan " +e.getMessage());
        }        
    }
    
    public void hapusData() {
        try {
            String sql = "DELETE FROM barang WHERE id_barang='"+txt_idbarang.getText()+"'";
            stat = conn.createStatement();
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(rootPane, "berhasil dihapus");
            showTable();
            kd_otomatis();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "terjadi kesalahan " + e.getMessage());
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

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_namabarang = new javax.swing.JTextField();
        txt_hargabeli = new javax.swing.JTextField();
        txt_hargajual = new javax.swing.JTextField();
        txt_stok = new javax.swing.JTextField();
        txt_idbarang = new javax.swing.JTextField();
        txt_searchbarang = new javax.swing.JTextField();
        search_barang = new javax.swing.JLabel();
        refresh_barang = new javax.swing.JLabel();
        save_barang = new javax.swing.JLabel();
        edit_barang = new javax.swing.JLabel();
        delete_barang = new javax.swing.JLabel();
        back_barang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_barang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Nama Barang");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 230, 80, 40));

        jLabel3.setText("Harga Beli");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 60, 30));

        jLabel4.setText("Harga Jual");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 400, 80, 30));

        jLabel5.setText("Stok");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, 60, 30));

        jLabel7.setText("Id Barang");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 60, 30));

        txt_namabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namabarangActionPerformed(evt);
            }
        });
        getContentPane().add(txt_namabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 240, 170, 30));

        txt_hargabeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargabeliActionPerformed(evt);
            }
        });
        getContentPane().add(txt_hargabeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 320, 170, 30));

        txt_hargajual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargajualActionPerformed(evt);
            }
        });
        getContentPane().add(txt_hargajual, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 400, 170, 30));

        txt_stok.setText("0");
        txt_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokActionPerformed(evt);
            }
        });
        getContentPane().add(txt_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 480, 170, 30));

        txt_idbarang.setEnabled(false);
        txt_idbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idbarangActionPerformed(evt);
            }
        });
        getContentPane().add(txt_idbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 170, 30));
        getContentPane().add(txt_searchbarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 260, 30));

        search_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/search.png"))); // NOI18N
        search_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search_barangMouseClicked(evt);
            }
        });
        getContentPane().add(search_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 90, 30, 40));

        refresh_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/refresh.png"))); // NOI18N
        refresh_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_barangMouseClicked(evt);
            }
        });
        getContentPane().add(refresh_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, 40));

        save_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/save.png"))); // NOI18N
        save_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save_barangMouseClicked(evt);
            }
        });
        getContentPane().add(save_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 500, 50, 40));

        edit_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/edit.png"))); // NOI18N
        edit_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_barangMouseClicked(evt);
            }
        });
        getContentPane().add(edit_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 500, 50, 40));

        delete_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/delete.png"))); // NOI18N
        delete_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_barangMouseClicked(evt);
            }
        });
        getContentPane().add(delete_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 500, 40, 40));

        back_barang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/back.png"))); // NOI18N
        back_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                back_barangMouseClicked(evt);
            }
        });
        getContentPane().add(back_barang, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 10, 50, 40));

        tabel_barang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_barang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_barangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_barang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 460, 360));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/barangg.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 620));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namabarangActionPerformed

    private void txt_hargabeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargabeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargabeliActionPerformed

    private void txt_hargajualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargajualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargajualActionPerformed

    private void txt_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokActionPerformed

    private void txt_idbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idbarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idbarangActionPerformed

    private void back_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_back_barangMouseClicked
//        try {
//            String sqlsearch = "select * from tb_karyawan where username'";
//            stat = conn.createStatement();
//            res = stat.executeQuery(sqlsearch);
//            
//
//                    String akses = res.getString("jenis_akses");
//                    
////                    String id_kar = res.getString("id_user");
//                    if (akses.equals("admin")) {
////                      JOptionPane.showMessageDialog(null, "Login Sebagai Admin");
//                        dashboard dash = new dashboard();
//                        dash.setVisible(true);
////                        dash.lbl_stst.setText(akses);
////                        dash.lbl_id.setText(id_kar);
//                        this.hide();
//                    } else if (akses.equals("karyawan")){
////                      JOptionPane.showMessageDialog(null, "Login Sebagai Karyawan");
//                        dashboard2 dash2 = new dashboard2();
//                        dash2.setVisible(true);
////                        dashboard2.lbl_status.setText(akses);
////                        dashboard2.label_apaan.setText(id_kar);
////                        sresv.txt_idkar.setText(id_kar);
//                        this.hide();
//                    }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
//        } 
//        new dashboard().setVisible(true);
//        dispose();
        try {
//     String id_user = res.getString("id_user");
//     String username=res.getString("username");       
     int id = session_login.getJenis_akses();
        if (id == 1) {
            dashboard dash = new dashboard();
            dash.setVisible(true);
//            dash.lbl_stst.setText(username);
//            dash.lbl_id.setText(id_user);
            this.dispose();             
            //System.out.println("akses = "+id);
        }else if(id == 0){
//            new dashboard2().setVisible(true);
            dashboard2 dash2 = new dashboard2();
            dash2.setVisible(true);
//            dash2.lbl_bangke.setText(username);
//            dash2.bgst.setText(id_user);
            this.dispose();
            //System.out.println("akses = "+id);
        }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_back_barangMouseClicked

    private void save_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save_barangMouseClicked
        simpanData();
        kd_otomatis();
        //txt_idbarang.setText(" ");
        txt_namabarang.setText(" ");
        txt_hargabeli.setText(" ");
        txt_hargajual.setText(" ");
        txt_stok.setText(" ");
     
    }//GEN-LAST:event_save_barangMouseClicked

    private void edit_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_barangMouseClicked
        editData();
        kd_otomatis();
        //txt_idbarang.setText(" ");
        txt_namabarang.setText(" ");
        txt_hargabeli.setText(" ");
        txt_hargajual.setText(" ");
        txt_stok.setText(" ");
    }//GEN-LAST:event_edit_barangMouseClicked

    private void delete_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_barangMouseClicked
        hapusData();
        kd_otomatis();
//        txt_idbarang.setText(" ");
        txt_namabarang.setText(" ");
        txt_hargabeli.setText(" ");
        txt_hargajual.setText(" ");
        txt_stok.setText(" ");        
    }//GEN-LAST:event_delete_barangMouseClicked

    private void refresh_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_barangMouseClicked
        showTable();
        txt_namabarang.setText(" ");
        txt_hargabeli.setText(" ");
        txt_hargajual.setText(" ");
        txt_stok.setText(" ");    
    }//GEN-LAST:event_refresh_barangMouseClicked

    private void search_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search_barangMouseClicked
        tb.setRowCount(0);
        cariData();
    }//GEN-LAST:event_search_barangMouseClicked

    private void tabel_barangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_barangMouseClicked
        int selectedRow=tabel_barang.getSelectedRow();
        DefaultTableModel model=(DefaultTableModel)tabel_barang.getModel();
        txt_idbarang.setText(model.getValueAt(selectedRow, 0).toString());
        txt_namabarang.setText(model.getValueAt(selectedRow, 1).toString());
        txt_hargabeli.setText(model.getValueAt(selectedRow, 2).toString());
        txt_hargajual.setText(model.getValueAt(selectedRow, 3).toString());
        txt_stok.setText(model.getValueAt(selectedRow, 4).toString());
       
        
    }//GEN-LAST:event_tabel_barangMouseClicked

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
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel back_barang;
    private javax.swing.JLabel delete_barang;
    private javax.swing.JLabel edit_barang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel refresh_barang;
    private javax.swing.JLabel save_barang;
    private javax.swing.JLabel search_barang;
    private javax.swing.JTable tabel_barang;
    private javax.swing.JTextField txt_hargabeli;
    private javax.swing.JTextField txt_hargajual;
    private javax.swing.JTextField txt_idbarang;
    private javax.swing.JTextField txt_namabarang;
    private javax.swing.JTextField txt_searchbarang;
    private javax.swing.JTextField txt_stok;
    // End of variables declaration//GEN-END:variables
}
