package nailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import nailservice.domain.Order;

public class OrderDao {

    private DAOFactory factory = DAOFactory.getInstance();

    public void insert(Order order) throws IllegalArgumentException, DAOException {
        String sql = "INSERT INTO orders (date, time, customer_id, nailservice_id) VALUES(?,?,?,?);";
        if (order.getId() != null) {
            throw new IllegalArgumentException("NailService is already created, y can only update it!");
        }
        Object[] values = { order.getDate(), order.getTime(), order.getCustomer().getId(),
                order.getNailService().getId() };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating order failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating order failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public Order getByTime(LocalDate date, LocalTime time) throws DAOException {
        Order order = null;
        String sql = "SELECT * FROM orders WHERE date = ? AND time = ?";
        Object[] values = { date, time };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, values);
                ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                order = init(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return order;
    }

    public List<Order> getOrdersOfDay(LocalDate date) throws DAOException {
        List<Order> ordersOfDay = new ArrayList<>();
        String sql = "SELECT order_id, date, time, customer_id, nailservice_id FROM orders WHERE date = ? ORDER BY time";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, date);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                ordersOfDay.add(init(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return ordersOfDay;
    }
    
    public List<Order> getOrdersOfWeek(LocalDate startDate, LocalDate finishDate) throws DAOException {
        List<Order> ordersOfDay = new ArrayList<>();
        String sql = "SELECT order_id, date, time, customer_id, nailservice_id FROM orders WHERE date BETWEEN ? AND ? ORDER BY date, time";
        Object[] values = { startDate, finishDate };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                ordersOfDay.add(init(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return ordersOfDay;
    }

    private Order init(ResultSet resultSet) throws SQLException {
        CustomerDao customerDao = new CustomerDao();
        NailServiceDao serviceDao = new NailServiceDao();
        Order order = new Order();
        order.setId(resultSet.getInt("order_id"));
        order.setDate(resultSet.getObject("date", LocalDate.class));
        order.setTime(resultSet.getObject("time", LocalTime.class));
        order.setCustomer(customerDao.getById(resultSet.getInt("customer_id")));
        order.setNailService(serviceDao.getById(resultSet.getInt("nailservice_id")));
        return order;
    }
}