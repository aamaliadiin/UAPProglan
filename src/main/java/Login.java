import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    private JFrame frame;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private Connection connection;

    public Login() {
        // Membuat koneksi ke database
        try {
            Koneksi koneksi = new Koneksi();
            connection = koneksi.getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat koneksi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Inisialisasi GUI
        frame = new JFrame("Login Aplikasi");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Komponen login
        JLabel nameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 25));
        addLetterOnlyFilter(txtName);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(txtName, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passwordLabel, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(txtPassword, gbc);

        loginButton = new JButton("Login");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(loginButton, gbc);

        // Listener tombol login
        loginButton.addActionListener(e -> {
            String username = txtName.getText();
            String password = new String(txtPassword.getPassword());

            if (authenticate(username, password)) {
                JOptionPane.showMessageDialog(frame, "Login berhasil!");

                // Membuka halaman Home
                frame.dispose();
                new Home();
            } else {
                JOptionPane.showMessageDialog(frame, "Login gagal. Cek username dan password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    boolean authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Terjadi kesalahan saat memverifikasi login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void addLetterOnlyFilter(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-Z]*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-Z]*")) {
                    super.insertString(fb, offset, text, attrs);
                }
            }
        });
    }

    public static void main(String[] args) {
        new Login();
    }
}
