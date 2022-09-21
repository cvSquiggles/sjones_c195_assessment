package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentsQuery {

    public static ObservableList<Appointments> selectAppointmentsList() throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"), rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectAppointmentsListWeek(int weekOffSet) throws SQLException {
        String sql;
        if (weekOffSet >= 0) {
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_ADD(NOW(), INTERVAL (7 * ?) DAY)))";
        }
        else{
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_SUB(NOW(), INTERVAL (-7 * ?) DAY)))";
        }
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, weekOffSet);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"), rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectAppointmentsListMonth(int monthOffset) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE MONTH(Start) = (MONTH(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH))) AND " +
                "YEAR(Start) = (YEAR(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH)))";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, monthOffset);
        ps.setInt(2, monthOffset);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"), rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ResultSet select() throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID ";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        return rs;
    }


    public static ResultSet select(int appointmentID) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE a.Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, appointmentID);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet selectByClient(int client_id) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, cu.Customer_Name, u.User_Name, u.User_ID, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID  WHERE u.User_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, client_id);

        ResultSet rs = ps.executeQuery();

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
        ps.setInt(6, customerID);
        ps.setInt(7, userID);
        ps.setInt(8, contactID);

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
