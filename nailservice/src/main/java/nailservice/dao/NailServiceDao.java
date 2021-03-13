package nailservice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nailservice.domain.NailService;

public class NailServiceDao {

    private DAOFactory factory = DAOFactory.getInstance();

    public void insert(NailService service) throws IllegalArgumentException, DAOException {
        String sql = "INSERT INTO services (name, cost) VALUES(?,?);";
        if (getByName(service.getName()) != null) {
            throw new IllegalArgumentException("NailService is already created, y can only update it!");
        }
        Object[] values = { service.getName(), service.getCost() };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating service failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    service.setId(generatedKeys.getInt(1));
                } else {
                    throw new DAOException("Creating service failed, no generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    public List<NailService> getAll() throws DAOException {
        List<NailService> services = new ArrayList<>();
        String sql = "SELECT service_id, name, cost FROM services ORDER BY service_id";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                services.add(init(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return services;
    }

    public NailService getByName(String name) throws DAOException {
        NailService service = null;
        String sql = "SELECT * FROM services WHERE name = ?";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, name);
                ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                service = init(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return service;
    }
    public NailService getById(int id) throws DAOException {
        NailService service = null;
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, false, id);
                ResultSet resultSet = statement.executeQuery();) {
            if (resultSet.next()) {
                service = init(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return service;
    }

    public void update(NailService service) throws DAOException {
        if (service.getId() == null) {
            throw new IllegalArgumentException("NailService is not created yet.");
        }
        String sql = "UPDATE services SET name = ?, cost = ? WHERE service_id = ?";
        Object[] values = { service.getName(), service.getCost(), service.getId() };
        try (Connection connection = factory.getConnection();
                PreparedStatement statement = DAOUtil.prepareStatement(connection, sql, true, values);) {
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Updating nail service failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private NailService init(ResultSet resultSet) throws SQLException {
        NailService service = new NailService();
        service.setId(resultSet.getInt("service_id"));
        service.setName(resultSet.getString("name"));
        service.setCost(resultSet.getInt("cost"));
        return service;
    }
}