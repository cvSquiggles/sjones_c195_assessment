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
                "a.Start as Start, a.End as End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID" +
                "ORDER BY Start ASC";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"),
                    rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Customer_Name"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectAppointmentsListWeek(int weekOffSet, String timeZone) throws SQLException {
        String sql;
        if (weekOffSet >= 0) {
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update," +
                    " a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_ADD(NOW(), INTERVAL (7 * ?) DAY)))" +
                    "ORDER BY Start ASC";
        }
        else{
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                    "a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_SUB(NOW(), INTERVAL (-7 * ?) DAY)))" +
                    "ORDER BY Start ASC";
        }
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, weekOffSet);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"),
                    rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Customer_Name"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectContactAppointmentsListWeek(int weekOffSet, int contactID, String timeZone) throws SQLException {
        String sql;
        if (weekOffSet >= 0) {
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update," +
                    " a.Last_Updated_By, a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_ADD(NOW(), INTERVAL (7 * ?) DAY)))" +
                    "AND a.Contact_ID = ? " +
                    "ORDER BY Start ASC";
        }
        else{
            sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                    "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                    "a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                    "FROM appointments as a " +
                    "JOIN Users as u on a.User_ID = u.User_ID " +
                    "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                    "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                    "WHERE YEARWEEK(Start) = (YEARWEEK(DATE_SUB(NOW(), INTERVAL (-7 * ?) DAY)))" +
                    "AND a.Contact_ID = ? " +
                    "ORDER BY Start ASC";
        }
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, weekOffSet);
        ps.setInt(4, contactID);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"),
                    rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Customer_Name"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectAppointmentsListMonth(int monthOffset, String timeZone) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                "a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE MONTH(Start) = (MONTH(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH))) AND " +
                "YEAR(Start) = (YEAR(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH))) " +
                "ORDER BY Start ASC";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, monthOffset);
        ps.setInt(4, monthOffset);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"),
                    rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Customer_Name"));

            appointmentList.add(appointmentValue);
        }

        return appointmentList;
    }

    public static ObservableList<Appointments> selectContactAppointmentsListMonth(int monthOffset, int contactID, String timeZone) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                "a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE MONTH(Start) = (MONTH(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH))) AND " +
                "YEAR(Start) = (YEAR(DATE_ADD(NOW(), INTERVAL (1 * ?) MONTH))) AND " +
                "a.Contact_ID = ? " +
                "ORDER BY Start ASC";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, monthOffset);
        ps.setInt(4, monthOffset);
        ps.setInt(5, contactID);

        ResultSet rs = ps.executeQuery();

        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList();

        while(rs.next()){
            Appointments appointmentValue = new Appointments(rs.getInt("Appointment_ID"), rs.getString("Title"),
                    rs.getString("Description"), rs.getString("Location"), rs.getString("Contact_Name"), rs.getString("Type"),
                    rs.getString("Created_By"), rs.getString("Start"), rs.getString("End"),
                    rs.getInt("Customer_ID") ,rs.getInt("User_ID"), rs.getInt("Contact_ID"), rs.getString("Customer_Name"));

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
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID";
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

    public static ResultSet selectByCustomer(int customer_id, String timeZone) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, cu.Customer_Name, u.User_Name, u.User_ID, co.Contact_Name, cu.Customer_ID, co.Contact_ID " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID  WHERE a.Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, customer_id);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet selectTotals() throws SQLException {
        ObservableList<Appointments> appmtList = FXCollections.observableArrayList();
        String sql = "SELECT Type, INSERT(EXTRACT(YEAR_MONTH FROM Start), 5, 0, '-') AS yearMonth, count(EXTRACT(YEAR_MONTH FROM Start)) as Total" +
                " FROM appointments Group by yearMonth, Type";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static int insert(String title, String description, String location, String type, String start, String end, String timeZoneOffset,String createdBy,
                             int customerID, int userID, int contactID) throws SQLException{
        String sql = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date," +
                " Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)" +
                " VALUES( ?, ?, ?, ?, convert_tz(?, ?, '+00:00')," +
                " CONVERT_TZ(?, ?, '+00:00'), current_timestamp(), ?, current_timestamp(), ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, start);
        ps.setString(6, timeZoneOffset);
        ps.setString(7, end);
        ps.setString(8, timeZoneOffset);
        ps.setString(9, createdBy);
        ps.setString(10, createdBy);
        ps.setInt(11, customerID);
        ps.setInt(12, userID);
        ps.setInt(13, contactID);


        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int update(String title, String description, String location, String type, int customerID,
                             String userName, int contactID, int appointmentID, int assignedUserID, String start, String end, String userTZ) throws SQLException{
        String sql = "UPDATE appointments SET Title = ?, Description = ?," +
                "Location = ?, Type = ?, Last_Update = current_timestamp(), Last_Updated_By = ?," +
                "Customer_ID = ?, User_ID = ?, Contact_ID = ?, Start = CONVERT_TZ(?, ?, '+00:00'), End = CONVERT_TZ(?, ?, '+00:00') WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setString(5, userName);
        ps.setInt(6, customerID);
        ps.setInt(7, assignedUserID);
        ps.setInt(8, contactID);
        ps.setString(9, start);
        ps.setString(10, userTZ);
        ps.setString(11, end);
        ps.setString(12, userTZ);
        ps.setInt(13, appointmentID);

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
