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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import net.proteanit.sql.DbUtils;
import java.sql.*;


/**
 *
 * @author Agung
 */
public class transaksi_return extends javax.swing.JFrame {

    /**
     * Creates new form transaksi_return
     */
    public transaksi_return() {
        initComponents();
        this.setLocationRelativeTo(null);
        jtxt_baristrpilih.hide();
        
    }
    
    public void carinomernota(){
    String nonota, idbrg, nmbrg, nmrtostr, katbrg, jmlh, hrgjl, pilih, stok, nourut, tgl_keluar;
    Boolean pilbrg = false ;
    //String cari = jtxt_carinmrnota.getText();
        int nmr=0;
        String cari =jtxt_carinmrnota.getText();
        try {
            Object [] baris = {"No","Nomer Nota", "No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual","Jumlah","Checklist"};
            DefaultTableModel modeltbbrg;
            modeltbbrg = new DefaultTableModel(null, baris);
            tabel_returpenjualan.setModel(modeltbbrg);
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement(); 
            String exec_sql = "SELECT detail_transaksi_barangklr.no_urut_keluar, detail_transaksi_barangklr.no_nota"
                    + ", barang.id_barang, barang.nama_barang, detail_transaksi_barangklr.harga_jual"
                    + ", detail_transaksi_barangklr.jumlah_brgkeluar FROM detail_transaksi_barangklr join barang ON "
                    + "barang.id_barang = detail_transaksi_barangklr.id_barang WHERE  "
                    + "detail_transaksi_barangklr.no_nota like '%" + cari + "%'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
            while(hasil.next()){
                String [] dataditampilkan = new String[7];
                nmr++;
                nmrtostr = Integer.toString(nmr);
                tabel_returpenjualan.getColumnModel().getColumn(0).setPreferredWidth(5);
                dataditampilkan[0] = nmrtostr;
                dataditampilkan[1] = hasil.getString("no_nota");
                dataditampilkan[2] = hasil.getString("no_urut_keluar");
                dataditampilkan[3] = hasil.getString("id_barang");
                dataditampilkan[4] = hasil.getString("nama_barang");
                dataditampilkan[5] = hasil.getString("harga_jual");
                dataditampilkan[6] = hasil.getString("jumlah_brgkeluar");   
                modeltbbrg.addRow(dataditampilkan);
                SetCheckBox(7, tabel_returpenjualan);
           }
//            if(!hasil.next()){
//            JOptionPane.showMessageDialog(null, "Data barang keluar tidak ditemukan.");
//            //lihat_barang();
//            }
            jtxt_carinmrnota.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
        
    }
    
    public void namaheadertabel(){
    Object [] baris = {"No","Nomer Nota", "No Urut keluar","Id Barang",
                "Nama Barang","Harga Jual","Jumlah","Checklist"};
    DefaultTableModel modeltbbrg;
    modeltbbrg = new DefaultTableModel(null, baris);
    tabel_returpenjualan.setModel(modeltbbrg);
    }
    
    public void kurangibarang(){
    int konfirmasi = JOptionPane.showConfirmDialog(null, "Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(konfirmasi==0) {
            String baris= Integer.toString(tabel_returpenjualan.getSelectedRow());
            jtxt_baristrpilih.setText(baris);
            DefaultTableModel model = (DefaultTableModel) tabel_returpenjualan.getModel();
            int row = Integer.parseInt(jtxt_baristrpilih.getText());
            model.removeRow(row);
        }
    }

     public void SetCheckBox(int kolom, JTable table)
    {
        TableColumn tc = table.getColumnModel().getColumn(kolom);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
     
      public boolean CheckboxSelected(int row, int column, JTable table)
    {    
        return table.getValueAt(row, column) != null;                       
    }  
    
    public void simpantransaksi(){
      //untuk menyimpan data transaksi keluar
     String tglretur = jdt_tglretur.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglretur.getDate()));
     String kdretur = jtxt_kdretur.getText();
     String nomernota = jlbl_nonota.getText();
     String keterangan = jtxt_ketretur.getText();
     String idbrg, kode, id_brgretur;
     Integer stok, tidak_ditemukan, notfound, totalhrgajual, stokupdate ,kosong = 0;
     String totalharga,jumlah,hrgbli,hrgjual, nourutkeluar;        
     DefaultTableModel tabelmodel = (DefaultTableModel) tabel_returpenjualan.getModel();
     int rowCount = tabelmodel.getRowCount();       
if(!"".equals(tglretur) && !"".equals(keterangan) && !"".equals(kdretur))
{
 try {
      Connection conn = db_koneksi.membukakoneksi();
      Statement statement = conn.createStatement();
      String executed_sql = "INSERT INTO transaksi_return (kd_return, no_nota, tgl_return, keterangan)VALUES('"
      + kdretur + "', '"+nomernota+"', '"+dateinputnya+"', '"+keterangan+"')"; 
      statement.executeUpdate(executed_sql);
     } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
 //while(i<rowCount)
for(int i=0;i<rowCount;i++)
 {
  tidak_ditemukan = 0;
  stok = 0;
  stokupdate = 0;
  notfound = 0; 
  nomernota = (tabel_returpenjualan.getModel().getValueAt(i, 1).toString());
  nourutkeluar = (tabel_returpenjualan.getModel().getValueAt(i, 2).toString());
  idbrg = (tabel_returpenjualan.getModel().getValueAt(i, 3).toString());
  hrgjual = (tabel_returpenjualan.getModel().getValueAt(i, 5).toString());
  jumlah = (tabel_returpenjualan.getModel().getValueAt(i, 6).toString());   
  if( CheckboxSelected(i, 7, tabel_returpenjualan))
  {
   try{
    Connection con = db_koneksi.membukakoneksi();
    Statement sttment = con.createStatement();
    String exec_sql = "SELECT jumlah_brgkeluar FROM detail_transaksi_barangklr "
    + "WHERE no_urut_keluar = '"+jlbl_nourutkel.getText()+"'";
    ResultSet hasil = sttment.executeQuery(exec_sql);
    if(hasil.getInt("jumlah_brgkeluar")<Integer.parseInt(jumlah))
    {
     //mengeksekusi apabila jumlah barang yang dikembalikan kurang dari sama dengan
     //jumlah barang yg ada di nota penjualan /  barangt keluar   
     try {
         Connection conn = db_koneksi.membukakoneksi();
         Statement statement = conn.createStatement();
         String executed_sql = "UPDATE transaksi_return SET no_nota = '"+nomernota+"' "
         + " WHERE kd_return = '" +kdretur + "'"; 
         statement.executeUpdate(executed_sql);
         } catch (SQLException e) { }
//    try {
//        Connection conn = db_koneksi.membukakoneksi();
//        Statement statement = conn.createStatement();
//        String execute_sql ="SELECT MAX(kd_return) as max FROM transaksi_return";
//        ResultSet hasilnya = statement.executeQuery(execute_sql); 
//       while(hasilnya.next()) 
//        {
//        id_brgretur = hasilnya.getString("max");
//        }            
//       } catch (SQLException e) { }
                      //------- Menambah stok dengan data jumlah
    try {
         Connection cn = db_koneksi.membukakoneksi();
         Statement st = cn.createStatement();
         String exec = "SELECT stok FROM barang WHERE id_barang = '" + idbrg + "'";
         ResultSet rest = st.executeQuery(exec);
        while(rest.next())
         {
         if (rest.getRow() == 1){
          stok = rest.getInt("stok") + Integer.parseInt(jumlah);
          } else {
           tidak_ditemukan = 1;
          }
         }
        } catch (SQLException e) {  } 
                
       try{
          Connection conn = db_koneksi.membukakoneksi();
          Statement stment = conn.createStatement();
          String edtjmlhbrgkel = "SELECT jumlah_brgkeluar FROM detail_transaksi_barangklr "
          + "WHERE no_urut_keluar = '" + nourutkeluar + "'";
          ResultSet reslt = stment.executeQuery(edtjmlhbrgkel);
          while(reslt.next())
          {
           if(reslt.getRow()==1)
            {
              stokupdate = reslt.getInt("jumlah_brgkeluar") - Integer.parseInt(jumlah);
             }else{
              notfound = 1;
             }
           }
          }catch(SQLException e){ JOptionPane.showMessageDialog(null, "Error " + e);}
        //------- Mengupdate jumlah barang keluar pada detail barang keluar
        try {
            Connection kon = db_koneksi.membukakoneksi();
            Statement statemen = kon.createStatement();
            String execute = "UPDATE detail_transaksi_barangklr SET jumlah_brgkeluar = '"+stokupdate+"' "
            + " WHERE no_urut_keluar = '" + nourutkeluar + "'";
            statemen.executeUpdate(execute);
           } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
        //------- Mengupdate jumlah stok barang
        try {
            Connection kon = db_koneksi.membukakoneksi();
            Statement statemen = kon.createStatement();
            statemen.executeUpdate("UPDATE barang SET stok='" + stok 
            + "' WHERE id_barang = '" + idbrg + "'");
            } catch (SQLException e) { }
         //------- Memasukan pada table transaksi detail
        try {
            Connection conect = db_koneksi.membukakoneksi();
            Statement stt = conect.createStatement();
            stt.executeUpdate("INSERT INTO detail_transaksi_return(kd_return, "
            + "id_barang, harga_jual, jumlah_brgkembali) VALUES ('" + kdretur + "', '" + idbrg + "', '" 
            + hrgjual + "', '" + jumlah + "')");
            } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);}
        JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi"+ i++); 
    }
    else{
    //apabila banyak barang yang dikembalikan lebih banyak daripada 
    //jumlah barang yang ada di nota penjualan/barang keluar
    JOptionPane.showMessageDialog(null, "Barang yang dikembalikan lebih banyak daripada barang yang dibeli");
    }
   }catch(SQLException a){}
  }
  else if(CheckboxSelected(i, 7, tabel_returpenjualan) == false)
  {
  i++;
  }
 } 
}else
{
    JOptionPane.showMessageDialog(null, "terdapat inputan yang belum diisi");
}

  }
    public void simpanreturbarang(){
         //untuk menyimpan data transaksi keluar
     String tglretur = jdt_tglretur.getDateFormatString();
     String setdatenya = "yyyy-MM-dd";
     SimpleDateFormat formatdatenya = new SimpleDateFormat(setdatenya);
     String dateinputnya = String.valueOf(formatdatenya.format(jdt_tglretur.getDate()));
     String kdretur = jtxt_kdretur.getText();
     String nomernota = jlbl_nonota.getText();
     String keterangan = jtxt_ketretur.getText();
     String idbrg, kode, id_brgretur;
     Integer stok, tidak_ditemukan, notfound, totalhrgajual, stokupdate ,kosong = 0;
     String totalharga,jumlah,hrgbli,hrgjual, nourutkeluar;        
     DefaultTableModel model = (DefaultTableModel) tabel_returpenjualan.getModel();
     int rowCount = model.getRowCount();  
     
if(!"".equals(tglretur) && !"".equals(keterangan) && !"".equals(kdretur)){
    try {
                Connection conn = db_koneksi.membukakoneksi();
                Statement statement = conn.createStatement();
                String executed_sql = "INSERT INTO transaksi_return(kd_return, no_nota, tgl_return, keterangan)VALUES('"
                + kdretur + "', '"+nomernota+"', '"+dateinputnya+"', '"+keterangan+"')"; 
                statement.executeUpdate(executed_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            } 
   for (int i = 0; i < rowCount; i++)
    {
         tidak_ditemukan = 0;
         stok = 0;
         stokupdate = 0;
         notfound = 0; 
         nomernota = (tabel_returpenjualan.getModel().getValueAt(i, 1).toString());
         nourutkeluar = (tabel_returpenjualan.getModel().getValueAt(i, 2).toString());
         idbrg = (tabel_returpenjualan.getModel().getValueAt(i, 3).toString());
         hrgjual = (tabel_returpenjualan.getModel().getValueAt(i, 5).toString());
         jumlah = (tabel_returpenjualan.getModel().getValueAt(i, 6).toString());  
     if  ( CheckboxSelected(i, 7, tabel_returpenjualan))
      {  
        try{
         Connection con = db_koneksi.membukakoneksi();
         Statement sttment = con.createStatement();
         String exec_sql = "SELECT jumlah_brgkeluar FROM detail_transaksi_barangklr "
         + "WHERE no_urut_keluar = '"+jlbl_nourutkel.getText()+"'";
         ResultSet hasil = sttment.executeQuery(exec_sql);
         while(hasil.next())
         {
          if(Integer.parseInt(jumlah)>hasil.getInt("jumlah_brgkeluar"))
           {
            //apabila banyak barang yang dikembalikan lebih banyak daripada 
            //jumlah barang yang ada di nota penjualan/barang keluar
             JOptionPane.showMessageDialog(null, "Barang yang dikembalikan lebih banyak daripada barang yang dibeli");
           }
          else
          {
             //mengeksekusi apabila jumlah barang yang dikembalikan kurang dari sama dengan
             //jumlah barang yg ada di nota penjualan /  barangt keluar
            try {
                Connection conn = db_koneksi.membukakoneksi();
                Statement statement = conn.createStatement();
                String executed_sql = "UPDATE transaksi_return SET no_nota = '"+nomernota+"', "
                + "keterangan = '"+keterangan+"' "
                + " WHERE kd_return = '" +kdretur + "'"; 
                statement.executeUpdate(executed_sql);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error " + e);
            }
//              try {
//                Connection conn = db_koneksi.membukakoneksi();
//                Statement statement = conn.createStatement();
//                String execute_sql ="SELECT MAX(kd_return) as max FROM transaksi_return";
//                ResultSet hasilnya = statement.executeQuery(execute_sql); 
//                while(hasilnya.next()) 
//                {
//                   id_brgretur = hasilnya.getString("max");
//                }            
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e);
//            }
                      //------- Menambah stok dengan data jumlah
                try {
                    Connection cn = db_koneksi.membukakoneksi();
                    Statement st = cn.createStatement();
                    String exec = "SELECT stok FROM barang WHERE id_barang = '" + idbrg + "'";
                    ResultSet rest = st.executeQuery(exec);
                   // sttment.executeUpdate(exec_sql);
                   while(rest.next())
                   {
                       if (rest.getRow() == 1){
                        stok = rest.getInt("stok") + Integer.parseInt(jumlah);
                        } else {
                           tidak_ditemukan = 1;
                        }
                   }
                    //Integer.parseInt(jumlah);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error " + e);
                } 
                
                try{
                Connection conn = db_koneksi.membukakoneksi();
                Statement stment = conn.createStatement();
                String edtjmlhbrgkel = "SELECT jumlah_brgkeluar FROM detail_transaksi_barangklr "
                + "WHERE no_urut_keluar = '" + nourutkeluar + "'";
                ResultSet reslt = stment.executeQuery(edtjmlhbrgkel);
                 while(reslt.next())
                 {
                    if(reslt.getRow()==1)
                    {
                        stokupdate = reslt.getInt("jumlah_brgkeluar") - Integer.parseInt(jumlah);
                    }else{
                        notfound = 1;
                    }
                 }
                }catch(SQLException e){ JOptionPane.showMessageDialog(null, "Error " + e);}
            
                if(tidak_ditemukan == 0){
                
                        //------- Mengupdate jumlah stok barang
                    try {
                        Connection kon = db_koneksi.membukakoneksi();
                        Statement statemen = kon.createStatement();
                        String execute = "UPDATE detail_transaksi_barangklr SET jumlah_brgkeluar = '"+stokupdate+"' "
                        + " WHERE no_urut_keluar = '" + nourutkeluar + "'";
                        statemen.executeUpdate(execute);
                    } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);} 
                        //------- Mengupdate jumlah barang keluar pada detail barang keluar
                    try {
                        Connection kon = db_koneksi.membukakoneksi();
                        Statement statemen = kon.createStatement();
                        statemen.executeUpdate("UPDATE barang SET stok='" + stok 
                        + "' WHERE id_barang = '" + idbrg + "'");
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Error " + e);
                    } 
                    
                        //------- Memasukan pada table transaksi detail
                    try {
                        Connection conect = db_koneksi.membukakoneksi();
                        Statement stt = conect.createStatement();
                        stt.executeUpdate("INSERT INTO detail_transaksi_return(kd_return, "
                          + "id_barang, harga_jual, jumlah_brgkembali) VALUES ('" + kdretur + "', '" + idbrg + "', '" 
                          + hrgjual + "', '" + jumlah + "')");
                        kosong = 1;
                    } catch (SQLException e) { JOptionPane.showMessageDialog(null, "Error " + e);}
                    //JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi"); 
                }  
           }
          } 
             }catch(SQLException e)
                {JOptionPane.showMessageDialog(null, "nota yang dipilih tidak ada dalam database");}  
//         }
           JOptionPane.showMessageDialog(null, "Berhasil menyimpan data transaksi");
     }
//     else{JOptionPane.showMessageDialog(null, "Belum memilih barang yang dikembalikan/retur");}
   }        
