Membuat "Fat JAR" (menggabungkan kode kamu dan library `mysql.jar` menjadi satu file tunggal) secara manual tanpa IDE seperti NetBeans atau Maven memang sedikit menantang, tapi sangat bagus untuk melatih insting teknismu.

Karena kamu tidak menggunakan alat otomatis, kita akan menggunakan teknik **unzip dan re-zip**.

### Langkah-langkah Membuat Fat JAR Manual

1. **Ekstrak (Unzip) `mysql.jar**`
Kita perlu mengambil isi dari library MySQL agar bisa kita bungkus ulang bersama kode kita.
```bash
mkdir temp_jar
cd temp_jar
jar xf ../mysql.jar

```


2. **Salin File Class Kamu ke Folder Tersebut**
Masukkan file hasil compile kamu ke dalam folder yang sama.
```bash
cp ../SistemSPP.class .
cp ../'SistemSPP$1.class' .

```


3. **Buat Manifest Sederhana**
Karena library sudah ada di dalam, kita tidak butuh `Class-Path` lagi di manifest, cukup `Main-Class`.
```bash
echo "Main-Class: SistemSPP" > manifest.txt

```


4. **Bungkus Semuanya Menjadi Satu**
Sekarang kita satukan semua isi folder `temp_jar` menjadi satu file JAR baru.
```bash
jar cvfm ../SistemSPP_Final.jar manifest.txt .

```


5. **Bersihkan Folder Sementara**
```bash
cd ..
rm -rf temp_jar

```



---

### Hasil Akhir

Sekarang kamu punya satu file bernama **`SistemSPP_Final.jar`**. File ini sudah mengandung driver MySQL di dalamnya.

**Cara Menjalankannya:**
Kamu tinggal membawa file satu ini saja ke komputer mana pun (yang sudah ada Java-nya) dengan perintah:

```bash
java -jar SistemSPP_Final.jar

```

---

### Catatan Penting untuk Database

Meskipun file `.jar` sudah jadi satu, **Database-nya tetap terpisah**. Jadi, saat kamu mengumpulkan tugas:

1. **File JAR**: Ini aplikasinya.
2. **File `.sql**`: Ini struktur datanya.
* Ingat bahwa di komputer gurumu nanti, dia harus menyalakan MySQL/MariaDB dan meng-import file `pembayaran_spp.sql` kamu agar aplikasinya bisa "bicara" dengan data.
* Karena kamu di NixOS menggunakan socket lokal (`mysql.sock`), pastikan di kode Java bagian `getConnection` menggunakan `localhost:3306` agar lebih universal di komputer lain.



**Apakah kamu ingin saya buatkan file `pembayaran_spp.sql` yang rapi (berisi perintah `CREATE DATABASE` dan `TABLE`) agar gurumu tinggal sekali klik untuk setup databasenya?**
