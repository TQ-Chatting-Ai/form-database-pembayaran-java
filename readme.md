# Java Project — Sistem Pembayaran SPP
Project Java GUI sederhana untuk manajemen pembayaran SPP sekolah.

Dependency yang harus kita punya untuk bisa menjalankan project ini : 
   [MariaDB](https://mariadb.org), 
   [java developmen kit (jdk)](https://openjdk.org/install/)

<!-- SETUP DATABASE -->
<details close>
   <summary><h2>Langkah 1: Setup MariaDB</h2></summary>
   
   ---
   
   1. Membuat folder MariaDB `data_db` di dalam PATH project
        ```bash
        mkdir data_db
        mariadb-install-db --datadir=$PWD/data_db
        ```
        
   2. Jalankan Server
        ```bash
         mariadbd \
           --datadir=$PWD/data_db \
           --socket=$PWD/mysql.sock \
           --port=3306 \
           --skip-grant-tables &
        ```
        > Tekan **Enter** jika muncul *"ready for connections"*
     
   3. Konfigurasi User MariaDB

      karena Project hanya percobaan, setting ke "Skip grant access"
      Perintah untuk Masuk ke environtment SQL:
        ```bash
         mariadb --socket=$PWD/mysql.sock
         # Prompt akan berubah menjadi:
         # MariaDB [(none)]>
        ```
      lalu Jalankan Perintah berikut:
        ```sql
         FLUSH PRIVILEGES;
         ALTER USER 'root'@'localhost'
         IDENTIFIED VIA mysql_native_password
         USING PASSWORD('');
         FLUSH PRIVILEGES;
         exit
        ```
        
   4. Restart Ulang MariaDB
      ```bash
      pkill mariadb
      ```
      ```bash
      mariadbd \
      --datadir=$PWD/data_db \
      --socket=$PWD/mysql.sock \
      --port=3306 &
      ```
      > Tekan 'ENTER'
      > 
      > Setelah itu, tidak akan perlu password lagi untuk akses kembali kedalam MariaDB-nya
</details>

<!-- SETUP DATABASE TABLE -->
<details close>
   <summary><h2>Langkah 2: Buat Table Database</h2></summary>

   ---

   1. Masuk kembali ke MariaDB
      ```bash
      mariadb --socket=$PWD/mysql.sock
      ```

   2. Setup Database

      Jalankan:
      ```sql
      CREATE DATABASE dbProjectSiswa;
      USE dbProjectSiswa;
      
      CREATE TABLE pembayaran_spp (
          id_siswa VARCHAR(20) PRIMARY KEY,
          nama_siswa VARCHAR(100),
          kelas VARCHAR(20),
          jurusan VARCHAR(50),
          pembayaran VARCHAR(50),
          jumlah DOUBLE
      );
      ```
   4. Keluar environtmen
      ```sql
      exit
      ```
      selesai
</details>


<!-- COMPILE PROJECT KE .jar -->
<details close>
   <summary><h2>Langkah 3: Compile .JAR</h2></summary>

   ---

   1. BUat file `manifest.txt`
      
      isi dengan :
      ```txt
      Main-Class: SistemSPP
      Class-Path: mysql.jar

      # pastikan ada newline kosong di paling bawah kodenya
      ```

   2. buat file .class

      ```bash
      javac SistemSPP.java
      ```
      > perintah ini akan mengenerate file SistemSPP.class & SistemSPP$1.class
      
   3. Bungkus Kedalam .jar

      ```bash
      jar cvfm SistemSPP.jar manifest.txt SistemSPP.class 'SistemSPP$1.class'
      ```

      selesai
</details>

<!-- COMPILE PROJECT -->
<details open>
  <summary><h2>Langkah 4: Program Java Siap dijalankan</h2></summary>
  
  - Jalankan perintah ini untuk memunculkan GUI
     ```bash
     # Windows
     java -cp .;mysql.jar SistemSPP.java
     
     # Linux
     java -cp .:mysql.jar SistemSPP.java
     ```
     or
     ```bash
     java -jar SistemSPP.jar
     ```

     <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/698dc6cd-2c0c-4a5f-9fd6-8693102c5db5" />

     ---
     
     <details open>
       <summary><h4>Struktur Akhir Projects</h4></summary>
       
       ```bash
       form-database-pembayaran/
             ├── data_db
             │   ├── aria_log.00000001
             │   └── ...
             ├── mysql.jar
             ├── mysql.sock
             ├── pembayaran_spp.sql
             └── SistemSPP.java
       
       7 directories, 216 files
       ```
     </details>
</details>

--- 

# Tips 

- Monitoring Data (Console)
   ```bash
   mariadb -u root \
   --socket=$PWD/mysql.sock \
   -t \
   -e "SELECT * FROM dbProjectSiswa.pembayaran_spp;"
   ```
   
- Auto refresh dengan `watch`
   ```bash
   watch -n 2 \
   'mariadb -u root --socket=$PWD/mysql.sock -t -e "SELECT * FROM dbProjectSiswa.pembayaran_spp;"'
   ```
