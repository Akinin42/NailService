package nailservice.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nailservice.dao.Connector;
import nailservice.entity.Customer;
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
    void saveShouldSaveCustomerToDB_WhenInputValidCustomer() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        Customer customer = Customer.builder().withId(4).withName("Ivan").withPhone("78556447721").build();
        customerDao.save(customer);
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM customers;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

}
