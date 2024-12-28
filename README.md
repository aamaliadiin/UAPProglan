# UAPProglan
# Aplikasi Perhitungan Nilai Akhir

Aplikasi ini dibuat menggunakan *Java Swing* dengan *koneksi MySQL* untuk mendukung pengelolaan data nilai akhir siswa. Aplikasi ini mencakup fitur registrasi, login, dan pengelolaan data nilai akhir.

## Fitur Utama
1. *Registrasi Pengguna:*
    - Membuat akun baru dengan validasi input.
2. *Login:*
    - Autentikasi pengguna berdasarkan username dan password.
3. *Manajemen Data Nilai:*
    - *Tambah Data:* Input nilai tugas, UTS, dan UAS siswa.
    - *Edit Data:* Memperbarui data yang sudah ada.
    - *Hapus Data:* Menghapus data siswa yang dipilih.
    - *Load Data:* Menampilkan semua data yang tersimpan di database.

## Struktur Proyek
- *Register.java*  
  Mengelola proses registrasi pengguna baru ke database.

- *Login.java*  
  Mengelola proses autentikasi pengguna.

- *Home.java*  
  Menyediakan fitur utama aplikasi seperti pengelolaan data nilai siswa.

- *Koneksi.java*  
  Mengatur koneksi ke database MySQL.

## Validasi Input
- *Nama* dan *Mata Kuliah:* Hanya dapat berisi huruf.
- *Kelas:* Dapat berisi angka (1-10) atau huruf.
- *Nilai Tugas, UTS, dan UAS:* Harus berupa angka.

## Persyaratan Sistem
- Java Development Kit (JDK) 8 atau lebih baru.
- MySQL 5.7 atau lebih baru.
- Library JDBC untuk koneksi MySQL.

## Instalasi dan Konfigurasi
1. *Koneksi Database:*  
   Buat database bernama perhitungantugasakhir dengan tabel berikut:

   ```sql
   CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        name VARCHAR(50) NOT NULL,
        password VARCHAR(255) NOT NULL
   );

   CREATE TABLE rekap_nilai (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nama_siswa VARCHAR(100) NOT NULL,
       kelas VARCHAR(10) NOT NULL,
       mata_kuliah VARCHAR(50) NOT NULL,
       nilai_tugas DOUBLE NOT NULL,
       nilai_uts DOUBLE NOT NULL,
       nilai_uas DOUBLE NOT NULL,
       hasil_akhir DOUBLE NOT NULL,
       grade VARCHAR(2) NOT NULL
   );


2. *Konfigurasi Koneksi:*  
   Perbarui file Koneksi.java dengan username dan password MySQL Anda:
   ```java
   String url = "jdbc:mysql://localhost:3306/perhitungantugasakhir";
   String user = "root"; // Ganti dengan username MySQL Anda
   String password = ""; // Ganti dengan password MySQL Anda


3. *Jalankan Aplikasi:*  
   Compile dan jalankan file Register.java atau Login.java untuk memulai.

## Cara Penggunaan
1. Jalankan aplikasi.
2. Registrasi pengguna baru.
3. Login menggunakan akun yang telah dibuat.
4. Tambah, edit, atau hapus data nilai siswa.

## Kalkulasi Nilai Akhir
Nilai akhir dihitung dengan rumus:

Nilai Akhir = (Nilai Tugas * 30%) + (Nilai UTS * 30%) + (Nilai UAS * 40%)

*Grade* diberikan berdasarkan nilai akhir:
- *A:* ≥ 85
- *B:* ≥ 70
- *C:* ≥ 55
- *D:* ≥ 40
- *E:* < 40

## Lisensi
Proyek ini dibuat untuk tujuan pembelajaran dan bebas digunakan untuk pengembangan lebih lanjut.
