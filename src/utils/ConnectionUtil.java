package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    Connection connection = null;
    public static Connection conDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            return con;
        }
        catch (ClassNotFoundException | SQLException ex) {
            return null;
        }
    }
}
