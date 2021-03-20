package nailservice.domain;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nailservice.models.Order;

public class SheduleCreator {

    public Map<LocalTime, String> createShedule(List<Order> orders) {
        Map<LocalTime, String> shedule = new LinkedHashMap<>();
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
}