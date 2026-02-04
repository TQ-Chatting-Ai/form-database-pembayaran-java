import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.text.MessageFormat; // Import baru untuk fitur Cetak



public class SistemSPP extends JFrame {
    // Komponen
    JLabel lblJudul, lblId, lblNama, lblKelas, lblJurusan, lblBayar, lblJml;
    JTextField txtId, txtNama, txtKelas, txtJml;
    JComboBox cbJurusan, cbBayar;
    JButton btnSimpan, btnHapus, btnUbah, btnCetak, btnClear; // Tambah tombol Clear

    JTable tabel;
    JScrollPane scrollPane;
    DefaultTableModel model;

    // Database
    Connection conn;
    Statement stat;
    ResultSet rs;

    
    public SistemSPP() {
        setTitle("Sistem Pembayaran SPP");
        setSize(800, 650); // Agak diperbesar sedikit
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        Font fontJudul = new Font("Segoe UI", Font.BOLD, 18);
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 13);
        Font fontButton = new Font("Segoe UI", Font.BOLD, 12);

        Color bgForm = new Color(245, 247, 250);
        Color bgButton = new Color(52, 152, 219);
        Color fgButton = Color.WHITE;

        getContentPane().setBackground(bgForm);

        // --- GUI SETUP ---
        lblJudul = new JLabel("SISTEM PEMBAYARAN SPP UNIVERSITAS SIBER ASIA", JLabel.CENTER);
        lblJudul.setBounds(0, 20, 800, 30);
        lblJudul.setFont(fontJudul);
        lblJudul.setForeground(new Color(44, 62, 80));
        add(lblJudul);

        // Baris 1
        lblId = new JLabel("ID Siswa :");
        lblId.setBounds(50, 70, 100, 25);
        txtId = new JTextField();
        txtId.setBounds(130, 70, 150, 25);

        lblNama = new JLabel("Nama Siswa :");
        lblNama.setBounds(350, 70, 100, 25);
        txtNama = new JTextField();
        txtNama.setBounds(450, 70, 250, 25);

        // Baris 2
        lblKelas = new JLabel("Kelas :");
        lblKelas.setBounds(50, 110, 100, 25);
        txtKelas = new JTextField();
        txtKelas.setBounds(130, 110, 150, 25);

        lblJurusan = new JLabel("Jurusan :");
        lblJurusan.setBounds(350, 110, 100, 25);
        String[] jurusan = {
            "Pilih",
            "PJJ Sistem Informasi",
            "PJJ Informatika",
            "PJJ Manajemen",
            "PJJ Akuntansi",
            "PJJ Komunikasi"
        };
        cbJurusan = new JComboBox(jurusan);
        cbJurusan.setBounds(450, 110, 250, 25);

        // Baris 3
        lblBayar = new JLabel("Pembayaran :");
        lblBayar.setBounds(30, 150, 100, 25);
        String[] bulan = {"Pilih",
            "Cicilan 1",
            "Cicilan 2",
            "Cicilan 3",
        };
        cbBayar = new JComboBox(bulan);
        cbBayar.setBounds(130, 150, 150, 25);

        lblJml = new JLabel("Jumlah :");
        lblJml.setBounds(350, 150, 100, 25);
        txtJml = new JTextField();
        txtJml.setBounds(450, 150, 250, 25);

