package nailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import nailservice.domain.Customer;

public class CustomerDB {

    private DAOFactory factory = DAOFactory.getInstance();

    public void insert(Customer customer) throws IllegalArgumentException, DAOException {
        String sql = "INSERT INTO customers (name, phone) VALUES(?,?);";
        if (getByPhone(customer.getPhone()) != null) {
            throw new IllegalArgumentException("User is already created, the user ID is not null.");
        }
        Object[] values = { customer.getName(), customer.getPhone() };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setCustomerId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }
    
    public Customer getByPhone(String phone) throws DAOException{
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE phone = ?";
        try (
                Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, phone);
                ResultSet resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    customer = new Customer();
                    customer.setCustomerId(resultSet.getInt("customer_id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setPhone(phone);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        return customer;
    }

}