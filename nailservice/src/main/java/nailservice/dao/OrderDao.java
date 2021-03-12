package nailservice.dao;

import java.util.Calendar;
import java.util.List;
import nailservice.domain.Order;

public interface OrderDao {
    
    public List<Order> getAllByDate(Calendar date) throws DAOException;
    
    public List<Order> getAll() throws DAOException;
    
    public void insert(Order order) throws IllegalArgumentException, DAOException;
    
    public void update(Order order) throws IllegalArgumentException, DAOException;
    
    public void delete(Order order) throws DAOException;
}