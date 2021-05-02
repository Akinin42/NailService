package nailservice.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nailservice.dao.Connector;
import nailservice.entity.Customer;
import nailservice.entity.NailService;
import nailservice.entity.Order;
import nailservice.exceptions.DaoException;
import nailservice.utils.TestUtil;

class OrderDaoImplTest {

    private static OrderDaoImpl orderDao;

    @BeforeAll
    static void init() {
        Connector connector = new ConnectorH2("h2");
        orderDao = new OrderDaoImpl(connector);
    }

    @BeforeEach
    void createTablesAndData() {
        TestUtil.executeScript("\\createtesttables.sql");
    }

    @Test
    void save_ShouldSaveOrderToDB_WhenInputValidCustomer() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        Order order = createOrders().get(0);
        orderDao.save(order);
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

    @Test
    void save_ShouldThrowDaoException_WhenInputInvalidOrder() {
        Customer customer = Customer.builder()
                .withId(1)
                .withName("Anna")
                .withPhone("89234567788")
                .build();
        NailService nailService = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(800)
                .withDuration(120)
                .build();
        Order invalidOrder = Order.builder()
                .withId(3)
                .withDate(null)
                .withTime(null)
                .withCustomer(customer)
                .withNailService(nailService)
                .build();
        assertThrows(DaoException.class, () -> orderDao.save(invalidOrder));
    }

    @Test
    void save_ShouldThrowDaoException_WhenInputNull() {
        assertThrows(DaoException.class, () -> orderDao.save(null));
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenInputIdNotExists() {
        Optional<Order> expected = Optional.empty();
        Optional<Order> actual = orderDao.findById(10);
        assertEquals(expected, actual);
    }

    @Test
    void findById_ShouldReturnExpectedOrder_WhenInputExistentId() {
        Order expected = createOrders().get(0);
        Order actual = orderDao.findById(1).get();
        assertEquals(expected, actual);
    }

    @Test
    void findById_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.findById(1));
    }

    @Test
    void findAll_ShouldReturnExpectedOrders_WhenOrdersTableNotEmpty() {
        List<Order> expected = createOrders();
        List<Order> actual = orderDao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenOrderTableEmpty() {
        TestUtil.queryToDB("DELETE FROM orders");
        List<Order> expected = new ArrayList<>();
        List<Order> actual = orderDao.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.findAll());
    }

    @Test
    void findAll_ShouldReturnExpectedOrders_WhenInputLimitAndOffset() {
        List<Order> expected = createOrders();
        expected.remove(2);
        List<Order> actual = orderDao.findAll(2, 0);
        assertEquals(expected, actual);
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenInputOffsetMoreTableSize() {
        List<Order> expected = new ArrayList<>();
        List<Order> actual = orderDao.findAll(2, 10);
        assertEquals(expected, actual);
    }

    @Test
    void deleteById_ShouldDeleteOrderWithInputId_WhenThisOrderExists() {
        int numberRowBeforeDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        orderDao.deleteById(1);
        int numberRowAfterDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        assertEquals(numberRowBeforeDelete - 1, numberRowAfterDelete);
    }

    @Test
    void deleteById_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.deleteById(1));
    }

    @Test
    void findByTime_ShouldReturnExpectedOrder_WhenOrderExistsWithInputDateAndTime() {
        Order expected = createOrders().get(0);
        LocalDate date = LocalDate.parse("2021-05-01");
        LocalTime time = LocalTime.parse("09:00:00");
        Order actual = orderDao.findByTime(date, time).get();
        assertEquals(expected, actual);
    }

    @Test
    void findByTime_ShouldReturnEmptyOptional_WhenOrderNotExistsWithInputDateAndTime() {
        Optional<Order> expected = Optional.empty();
        LocalDate date = LocalDate.parse("2021-06-06");
        LocalTime time = LocalTime.parse("18:00:00");
        Optional<Order> actual = orderDao.findByTime(date, time);
        assertEquals(expected, actual);
    }

    @Test
    void findByTime_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        LocalDate date = LocalDate.parse("2021-06-06");
        LocalTime time = LocalTime.parse("18:00:00");
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.findByTime(date, time));
    }

    @Test
    void findAllOfDay_ShouldReturnExpectedOrders_WhenOrdersExistsWithInputDate() {
        List<Order> expected = createOrders();
        expected.remove(2);
        LocalDate date = LocalDate.parse("2021-05-01");
        List<Order> actual = orderDao.findAllOfDay(date);
        assertEquals(expected, actual);
    }

    @Test
    void findAllOfDay_ShouldReturnEmptyList_WhenOrdersNotExistsWithInputDate() {
        List<Order> expected = new ArrayList<>();
        LocalDate date = LocalDate.parse("2021-05-15");
        List<Order> actual = orderDao.findAllOfDay(date);
        assertEquals(expected, actual);
    }

    @Test
    void findAllOfDay_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        LocalDate date = LocalDate.parse("2021-05-15");
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.findAllOfDay(date));
    }

    @Test
    void findAllOfWeek_ShouldReturnExpectedOrders_WhenOrdersExistsWithInputDate() {
        List<Order> expected = createOrders();
        LocalDate startDate = LocalDate.parse("2021-05-01");
        LocalDate finishDate = LocalDate.parse("2021-05-07");
        List<Order> actual = orderDao.findAllOfWeek(startDate, finishDate);
        assertEquals(expected, actual);
    }

    @Test
    void findAllOfWeek_ShouldReturnEmptyList_WhenOrdersNotExistsWithInputDate() {
        List<Order> expected = new ArrayList<>();
        LocalDate startDate = LocalDate.parse("2021-05-10");
        LocalDate finishDate = LocalDate.parse("2021-05-16");
        List<Order> actual = orderDao.findAllOfWeek(startDate, finishDate);
        assertEquals(expected, actual);
    }

    @Test
    void findAllOfWeek_ShouldThrowDaoException_WhenConnectNull() {
        OrderDaoImpl orderDaoWithoutConnector = new OrderDaoImpl(null);
        LocalDate startDate = LocalDate.parse("2021-05-01");
        LocalDate finishDate = LocalDate.parse("2021-05-07");
        assertThrows(DaoException.class, () -> orderDaoWithoutConnector.findAllOfWeek(startDate, finishDate));
    }

    private List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();
        LocalDate date = LocalDate.parse("2021-05-01");
        LocalTime time = LocalTime.parse("09:00:00");
        Customer customer = Customer.builder()
                .withId(1)
                .withName("Anna")
                .withPhone("89234567788")
                .build();
        NailService nailService = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(800)
                .withDuration(120)
                .build();
        Order order = Order.builder()
                .withId(1)
                .withDate(date)
                .withTime(time)
                .withCustomer(customer)
                .withNailService(nailService)
                .build();
        orders.add(order);
        date = LocalDate.parse("2021-05-01");
        time = LocalTime.parse("11:00:00");
        customer = Customer.builder()
                .withId(3)
                .withName("Igor")
                .withPhone("89234564443")
                .build();
        nailService = NailService.builder()
                .withId(2)
                .withName("Pedicure")
                .withCost(1000)
                .withDuration(90)
                .build();
        order = Order.builder()
                .withId(2)
                .withDate(date)
                .withTime(time)
                .withCustomer(customer)
                .withNailService(nailService)
                .build();
        orders.add(order);
        date = LocalDate.parse("2021-05-05");
        time = LocalTime.parse("11:00:00");
        order = Order.builder()
                .withId(3)
                .withDate(date)
                .withTime(time)
                .withCustomer(customer)
                .withNailService(nailService)
                .build();
        orders.add(order);
        return orders;
    }
}
