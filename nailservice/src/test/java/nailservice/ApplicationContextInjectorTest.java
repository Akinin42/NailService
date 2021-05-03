package nailservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import nailservice.dao.Connector;
import nailservice.dao.impl.ConnectorPostgres;

class ApplicationContextInjectorTest {

    @Test
    void connectorPostgres_ShouldReturnConnectorPostgres() {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextInjector.class);        
        Connector actual = context.getBean(Connector.class);
        assertTrue(actual instanceof ConnectorPostgres);
    }
}
