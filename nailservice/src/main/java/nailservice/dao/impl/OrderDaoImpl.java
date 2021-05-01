package nailservice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nailservice.dao.Connector;
import nailservice.dao.CustomerDao;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDao;
import nailservice.entity.Order;
import nailservice.exceptions.DaoException;

public class OrderDaoImpl extends AbstractCrudImpl<Order> implements OrderDao {

    private static final String SAVE_QUERY = "INSERT INTO orders (order_date, order_time, customer_id, nailservice_id) VALUES(?,?,?,?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM orders WHERE order_id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM orders ORDER BY order_id;";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM orders ORDER BY order_id LIMIT ? OFFSET ?;";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM orders WHERE order_id = ?;";
    private static final String FIND_BY_TIME_QUERY = "SELECT * FROM orders WHERE order_date = ? AND order_time = ?;";
    private static final String FIND_ALL_BY_DAY_QUERY = "SELECT * FROM orders WHERE order_date = ? ORDER BY order_time;";
    private static final String FIND_ALL_BY_WEEK_QUERY = "SELECT * FROM orders WHERE order_date BETWEEN ? AND ? ORDER BY order_date, order_time;";

    public OrderDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, DELETE_BY_ID_QUERY);
    }

    public Optional<Order> findByTime(LocalDate date, LocalTime time) throws DaoException {
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_TIME_QUERY)) {
            statement.setObject(1, date);
            statement.setObject(2, time);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(createEntityFromResultSet(resultSet)) : Optional.empty();
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't return by time " + time + " at " + date, e);
        }
    }

    public List<Order> findAllOfDay(LocalDate date) throws DaoException {
        List<Order> ordersOfDay = new ArrayList<>();
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_DAY_QUERY)) {
            statement.setObject(1, date);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ordersOfDay.add(createEntityFromResultSet(resultSet));
                }
            }
            return ordersOfDay;
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't return by day " + date, e);
        }
    }

    public List<Order> findAllOfWeek(LocalDate startDate, LocalDate finishDate) throws DaoException {
        List<Order> ordersOfDay = new ArrayList<>();
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_WEEK_QUERY)) {
            statement.setObject(1, startDate);
            statement.setObject(1, finishDate);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ordersOfDay.add(createEntityFromResultSet(resultSet));
                }
            }
            return ordersOfDay;
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't return from day " + startDate + " to " + finishDate, e);
        }
    }    

    @Override
    protected void insert(PreparedStatement statement, Order order) throws SQLException {        
        statement.setObject(1, order.getDate());
        statement.setObject(2, order.getTime());
        statement.setInt(3, order.getCustomer().getId());
        statement.setInt(4, order.getNailService().getId());
    }

    @Override
    protected Order createEntityFromResultSet(ResultSet resultSet) throws SQLException {
        CustomerDao customerDao = new CustomerDaoImpl(connector);
        NailServiceDao nailServiceDao = new NailServiceDaoImpl(connector);
        return Order.builder().withId(resultSet.getInt("order_id"))
                .withDate(resultSet.getObject("order_date", LocalDate.class))
                .withTime(resultSet.getObject("order_time", LocalTime.class))
                .withCustomer(customerDao.findById(resultSet.getInt("customer_id")).get())
                .withNailService(nailServiceDao.findById(resultSet.getInt("nailservice_id")).get())
                .build();
    }
}
