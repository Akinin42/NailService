package nailservice.dao;

import nailservice.domain.Customer;

public class DBTest {

    public static void main(String[] args) {
        CustomerDB customerDB = new CustomerDB();
        Customer customer = customerDB.getByPhone("89236170788");
        if(customer==null) {
            System.out.println("I can't find this customer. Let's create him!");
        }
        Customer testCustomer = new Customer();
        testCustomer.setName("Anastasia");
        testCustomer.setPhone("89236170788");        
        customerDB.insert(testCustomer);
        customer = customerDB.getByPhone("89236170788");
        System.out.println(customer);
        customerDB.insert(testCustomer);
    }
}