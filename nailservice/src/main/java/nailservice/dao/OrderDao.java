package nailservice.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import nailservice.entity.Order;

public interface OrderDao extends CrudDao<Order, Integer> {
    
    Optional<Order> findByTime(LocalDate date, LocalTime time);
    
    List<Order> findAllOfDay(LocalDate date);
    
    List<Order> findAllOfWeek(LocalDate startDate, LocalDate finishDate);

}
