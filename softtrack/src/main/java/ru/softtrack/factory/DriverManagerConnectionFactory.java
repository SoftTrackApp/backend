package ru.softtrack.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import ru.softtrack.common.ConnectionFactory;

public class DriverManagerConnectionFactory implements ConnectionFactory  {
    private String url = null;
    private String user = null;
    private String password = null;

    public DriverManagerConnectionFactory(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public Connection getNewConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        return connection;
    }
    
}
