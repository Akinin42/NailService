package nailservice.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nailservice.dao.Connector;
import nailservice.entity.Customer;
import nailservice.exceptions.DaoException;
import nailservice.utils.TestUtil;

class CustomerDaoImplTest {

    private static CustomerDaoImpl customerDao;

    @BeforeAll
    static void init() {
        Connector connector = new ConnectorH2("h2");
        customerDao = new CustomerDaoImpl(connector);
    }

    @BeforeEach
    void createTablesAndData() {
        TestUtil.executeScript("\\createtesttables.sql");
    }

    @Test
    void save_ShouldSaveCustomerToDB_WhenInputValidCustomer() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        Customer customer = Customer.builder()
                .withId(4)
                .withName("Ivan")
                .withPhone("78556447721")
                .build();
        customerDao.save(customer);
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

    @Test
    void save_ShouldThrowDaoException_WhenInputInvalidCustomer() {
        Customer invalidCustomer = Customer.builder()
                .withId(0)
                .withName(null)
                .withPhone("78556447721")
                .build();
        assertThrows(DaoException.class, () -> customerDao.save(invalidCustomer));
    }
    
    @Test
    void save_ShouldThrowDaoException_WhenInputNull() {
        assertThrows(DaoException.class, () -> customerDao.save(null));
    }
    
    @Test
    void findById_ShouldReturnEmptyOptional_WhenInputIdNotExists() {
        Optional<Customer> expected = Optional.empty();
        Optional<Customer> actual = customerDao.findById(10);
        assertEquals(expected, actual);
    }
    
    @Test
    void findById_ShouldReturnExpectedCustomer_WhenInputExistentId() {
        Customer expected = createCustomers().get(0);
        Customer actual = customerDao.findById(1).get();
        assertEquals(expected, actual);
    }
    
    @Test
    void findById_ShouldThrowDaoException_WhenConnectNull() {
        CustomerDaoImpl customerDaoWithoutConnector = new CustomerDaoImpl(null);
        assertThrows(DaoException.class, () -> customerDaoWithoutConnector.findById(1));
    }  
    
    @Test
    void findAll_ShouldReturnExpectedCustomers_WhenCustomersTableNotEmpty() {
        List<Customer> expected = createCustomers();
        List<Customer> actual = customerDao.findAll();
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldReturnEmptyList_WhenCoursesTableEmpty() {
        TestUtil.queryToDB("DELETE FROM customers");
        List<Customer> expected = new ArrayList<>();
        List<Customer> actual = customerDao.findAll();
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldThrowDaoException_WhenConnectNull() {
        CustomerDaoImpl customerDaoWithoutConnector = new CustomerDaoImpl(null);
        assertThrows(DaoException.class, () -> customerDaoWithoutConnector.findAll());
    }
    
    @Test
    void findAll_ShouldReturnExpectedCustomers_WhenInputLimitAndOffset() {
        List<Customer> expected = createCustomers();
        expected.remove(2);
        List<Customer> actual = customerDao.findAll(2, 0);
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldReturnEmptyList_WhenInputOffsetMoreTableSize() {
        List<Customer> expected = new ArrayList<>();
        List<Customer> actual = customerDao.findAll(2, 10);
        assertEquals(expected, actual);
    }
    
    @Test
    void deleteById_ShouldDeleteCustomerWithInputId_WhenThisCustomerExists() {
        int numberRowBeforeDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        customerDao.deleteById(1);
        int numberRowAfterDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        assertEquals(numberRowBeforeDelete - 1, numberRowAfterDelete);
    }
    
    @Test
    void deleteById_ShouldThrowDaoException_WhenConnectNull() {
        CustomerDaoImpl customerDaoWithoutConnector = new CustomerDaoImpl(null);
        assertThrows(DaoException.class, () -> customerDaoWithoutConnector.deleteById(1));
    }
    
    @Test
    void findByPhone_ShouldReturnExpectedCourse_WhenInputExistentPhone() {
        Customer expected = createCustomers().get(0);
        Customer actual = customerDao.findByPhone("89234567788").get();
        assertEquals(expected, actual);
    }
    
    @Test
    void findByPhone_ShouldReturnEmptyOptional_WhenInputPhoneNotExists() {
        Optional<Customer> expected = Optional.empty();
        Optional<Customer> actual = customerDao.findByPhone("not exist phone");
        assertEquals(expected, actual);
    }
    
    @Test
    void findByPhone_ShouldThrowDaoException_WhenConnectNull() {
        CustomerDaoImpl customerDaoWithoutConnector = new CustomerDaoImpl(null);
        assertThrows(DaoException.class, () -> customerDaoWithoutConnector.findByPhone("89234567788"));
    }    

    private List<Customer> createCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = Customer.builder()
                .withId(1)
                .withName("Anna")
                .withPhone("89234567788")
                .build();
        customers.add(customer);
        customer = Customer.builder()
                .withId(2)
                .withName("Elena")
                .withPhone("89234561122")
                .build();
        customers.add(customer);
        customer = Customer.builder()
                .withId(3)
                .withName("Igor")
                .withPhone("89234564443")
                .build();
        customers.add(customer);
        return customers;
    }    
}
