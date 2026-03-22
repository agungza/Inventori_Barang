/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import session.session_login;
/**
 *
 * @author Agung
 */
public class login extends javax.swing.JFrame {
//    Connection conn = new db_koneksi().membukakoneksi();
//    Statement stat;
//    ResultSet res;
    
//    DefaultTableModel tb;
    /**
     * Creates new form login
     */
    Apk_inv.koneksi koneksinya = new Apk_inv.koneksi();
    Apk_inv.SesiPemilik PemilikSession = new Apk_inv.SesiPemilik();
    Apk_inv.SesiAdmin AdminSession = new Apk_inv.SesiAdmin();
    public login() {
        initComponents();
        
    }
        public void loginmen() {
        String username = txt_username.getText();
        String pilihakses =  jcb_pil_akses.getSelectedItem().toString();
        String pass = jPasswordField1.getText();
        try {
//            String sqlsearch = "select * from user where username='" +txt_username.getText()+ "'";
//            stat = conn.createStatement();
//            res = stat.executeQuery(sqlsearch);
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT * FROM user WHERE username= '"
            + username + "' AND password = '" + pass + "' and jenis_akses = '" 
            +  pilihakses + "'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            int id= 0;
            if (hasil.next()) {
                if (jPasswordField1.getText().equals(hasil.getString("password"))) {
                    String akses = hasil.getString("jenis_akses");
                    System.out.println("akses = "+akses);
                    if("admin".equals(akses)){
                        id = 1;
                    }
                    if ("karyawan".equals(akses)) {
                        id = 0;  
                    }   
//                    String id_user = res.getString("id_user");
//                    String username=res.getString("username");
                    if (akses.equals("admin")) {
                       session_login.setJenis_akses(id);
                        System.out.println("Akses login = "+id);
                        JOptionPane.showMessageDialog(null, "Login Sebagai Admin");
                        AdminSession.SetU_id_user(hasil.getString("id_user"));
                        AdminSession.SetU_nama_user(hasil.getString("username"));
                        AdminSession.SetU_jenis_akses(hasil.getString("jenis_akses"));
                        dashboard dash = new dashboard();
                        dash.setVisible(true);
//                        dash.lbl_stst.setText(username);
//                        dash.lbl_id.setText(id_user);
                        this.hide();
                    } else if (akses.equals("karyawan")){
                        session_login.setJenis_akses(id);
                        System.out.println("Akses login = "+id);
                        JOptionPane.showMessageDialog(null, "Login Sebagai Karyawan");
                        PemilikSession.SetU_id_user(hasil.getString("id_user"));
                        PemilikSession.SetU_nama_user(hasil.getString("username"));
                        PemilikSession.SetU_jenis_akses(hasil.getString("jenis_akses"));
                        dashboard2 dash2 = new dashboard2();
                        dash2.setVisible(true);
//                        dash2.lbl_bangke.setText(username);
//                        dash2.bbs.setText(id_user);
//                        sresv.txt_idkar.setText(id_kar);
                        this.hide();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "username atau password tidak sesuai !");
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Ada yang salah ! ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }     
//        try {
//            Connection con = db_koneksi.membukakoneksi();
//            Statement sttment = con.createStatement();
//            String exec_sql = "SELECT * FROM user WHERE username= '"
//            + username + "' AND password = '" + pass + "' and jenis_akses = '" 
//             + "'";
//            ResultSet hasil = sttment.executeQuery(exec_sql);
//            if(hasil.next())
//            {
//                if (jcb_pil_akses.getSelectedIndex()==1){
//                    PemilikSession.SetU_id_user(hasil.getString("id_user"));
//                    PemilikSession.SetU_nama_user(hasil.getString("username"));
//                    PemilikSession.SetU_jenis_akses(hasil.getString("jenis_akses"));
//                    new dashboard().setVisible(true);
//                    this.dispose();
//                } else  {
//                    AdminSession.SetU_id_user(hasil.getString("id_user"));
//                    AdminSession.SetU_nama_user(hasil.getString("username"));
//                    AdminSession.SetU_jenis_akses(hasil.getString("jenis_akses"));
//                    new dashboard2().setVisible(true);
//                    this.dispose();
//                } 
//            }else{
//                    JOptionPane.showMessageDialog(null, " Username atau password salah.");
//                    txt_username.setText(" ");
//                    jPasswordField1.setText("");
//                }    
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Username atau password belum terdaftar." );
//            txt_username.setText(" ");
//            jPasswordField1.setText("");
//        } 
            
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
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jcb_pil_akses = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        signin = new javax.swing.JButton();
        registrasi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("LOGIN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 20, 60, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/close.png"))); // NOI18N
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 30, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 60));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jcb_pil_akses.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Silahkan Pilih", "Admin", "Karyawan" }));
        jPanel2.add(jcb_pil_akses, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 130, 130, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("USERNAME");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 100, 30));
        jPanel2.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 40, 140, -1));
        jPanel2.add(jPasswordField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 90, 140, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("PASSWORD");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 100, 30));

        signin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/loginkcl.png"))); // NOI18N
        signin.setText("LOGIN");
        signin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                signinActionPerformed(evt);
            }
        });
        jPanel2.add(signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 100, 40));

        registrasi.setText("Registrasi");
        registrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrasiActionPerformed(evt);
            }
        });
        jPanel2.add(registrasi, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 100, 40));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 400, 270));

        setSize(new java.awt.Dimension(397, 329));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered

    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited

    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed

    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged

    }//GEN-LAST:event_jLabel2MouseDragged

    private void signinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_signinActionPerformed
        loginmen();
    }//GEN-LAST:event_signinActionPerformed

    private void registrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrasiActionPerformed
        new karyawan().setVisible(true);
        dispose();
    }//GEN-LAST:event_registrasiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JComboBox jcb_pil_akses;
    private javax.swing.JButton registrasi;
    private javax.swing.JButton signin;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
