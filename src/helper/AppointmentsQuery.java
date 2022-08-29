package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentsQuery {

    public static ResultSet select(int appointmentID) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        /*while(rs.next()){
            appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp start = rs.getTimestamp("Start");
            Timestamp end = rs.getTimestamp("End");
            Timestamp created = rs.getTimestamp("Create_Date");
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            String customer = rs.getString("Customer_Name");
            String userName = rs.getString("User_Name");
            String contactName = rs.getString("Contact_Name");
        }*/

        return rs;
    }

    public static int insert(String title, String description, String location, String type, String createdBy,
                             int customerID, int userID, int contactID) throws SQLException{
        String sql = "INSERT INTO appointments (Title, Description," +
                "Location, Type, Create_Date ,Created_By, Last_Update ,Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, current_timestamp(), ?, current_timestamp(), ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, createdBy);
        ps.setInt(6, 1);
        ps.setInt(7, 1);
        ps.setInt(8, 1);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String title, String description, String location, String type, int customerID, int userID, int contactID, int appointmentID) throws SQLException{
        String sql = "UPDATE appointments SET Title = ?, Description = ?," +
                "Location = ?, Type = ?, Last_Update = current_timestamp(), Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setInt(5, 1);
        ps.setInt(6, 1);
        ps.setInt(7, 1);
        ps.setInt(8, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, appointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

}
