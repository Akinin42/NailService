package nailservice.dao;

import java.util.List;

import nailservice.domain.Administrator;
import nailservice.models.Order;

public class DBTest {

    public static void main(String[] args) {
        /*
         * CustomerDao customerDB = new CustomerDao(); Customer customer =
         * customerDB.getByPhone("89236170788"); if (customer == null) {
         * System.out.println("I can't find this customer. Let's create him!"); }
         * Customer firstTestCustomer = new Customer();
         * firstTestCustomer.setName("Anastasia");
         * firstTestCustomer.setPhone("89236170788");
         * customerDB.insert(firstTestCustomer); customer =
         * customerDB.getByPhone("89236170788"); System.out.println(customer);
         * customerDB.insert(firstTestCustomer); Customer secondTestCustomer = new
         * Customer(); secondTestCustomer.setName("Igor");
         * secondTestCustomer.setPhone("8951147856");
         * customerDB.insert(secondTestCustomer); customer =
         * customerDB.getByPhone("8951147856"); System.out.println(customer);
         * System.out.println(customerDB.getAll()); NailServiceDao serviceDB = new
         * NailServiceDao(); NailService service = serviceDB.getByName("manicur"); if
         * (service == null) {
         * System.out.println("I can't find this service. Let's create it!"); }
         * NailService manicur = new NailService(); manicur.setName("manicur");
         * manicur.setCost(700); serviceDB.insert(manicur); service =
         * serviceDB.getByName("manicur"); System.out.println(service); NailService
         * pedicur = new NailService(); pedicur.setName("pedicur");
         * pedicur.setCost(1000); serviceDB.insert(pedicur);
         * System.out.println(serviceDB.getAll()); NailServiceDao serviceDB = new
         * NailServiceDao(); NailService manicur = serviceDB.getByName("manicur");
         * manicur.setCost(1500); serviceDB.update(manicur); CustomerDao customerDao =
         * new CustomerDao(); System.out.println(customerDao.getByPhone("114455146"));
         */
        Administrator admin = new Administrator();
//        admin.createOrder("Petr", "777888555", "2021-03-08", "13:00:00", "manicur");
//        admin.createOrder("Anastasia", "89236170788", "2021-03-08", "09:00:00", "manicur");
//        admin.createOrder("Igor", "8951147856", "2021-03-08", "17:00:00", "pedicur");
//        admin.createOrder("Igor", "8951147856", "2021-03-10", "17:00:00", "pedicur");
//        admin.createOrder("Igor", "8951147856", "2021-03-11", "17:00:00", "pedicur");
//        admin.createOrder("Igor", "8951147856", "2021-03-12", "17:00:00", "pedicur");
//        admin.createOrder("Igor", "8951147856", "2021-03-14", "17:00:00", "pedicur");
//        System.out.println(admin.getShedule("2021-03-08"));
        List<Order> weekShedule = admin.getWeekShedule("2021-03-08");
        for(Order order:weekShedule) {
            System.out.println(order);
        }
        admin.deleteOrder("2021-03-10", "17:00:00");
        weekShedule = admin.getWeekShedule("2021-03-08");
        for(Order order:weekShedule) {
            System.out.println(order);
        }
    }
}