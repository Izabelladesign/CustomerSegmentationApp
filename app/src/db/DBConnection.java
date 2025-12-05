package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    //Loads database configuration properties from the db.properties file
    private static Properties loadProps() throws Exception {
        Properties props = new Properties();
        try (InputStream in = DBConnection.class.getResourceAsStream("/db/db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties not found!");
            }
            props.load(in);
        }
        return props;
    }
//creates and returns a database connection using credentials
    public static Connection get() throws Exception {
        Properties props = loadProps();
        String url = props.getProperty("url");
        String user = props.getProperty("user");
        String pass = props.getProperty("password");

        return DriverManager.getConnection(url, user, pass);
    }
}
