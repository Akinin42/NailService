package nailservice.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
                .build();                                               // here thing
        NailService nailService = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(800)
                .withDuration(120)
                .build();
        Order invalidOrder = Order.builder().withId(3).withDate(null).withTime(null).withCustomer(customer).withNailService(nailService).build();
        assertThrows(DaoException.class, () -> orderDao.save(invalidOrder));
    }

    @Test
    void save_ShouldThrowDaoException_WhenInputNull() {
        assertThrows(DaoException.class, () -> orderDao.save(null));
    }
    
    private List<Order> createOrders(){
        List<Order> orders = new ArrayList<>();
        LocalDate date = LocalDate.parse("2021-05-01");
        LocalTime time = LocalTime.parse("11:00:00");
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
        date = LocalDate.parse("2021-05-03");
        time = LocalTime.parse("09:00:00");
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
        return orders;
    }
}
