import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.*;

public class HomeTest {

    private Connection getTestConnection() throws SQLException {
        // Set up a test connection (assuming a test database is available)
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/perhitungantugasakhir", "root", "");
    }

    @Test
    public void testCreateData() {
        try (Connection conn = getTestConnection()) {
            Home home = new Home();
            home.setConnection(conn);
            home.createData("John Doe", "10A", "Math", 90, 85, 88);

            String selectQuery = "SELECT * FROM rekap_nilai WHERE nama_siswa = 'John Doe'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
                assertTrue(rs.next(), "Data should be inserted.");
                assertEquals("John Doe", rs.getString("nama_siswa"), "Nama should match.");
            }
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateData() {
        try (Connection conn = getTestConnection()) {
            Home home = new Home();
            home.setConnection(conn);

            // First, create data
            home.createData("Jane Doe", "10B", "Science", 85, 88, 90);

            // Now, update data
            String selectQuery = "SELECT id FROM rekap_nilai WHERE nama_siswa = 'Jane Doe'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
                assertTrue(rs.next());
                int id = rs.getInt("id");
                home.updateData(id, "Jane Doe", "10B", "Science", 95, 90, 92);

                // Verify that data has been updated
                String verifyQuery = "SELECT * FROM rekap_nilai WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(verifyQuery)) {
                    pstmt.setInt(1, id);
                    ResultSet rs2 = pstmt.executeQuery();
                    assertTrue(rs2.next());
                    assertEquals(95, rs2.getDouble("nilai_tugas"), "Nilai Tugas should be updated.");
                }
            }
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteData() {
        try (Connection conn = getTestConnection()) {
            Home home = new Home();
            home.setConnection(conn);

            // First, create data
            home.createData("Mark Smith", "10C", "English", 80, 75, 70);

            // Now, delete data
            String selectQuery = "SELECT id FROM rekap_nilai WHERE nama_siswa = 'Mark Smith'";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectQuery)) {
                assertTrue(rs.next());
                int id = rs.getInt("id");
                home.deleteData(id);

                // Verify that data is deleted
                String verifyQuery = "SELECT * FROM rekap_nilai WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(verifyQuery)) {
                    pstmt.setInt(1, id);
                    ResultSet rs2 = pstmt.executeQuery();
                    assertFalse(rs2.next(), "Data should be deleted.");
                }
            }
        } catch (SQLException e) {
            fail("SQL exception occurred: " + e.getMessage());
        }
    }
}
