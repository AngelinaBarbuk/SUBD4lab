package connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;

/**
 * Created by VVV on 20.07.2016.
 */
public class DBConnector {
    private static DBConnector datasource;
    Connection connection;
    Statement statement;
    private DBConnector() throws IOException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        connection = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe","angelina","angelina");
        statement = connection.createStatement();
        System.out.println("Load JDBC");
    }

    public static DBConnector getInstance() throws IOException, SQLException{
        if (datasource == null) {
            datasource = new DBConnector();
            return datasource;
        } else {
            return datasource;
        }
    }

    public Connection getConnection() throws SQLException {
        return connection;
    }

}
