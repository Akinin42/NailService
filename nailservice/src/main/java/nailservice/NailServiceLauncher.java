package nailservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import nailservice.context.ApplicationContextInjector;
import nailservice.dao.NailServiceDao;
import nailservice.dao.ScriptExecutor;
import nailservice.domain.Administrator;
import nailservice.entity.NailService;

public class NailServiceLauncher {    

    private static final String SQL_SCRIPT_FILE = "schema.sql";
    
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationContextInjector.class);
        ScriptExecutor executor = context.getBean(ScriptExecutor.class);
        executor.executeScript(SQL_SCRIPT_FILE);
        NailServiceDao nailServiceDao = context.getBean(NailServiceDao.class);
        Administrator administrator = context.getBean(Administrator.class);
        NailService manicure = NailService.builder().withName("Manicure").withCost(800).withDuration(120).build();
        NailService pedicure = NailService.builder().withName("Pedicure").withCost(1000).withDuration(90).build();
        nailServiceDao.save(manicure);
        nailServiceDao.save(pedicure);
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "11:00:00", "Manicure");
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "15:00:00", "Manicure");
        administrator.createOrder("Igor Akinin", "89236170788", "2021-05-01", "17:00:00", "Manicure");
    }
}