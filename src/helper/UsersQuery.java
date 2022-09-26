package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {

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

    public static int signIn(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE User_Name = ?";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                username = rs.getString("User_Name");
                String dbPassword = rs.getString("Password");

                System.out.println(dbPassword + " " + password + " " + username);

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
