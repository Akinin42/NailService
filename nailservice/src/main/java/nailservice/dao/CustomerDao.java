package nailservice.dao;

import java.util.Optional;
import nailservice.entity.Customer;

public interface CustomerDao extends CrudDao<Customer, Integer> {
    
    Optional<Customer> findByPhone(String phone);

}
