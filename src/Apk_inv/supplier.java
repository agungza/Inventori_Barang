/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;

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
public class supplier extends javax.swing.JFrame {
    Connection conn = new db_koneksi().membukakoneksi();
    Statement stat;
    ResultSet res;
    DefaultTableModel tb;
    /**
     * Creates new form supplier
     */
    public supplier() {
        initComponents();
        showTable();
        kd_otomatis();
        this.setLocationRelativeTo(null);
    }
    
       private void kd_otomatis() {
        try {
                String sql = "SELECT MAX(right(id_supplier,2)) FROM supplier";

            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {
                int a = res.getInt(1);
                txt_idsupplier.setText("SUP00" + Integer.toString(a + 1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }      
     public void cariData(){
        String cari = txt_searchsupplier.getText();
        try {
            stat = conn.createStatement();
            String sql = "SELECT * FROM `supplier` WHERE `nama_supplier` = '"+cari+"';";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {                
                tb.addRow(new Object[]{
                res.getString("id_supplier"),
                res.getString("nama_supplier"),
                res.getString("alamat"),
                res.getString("no_hp")});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan" +e.getMessage());
        }
     }
    
        public void showTable(){
        try {
            stat = conn.createStatement();
            tb = new DefaultTableModel();
            tb.addColumn("Id Supplier");
            tb.addColumn("Nama Supplier");
            tb.addColumn("Alamat");
            tb.addColumn("No Hp");
            tabel_supplier.setModel(tb);
            
            String sql = "select * from supplier";
            stat = conn.createStatement();
            res = stat.executeQuery(sql);
            while (res.next()) {                
                tb.addRow(new Object[]{
                res.getString("id_supplier"),
                res.getString("nama_supplier"),
                res.getString("alamat"),
                res.getString("no_hp")});
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Terjadi kesalahan" +e.getMessage());
        }
    }
        
    public void simpanData() {
        try {
            String sql = "insert into supplier (id_supplier,nama_supplier,alamat,no_hp) values ('"
                    +txt_idsupplier.getText()+"','"
                    +txt_namasupplier.getText()+"','"
                    +txt_alamat.getText()+"','"
                    +txt_nohp.getText()+"')";
            stat = conn.createStatement();
            stat.execute(sql);
            JOptionPane.showMessageDialog(null, "berhasil Tersimpan");
            showTable();
            kd_otomatis();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "terjadi kesalahan" +e.getMessage());
        }
    }    
    
    public void editData() {
        try {
            String sql = "UPDATE supplier SET " +"nama_supplier= '"+ txt_namasupplier.getText()
                                            + "', alamat= '" + txt_alamat.getText()
                                            + "', no_hp= '" + txt_nohp.getText() 
                                            +"' WHERE id_supplier= '" +txt_idsupplier.getText()+ "'" ;
            stat = conn.createStatement();
            stat.execute(sql);
            showTable();
            JOptionPane.showMessageDialog(rootPane, "berhasil Terubah");
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Gagal  " +e.getMessage());
        }        
    }
    
    public void hapusData() {
        try {
            String sql = "DELETE FROM supplier WHERE id_supplier='"+txt_idsupplier.getText()+"'";
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

        id_variabel = new javax.swing.JLabel();
        nama_supplier = new javax.swing.JLabel();
        alamat = new javax.swing.JLabel();
        no_hp = new javax.swing.JLabel();
        txt_idsupplier = new javax.swing.JTextField();
        txt_namasupplier = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_nohp = new javax.swing.JTextField();
        txt_searchsupplier = new javax.swing.JTextField();
        refresh_supplier = new javax.swing.JLabel();
        save_supplier = new javax.swing.JLabel();
        edit_supplier = new javax.swing.JLabel();
        cari_supplier = new javax.swing.JLabel();
        delete_supplier = new javax.swing.JLabel();
        back_supplier = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_supplier = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        id_variabel.setText("Id Supplier");
        getContentPane().add(id_variabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, 70, 30));

        nama_supplier.setText("Nama Suplier");
        getContentPane().add(nama_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 80, 30));

        alamat.setText("Alamat");
        getContentPane().add(alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 310, 70, 30));

        no_hp.setText("No Hp");
        getContentPane().add(no_hp, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 390, 50, 30));

        txt_idsupplier.setEnabled(false);
        txt_idsupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idsupplierActionPerformed(evt);
            }
        });
        getContentPane().add(txt_idsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 170, 30));
        getContentPane().add(txt_namasupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 170, 30));
        getContentPane().add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 170, 30));
        getContentPane().add(txt_nohp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 390, 170, 30));
        getContentPane().add(txt_searchsupplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 120, 260, 30));

        refresh_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/refresh.png"))); // NOI18N
        refresh_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                refresh_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(refresh_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 120, 40, 30));

        save_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/save.png"))); // NOI18N
        save_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                save_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(save_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 510, 40, 40));

        edit_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/edit.png"))); // NOI18N
        edit_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                edit_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(edit_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 510, 40, 40));

        cari_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/search.png"))); // NOI18N
        cari_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cari_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(cari_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 120, 40, -1));

        delete_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/delete.png"))); // NOI18N
        delete_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                delete_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(delete_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 510, 40, 40));

        back_supplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/back.png"))); // NOI18N
        back_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                back_supplierMouseClicked(evt);
            }
        });
        getContentPane().add(back_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 10, 50, 40));

        tabel_supplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_supplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_supplier);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 160, 500, 340));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/supplier.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, 630));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idsupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idsupplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idsupplierActionPerformed

    private void back_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_back_supplierMouseClicked
        int id = session_login.getJenis_akses();
        if (id == 1) {
            new dashboard().setVisible(true);
            this.dispose();
            System.out.println("akses = "+id);
        }else if(id == 0){
            new dashboard2().setVisible(true);
            this.dispose();
            System.out.println("akses = "+id);
        }
    }//GEN-LAST:event_back_supplierMouseClicked

    private void save_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_save_supplierMouseClicked
        simpanData();
        kd_otomatis();
