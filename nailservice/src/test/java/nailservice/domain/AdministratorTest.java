package nailservice.domain;

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
import nailservice.dao.impl.ConnectorH2;
import nailservice.dao.impl.CustomerDaoImpl;
import nailservice.dao.impl.NailServiceDaoImpl;
import nailservice.dao.impl.OrderDaoImpl;
import nailservice.entity.Customer;
import nailservice.entity.NailService;
import nailservice.entity.Order;
import nailservice.utils.TestUtil;

class AdministratorTest {

    private static Administrator administrator;

    @BeforeAll
    static void init() {
        Connector connector = new ConnectorH2("h2");
        OrderDaoImpl orderDao = new OrderDaoImpl(connector);
        CustomerDaoImpl customerDao = new CustomerDaoImpl(connector);
        NailServiceDaoImpl nailServiceDao = new NailServiceDaoImpl(connector);
        administrator = new Administrator(orderDao, customerDao, nailServiceDao);
    }

    @BeforeEach
    void createTablesAndData() {
        TestUtil.executeScript("\\createtesttables.sql");
    }

    @Test
    void getWeekOrders_ShouldReturnExpectedList_WhenOrdersExistsWithInputDate() {
        List<Order> expected = createOrders();
        List<Order> actual = administrator.getWeekOrders("2021-05-01");
        assertEquals(expected, actual);
    }

    @Test
    void createOrder_ShouldAddNewCustomerToDB_WhenCustomerNotExists() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        administrator.createOrder("New Customer", "with new phone", "2021-05-10", "09:00:00", "Manicure");
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

    @Test
    void createOrder_ShouldCreateNewOrder_WhenCustomerNotExists() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        administrator.createOrder("New Customer", "with new phone", "2021-05-10", "09:00:00", "Manicure");
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

    @Test
    void createOrder_ShouldNotAddNewCustomerToDB_WhenCustomerExistsInBD() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        administrator.createOrder("Anna", "89234567788", "2021-05-10", "09:00:00", "Manicure");
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        assertEquals(numberRowBeforeSave, numberRowAfterSave);
    }

    @Test
    void save_ShouldThrowIllegalArgumentException_WhenOrderWithInputDateAndTimeExists() {
        assertThrows(IllegalArgumentException.class,
                () -> administrator.createOrder("Anna", "89234567788", "2021-05-01", "09:00:00", "Manicure"));
    }
    
    @Test
    void deleteOrder_ShouldDeleteOrderWithInputDateAndTime_WhenThisOrderExists() {
        int numberRowBeforeDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        administrator.deleteOrder("2021-05-01","09:00:00");
        int numberRowAfterDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM orders;");
        assertEquals(numberRowBeforeDelete - 1, numberRowAfterDelete);
    }

    private List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();
        LocalDate date = LocalDate.parse("2021-05-01");
        LocalTime time = LocalTime.parse("09:00:00");
        Customer customer = Customer.builder().withId(1).withName("Anna").withPhone("89234567788").build();
        NailService nailService = NailService.builder().withId(1).withName("Manicure").withCost(800).withDuration(120)
                .build();
        Order order = Order.builder().withId(1).withDate(date).withTime(time).withCustomer(customer)
                .withNailService(nailService).build();
        orders.add(order);
        date = LocalDate.parse("2021-05-01");
        time = LocalTime.parse("11:00:00");
        customer = Customer.builder().withId(3).withName("Igor").withPhone("89234564443").build();
        nailService = NailService.builder().withId(2).withName("Pedicure").withCost(1000).withDuration(90).build();
        order = Order.builder().withId(2).withDate(date).withTime(time).withCustomer(customer)
                .withNailService(nailService).build();
        orders.add(order);
        date = LocalDate.parse("2021-05-05");
        time = LocalTime.parse("11:00:00");
        order = Order.builder().withId(3).withDate(date).withTime(time).withCustomer(customer)
                .withNailService(nailService).build();
        orders.add(order);
        return orders;
    }

}
