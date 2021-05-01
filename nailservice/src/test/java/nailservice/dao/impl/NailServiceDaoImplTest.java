package nailservice.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nailservice.dao.Connector;
import nailservice.entity.NailService;
import nailservice.exceptions.DaoException;
import nailservice.utils.TestUtil;

class NailServiceDaoImplTest {

    private static NailServiceDaoImpl nailServiceDao;
    
    @BeforeAll
    static void init() {
        Connector connector = new ConnectorH2("h2");
        nailServiceDao = new NailServiceDaoImpl(connector);
    }

    @BeforeEach
    void createTablesAndData() {
        TestUtil.executeScript("\\createtesttables.sql");
    }
    
    @Test
    void save_ShouldSaveNailServiceToDB_WhenInputValidNailService() {
        int numberRowBeforeSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM services;");
        NailService nailService = NailService.builder()
                .withId(5)
                .withName("test service")
                .withCost(1)
                .withDuration(10)
                .build();
        nailServiceDao.save(nailService);
        int numberRowAfterSave = TestUtil.getNumberRow("SELECT COUNT(*) FROM services;");
        assertEquals(numberRowBeforeSave + 1, numberRowAfterSave);
    }

    @Test
    void save_ShouldThrowDaoException_WhenInputInvalidCustomer() {
        NailService invalidNailService = NailService.builder()
                .withId(5)
                .withName(null)
                .withCost(1)
                .withDuration(10)
                .build();               
        assertThrows(DaoException.class, () -> nailServiceDao.save(invalidNailService));
    }
    
    @Test
    void save_ShouldThrowDaoException_WhenInputNull() {
        assertThrows(DaoException.class, () -> nailServiceDao.save(null));
    }
    
    @Test
    void findById_ShouldReturnEmptyOptional_WhenInputIdNotExists() {
        Optional<NailService> expected = Optional.empty();
        Optional<NailService> actual = nailServiceDao.findById(10);
        assertEquals(expected, actual);
    }
    
    @Test
    void findById_ShouldReturnExpectedNailService_WhenInputExistentId() {
        NailService expected = createNailServices().get(0);
        NailService actual = nailServiceDao.findById(1).get();
        assertEquals(expected, actual);
    }   

    @Test
    void findById_ShouldThrowDaoException_WhenConnectNull() {
        NailServiceDaoImpl nailServiceDaoWithoutConnector = new NailServiceDaoImpl(null);
        assertThrows(DaoException.class, () -> nailServiceDaoWithoutConnector.findById(1));
    }
    
    @Test
    void findAll_ShouldReturnExpectedNailServices_WhenNailServicesTableNotEmpty() {
        List<NailService> expected = createNailServices();
        List<NailService> actual = nailServiceDao.findAll();
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldReturnEmptyList_WhenNailServicesTableEmpty() {
        TestUtil.queryToDB("DELETE FROM services");
        List<NailService> expected = new ArrayList<>();
        List<NailService> actual = nailServiceDao.findAll();
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldThrowDaoException_WhenConnectNull() {
        NailServiceDaoImpl nailServiceDaoWithoutConnector = new NailServiceDaoImpl(null);
        assertThrows(DaoException.class, () -> nailServiceDaoWithoutConnector.findAll());
    }
    
    @Test
    void findAll_ShouldReturnExpectedNailServices_WhenInputLimitAndOffset() {
        List<NailService> expected = createNailServices();
        expected.remove(1);
        List<NailService> actual = nailServiceDao.findAll(1, 0);
        assertEquals(expected, actual);
    }
    
    @Test
    void findAll_ShouldReturnEmptyList_WhenInputOffsetMoreTableSize() {
        List<NailService> expected = new ArrayList<>();
        List<NailService> actual = nailServiceDao.findAll(2, 10);
        assertEquals(expected, actual);
    }
    
    @Test
    void deleteById_ShouldDeleteNailServiceWithInputId_WhenThisNailServiceExists() {
        int numberRowBeforeDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM services;");
        nailServiceDao.deleteById(1);
        int numberRowAfterDelete = TestUtil.getNumberRow("SELECT COUNT(*) FROM services;");
        assertEquals(numberRowBeforeDelete - 1, numberRowAfterDelete);
    }
    
    @Test
    void deleteById_ShouldThrowDaoException_WhenConnectNull() {
        NailServiceDaoImpl nailServiceDaoWithoutConnector = new NailServiceDaoImpl(null);
        assertThrows(DaoException.class, () -> nailServiceDaoWithoutConnector.deleteById(1));
    }
    
    @Test
    void findByName_ShouldReturnEmptyOptional_WhenInputIdNotExists() {
        Optional<NailService> expected = Optional.empty();
        Optional<NailService> actual = nailServiceDao.findByName("not exist name");
        assertEquals(expected, actual);
    }
    
    @Test
    void findByName_ShouldReturnExpectedNailService_WhenInputExistentId() {
        NailService expected = createNailServices().get(0);
        NailService actual = nailServiceDao.findByName("Manicure").get();
        assertEquals(expected, actual);
    }   

    @Test
    void findByName_ShouldThrowDaoException_WhenConnectNull() {
        NailServiceDaoImpl nailServiceDaoWithoutConnector = new NailServiceDaoImpl(null);
        assertThrows(DaoException.class, () -> nailServiceDaoWithoutConnector.findByName("Manicure"));
    }
    
    @Test
    void update_ShouldUpdateNailService_WhenInputNailServiceExistsInDB(){        
        NailService serviceForUpdate = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(1500)
                .withDuration(60)
                .build();
        NailService expected = serviceForUpdate;
        nailServiceDao.update(serviceForUpdate);
        NailService actual = nailServiceDao.findById(1).get();
        assertEquals(expected, actual);
    }
    
    @Test
    void update_ShouldThrowDaoException_WhenConnectNull(){
        NailServiceDaoImpl nailServiceDaoWithoutConnector = new NailServiceDaoImpl(null);
        NailService serviceForUpdate = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(1500)
                .withDuration(60)
                .build();
        assertThrows(DaoException.class, () -> nailServiceDaoWithoutConnector.update(serviceForUpdate));
    }
    
    private List<NailService> createNailServices() {
        List<NailService> nailServices = new ArrayList<>();
        NailService nailService = NailService.builder()
                .withId(1)
                .withName("Manicure")
                .withCost(800)
                .withDuration(120)
                .build();
        nailServices.add(nailService);
        nailService = NailService.builder()
                .withId(2)
                .withName("Pedicure")
                .withCost(1000)
                .withDuration(90)
                .build();
        nailServices.add(nailService);
        return nailServices;
    }
}
