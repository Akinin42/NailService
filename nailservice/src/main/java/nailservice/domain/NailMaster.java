package nailservice.domain;

import java.util.Date;
import java.util.Map;

public class NailMaster {

    private String name;
    private String phone;
    private Map<Date, Order> dayShedule;
    private Map<Date, Order> weekShedule;

    public NailMaster(String name) {
        super();
        this.name = name;
    }

    public Map<Date, Order> createDayShedule(Date date) {
        return null;
    }
    
    public Map<Date, Order> createWeekShedule(Date date) {
        return null;
    }
}