package nailservice.domain;

import java.util.ArrayList;
import java.util.List;

import nailservice.models.Order;

public class LauncherTest {

    public static void main(String[] args) {
        SheduleCreator creator = new SheduleCreator();
//        List<Order> orders = new ArrayList<>();        
//        System.out.println(creator.createShedule(orders));
//        Administrator admin = new Administrator();
//        admin.createOrder("Igor", "8951147856", "2021-03-08", "17:00:00", "manicure");
//        admin.createOrder("Igor", "8951147856", "2021-03-08", "10:00:00", "manicure");
//        admin.createOrder("Igor", "8951147856", "2021-03-08", "19:00:00", "manicure");
//        orders = admin.getShedule("2021-03-08");
//        admin.createOrder("Igor", "8951147856", "2021-03-17", "16:00:00", "manicure");
//        orders = admin.getDayOrders("2021-03-17");
        System.out.println(creator.createShedule("2021-03-17"));
        
    }
}