# 📦 Aplikasi Inventori Barang

Aplikasi desktop manajemen inventori barang berbasis Java yang dirancang untuk membantu pengelolaan stok, transaksi pembelian, penjualan, dan retur barang secara efisien. Dibangun menggunakan Java Swing dengan koneksi database MySQL.

---

## 🖥️ Tampilan Aplikasi

> Aplikasi ini memiliki antarmuka grafis (GUI) berbasis Java Swing dengan tampilan yang intuitif dan mudah digunakan.

---

## ✨ Fitur Utama

### 🔐 Autentikasi & Manajemen Pengguna
- Login multi-role: **Admin** dan **Karyawan**
- Sesi pengguna terpisah untuk setiap role (`SesiAdmin`, `SesiPemilik`)
- Registrasi akun pengguna baru
- Dashboard berbeda sesuai hak akses

### 📋 Manajemen Data Master
- **Data Barang** — tambah, edit, hapus, dan cari data barang dengan kode otomatis
- **Data Supplier** — kelola informasi supplier dengan kode ID otomatis
- **Data Karyawan** — manajemen data karyawan beserta detail lengkap

### 💼 Manajemen Transaksi
- **Transaksi Beli** — pencatatan pembelian barang dari supplier
- **Transaksi Jual** — pencatatan penjualan barang dengan perhitungan total otomatis
- **Transaksi Retur** — pengelolaan pengembalian barang berdasarkan nomor nota

### 📊 Laporan & Cetak
- Laporan stok barang
- Laporan barang masuk
- Laporan barang keluar
- Laporan retur barang
- Didukung oleh **JasperReports** untuk cetak laporan profesional (`.jasper` & `.jrxml`)

### 🗂️ Dashboard
- Dashboard **Admin**: ringkasan data barang, supplier, dan karyawan
- Dashboard **Karyawan**: monitoring barang keluar, barang masuk, dan retur secara real-time

---

## 🗃️ Struktur Proyek

```
src/
├── Apk_inv/
│   ├── main.java                  # Entry point aplikasi
│   ├── login.java                 # Form login
│   ├── dashboard.java             # Dashboard admin
│   ├── dashboard2.java            # Dashboard karyawan
│   ├── barang.java                # Manajemen data barang
│   ├── supplier.java              # Manajemen data supplier
│   ├── karyawan.java              # Manajemen data karyawan
│   ├── detail_karyawan.java       # Detail & edit karyawan
│   ├── transaksi_beli.java        # Transaksi pembelian
│   ├── transaksi_jual.java        # Transaksi penjualan
│   ├── transaksi_return.java      # Transaksi retur
│   ├── laporan.java               # Menu laporan
│   ├── Laporan_1.java             # Detail laporan
│   ├── menu_transaksi.java        # Menu transaksi
│   ├── db_koneksi.java            # Konfigurasi koneksi database
│   ├── SesiAdmin.java             # Manajemen sesi admin
│   ├── SesiPemilik.java           # Manajemen sesi karyawan
│   ├── report.java                # Utilitas cetak laporan
│   ├── *.jasper / *.jrxml         # Template laporan JasperReports
│   └── *.form                     # File form NetBeans GUI Builder
├── Aplikasi_inventory/
│   └── images/                    # Aset gambar & ikon aplikasi
└── session/
    └── session_login.java         # Utilitas sesi login
```

---

## 🛠️ Teknologi yang Digunakan

| Teknologi | Keterangan |
|-----------|------------|
| **Java** | Bahasa pemrograman utama |
| **Java Swing** | Framework GUI desktop |
| **NetBeans GUI Builder** | IDE & form designer |
| **MySQL** | Database relasional |
| **JDBC** | Koneksi Java ke MySQL |
| **JasperReports** | Pembuatan & cetak laporan |
| **rs2xml** | Konversi ResultSet ke TableModel |

---

## ⚙️ Prasyarat

Sebelum menjalankan aplikasi, pastikan sudah terinstal:

- ☕ **Java JDK 8** atau lebih baru
- 🐬 **MySQL Server** (versi 5.x atau 8.x)
- 🧰 **NetBeans IDE** (disarankan versi 8.x atau 12.x)
- 📚 Library yang dibutuhkan:
  - `mysql-connector-java.jar`
  - `jasperreports-x.x.x.jar`
  - `rs2xml.jar`

---

## 🚀 Cara Instalasi & Menjalankan

### 1. Clone Repositori
```bash
git clone https://github.com/agungza/Inventori_Barang.git
cd Inventori_Barang
```

### 2. Buat Database MySQL
Buat database baru di MySQL dengan nama `db_inventori`:
```sql
CREATE DATABASE db_inventori;
```
Kemudian import file SQL (jika tersedia) atau buat tabel secara manual sesuai struktur yang digunakan aplikasi.

### 3. Konfigurasi Koneksi Database
Edit file `src/Apk_inv/db_koneksi.java` sesuai konfigurasi MySQL kamu:
```java
String db   = "jdbc:mysql://localhost/db_inventori";
String user = "root";
String pasword = "your_password"; // Isi password MySQL kamu
```

### 4. Buka di NetBeans
- Buka **NetBeans IDE**
- Pilih **File → Open Project**
- Arahkan ke folder hasil clone
- Tambahkan library yang dibutuhkan ke project (klik kanan project → Properties → Libraries)

### 5. Jalankan Aplikasi
- Klik kanan pada file `main.java`
- Pilih **Run File**, atau tekan `Shift + F6`

---

## 👤 Akun Default

Sesuaikan akun pengguna di database. Tabel `user` memiliki kolom:

| Kolom | Keterangan |
|-------|------------|
| `id_user` | ID pengguna |
| `username` | Nama pengguna |
| `password` | Kata sandi |
| `jenis_akses` | `admin` atau `karyawan` |

---

## 📁 Struktur Tabel Database (Referensi)

Berikut tabel-tabel yang digunakan oleh aplikasi berdasarkan analisis kode:

- `user` — data akun pengguna
- `barang` — data master barang
- `supplier` — data master supplier
- `karyawan` — data karyawan
- `detail_transaksi_barangklr` — detail transaksi barang keluar (jual)
- `detail_transaksi_barangmsk` — detail transaksi barang masuk (beli)
- `detail_transaksi_barangretur` — detail transaksi retur barang

---

## 🤝 Kontribusi

Kontribusi sangat diterima! Silakan:

1. Fork repositori ini
2. Buat branch baru: `git checkout -b fitur/nama-fitur`
3. Commit perubahan: `git commit -m 'Menambahkan fitur X'`
4. Push ke branch: `git push origin fitur/nama-fitur`
5. Buat Pull Request

---

## 📄 Lisensi

Proyek ini bersifat open-source dan bebas digunakan untuk keperluan belajar dan pengembangan.

---

## 👨‍💻 Author

**Agung** — Dibuat sebagai proyek aplikasi inventori desktop berbasis Java.

> ⭐ Jika proyek ini bermanfaat, jangan lupa berikan bintang!
