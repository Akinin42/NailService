package nailservice.dao;

import java.sql.Connection;

public interface Connector {
    
    Connection getConnection();    
}
