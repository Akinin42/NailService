package nailservice.dao;

import java.util.Optional;
import nailservice.entity.NailService;

public interface NailServiceDao extends CrudDao<NailService, Integer> {

    Optional<NailService> findByName(String name);
    
    void update(NailService nailService);
}
