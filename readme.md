# Sistem Pembayaran SPP (Java Swing)

Project Java GUI sederhana untuk manajemen pembayaran SPP sekolah.

<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a64bbbe0-4ad8-431f-8cf3-b98748b2ca48" />


## Cara Menjalankan (NixOS/Linux)
1. Jalankan MariaDB server:
   `mariadbd --datadir=$PWD/data_db --socket=$PWD/mysql.sock --port=3306 &`
2. Compile:
   `javac -cp .:mysql.jar SistemSPP.java`
3. Run:
   `java -cp .:mysql.jar SistemSPP`
