package nailservice;

import org.postgresql.ds.PGSimpleDataSource;

import nailservice.dao.Connector;
import nailservice.dao.CustomerDao;
import nailservice.dao.NailServiceDao;
import nailservice.dao.OrderDao;
import nailservice.dao.ScriptExecutor;
import nailservice.dao.impl.ConnectorPostgres;
import nailservice.dao.impl.CustomerDaoImpl;
import nailservice.dao.impl.NailServiceDaoImpl;
import nailservice.dao.impl.OrderDaoImpl;
import nailservice.domain.Administrator;
import nailservice.entity.NailService;
import nailservice.io.FileReader;

public class NailServiceLauncher {
    
    private static final String PROPERTIES_FILE = "postgresdb";
    private static final String SQL_SCRIPT_FILE = "schema.sql";
    
    public static void main(String[] args) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        Connector connector = new ConnectorPostgres(PROPERTIES_FILE, dataSource);
        FileReader reader = new FileReader();
        ScriptExecutor executor = new ScriptExecutor(connector, reader);
        executor.executeScript(SQL_SCRIPT_FILE);
        OrderDao orderDao = new OrderDaoImpl(connector);
        CustomerDao customerDao = new CustomerDaoImpl(connector);
        NailServiceDao nailServiceDao = new NailServiceDaoImpl(connector);
        Administrator administrator = new Administrator(orderDao, customerDao, nailServiceDao);
        NailService manicure = NailService.builder().withName("Manicure").withCost(800).withDuration(120).build();
        NailService pedicure = NailService.builder().withName("Pedicure").withCost(1000).withDuration(90).build();
        nailServiceDao.save(manicure);
        nailServiceDao.save(pedicure);
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "11:00:00", "Manicure");
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "15:00:00", "Manicure");
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "17:00:00", "Manicure");
    }
}