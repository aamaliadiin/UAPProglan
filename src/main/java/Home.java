import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Home {
    private JFrame frame;
    private JTable table;
    DefaultTableModel tableModel;
    private Connection connection;

    // Getter and Setter for connection
    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void login(String username, String password) {
        // Implement the logic for login here, using username and password
        if ("amalia".equals(username) && "amalia123".equals(password)) {
            // Set up the connection for authenticated user
        } else {
            System.out.println("Invalid login");
        }
    }

    public Home() {
        // Membuat koneksi ke database
        try {
            Koneksi koneksi = new Koneksi();
            connection = koneksi.getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat koneksi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Inisialisasi GUI
        frame = new JFrame("APLIKASI PERHITUNGAN TUGAS AKHIR");
        frame.setSize(900, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Tabel data
        tableModel = new DefaultTableModel(new Object[][]{}, new String[]{
                "No.", "Nama Siswa", "Kelas", "Mata Kuliah", "Nilai Tugas", "Nilai UTS", "Nilai UAS", "Hasil Akhir", "Grade"
        });
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel tombol
        JPanel buttonPanel = new JPanel();

        JButton loadButton = new JButton("Load Data");
        JButton addButton = new JButton("Tambah Data");
        JButton updateButton = new JButton("Update Data");
        JButton deleteButton = new JButton("Hapus Data");

        buttonPanel.add(loadButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Listener tombol
        loadButton.addActionListener(e -> loadData());

        addButton.addActionListener(e -> {
            JTextField txtNama = new JTextField();
            JTextField txtKelas = new JTextField();
            JTextField txtMataKuliah = new JTextField();
            JTextField txtNilaiTugas = new JTextField();
            JTextField txtNilaiUTS = new JTextField();
            JTextField txtNilaiUAS = new JTextField();

            Object[] message = {
                    "Nama:", txtNama,
                    "Kelas:", txtKelas,
                    "Mata Kuliah:", txtMataKuliah,
                    "Nilai Tugas:", txtNilaiTugas,
                    "Nilai UTS:", txtNilaiUTS,
                    "Nilai UAS:", txtNilaiUAS
            };

            int option = JOptionPane.showConfirmDialog(null, message, "Tambah Data", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Validasi input "Nama" dan "Mata Kuliah" hanya huruf
                if (!txtNama.getText().matches("[a-zA-Z ]+")) {
                    JOptionPane.showMessageDialog(null, "Nama hanya boleh berisi huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Keluar jika validasi gagal
                }
                if (!txtMataKuliah.getText().matches("[a-zA-Z ]+")) {
                    JOptionPane.showMessageDialog(null, "Mata Kuliah hanya boleh berisi huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Keluar jika validasi gagal
                }
                // Validasi input "Kelas" hanya angka 1-10 atau huruf
                if (!txtKelas.getText().matches("([1-9]|10|[a-zA-Z]+)")) {
                    JOptionPane.showMessageDialog(null, "Kelas hanya boleh berisi angka 1-10 atau huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Keluar jika validasi gagal
                }

                try {
                    createData(
                            txtNama.getText(),
                            txtKelas.getText(),
                            txtMataKuliah.getText(),
                            Double.parseDouble(txtNilaiTugas.getText()),
                            Double.parseDouble(txtNilaiUTS.getText()),
                            Double.parseDouble(txtNilaiUAS.getText())
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Input nilai harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);

                JTextField txtNama = new JTextField((String) tableModel.getValueAt(selectedRow, 1));
                JTextField txtKelas = new JTextField((String) tableModel.getValueAt(selectedRow, 2));
                JTextField txtMataKuliah = new JTextField((String) tableModel.getValueAt(selectedRow, 3));
                JTextField txtNilaiTugas = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
                JTextField txtNilaiUTS = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
                JTextField txtNilaiUAS = new JTextField(String.valueOf(tableModel.getValueAt(selectedRow, 6)));

                Object[] message = {
                        "Nama:", txtNama,
                        "Kelas:", txtKelas,
                        "Mata Kuliah:", txtMataKuliah,
                        "Nilai Tugas:", txtNilaiTugas,
                        "Nilai UTS:", txtNilaiUTS,
                        "Nilai UAS:", txtNilaiUAS
                };

                int option = JOptionPane.showConfirmDialog(null, message, "Update Data", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    // Validasi input "Nama" dan "Mata Kuliah" hanya huruf
                    if (!txtNama.getText().matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(null, "Nama hanya boleh berisi huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Keluar jika validasi gagal
                    }
                    if (!txtMataKuliah.getText().matches("[a-zA-Z ]+")) {
                        JOptionPane.showMessageDialog(null, "Mata Kuliah hanya boleh berisi huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Keluar jika validasi gagal
                    }
                    // Validasi input "Kelas" hanya angka 1-10 atau huruf
                    if (!txtKelas.getText().matches("([1-9]|10|[a-zA-Z]+)")) {
                        JOptionPane.showMessageDialog(null, "Kelas hanya boleh berisi angka 1-10 atau huruf!", "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Keluar jika validasi gagal
                    }

                    try {
                        updateData(
                                id,
                                txtNama.getText(),
                                txtKelas.getText(),
                                txtMataKuliah.getText(),
                                Double.parseDouble(txtNilaiTugas.getText()),
                                Double.parseDouble(txtNilaiUTS.getText()),
                                Double.parseDouble(txtNilaiUAS.getText())
                        );
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Input nilai harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin diperbarui!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                deleteData(id);
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public void loadData() {
        String sql = "SELECT * FROM rekap_nilai";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            tableModel.setRowCount(0); // Kosongkan tabel
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("nama_siswa"),
                        rs.getString("kelas"),
                        rs.getString("mata_kuliah"),
                        rs.getDouble("nilai_tugas"),
                        rs.getDouble("nilai_uts"),
                        rs.getDouble("nilai_uas"),
                        rs.getDouble("hasil_akhir"),
                        rs.getString("grade")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memuat data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createData(String nama, String kelas, String mataKuliah, double nilaiTugas, double nilaiUTS, double nilaiUAS) {
        String sql = "INSERT INTO rekap_nilai (nama_siswa, kelas, mata_kuliah, nilai_tugas, nilai_uts, nilai_uas, hasil_akhir, grade) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            double nilaiAkhir = (nilaiTugas * 0.3) + (nilaiUTS * 0.3) + (nilaiUAS * 0.4);
            String grade = calculateGrade(nilaiAkhir);

            statement.setString(1, nama);
            statement.setString(2, kelas);
            statement.setString(3, mataKuliah);
            statement.setDouble(4, nilaiTugas);
            statement.setDouble(5, nilaiUTS);
            statement.setDouble(6, nilaiUAS);
            statement.setDouble(7, nilaiAkhir);
            statement.setString(8, grade);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke database!");
                loadData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData(int id, String nama, String kelas, String mataKuliah, double nilaiTugas, double nilaiUTS, double nilaiUAS) {
        String sql = "UPDATE rekap_nilai SET nama_siswa = ?, kelas = ?, mata_kuliah = ?, nilai_tugas = ?, nilai_uts = ?, nilai_uas = ?, hasil_akhir = ?, grade = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            double nilaiAkhir = (nilaiTugas * 0.3) + (nilaiUTS * 0.3) + (nilaiUAS * 0.4);
            String grade = calculateGrade(nilaiAkhir);

            statement.setString(1, nama);
            statement.setString(2, kelas);
            statement.setString(3, mataKuliah);
            statement.setDouble(4, nilaiTugas);
            statement.setDouble(5, nilaiUTS);
            statement.setDouble(6, nilaiUAS);
            statement.setDouble(7, nilaiAkhir);
            statement.setString(8, grade);
            statement.setInt(9, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
                loadData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal memperbarui data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteData(int id) {
        String sql = "DELETE FROM rekap_nilai WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                loadData();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String calculateGrade(double nilaiAkhir) {
        if (nilaiAkhir >= 85) return "A";
        if (nilaiAkhir >= 70) return "B";
        if (nilaiAkhir >= 55) return "C";
        if (nilaiAkhir >= 40) return "D";
        return "E";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }
}
