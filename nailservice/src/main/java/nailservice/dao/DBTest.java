package nailservice.dao;

import nailservice.models.NailService;

public class DBTest {

    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        NailServiceDao serviceDao = factory.getNailServiceDao();
        NailService manicureCoated = new NailService();
        manicureCoated.setName("coated manicure");
        manicureCoated.setCost(950);
        manicureCoated.setDuration(180);
        serviceDao.insert(manicureCoated);
    }
}