package nailservice.domain;

import java.util.Date;
import java.util.Map;

public class Administrator {
    
    public Map<Date, Order> getMasterDayShedule(String masterName, Date date) {
        NailMaster master = new NailMaster(masterName);
        return master.createDayShedule(date);
    }
    
    public Map<Date, Order> getMasterWeekShedule(String masterName, Date date) {
        NailMaster master = new NailMaster(masterName);
        return master.createWeekShedule(date);
    }
    
    public void createOrder(String customerName, String customerPhone, Date date, NailService service) {
        
    }
    
    private void createCustomer(String customerName, String customerPhone) {
        
    }
}