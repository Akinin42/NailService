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
    private Map<LocalTime, String> shedule = new LinkedHashMap<>();
    private static final int START_WORK = 9;
    private static final int END_WORK = 20;
    private static final int AVERAGE_SERVICE_TIME = 2;
    private int timeLimit = START_WORK;

    public Map<LocalTime, String> createShedule(String inputDate) {
        List<Order> orders = getOrders(inputDate);
        if (orders.isEmpty()) {
            while (timeLimit < END_WORK) {
                addFreeLine();
            }
        }
        for (Order order : orders) {
            while (true) {
                if (order.getTime().getHour() >= timeLimit && (order.getTime().getHour() - timeLimit) > 1) {
                    addFreeLine();
                } else {
                    shedule.put(order.getTime(), order.getCustomer().getName());
                    int serviceDuration = (int) Math.ceil(((double) order.getNailService().getDuration() / 60));
                    timeLimit = (order.getTime().getHour() + serviceDuration);
                    break;
                }
            }

        }
        if (timeLimit < END_WORK) {
            addFreeLine();
        }
        return shedule;
    }

    private void addFreeLine() {
        shedule.put(LocalTime.parse(String.format("%02d:00:00", timeLimit)), "free");
        timeLimit = timeLimit + AVERAGE_SERVICE_TIME;
    }

    private List<Order> getOrders(String inputDate) {
        OrderDao orderDao = factory.getOrderDao();
        LocalDate date = LocalDate.parse(inputDate);
        return orderDao.getOrdersOfDay(date);
    }
}