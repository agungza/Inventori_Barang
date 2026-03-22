/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apk_inv;
import java.awt.Color;
//import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import java.sql.*;

/**
 *
 * @author Agung
 */
public class transaksi_jual extends javax.swing.JFrame {

    /**
     * Creates new form transaksi_jual
     */
    
    Apk_inv.koneksi koneksinya = new Apk_inv.koneksi();
    Apk_inv.SesiPemilik PemilikSession = new Apk_inv.SesiPemilik();
    Apk_inv.SesiAdmin AdminSession = new Apk_inv.SesiAdmin();
    
    public transaksi_jual() {
        initComponents();
        this.setLocationRelativeTo(null);
        set_namauser();
        tombolkurang(false);
        hitungtotalpengeluaransemua();
        jtxt_baristrpilih.hide();
        jtxt_hrgbeli.hide();
        jButton1.hide();
    }
    
    public void tambahbarang(){
        int nmr = 1;
        String nomernota = txt_nonota.getText();
        String idbrgnya = txt_idbrg.getText();
        String nmbrgnya = txt_nmbrg.getText();
        int hrgbrgbeli = Integer.valueOf(jtxt_hrgbeli.getText());
        int hrgbrgjual = Integer.valueOf(jtxt_hrgjual.getText());
        int jmlhbrg = Integer.parseInt(txt_jumlah.getText());
        int jmlhstoktersisa = Integer.parseInt(jtxt_setstokbrg.getText());
        int totalbrgnya = Integer.valueOf(jtxt_total.getText());
            if(!"".equals(idbrgnya) && !"".equals(nmbrgnya) && !"".equals(jmlhbrg) && !"".equals(hrgbrgbeli)
                && !"".equals(totalbrgnya) && !"".equals(nomernota) && !"".equals(hrgbrgjual)){
                 if(jmlhbrg <= jmlhstoktersisa)
            {
                //untuk menambahkan barang ke tabel sementara
                Object[] row = { idbrgnya, nmbrgnya, hrgbrgbeli, hrgbrgjual, jmlhbrg, totalbrgnya };
                DefaultTableModel model = (DefaultTableModel) tbltrans.getModel();
                model.addRow(row);
                txt_idbrg.setText("");
                txt_nmbrg.setText("-");
                jtxt_hrgbeli.setText("");
                txt_jumlah.setText("");
                jtxt_total.setText(""); 
                jtxt_hrgjual.setText("");
            }else{
                JOptionPane.showMessageDialog(null, "Jumlah melebihi stok barang / stok barang sudah habis.");
            }
                
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
            
    }
    
    public void caribarang(){
        String idbrgnya = txt_idbrg.getText();
        String nmbrgnya = txt_nmbrg.getText();
        String hrgbrgjual = jtxt_hrgjual.getText();
        String hrgbrgbeli = jtxt_hrgbeli.getText();
        String cari = txt_idbrg.getText();
        int stoktersisa;
        if(!idbrgnya.equals("")){
                //--------- Cek In jtable
            int idygsama = 0;
            DefaultTableModel model = (DefaultTableModel) tbltrans.getModel();
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                if(idbrgnya.equals(tbltrans.getModel().getValueAt(i, 1).toString())){
                    idygsama = 1;
                }
            }
            if(idygsama == 0){
                try {
                   Connection con = db_koneksi.membukakoneksi();
                   Statement sttment = con.createStatement();
                   String exec_sql = "Select nama_barang, harga_jual, harga_beli, stok"
                           + " from barang where id_barang like '" + cari + "' or nama_barang like '%" + cari + "%'";
                  ResultSet hasil = sttment.executeQuery(exec_sql);
                    if(hasil.next()){
                        idbrgnya=hasil.getString("nama_barang");
                        txt_nmbrg.setText(idbrgnya);
                        hrgbrgbeli=hasil.getString("harga_beli");
                        jtxt_hrgbeli.setText(hrgbrgbeli);
                        hrgbrgjual = hasil.getString("harga_jual");
                        jtxt_hrgjual.setText(hrgbrgjual);
                        jtxt_setstokbrg.setText(hasil.getString("stok"));;
                    }else{
                        JOptionPane.showMessageDialog(null, "Barang tidak ditemukan / belum ada di database.");
                        txt_idbrg.setText("");
                        txt_nmbrg.setText("");
                        jtxt_hrgjual.setText("");
                        txt_jumlah.setText("");
                        jtxt_total.setText("");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
            }else{
                JOptionPane.showMessageDialog(null, "Barang sudah pernah ditambahkan.");
                txt_idbrg.setText("");
            }
        }else{
            txt_idbrg.setText("");
            txt_nmbrg.setText("-");
            jtxt_hrgjual.setText("");
            jtxt_total.setText("");
            jtxt_hrgbeli.setText("");
            jtxt_setstokbrg.setText("");
        }
    }
    
    public void hitungjumlah(){
        int jmlhbrg = Integer.parseInt(String.valueOf(txt_jumlah.getText()));
        int hrgbrgjual  = Integer.parseInt(jtxt_hrgjual.getText());
        int total   = 0;
        if(!"".equals(jmlhbrg) && !"".equals(hrgbrgjual) )
        {
            total = (hrgbrgjual * jmlhbrg);
            String hasil = Integer.toString(total);
            jtxt_total.setText(hasil);

        }else{
             JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
    }
    
    public void namaheadertabel(){
        Object [] baris = {"Id Barang","Nama Barang", "Harga Beli","Harga Jual",
                    "Jumlah","Total"};
        DefaultTableModel modeltbbrg;
        modeltbbrg = new DefaultTableModel(null, baris);
        tbltrans.setModel(modeltbbrg);
    }
    
    public void hitungtotalpengeluaransemua(){
         //untuk menghitung hasil seluruh total 
        int rowsCount = tbltrans.getRowCount();
            int totalnya = 0;
            for(int i = 0; i < rowsCount; i++){
                totalnya = totalnya+Integer.parseInt(tbltrans.getValueAt(i, 5).toString());
            }
        jlbl_hasilsemuatotal.setText(Integer.toString(totalnya));
    }
    
    public void kurangibarang(){
         //mengurangi barang dari tabel sementara
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
            if(konfirmasi==0) {
                String baris= Integer.toString(tbltrans.getSelectedRow());
                jtxt_baristrpilih.setText(baris);
                DefaultTableModel model = (DefaultTableModel) tbltrans.getModel();
                int row = Integer.parseInt(jtxt_baristrpilih.getText());
                model.removeRow(row);
        }
    }
    
    public void set_namauser(){
         //untuk set nama sessi yang dipanggil
        jlbl_nmuser.setText(PemilikSession.GetU_nama_user());
        jlbl_idusernya.setText(PemilikSession.GetU_id_user());
    }
    
    public void tombolkurang(boolean a){
         //untuk mengaktifkan fungsi tombol kurang
        btn_kurang.setEnabled(a);
    }
    
    public void kosongkantabel(){
          //untuk menghapus isi semua tabel
        DefaultTableModel model = (DefaultTableModel) tbltrans.getModel();
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    public void simpan(){
        String tglmasuk = jdt_tglbrgkeluar.getDateFormatString();
        String setdatenya = "yyyy-MM-dd";
        SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
        String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglbrgkeluar.getDate()));
        String idusernya = jlbl_idusernya.getText();
//        idusernya = "001";
        String nmusernya = jlbl_nmuser.getText();
        String nomernota = txt_nonota.getText();
        int hasiltotalhrgjual = Integer.parseInt(jlbl_hasilsemuatotal.getText()) ;
        String idbrg, kode, id_brgkeluar;
        Integer stok, tidak_ditemukan,totalhrgajual, kosong = 0;
        String totalharga,jumlah,hrgbli,hrgjual;
        DefaultTableModel model = (DefaultTableModel) tbltrans.getModel();
        int rowCount = model.getRowCount();
        
        if(!"".equals(tglmasuk) && !"".equals(idusernya) && !"".equals(nmusernya) 
         && !"".equals(nomernota)){

            try {
                Connection con = db_koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                
                String exec_sql = "INSERT INTO transaksi_barang_keluar(no_nota, id_user, tgl_keluar, hasil_totaljual)VALUES('"
                + nomernota + "', '" +  idusernya+ "', '"+dateinputnya+"', '" + hasiltotalhrgjual + "')"; 
                sttment.executeUpdate(exec_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            try {
                Connection con = db_koneksi.membukakoneksi();
                Statement sttment = con.createStatement();
                String exec_sql ="SELECT MAX(no_nota) as max FROM transaksi_barang_keluar";
                ResultSet hasil = sttment.executeQuery(exec_sql); 
                while(hasil.next()) 
                {
                   id_brgkeluar = hasil.getString("max");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
            
            for (int i = 0; i < rowCount; i++) {
                tidak_ditemukan = 0;
                stok = 0;
                idbrg = (tbltrans.getModel().getValueAt(i, 0).toString());
                hrgbli = (tbltrans.getModel().getValueAt(i, 2).toString());
                hrgjual = (tbltrans.getModel().getValueAt(i, 3).toString());
                jumlah = (tbltrans.getModel().getValueAt(i, 4).toString());
                totalharga = (tbltrans.getModel().getValueAt(i, 5).toString());
                    //------- Menngurangi stok dengan data jumlah
                try {
                    Connection con = db_koneksi.membukakoneksi();
                    Statement sttment = con.createStatement();
                    String exec_sql = "SELECT stok FROM barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet hasil = sttment.executeQuery(exec_sql);
                   while(hasil.next())
                   {
                       if (hasil.getRow() == 1){
                        stok = hasil.getInt("stok") - Integer.parseInt(jumlah);
                        } else {
                           tidak_ditemukan = 1;
                        }
                   }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
                
                if(tidak_ditemukan == 0){
                        //------- Mengupdate jumlah stok barang
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("UPDATE barang SET stok='" + stok 
                        + "' WHERE id_barang = '" + idbrg + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                    
                        //------- Memasukan pada table transaksi detail
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("INSERT INTO detail_transaksi_barangklr(id_barang, no_nota, "
                          + "harga_beli, harga_jual, jumlah_brgkeluar, total_hargajual) VALUES ('" 
                          + idbrg + "', '" + nomernota+ "', '" + hrgbli + "', '" + hrgjual + "','" 
                          + Integer.parseInt(jumlah) + "', '" + Integer.parseInt(totalharga)+ "')");
                        kosong = 1;
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
//                    JOptionPane.showMessageDialog(null, "Sistem tidak menemukan barang dengan Id Barang = " + idbrg  + " Gagal Disimpan");
                }
                
                //------- Opsi jika terdapat barang yang belum satupun di masukan
                if(kosong == 0){
                    try {
                        Connection con = db_koneksi.membukakoneksi();
                        Statement sttment = con.createStatement();
                        sttment.executeUpdate("DELETE FROM transaksi_barang_keluar WHERE no_nota = '" + nomernota + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                }else{
                }
                   JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi");
            }
         }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
         namaheadertabel();
         kosongkantabel();
         jdt_tglbrgkeluar.setDateFormatString("");
         txt_nonota.setText("");
         jtxt_baristrpilih.setText("");
         txt_idbrg.setText("");
         jtxt_hrgbeli.setText("");
         txt_jumlah.setText("");
         jtxt_hrgjual.setText("");
         jtxt_total.setText("");
         jtxt_setstokbrg.setText("");
    }
    
    public void tampilbrgkeluar(){
      String nonota, idbrg, nmbrg, nmrtostr, katbrg, hrgbl, hrgjl, spek, stok, nourut, tgl_keluar;
      int nmr=0;
    try {
            Object [] baris = {"No","Nomer Nota", "Tanggal Keluar","No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual", "Jumlah Keluar"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tbltrans.setModel(modeltbbrg);
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
                tbltrans.getColumnModel().getColumn(0).setPreferredWidth(5);
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
        jLabel3 = new javax.swing.JLabel();
        txt_nonota = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_nmbrg = new javax.swing.JTextField();
        jtxt_hrgbeli = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbltrans = new javax.swing.JTable();
        cmbadd = new javax.swing.JButton();
        cmbdelete = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jlbl_nmuser = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlbl_idusernya = new javax.swing.JLabel();
        txt_idbrg = new javax.swing.JTextField();
        btn_caribrg = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_kurang = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jlbl_hasilsemuatotal = new javax.swing.JLabel();
        jtxt_setstokbrg = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtxt_total = new javax.swing.JTextField();
        jdt_tglbrgkeluar = new com.toedter.calendar.JDateChooser();
        jLabel13 = new javax.swing.JLabel();
        jtxt_hrgjual = new javax.swing.JTextField();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Form Transaksi Pembayaran");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/back.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(336, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(310, 310, 310)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setText("No Nota");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 94, 62, 26));

        txt_nonota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nonotaActionPerformed(evt);
            }
        });
        txt_nonota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_nonotaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nonotaKeyTyped(evt);
            }
        });
        jPanel2.add(txt_nonota, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 100, 170, -1));

        jLabel4.setText("Id Barang");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 182, 62, 26));

        jLabel8.setText("Tanggal Barang Keluar");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 138, 140, 26));

        jLabel9.setText("Jumlah");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 62, 26));

