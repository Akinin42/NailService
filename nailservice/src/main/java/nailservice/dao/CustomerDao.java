package nailservice.dao;

import java.util.List;

import nailservice.domain.Customer;

public interface CustomerDao {

    public Customer getByPhone(String phone) throws DAOException;

    public void insert(Customer customer) throws IllegalArgumentException, DAOException;

    public List<Customer> getAll() throws DAOException;
}