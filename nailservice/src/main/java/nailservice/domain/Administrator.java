package nailservice.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Administrator {
    
    private List<Order> masterShedule;
    private Map<Date, List<Order>> masterWeekShedule;
    
    public List<Order> getShedule(Date date) {
        
        return masterShedule;
    }
    
    public Map<Date, List<Order>> getWeekShedule(Date date) {
        
        return masterWeekShedule;
    }
    
    public void createOrder(String customerName, String customerPhone, Date date, NailService service) {
        
    }
    
    private void createCustomer(String customerName, String customerPhone) {
        
    }
}