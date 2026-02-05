# Java Project — Sistem Pembayaran SPP
Project Java GUI sederhana untuk manajemen pembayaran SPP sekolah.

Dependency yang harus kita punya untuk bisa menjalankan project ini : [MariaDB](https://mariadb.com/downloads/community/community-server/), [java developmen kit(jdk)](https://www.oracle.com/java/technologies/downloads/)

<!-- SETUP DATABASE -->
<details close>
   <summary><h2>Langkah 1: Setup MariaDB</h2></summary>
   
   ---
   
   1. Membuat folder MariaDB `data_db` di dalam PATH project
        ```bash
        mkdir data_db
        mariadb-install-db --datadir=$PWD/data_db\
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
</details>

<!-- COMPILE PROJECT -->
<details open>
  <summary><h2>Langkah 3: Program Java Siap dijalankan</h2></summary>
  
  - Jalankan perintah ini untuk memunculkan GUI
     ```bash
     java -cp .:mysql.jar SistemSPP.java
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
