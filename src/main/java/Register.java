import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register {
    private JFrame frame;
    private JTextField txtUsername;
    private JTextField txtName;
    private JPasswordField txtPassword;
    private JButton registerButton;
    private Connection connection;

    public Register() {
        // Membuat koneksi ke database
        try {
            Koneksi koneksi = new Koneksi();
            connection = koneksi.getConnection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal membuat koneksi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // Inisialisasi GUI
        frame = new JFrame("Register Pengguna");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(usernameLabel, gbc);

        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(200, 25));
        addLetterOnlyFilter(txtUsername);
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(txtUsername, gbc);

        JLabel nameLabel = new JLabel("Nama:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(nameLabel, gbc);

        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(200, 25));
        addLetterOnlyFilter(txtName);
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(txtName, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);

        txtPassword = new JPasswordField();
        txtPassword.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(txtPassword, gbc);

        registerButton = new JButton("Register");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            String username = txtUsername.getText();
            String name = txtName.getText();
            String password = new String(txtPassword.getPassword());

            if (username.isEmpty() || name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                registerUser(username, name, password);
            }
        });

        frame.setVisible(true);
    }

    void registerUser(String username, String name, String password) {
        String sql = "INSERT INTO users (username, name, password) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, name);
            statement.setString(3, password);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Registrasi berhasil!");
                frame.dispose();
                new Login();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Gagal registrasi: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
        new Register();
    }
}
