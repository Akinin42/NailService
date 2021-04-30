package nailservice.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import nailservice.dao.Connector;
import nailservice.dao.NailServiceDao;
import nailservice.entity.NailService;
import nailservice.exceptions.DaoException;

public class NailServiceDaoImpl extends AbstractCrudImpl<NailService> implements NailServiceDao {

    private static final String SAVE_QUERY = "INSERT INTO services (name, cost, duration) VALUES(?,?,?);";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM services WHERE service_id = ?;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM services ORDER BY service_id;";
    private static final String FIND_ALL_PAGINATION_QUERY = "SELECT * FROM services ORDER BY service_id LIMIT ? OFFSET ?;";
    private static final String DELETE_BY_ID_QUERY = "DELETE FROM services WHERE service_id = ?;";
    private static final String FIND_BY_NAME_QUERY = "SELECT * FROM services WHERE name = ?;";
    private static final String UPDATE_QUERY = "UPDATE services SET name = ?, cost = ?, duration = ? WHERE service_id = ?;";

    public NailServiceDaoImpl(Connector connector) {
        super(connector, SAVE_QUERY, FIND_BY_ID_QUERY, FIND_ALL_QUERY, FIND_ALL_PAGINATION_QUERY, DELETE_BY_ID_QUERY);
    }

    public Optional<NailService> findByName(String name) throws DaoException {
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_QUERY)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(createEntityFromResultSet(resultSet)) : Optional.empty();
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't return by name " + name, e);
        }
    }

    public void update(NailService nailService) throws DaoException {
        try (Connection connection = connector.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, nailService.getName());
            statement.setInt(2, nailService.getCost());
            statement.setInt(3, nailService.getDuration());
            statement.setInt(4, nailService.getId());
            statement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Can't update by " + nailService, e);
        }
    }

    @Override
    protected void insert(PreparedStatement statement, NailService nailService) throws SQLException {
        statement.setInt(1, nailService.getId());
        statement.setString(2, nailService.getName());
        statement.setInt(3, nailService.getCost());
        statement.setInt(4, nailService.getDuration());        
    }

    @Override
    protected NailService createEntityFromResultSet(ResultSet resultSet) throws SQLException {        
        return NailService.builder()
                .withId(resultSet.getInt("service_id"))
                .withName(resultSet.getString("name"))
                .withCost(resultSet.getInt("cost"))
                .withDuration(resultSet.getInt("duration"))
                .build();
    }
}