        jLabel10.setText("Nama Barang");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 226, 120, 26));

        txt_nmbrg.setEnabled(false);
        jPanel2.add(txt_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 170, -1));

        jtxt_hrgbeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgbeliActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgbeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 400, 170, -1));

        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        jPanel2.add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 170, -1));

        tbltrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Barang", "Nama Barang", "Harga Beli", "Jumlah Jual", "Jumlah", "Total"
            }
        ));
        jScrollPane1.setViewportView(tbltrans);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 550, 259));

        cmbadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/save.png"))); // NOI18N
        cmbadd.setText("save");
        cmbadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbaddActionPerformed(evt);
            }
        });
        jPanel2.add(cmbadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 370, -1, 40));

        cmbdelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/delete.png"))); // NOI18N
        cmbdelete.setText("Hapus");
        cmbdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbdeleteActionPerformed(evt);
            }
        });
        jPanel2.add(cmbdelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 370, -1, 40));

        jLabel12.setText("User Saat Ini");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 19, 90, -1));

        jlbl_nmuser.setText("-");
        jPanel2.add(jlbl_nmuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 19, 85, -1));

        jLabel5.setText("Id User");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 51, 80, -1));

        jlbl_idusernya.setText("-");
        jPanel2.add(jlbl_idusernya, new org.netbeans.lib.awtextra.AbsoluteConstraints(121, 51, 85, -1));
        jPanel2.add(txt_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 180, 170, -1));

        btn_caribrg.setText("Cari");
        btn_caribrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_caribrgActionPerformed(evt);
            }
        });
        jPanel2.add(btn_caribrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 70, -1));

        btn_tambah.setText("+");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel2.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 50, -1, -1));

        btn_kurang.setText("-");
        btn_kurang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kurangActionPerformed(evt);
            }
        });
        jPanel2.add(btn_kurang, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 50, -1, -1));

        jLabel6.setText("Total");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 350, 40, -1));

        jlbl_hasilsemuatotal.setText("-");
        jPanel2.add(jlbl_hasilsemuatotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 350, 140, -1));
        jPanel2.add(jtxt_setstokbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 50, 26, -1));

        jLabel11.setText("Total");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

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
        jPanel2.add(jtxt_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 360, 170, -1));
        jPanel2.add(jdt_tglbrgkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, 170, -1));

        jLabel13.setText("Harga Jual");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, -1, -1));

        jtxt_hrgjual.setEnabled(false);
        jtxt_hrgjual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_hrgjualActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 270, 170, -1));
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 380, 50, 30));

        jButton1.setText("Tampil");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, -1, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1068, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 474, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(90, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        new menu_transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txt_nonotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nonotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nonotaActionPerformed

    private void txt_nonotaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nonotaKeyReleased
//        String notasama = txt_nonota.getText();
//        try {
//            Connection con = db_koneksi.membukakoneksi();
//            Statement sttment = con.createStatement();
//            String exec_sql = "SELECT no_nota FROM transaksi_barang_keluar WHERE no_nota = '"+notasama+"'";
//            ResultSet hasil = sttment.executeQuery(exec_sql);
//            if(hasil.next())
//            {
//                JOptionPane.showMessageDialog(null, "Nota sudah ada di database");
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Error " + e);
//        }
    }//GEN-LAST:event_txt_nonotaKeyReleased

    private void txt_nonotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nonotaKeyTyped

    }//GEN-LAST:event_txt_nonotaKeyTyped

    private void jtxt_hrgbeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgbeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgbeliActionPerformed

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void cmbaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbaddActionPerformed
        simpan();
        txt_idbrg.setText("");
        txt_nmbrg.setText("-");
        jtxt_hrgbeli.setText("");
        txt_jumlah.setText("");
        jtxt_total.setText("");
        jtxt_hrgjual.setText("");
        jlbl_hasilsemuatotal.setText("-");
        jtxt_baristrpilih.setText("");
        txt_nonota.setText("");
        jdt_tglbrgkeluar.setCalendar(null);
    }//GEN-LAST:event_cmbaddActionPerformed

    private void cmbdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbdeleteActionPerformed
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus semua baris, "
            + "dan semua inputan ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            kosongkantabel();
            jtxt_baristrpilih.setText("");
            jtxt_hrgbeli.setText("");
            txt_idbrg.setText("");
            jdt_tglbrgkeluar.setCalendar(null);
            //jtxt_idsupp.setText("");
            txt_jumlah.setText("");
            jtxt_total.setText("");
            txt_nonota.setText("");
            jtxt_setstokbrg.setText("");
        }
    }//GEN-LAST:event_cmbdeleteActionPerformed

    private void btn_caribrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_caribrgActionPerformed
        caribarang();
    }//GEN-LAST:event_btn_caribrgActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahbarang();
        tombolkurang(true);
        hitungtotalpengeluaransemua();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_kurangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kurangActionPerformed
        kurangibarang();
    }//GEN-LAST:event_btn_kurangActionPerformed

    private void jtxt_totalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_totalMouseClicked
        hitungjumlah();
        tombolkurang(true);
    }//GEN-LAST:event_jtxt_totalMouseClicked

    private void jtxt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_totalActionPerformed

    }//GEN-LAST:event_jtxt_totalActionPerformed

    private void jtxt_hrgjualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_hrgjualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_hrgjualActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tampilbrgkeluar();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_jual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_caribrg;
    private javax.swing.JButton btn_kurang;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton cmbadd;
    private javax.swing.JButton cmbdelete;
    private javax.swing.JButton jButton1;
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
    private com.toedter.calendar.JDateChooser jdt_tglbrgkeluar;
    private javax.swing.JLabel jlbl_hasilsemuatotal;
    private javax.swing.JLabel jlbl_idusernya;
    private javax.swing.JLabel jlbl_nmuser;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_hrgbeli;
    private javax.swing.JTextField jtxt_hrgjual;
    private javax.swing.JTextField jtxt_setstokbrg;
    private javax.swing.JTextField jtxt_total;
    private javax.swing.JTable tbltrans;
    private javax.swing.JTextField txt_idbrg;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_nmbrg;
    private javax.swing.JTextField txt_nonota;
    // End of variables declaration//GEN-END:variables
}
