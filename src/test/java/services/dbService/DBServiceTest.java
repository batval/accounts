package services.dbService;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class DBServiceTest {


    @Test
    public void getMysqlConnection() {
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

            Connection connection = DriverManager.getConnection(url.toString());
            String expected= "jdbc:mysql://localhost:3306/accounts?user=root&password=admin&useSSL=false&serverTimezone=UTC";
            String actual = url.toString();
            assertEquals(expected,actual);

        } catch (SQLException | IOException e) {
            System.out.println(e.toString());
        }
    }

}