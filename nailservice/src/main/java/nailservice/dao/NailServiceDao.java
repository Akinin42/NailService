package nailservice.dao;

import java.util.List;

import nailservice.domain.NailService;

public interface NailServiceDao {
    
    public NailService getById(int serviceId) throws DAOException;
    
    public void insert(NailService service) throws IllegalArgumentException, DAOException;
    
    public void update(NailService service) throws IllegalArgumentException, DAOException;
    
    public List<NailService> getAll() throws DAOException;
}