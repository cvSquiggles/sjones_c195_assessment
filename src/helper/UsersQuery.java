package helper;

import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {

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
                    Users.currentUser = username;
                    return 1;
                }
            }
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
            return 0;
        }

        return 0;

    }
}
