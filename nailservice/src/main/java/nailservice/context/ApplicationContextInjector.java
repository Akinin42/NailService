package nailservice.context;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import nailservice.io.FileReader;

@Configuration
public class ApplicationContextInjector {

    private static final String PROPERTIES_FILE = "postgresdb";

    @Bean
    public Connector connectorPostgres() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        return new ConnectorPostgres(PROPERTIES_FILE, dataSource);
    }

    @Bean
    public FileReader fileReader() {
        return new FileReader();
    }

    @Bean
    public ScriptExecutor scriptExecutor() {
        return new ScriptExecutor(connectorPostgres(), fileReader());
    }

    @Bean
    public CustomerDao customerDao() {
        return new CustomerDaoImpl(connectorPostgres());
    }

    @Bean
    public OrderDao orderDao() {
        return new OrderDaoImpl(connectorPostgres());
    }

    @Bean
    public NailServiceDao nailServiceDao() {
        return new NailServiceDaoImpl(connectorPostgres());
    }
    
    @Bean
    public Administrator administrator() {
        return new Administrator(orderDao(), customerDao(), nailServiceDao());
    }
}
