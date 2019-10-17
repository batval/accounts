package services.dbService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBService {

    private final Connection connection;

    public DBService() throws FileNotFoundException {
        this.connection = getMysqlConnection();
    }

    public static Connection getMysqlConnection() throws FileNotFoundException {
        Properties properties = new Properties();

        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);

            StringBuilder url = new StringBuilder();

            url.

                    append("jdbc:"+properties.getProperty("DBType")+"://").
                    append(properties.getProperty("HostName")+":").
                    append(properties.getProperty("Port")+"/").
                    append(properties.getProperty("DataBase")+"?").
                    append("user="+properties.getProperty("DbUser")+"&").
                    append("password="+properties.getProperty("DbPassword")+"&").
                    append("useSSL="+properties.getProperty("UseSSL")+"&").
                    append("serverTimezone="+properties.getProperty("ServerTimeZone"));

            //        System.out.println("URL: " + url + "\n");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;

        } catch (SQLException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   /* public Customer getUser(long id) throws DBException {
        try {
            return (new UsersDAO(connection).get(id));
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
*/
}
