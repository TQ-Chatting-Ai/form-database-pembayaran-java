Membuat "Fat JAR" (menggabungkan kode dan library `mysql.jar` menjadi satu file tunggal).
```sql
SistemSPP_Final.jar
```

### Langkah-langkah Membuat Fat JAR Manual

```bash
mkdir temp_jar
cd temp_jar
jar xf ../mysql.jar

```

```bash
cp ../SistemSPP.class .
cp ../'SistemSPP$1.class' .

```

```bash
echo "Main-Class: SistemSPP" > manifest.txt

```

```bash
jar cvfm ../SistemSPP_Final.jar manifest.txt .

```

```bash
cd ..
rm -rf temp_jar

```



---

### Hasil Akhir

Sekarang satu file baru bernama **`SistemSPP_Final.jar`** sudah mengandung driver MySQL di dalamnya.

```bash
# jalankan
java -jar SistemSPP_Final.jar

```
