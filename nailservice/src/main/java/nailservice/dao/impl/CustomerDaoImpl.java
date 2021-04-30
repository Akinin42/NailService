package nailservice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import nailservice.dao.Connector;
import nailservice.dao.CustomerDao;
import nailservice.entity.Customer;
import nailservice.exceptions.DaoException;

public class CustomerDaoImpl extends AbstractCrudImpl<Customer> implements CustomerDao {
    
    private static final String SAVE_QUERY = "INSERT INTO customers (name, phone) VALUES(?,?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM customers WHERE customer_id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM customers ORDER BY customer_id;";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM customers ORDER BY customer_id LIMIT ? OFFSET ?;";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM customers WHERE customer_id = ?;";
    private static final String FIND_BY_PHONE_QUERY = "SELECT * FROM customers WHERE phone = ?";

    public CustomerDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, DELETE_BY_ID_QUERY);
    }

    public Optional<Customer> findByPhone(String phone) throws DaoException {              
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_PHONE_QUERY)) {
            statement.setString(1, phone);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(createEntityFromResultSet(resultSet)) : Optional.empty();
            }        
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't return by phone " + phone, e);
        }        
    }

    @Override
    protected void insert(PreparedStatement statement, Customer customer) throws SQLException {
        statement.setInt(1, customer.getId());
        statement.setString(2, customer.getName());
        statement.setString(3, customer.getPhone());        
    }

    @Override
    protected Customer createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        return Customer.builder()
                .withId(resultSet.getInt("customer_id"))
                .withName(resultSet.getString("name"))
                .withPhone(resultSet.getString("phone"))
                .build();
    }
}
