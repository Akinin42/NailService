package nailservice.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FactoryDao {

    private static FactoryDao factory;

    private String user;
    private String password;
    private String url;
    private String driver;

    public static FactoryDao getInstance() {
        if (factory == null) {
            factory = new FactoryDao();
        }
        return factory;
    }

    private FactoryDao() {
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(FactoryDao.class.getResourceAsStream("/db.properties"));
            user = properties.getProperty("username");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (IOException e) {
            throw new DAOConfigurationException("Can't read properties file!", e);
        }
    }

    public Connection getConnection() throws DAOException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new DAOException("There isn't database connect!", e);
        }
    }

}