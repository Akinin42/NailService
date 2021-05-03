package nailservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import nailservice.dao.CustomerDao;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDao;
import nailservice.entity.Customer;
import nailservice.entity.NailService;
import nailservice.entity.Order;

@Component
public class Administrator {
    
    private final OrderDao orderDao;
    private final CustomerDao customerDao;
    private final NailServiceDao nailServiceDao;

    public Administrator(OrderDao orderDao, CustomerDao customerDao, NailServiceDao nailServiceDao) {       
        this.orderDao = orderDao;
        this.customerDao = customerDao;
        this.nailServiceDao = nailServiceDao;
    }

    public List<Order> getWeekOrders(String inputDate) {
        LocalDate startDate = LocalDate.parse(inputDate);
        LocalDate finishDate = startDate.plusDays(7);        
        return orderDao.findAllOfWeek(startDate, finishDate);
    }

    public void createOrder(String customerName, String customerPhone, String inputDate, String inputTime,
            String nailServiceName) {
        Customer customer = null;
        Optional<Customer> value = customerDao.findByPhone(customerPhone);
        if(value.isPresent()) {
            customer = customerDao.findByPhone(customerPhone).get();
        } else {
            customer = createCustomer(customerName, customerPhone);
        }        
        NailService nailService = nailServiceDao.findByName(nailServiceName).get();
        LocalDate date = LocalDate.parse(inputDate);
        LocalTime time = LocalTime.parse(inputTime);
        if (!orderDao.findByTime(date, time).equals(Optional.empty())) {
            throw new IllegalArgumentException("Sorry you can't get order on this time!");
        }
        Order order = Order.builder()
                .withDate(date)
                .withTime(time)
                .withNailService(nailService)
                .withCustomer(customer)
                .build();        
        orderDao.save(order);
    }

    public void deleteOrder(String inputDate, String inputTime) {        
        LocalDate date = LocalDate.parse(inputDate);
        LocalTime time = LocalTime.parse(inputTime);
        Order order = orderDao.findByTime(date, time).get();
        orderDao.deleteById(order.getId());
    }

    private Customer createCustomer(String customerName, String customerPhone) {       
        Customer customer = Customer.builder()
                .withName(customerName)
                .withPhone(customerPhone)
                .build();        
        customerDao.save(customer);
        return customerDao.findByPhone(customerPhone).get();
    }
}
