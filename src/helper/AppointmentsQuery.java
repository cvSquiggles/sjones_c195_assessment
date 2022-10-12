package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains various methods of querying the database for appointment information
 */
public class AppointmentsQuery {
    /**
     * Queries the database for all appointments within the provided weekOffSet, and converts them to the local users time zone
     * @param weekOffSet the number of weeks ahead or behind the current week we're searching the database for
     * @param timeZone the current users time zone offset
     * @return AppointmentList object containing all the appointments within the time interval specified
     * @throws SQLException
     */
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

    /**
     * Queries the database for all appointments for a given contact within the provided weekOffSet, and converts them to the local users time zone
     * @param weekOffSet the number of weeks ahead or behind the current week we're searching the database for
     * @param contactID the ID of the contact that is being searched for
     * @param timeZone the current users' time zone offset
     * @return AppointmentList object containing all the appointments for the specified client within the time interval input
     * @throws SQLException
     */
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

    /**
     * Queries the database for all appointments within the provided monthOffSet, and converts them to the local users time zone
     * @param monthOffset the number of months ahead or behind the current month we're searching the database for
     * @param timeZone the current users' time zone offset
     * @return AppointmentList object containing all the appointments within the time interval input
     * @throws SQLException
     */
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

    /**
     * Queries the database for all appointments for a given contact within the provided monthOffSet, and converts them to the local users time zone
     * @param monthOffset the number of months ahead or behind the current month we're searching the database for
     * @param contactID the ID of the contact that is being searched for
     * @param timeZone the current users' time zone offset
     * @return AppointmentList object containing all the appointments within the time interval input
     * @throws SQLException
     */
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

    /**
     *
     * @return A resultSet containing the appointments in the database
     * @throws SQLException
     */
    public static ResultSet select() throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "a.Start, a.End, a.Created_By, a.Last_Update, a.Last_Updated_By, a.Customer_ID, a.User_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        return ps.executeQuery();
    }

    /**
     * Searches the database for the appointment information based on the passed in appointmentID value
     * @param appointmentID the ID of the appointment to search for
     * @return the appointment information for the specified appointment
     * @throws SQLException
     */
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

        return ps.executeQuery();
    }

    /**
     * Queries the database for appointments for the passed in User within 15 minutes of the specified logInTime
     * @param userID the ID of the user that is being searched for
     * @param timeZone the current users' time zone offset
     * @param logInTime the current time stamp passed in to compare appointment times against
     * @return A ResultSet of appointments for the given User within the specified time frame.
     * @throws SQLException
     */
    public static ResultSet selectByUserWithTime(int userID, String timeZone, String logInTime) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, " +
                "a.Last_Updated_By, cu.Customer_Name, u.User_Name, u.User_ID, co.Contact_Name, cu.Customer_ID, co.Contact_ID " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE a.User_ID = ? " +
                "AND TIMEDIFF(CONVERT_TZ(?, ?, '+00:00'), a.Start) < '-00:15:00' " +
                "AND TIMEDIFF(CONVERT_TZ(?, ?, '+00:00'), a.Start) LIKE '-%'";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, userID);
        ps.setString(4, logInTime);
        ps.setString(5, timeZone);
        ps.setString(6, logInTime);
        ps.setString(7, timeZone);

        return ps.executeQuery();
    }

    /**
     * Queries the database for appointments for the passed in Customer
     * @param customer_id the ID of the customer that is being searched for
     * @param timeZone the current users' time zone offset
     * @return a ResultSet with all of the specified customers' appointments in the database
     * @throws SQLException
     */
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

        return ps.executeQuery();
    }

    public static ResultSet selectByContact(int contact_id, String timeZone) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, cu.Customer_Name, u.User_Name, u.User_ID, co.Contact_Name, cu.Customer_ID, co.Contact_ID " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID  WHERE a.Contact_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);
        ps.setInt(3, contact_id);

        return ps.executeQuery();
    }

    /**
     * Queries the database for all appointments, and sorts them by Month, and then by Type, so you can see how many of each type of appointment are scheduled each month
     * @return a ResultSet containing all of the appointments grouped by month, and then type
     * @throws SQLException
     */
    public static ResultSet selectTotals() throws SQLException {
        String sql = "SELECT Type, INSERT(EXTRACT(YEAR_MONTH FROM Start), 5, 0, '-') AS yearMonth, count(EXTRACT(YEAR_MONTH FROM Start)) as Total" +
                " FROM appointments Group by yearMonth, Type";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        return ps.executeQuery();
    }

    /**
     * Queries the database for all appointments in the database where the Type field contains the characters 'test', or 'Test' anywhere in the field
     * @param timeZone the current users' time zone offset
     * @return an ObservableList of appointments containing 'test', or 'Test' anywhere in the Type field
     * @throws SQLException
     */
    public static ObservableList<Appointments> selectTesting(String timeZone) throws SQLException {
        String sql = "SELECT a.Appointment_ID, a.Title, a.Description, a.Location, a.Type, a.Create_Date, " +
                "CONVERT_TZ(a.Start, '+00:00', ?) as Start, CONVERT_TZ(a.End, '+00:00', ?) as End, a.Created_By, a.Last_Update, a.Last_Updated_By, " +
                "a.Customer_ID, a.User_ID, a.Contact_ID, cu.Customer_Name, u.User_Name, co.Contact_Name " +
                "FROM appointments as a " +
                "JOIN Users as u on a.User_ID = u.User_ID " +
                "JOIN Customers as cu on a.Customer_ID = cu.Customer_ID " +
                "JOIN Contacts as co on a.Contact_ID = co.Contact_ID " +
                "WHERE Type LIKE '%test%' OR Type LIKE '%Test%'" +
                "ORDER BY Start ASC";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setString(1, timeZone);
        ps.setString(2, timeZone);

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

    /**
     *
     * @param title the title of the appointment to be created
     * @param description the description of the appointment to be created
     * @param location the location of the appointment to be created
     * @param type the type of the appointment to be created
     * @param start the start of the appointment to be created
     * @param end the end of the appointment to be created
     * @param timeZoneOffset the current users' time offset
     * @param createdBy the user that is creating the appointment
     * @param customerID the ID of the customer to associate with the appointment
     * @param userID the ID of the user to associate with the appointment
     * @param contactID the ID of the contact to associate with the appointment
     * @return an integer to indicate whether the insert was successful, non-zero value indicating success
     * @throws SQLException
     */
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


        return ps.executeUpdate();
    }

    /**
     *
     * @param title the title of the appointment to be updated
     * @param description the description of the appointment to be updated
     * @param location the location of the appointment to be updated
     * @param type the type of the appointment to be updated
     * @param customerID the ID of the customer to associate with the appointment
     * @param userName the username of the user making the update
     * @param contactID the ID of the contact to associate with the appointment
     * @param appointmentID the ID of the appointment to update
     * @param assignedUserID the ID of the user to associate with the appointment
     * @param start the start of the appointment to be updated
     * @param end the end of the appointment to be updated
     * @param userTZ the current users' time offset
     * @return an integer to indicate whether the update was successful, non-zero value indicating success
     * @throws SQLException
     */
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

        return ps.executeUpdate();
    }

    /**
     *
     * @param appointmentID the ID of the appointment to delete
     * @return * @return an integer to indicate whether the delete was successful, non-zero value indicating success
     * @throws SQLException
     */
    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ps.setInt(1, appointmentID);

        return ps.executeUpdate();
    }

}
