package nailservice;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import nailservice.dao.Connector;
import nailservice.dao.impl.ConnectorPostgres;

@Configuration
@ComponentScan
public class ApplicationContextInjector {

    private static final String PROPERTIES_FILE = "postgresdb";

    @Bean
    @Scope("singleton")
    public Connector connectorPostgres() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        return new ConnectorPostgres(PROPERTIES_FILE, dataSource);
    }
}
