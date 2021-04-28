package nailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nailservice.models.Customer;

public class CustomerDao {

    private DAOFactory factory = DAOFactory.getInstance();

    public void insert(Customer customer) throws IllegalArgumentException, DAOException {
        String sql = "INSERT INTO customers (name, phone) VALUES(?,?);";
        if (getByPhone(customer.getPhone()) != null) {
            throw new IllegalArgumentException("Customer is already created!");
        }
        Object[] values = { customer.getName(), customer.getPhone() };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating customer failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating customer failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Customer getByPhone(String phone) throws DAOException {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE phone = ?";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, phone);
                ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                customer = init(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return customer;
    }
    public Customer getById(int id) throws DAOException {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, id);
                ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                customer = init(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return customer;
    }

    public List<Customer> getAll() throws DAOException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, name, phone FROM customers ORDER BY customer_id";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                customers.add(init(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return customers;
    }

    private Customer init(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("customer_id"));
        customer.setName(resultSet.getString("name"));
        customer.setPhone(resultSet.getString("phone"));
        return customer;
    }
}