//        txt_idsupplier.setText(" ");
        txt_namasupplier.setText(" ");
        txt_alamat.setText(" ");
        txt_nohp.setText(" ");
    }//GEN-LAST:event_save_supplierMouseClicked

    private void edit_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_edit_supplierMouseClicked
        editData();
    }//GEN-LAST:event_edit_supplierMouseClicked

    private void delete_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_delete_supplierMouseClicked
        hapusData();
        kd_otomatis();
//        txt_idsupplier.setText(" ");
        txt_namasupplier.setText(" ");
        txt_alamat.setText(" ");
        txt_nohp.setText(" ");

    }//GEN-LAST:event_delete_supplierMouseClicked

    private void refresh_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_refresh_supplierMouseClicked
        showTable();
        txt_namasupplier.setText(" ");
        txt_alamat.setText(" ");
        txt_nohp.setText(" ");
        txt_searchsupplier.setText(" ");
    }//GEN-LAST:event_refresh_supplierMouseClicked

    private void tabel_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_supplierMouseClicked
        int selectedRow=tabel_supplier.getSelectedRow();
        DefaultTableModel model=(DefaultTableModel)tabel_supplier.getModel();
        txt_idsupplier.setText(model.getValueAt(selectedRow, 0).toString());
        txt_namasupplier.setText(model.getValueAt(selectedRow, 1).toString());
        txt_alamat.setText(model.getValueAt(selectedRow, 2).toString());
        txt_nohp.setText(model.getValueAt(selectedRow, 3).toString());
    }//GEN-LAST:event_tabel_supplierMouseClicked

    private void cari_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cari_supplierMouseClicked
        tb.setRowCount(0);
        cariData();
    }//GEN-LAST:event_cari_supplierMouseClicked

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
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(supplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new supplier().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alamat;
    private javax.swing.JLabel back_supplier;
    private javax.swing.JLabel cari_supplier;
    private javax.swing.JLabel delete_supplier;
    private javax.swing.JLabel edit_supplier;
    private javax.swing.JLabel id_variabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nama_supplier;
    private javax.swing.JLabel no_hp;
    private javax.swing.JLabel refresh_supplier;
    private javax.swing.JLabel save_supplier;
    private javax.swing.JTable tabel_supplier;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_idsupplier;
    private javax.swing.JTextField txt_namasupplier;
    private javax.swing.JTextField txt_nohp;
    private javax.swing.JTextField txt_searchsupplier;
    // End of variables declaration//GEN-END:variables
}
