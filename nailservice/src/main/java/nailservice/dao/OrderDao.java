package nailservice.dao;

import java.util.Date;
import java.util.List;
import nailservice.domain.Order;

public interface OrderDao {
    
    public List<Order> getAllByDate(Date date) throws DAOException;
    
    public List<Order> getAll() throws DAOException;
    
    public void insert(Order order) throws IllegalArgumentException, DAOException;
    
    public void update(Order order) throws IllegalArgumentException, DAOException;
    
    public void delete(Order order) throws DAOException;
}