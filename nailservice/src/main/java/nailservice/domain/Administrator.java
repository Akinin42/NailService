package nailservice.domain;

import java.util.Calendar;

import java.util.List;
import java.util.Map;

public class Administrator {
    
    private List<Order> masterShedule;
    private Map<Calendar, List<Order>> masterWeekShedule;
    
    public List<Order> getShedule(Calendar date) {
        
        return masterShedule;
    }
    
    public Map<Calendar, List<Order>> getWeekShedule(Calendar date) {
        
        return masterWeekShedule;
    }
    
    public void createOrder(String customerName, String customerPhone, Calendar date, NailService service) {
        
    }
    
    private void createCustomer(String customerName, String customerPhone) {
        
    }
}