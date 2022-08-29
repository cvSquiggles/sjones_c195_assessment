package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersQuery {

    public static int signIn(String username, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE User_Name = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if(rs.getFetchSize() > 0) {

            rs.next();
            username = rs.getString("User_Name");
            String dbPassword = rs.getString("Password");

            System.out.println(dbPassword + " " + password + " " + username);

            if (dbPassword.contentEquals(password)) {
                return 1;
            }
        }

        return 0;
    }
}