        JPanel panelForm = new JPanel(null);
        panelForm.setBounds(30, 60, 730, 240);
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(200,200,200)),
                "Data Siswa"
        ));
        add(panelForm);

        // Tombol
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(150, 200, 80, 25);
        panelForm.add(btnSimpan);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(270, 200, 80, 25);
        panelForm.add(btnHapus);

        btnUbah = new JButton("Ubah");
        btnUbah.setBounds(390, 200, 80, 25);
        panelForm.add(btnUbah);

        btnCetak = new JButton("Cetak");
        btnCetak.setBounds(520, 200, 80, 25);
        panelForm.add(btnCetak);

        // Tabel
        String[] header = {"ID Siswa", "Nama", "Kelas", "Jurusan", "Pembayaran", "Jumlah"};
        model = new DefaultTableModel(header, 0);
        tabel = new JTable(model);
        scrollPane = new JScrollPane(tabel);
        scrollPane.setBounds(50, 300, 700, 300);
        add(scrollPane);

        
        tabel.setRowHeight(24);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tabel.getTableHeader().setBackground(new Color(230, 230, 230));

        
        // SET PANEL
        panelForm.add(lblId);
        panelForm.add(txtId);
        panelForm.add(lblNama);
        panelForm.add(txtNama);
        panelForm.add(lblKelas);
        panelForm.add(txtKelas);
        panelForm.add(lblJurusan);
        panelForm.add(cbJurusan);
        panelForm.add(lblBayar);
        panelForm.add(cbBayar);
        panelForm.add(lblJml);
        panelForm.add(txtJml);


        // SET FONT
        lblId.setFont(fontLabel);
        txtId.setFont(fontField);
        lblNama.setFont(fontLabel);
        txtNama.setFont(fontField);
        cbJurusan.setFont(fontField);
        cbBayar.setFont(fontField);

        // --- KONEKSI & LOAD DATA ---
        koneksiDatabase();
        tampilData();

        // --- EVENT LISTENER (AKSI) ---
        
        // 1. Tombol Simpan
        btnSimpan.addActionListener(e -> simpanData());

        // 2. Tombol Hapus (BARU)
        btnHapus.addActionListener(e -> hapusData());

        // 3. Tombol Ubah (BARU)
        btnUbah.addActionListener(e -> ubahData());

        // 4. Tombol Cetak (BARU)
        btnCetak.addActionListener(e -> cetakLaporan());

        // 5. Klik Tabel (Agar data masuk ke form saat diklik)
        tabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int baris = tabel.getSelectedRow();
                // Ambil data dari tabel, taruh ke textfield
                txtId.setText(model.getValueAt(baris, 0).toString());
                txtNama.setText(model.getValueAt(baris, 1).toString());
                txtKelas.setText(model.getValueAt(baris, 2).toString());
                cbJurusan.setSelectedItem(model.getValueAt(baris, 3).toString());
                cbBayar.setSelectedItem(model.getValueAt(baris, 4).toString());
                txtJml.setText(model.getValueAt(baris, 5).toString());
            }
        });
    }

    // --- DATABASE ---

    public void koneksiDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/dbProjectSiswa", "root", "");
            stat = conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Error: " + e.getMessage());
        }
    }

    public void tampilData() {
        model.setRowCount(0);
        try {
            rs = stat.executeQuery("SELECT * FROM pembayaran_spp");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("id_siswa"),
                    rs.getString("nama_siswa"),
                    rs.getString("kelas"),
                    rs.getString("jurusan"),
                    rs.getString("pembayaran"),
                    rs.getString("jumlah")
                });
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void kosongkanForm() {
        txtId.setText("");
        txtNama.setText("");
        txtKelas.setText("");
        cbJurusan.setSelectedIndex(0);
        cbBayar.setSelectedIndex(0);
        txtJml.setText("");
        txtId.requestFocus();
    }

    public void simpanData() {
        try {
            String sql = "INSERT INTO pembayaran_spp VALUES ('" +
                txtId.getText() + "','" +
                txtNama.getText() + "','" +
                txtKelas.getText() + "','" +
                cbJurusan.getSelectedItem() + "','" +
                cbBayar.getSelectedItem() + "','" +
                txtJml.getText() + "')";
            
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Tersimpan!");
            tampilData();
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Simpan: " + e.getMessage());
        }
    }

    // --- LOGIKA BUTTON (HAPUS, EDIT, SAVE, dan CETAK) ---

    public void hapusData() {
        int jawab = JOptionPane.showConfirmDialog(null, "Yakin mau hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
            try {
                // Hapus berdasarkan ID Siswa (Primary Key)
                String sql = "DELETE FROM pembayaran_spp WHERE id_siswa='" + txtId.getText() + "'";
                stat.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Data Terhapus!");
                tampilData();
                kosongkanForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal Hapus: " + e.getMessage());
            }
        }
    }

    public void ubahData() {
        try {
            // Update semua kolom KECUALI id_siswa (karena ID biasanya tidak boleh berubah)
            // KONDISINYA: WHERE id_siswa = txtId
            String sql = "UPDATE pembayaran_spp SET " +
                    "nama_siswa='" + txtNama.getText() + "'," +
                    "kelas='" + txtKelas.getText() + "'," +
                    "jurusan='" + cbJurusan.getSelectedItem() + "'," +
                    "pembayaran='" + cbBayar.getSelectedItem() + "'," +
                    "jumlah='" + txtJml.getText() + "' " +
                    "WHERE id_siswa='" + txtId.getText() + "'";
            
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah!");
            tampilData();
            kosongkanForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Ubah: " + e.getMessage());
        }
    }

    public void cetakLaporan() {
        try {
            // Fitur bawaan JTable untuk mencetak ke Printer/PDF
            // Header laporan: "Laporan Pembayaran SPP"
            MessageFormat header = new MessageFormat("Laporan Pembayaran SPP SMP Jakenan");
            MessageFormat footer = new MessageFormat("Halaman {0,number,integer}");
            
            tabel.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Cetak: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        new SistemSPP().setVisible(true);
    }
}
