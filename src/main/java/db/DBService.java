package db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class that creates and manages the connection to database.
 *
 * @author Baturo Valery
 * @version 1.0
 */
public class DBService {

    /**
     * Event Logger for class DBService {@value}.
     */
    private static final Logger log = LogManager.getLogger(DBService.class.getName());
    /**
     * The connection to database.
     */
    private final Connection connection;

    /**
     * Initializing MySQL database connection.
     */
    public DBService() {
        this.connection = getMysqlConnection();
    }

    /**
     * Connecting to the MySQL database.
     * Reads the configuration file "config.properties" to initialize the connection.
     *
     * @return connection or null in case of failure.
     */
    public static Connection getMysqlConnection() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:" + properties.getProperty("DBType") + "://").
                    append(properties.getProperty("HostName") + ":").
                    append(properties.getProperty("Port") + "/").
                    append(properties.getProperty("DataBase") + "?").
                    append("user=" + properties.getProperty("DbUser") + "&").
                    append("password=" + properties.getProperty("DbPassword") + "&").
                    append("useSSL=" + properties.getProperty("UseSSL") + "&").
                    append("serverTimezone=" + properties.getProperty("ServerTimeZone"));
            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | IOException e) {
            log.error(e.toString());
        }
        return null;
    }

    /**
     * Display database connection information.
     * Database name, version, driver and autocommit.
     */
    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            log.error(e.toString());
        }
    }
}
