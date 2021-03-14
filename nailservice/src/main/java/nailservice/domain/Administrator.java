package nailservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import nailservice.dao.CustomerDao;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDao;

public class Administrator {

    private List<Order> shedule;
    private List<Order> weekShedule;
    private CustomerDao customerDao = new CustomerDao();
    private NailServiceDao serviceDao = new NailServiceDao();
    private OrderDao orderDao = new OrderDao();

    public List<Order> getShedule(String inputDate) {
        LocalDate date = LocalDate.parse(inputDate);
        shedule = orderDao.getOrdersOfDay(date);
        return shedule;
    }

    public List<Order> getWeekShedule(String inputDate) {
        LocalDate startDate = LocalDate.parse(inputDate);
        LocalDate finishDate = startDate.plusDays(7);
        weekShedule = orderDao.getOrdersOfWeek(startDate, finishDate);
        return weekShedule;
    }

    public void createOrder(String customerName, String customerPhone, String inputDate, String inputTime,
            String nailService) {
        Customer customer = customerDao.getByPhone(customerPhone);
        if (customer == null) {
            customer = createCustomer(customerName, customerPhone);
        }
        NailService service = serviceDao.getByName(nailService);
        LocalDate date = LocalDate.parse(inputDate);
        LocalTime time = LocalTime.parse(inputTime);
        if (orderDao.getByTime(date, time) != null) {
            throw new IllegalArgumentException("Sorry you can't get order on this time!");
        }
        Order order = new Order(date, time, customer, service);
        orderDao.insert(order);
    }
    
    public void deleteOrder(String inputDate, String inputTime) {
        LocalDate date = LocalDate.parse(inputDate);
        LocalTime time = LocalTime.parse(inputTime);
        Order order = orderDao.getByTime(date, time);
        orderDao.delete(order);
    }

    private Customer createCustomer(String customerName, String customerPhone) {
        customerDao.insert(new Customer(customerName, customerPhone));
        return customerDao.getByPhone(customerPhone);
    }
}