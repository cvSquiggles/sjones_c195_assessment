package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contains methods of querying the database for user information, including the application sign in credential check functionality
 */
public class UsersQuery {
    /**
     *
     * @return an ObservableList of the users that exist in the database
     */
    public static ObservableList<Users> selectUsers() throws SQLException{
        ObservableList<Users> userList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            Users userValue = new Users(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"), rs.getString("Created_By"));
            userList.add(userValue);
        }

        return userList;
    }

    /**
     * Query the database for the username/password combination to determine if the login attempt is successful
     * @param username the username to search for in the database
     * @param password the password to search for with the associated username
     * @return an integer to indicate whether the update was successful, non-zero value indicating success
     */
    public static int signIn(String username, String password) {
        String sql = "SELECT * FROM Users WHERE User_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            //Iterate through the result set
            while (rs.next()) {
                username = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");

                if (dbPassword.contentEquals(password)) {
                    Users.currentUser = new Users(rs.getInt("User_ID"), rs.getString("User_Name"), rs.getString("Password"), rs.getString("Created_By"));
                    return 1;
                }
            }
        }
        catch(SQLException throwable){
            throwable.printStackTrace();
            return 0;
        }

        return 0;

    }
}
