package nailservice.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nailservice.dao.DAOFactory;
import nailservice.dao.OrderDao;
import nailservice.models.Order;

public class SheduleCreator {

    private DAOFactory factory = DAOFactory.getInstance();

    public Map<LocalTime, String> createShedule(String inputDate) {
        Map<LocalTime, String> shedule = new LinkedHashMap<>();
        List<Order> orders = getOrders(inputDate);
        int timeLimit = 9;
        if (orders.isEmpty()) {
            while (timeLimit < 20) {
                shedule.put(LocalTime.parse(String.format("%02d:00:00", timeLimit)), "free");
                timeLimit = timeLimit + 2;
            }
        }
        for (Order order : orders) {
            while (true) {
                if (order.getTime().getHour() >= timeLimit && (order.getTime().getHour() - timeLimit) > 1) {
                    shedule.put(LocalTime.parse(String.format("%02d:00:00", timeLimit)), "free");
                    timeLimit = timeLimit + 2;
                } else {
                    shedule.put(order.getTime(), order.getCustomer().getName());
                    timeLimit = (int) (order.getTime().getHour()
                            + (Math.ceil(((double) order.getNailService().getDuration() / 60))));
                    break;
                }
            }

        }
        if (timeLimit < 20) {
            shedule.put(LocalTime.parse(String.format("%02d:00:00", timeLimit)), "free");
        }
        return shedule;
    }

    private List<Order> getOrders(String inputDate) {
        OrderDao orderDao = factory.getOrderDao();
        LocalDate date = LocalDate.parse(inputDate);
        return orderDao.getOrdersOfDay(date);
    }
}