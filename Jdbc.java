import java.sql.*;
import java.util.*;
public class Jdbc{
     static final String URL = "jdbc:mysql://localhost:3306/Edb";
     static final String USERNAME = "root";
     static final String PASS = "vijay";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USERNAME, PASS);
    }
    void empadd(String name, String job, float sal) {
        String que = "INSERT INTO emp (name, job, sal) VALUES ('" + name + "', '" + job + "', " + sal + ")";
        try (Connection connection = Jdbc.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            stmt.executeUpdate();
            System.out.println("Employee added successfully to db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void getemp(int eid) {
        String que = "SELECT * FROM emp WHERE eid = ?";
        try (Connection connection = Jdbc.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            stmt.setInt(1, eid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("eid: " + rs.getInt("eid"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("job: " + rs.getString("job"));
                System.out.println("Salary: " + rs.getFloat("salary"));
            } else {
                System.out.println("Employee not found in db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void empupd(int eid, String name, String job, float sal) {
        String que = "UPDATE emp SET name = '" + name + "', job = '" + job + "', sal = " + sal + " WHERE eid = " + eid;
        try (Connection connection = Jdbc.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee updated successfully in db");
            } else {
                System.out.println("Employee not found in db");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteEmployee(int eid) {
        String que = "DELETE FROM emp WHERE eid = " + eid;
        try (Connection connection = Jdbc.getConnection();
             PreparedStatement stmt = connection.prepareStatement(que)) {
            
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully from db");
            } else {
                System.out.println("Employee not found in db ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Jdbc crud = new Jdbc();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an operation:");
            System.out.println("1. Add Employee");
            System.out.println("2. Get Employee");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");

            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter job: ");
                    String job = sc.nextLine();
                    System.out.print("Enter sal: ");
                    float sal = sc.nextFloat();
                    crud.empadd(name, job, sal);
                    break;
                case 2:
                    System.out.print("Enter employee eid: ");
                    int eid = sc.nextInt();
                    crud.getemp(eid);
                    break;
                case 3:
                    System.out.print("Enter employee eid: ");
                    int updateeid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();
                    System.out.print("Enter new job: ");
                    String newjob = sc.nextLine();
                    System.out.print("Enter new sal: ");
                    float nSal = sc.nextFloat();
                    crud.empupd(updateeid, newName, newjob, nSal);
                    break;
                case 4:
                    System.out.print("Enter employee eid: ");
                    int deleteeid = sc.nextInt();
                    crud.deleteEmployee(deleteeid);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}