//            try {
//                HashMap hash = new HashMap();
//                hash.put("nm_supplier", cmbid_pelanggan.getSelectedItem().toString());
//                hash.put("id", id_barang_masuk);
//                        
//                File file = new File("src/report/report_transaksiMasuk.jrxml");
//                JasperDesign jasperDesign = JRXmlLoader.load(file);
//                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, konek.openkoneksi());
//                JasperViewer.viewReport(jasperPrint, false);
//            }catch (ClassNotFoundException | JRException e) {
//                JOptionPane.showMessageDialog(null, "Error " + e);
//            }
            //this.hide();
        }else{
            JOptionPane.showMessageDialog(null, "Terdapat inputan yang kosong.");
        }
     namaheadertabel();
     jtxt_kdretur.setText("");
     jtxt_carinmrnota.setText("");
     jtxt_ketretur.setText("");
     jlbl_jmlhdikembalikan.setText("");
     jdt_tglretur.setDateFormatString("");
     jlbl_nonota.setText("-");
     jlbl_nmbrg.setText("-");
     jlbl_hrgjual.setText("-");
     jlbl_nourutkel.setText("-");
     jtxt_baristrpilih.setText("");
     jlbl_idbrg.setText("-");
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
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxt_kdretur = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jdt_tglretur = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jtxt_carinmrnota = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jtxt_ketretur = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jlbl_hrgjual = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jlbl_nonota = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jlbl_idbrg = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jlbl_nmbrg = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_returpenjualan = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jlbl_jmlhdikembalikan = new javax.swing.JLabel();
        jtxt_baristrpilih = new javax.swing.JTextField();
        jlbl_nourutkel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Form Transaksi Return");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, -1, 40));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/back.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(744, 10, 60, 40));

        jPanel2.setBackground(new java.awt.Color(0, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Kode Return");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 70, 30));

        jtxt_kdretur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jtxt_kdreturMouseReleased(evt);
            }
        });
        jtxt_kdretur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_kdreturActionPerformed(evt);
            }
        });
        jtxt_kdretur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxt_kdreturKeyReleased(evt);
            }
        });
        jPanel2.add(jtxt_kdretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 120, 30));

        jLabel3.setText("Tanggal Barang Return");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 140, -1));
        jPanel2.add(jdt_tglretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 120, 30));

        jLabel4.setText("Pilih Nomor Nota");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 100, 20));

        jtxt_carinmrnota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_carinmrnotaActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_carinmrnota, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 120, -1));

        jButton1.setText("cari");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 40, 60, -1));

        jLabel5.setText("Keterangan");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 80, 20));

        jtxt_ketretur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxt_ketreturActionPerformed(evt);
            }
        });
        jPanel2.add(jtxt_ketretur, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 120, 60));

        jLabel6.setText("Nomor Nota");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 70, -1));

        jlbl_hrgjual.setText("-");
        jPanel2.add(jlbl_hrgjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 90, -1));

        jLabel8.setText("ID Barang");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 70, -1));

        jlbl_nonota.setText("-");
        jPanel2.add(jlbl_nonota, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 90, -1));

        jLabel10.setText("Nama Barang");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 160, 80, -1));

        jlbl_idbrg.setText("-");
        jPanel2.add(jlbl_idbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 90, -1));

        jLabel12.setText("Jumlah Barang Kembali");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 140, -1));

        jlbl_nmbrg.setText("-");
        jPanel2.add(jlbl_nmbrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 90, -1));

        tabel_returpenjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Nomer Nota", "No Urut Keluar", "Id barang", "Nama Barang", "Harga Jual", "Jumlah", "Checklist"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_returpenjualan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_returpenjualanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_returpenjualan);
        if (tabel_returpenjualan.getColumnModel().getColumnCount() > 0) {
            tabel_returpenjualan.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 760, 200));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Aplikasi_inventory/images/save.png"))); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 430, -1, -1));

        jLabel9.setText("Harga Jual");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 80, 70, -1));

        jlbl_jmlhdikembalikan.setText("-");
        jPanel2.add(jlbl_jmlhdikembalikan, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 90, -1));
        jPanel2.add(jtxt_baristrpilih, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 420, 60, -1));

        jlbl_nourutkel.setText("-");
        jPanel2.add(jlbl_nourutkel, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 190, 30, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 817, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        carinomernota();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtxt_kdreturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_kdreturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_kdreturActionPerformed

    private void jtxt_kdreturKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxt_kdreturKeyReleased

    }//GEN-LAST:event_jtxt_kdreturKeyReleased

    private void jtxt_ketreturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_ketreturActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_ketreturActionPerformed

    private void jtxt_carinmrnotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxt_carinmrnotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxt_carinmrnotaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        simpanreturbarang();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        new menu_transaksi().setVisible(true);
        dispose();
    }//GEN-LAST:event_jLabel7MouseClicked

    private void tabel_returpenjualanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_returpenjualanMouseClicked
        DefaultTableModel dt_tbreturjualk= (DefaultTableModel)tabel_returpenjualan.getModel();
        int selecttedrow = tabel_returpenjualan.getSelectedRow();
        jtxt_baristrpilih.setText(Integer.toString(selecttedrow) );
        jlbl_nonota.setText(tabel_returpenjualan.getValueAt(selecttedrow, 1).toString());
        jlbl_nourutkel.setText(tabel_returpenjualan.getValueAt(selecttedrow, 2).toString());
        jlbl_idbrg.setText(tabel_returpenjualan.getValueAt(selecttedrow, 3).toString());
        jlbl_nmbrg.setText(tabel_returpenjualan.getValueAt(selecttedrow, 4).toString());
        jlbl_hrgjual.setText(tabel_returpenjualan.getValueAt(selecttedrow, 5).toString());
        jlbl_jmlhdikembalikan.setText(tabel_returpenjualan.getValueAt(selecttedrow, 6).toString());
    }//GEN-LAST:event_tabel_returpenjualanMouseClicked

    private void jtxt_kdreturMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtxt_kdreturMouseReleased
       String retursama = jtxt_kdretur.getText();
        try {
            Connection con = db_koneksi.membukakoneksi();
            Statement sttment = con.createStatement();
            String exec_sql = "SELECT kd_return FROM transaksi_return WHERE kd_return = '"+retursama+"'";
            ResultSet hasil = sttment.executeQuery(exec_sql);
           if(hasil.next())
           {
               JOptionPane.showMessageDialog(null, "Kode retur sudah ada di database");
           }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }//GEN-LAST:event_jtxt_kdreturMouseReleased

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
            java.util.logging.Logger.getLogger(transaksi_return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_return.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_return().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdt_tglretur;
    private javax.swing.JLabel jlbl_hrgjual;
    private javax.swing.JLabel jlbl_idbrg;
    private javax.swing.JLabel jlbl_jmlhdikembalikan;
    private javax.swing.JLabel jlbl_nmbrg;
    private javax.swing.JLabel jlbl_nonota;
    private javax.swing.JLabel jlbl_nourutkel;
    private javax.swing.JTextField jtxt_baristrpilih;
    private javax.swing.JTextField jtxt_carinmrnota;
    private javax.swing.JTextField jtxt_kdretur;
    private javax.swing.JTextField jtxt_ketretur;
    private javax.swing.JTable tabel_returpenjualan;
    // End of variables declaration//GEN-END:variables
}
