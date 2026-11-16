import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import  java.sql.Statement;
import java.sql.ResultSet;
import  java.sql.PreparedStatement;

public class student {
    public static void main(String[] args) {
        // JDBC & Database credentials
        String url = "jdbc:postgresql://localhost:5432/studentdb";
        String user = "postgres";
        String password = "password";
        try { // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");
            // Connect to the database
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Connected to PostgreSQL successfully!");
                
                // Retreve all students data
                getAllStudents(conn); 
                
                // Adds a new student to the database
                //addStudent(conn, "Bob", "Bobby", "bob@gmail.com", "2026-01-01");
                //getAllStudents(conn);
                
                // Updates the newly created students email
                //updateStudentEmail(conn, 4, "bobby@newmail.com");
                //getAllStudents(conn);
                
                // Deletes the newly created student
                //deleteStudent(conn, 4);
                //getAllStudents(conn);

            } else {
                System.out.println("Failed to establish connection.");
            } // Close the connection (in a real scenario, do this in a finally
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void getAllStudents(Connection conn) throws SQLException{
        Statement stmt = conn.createStatement();
        String SQL = "SELECT * FROM students";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){ // loops through results and prints out data of each student
            System.out.println(rs.getInt("student_id") + " " + rs.getString("first_name") + " " + rs.getString("last_name") + " " + rs.getString("email") + " " + rs.getString("enrollment_date"));
        }
        rs.close();
        stmt.close();
    }
    public static void addStudent(Connection conn, String first_name, String last_name, String email, String enrollment_date) throws SQLException{
        String insertSQL = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(insertSQL); // inserts new student data which was entered in main()
        pstmt.setString(1, first_name);
        pstmt.setString(2, last_name);
        pstmt.setString(3, email);
        pstmt.setDate(4, java.sql.Date.valueOf(enrollment_date));
        pstmt.executeUpdate();
        System.out.println("Student added");
        pstmt.close();
    }
    public static void updateStudentEmail(Connection conn, int student_id, String new_email) throws SQLException{
        String updateSQL = "UPDATE students SET email = ? WHERE student_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateSQL); // updates student email which was entered in main()
        pstmt.setString(1, new_email);
        pstmt.setInt(2, student_id);
        pstmt.executeUpdate();
        System.out.println("Student email updated");
        pstmt.close();
    }
    public static void deleteStudent(Connection conn, int student_id) throws SQLException{
        String deleteSQL = "DELETE FROM students WHERE student_id = ?"; // deletes a student using a id which was entered in main()
        PreparedStatement pstmt = conn.prepareStatement(deleteSQL);
        pstmt.setInt(1, student_id);
        pstmt.executeUpdate();
        System.out.println("Student deleted");
        pstmt.close();
    }
}

