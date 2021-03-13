package nailservice.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nailservice.dao.CustomerDao;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDB;

public class Administrator {

    private List<Order> masterShedule;
    private Map<Calendar, List<Order>> masterWeekShedule;
    private CustomerDao customerDao = new CustomerDao();
    private NailServiceDao serviceDao = new NailServiceDao();
    private OrderDB orderDao = new OrderDB();

    public List<Order> getShedule(String inputDate) {
        Date date = Date.valueOf(inputDate);
        masterShedule = orderDao.getOrdersOfDay(date);
        return masterShedule;
    }

    public Map<Calendar, List<Order>> getWeekShedule(Calendar date) {

        return masterWeekShedule;
    }

    public void createOrder(String customerName, String customerPhone, String inputDate, String inputTime,
            String nailService) {
        Customer customer = customerDao.getByPhone(customerPhone);
        if (customer == null) {
            customer = createCustomer(customerName, customerPhone);
        }
        NailService service = serviceDao.getByName(nailService);
        Date date = Date.valueOf(inputDate);
        Time time = Time.valueOf(inputTime);
        if (orderDao.getByTime(date, time) != null) {
            throw new IllegalArgumentException("Sorry you can't get order on this time!");
        }
        Order order = new Order(date, time, customer, service);
        orderDao.insert(order);
    }

    private Customer createCustomer(String customerName, String customerPhone) {
        customerDao.insert(new Customer(customerName, customerPhone));
        return customerDao.getByPhone(customerPhone);
    }
}