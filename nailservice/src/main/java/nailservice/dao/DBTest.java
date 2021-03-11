package nailservice.dao;

import nailservice.domain.Customer;

public class DBTest {

    public static void main(String[] args) {

        Customer testCustomer = new Customer();
        testCustomer.setName("Anastasia");
        testCustomer.setPhone("89236170788");
        CustomerDB customerDB = new CustomerDB();
        customerDB.insert(testCustomer);
    }
}