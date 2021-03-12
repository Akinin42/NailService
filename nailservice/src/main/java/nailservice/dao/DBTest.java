package nailservice.dao;

import nailservice.domain.Customer;
import nailservice.domain.NailService;

public class DBTest {

    public static void main(String[] args) {
//        CustomerDao customerDB = new CustomerDao();
//        Customer customer = customerDB.getByPhone("89236170788");
//        if(customer==null) {
//            System.out.println("I can't find this customer. Let's create him!");
//        }
////        Customer firstTestCustomer = new Customer();
////        firstTestCustomer.setName("Anastasia");
////        firstTestCustomer.setPhone("89236170788");        
////        customerDB.insert(firstTestCustomer);
//        customer = customerDB.getByPhone("89236170788");
//        System.out.println(customer);
////        customerDB.insert(firstTestCustomer);
////        Customer secondTestCustomer = new Customer();
////        secondTestCustomer.setName("Igor");
////        secondTestCustomer.setPhone("8951147856");
////        customerDB.insert(secondTestCustomer);
//        customer = customerDB.getByPhone("8951147856");
//        System.out.println(customer);
//        System.out.println(customerDB.getAll());
        NailServiceDB serviceDB = new NailServiceDB();
        NailService service = serviceDB.getByName("manicur");
        if (service == null) {
            System.out.println("I can't find this service. Let's create it!");
        }
//        NailService manicur = new NailService();
//        manicur.setName("manicur");
//        manicur.setCost(700);
//        serviceDB.insert(manicur);
        service = serviceDB.getByName("manicur");
        System.out.println(service);
        NailService pedicur = new NailService();
        pedicur.setName("pedicur");
        pedicur.setCost(1000);
        serviceDB.insert(pedicur);
        System.out.println(serviceDB.getAll());        
    }
}