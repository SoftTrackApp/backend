package ru.softtrack;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import ru.softtrack.common.ConnectionFactory;
import ru.softtrack.factory.DriverManagerConnectionFactory;
import ru.softtrack.factory.HikariDataSourceConnectionFactory;
import ru.softtrack.util.DbConfig;

public class App {
    public static void main(String[] args) {
        new App().ConnectoToDatabase();
    }
    
    public void ConnectoToDatabase() {
        DbConfig dbConfig = new DbConfig();
        String url = dbConfig.getUrl();
        String user = dbConfig.getUser();
        String password = dbConfig.getPassword();
        ConnectionFactory connectionFactory = new HikariDataSourceConnectionFactory(url,user,password);
        try {
            Connection connection = connectionFactory.getNewConnection();
            System.out.println(connection.isValid(0));
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
}
