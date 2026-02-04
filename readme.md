# Sistem Pembayaran SPP (Java Swing)

Project Java GUI sederhana untuk manajemen pembayaran SPP sekolah.

## Cara Menjalankan (NixOS/Linux)
1. Jalankan MariaDB server:
   `mariadbd --datadir=$PWD/data_db --socket=$PWD/mysql.sock --port=3306 &`
2. Compile:
   `javac -cp .:mysql.jar SistemSPP.java`
3. Run:
   `java -cp .:mysql.jar SistemSPP`
