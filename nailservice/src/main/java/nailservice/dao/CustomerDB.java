package nailservice.dao;

import nailservice.domain.Customer;

public class CustomerDB {
    
    private FactoryDao factory = FactoryDao.getInstance();
    
    public void insert(Customer customer) throws IllegalArgumentException, DAOException{
        String sql = "insert into customers (customer_id, name, phone) values(?,?,?);";
    }

}