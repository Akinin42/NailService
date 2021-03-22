package nailservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nailservice.dao.CustomerDao;
import nailservice.dao.DAOFactory;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDao;
import nailservice.models.Customer;
import nailservice.models.NailService;
import nailservice.models.Order;

public class Administrator {

    private DAOFactory factory = DAOFactory.getInstance();
    private OrderDao orderDao = factory.getOrderDao();

    public List<Order> getWeekOrders(String inputDate) {
        LocalDate startDate = LocalDate.parse(inputDate);
        LocalDate finishDate = startDate.plusDays(7);
        return orderDao.getOrdersOfWeek(startDate, finishDate);
    }

    public void createOrder(String customerName, String customerPhone, String inputDate, String inputTime,
            String nailService) {
        CustomerDao customerDao = factory.getCustomerDao();
        NailServiceDao serviceDao = factory.getNailServiceDao();
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
        CustomerDao customerDao = factory.getCustomerDao();
        Customer customer = new Customer();
        customer.setName(customerName);
        customer.setPhone(customerPhone);
        customerDao.insert(customer);
        return customerDao.getByPhone(customerPhone);
    }
}