package com.lhind.internship.jdbc.repository;

import com.lhind.internship.jdbc.mapper.EmployeeMapper;
import com.lhind.internship.jdbc.model.Employee;
import com.lhind.internship.jdbc.model.enums.EmployeeQuery;
import com.lhind.internship.jdbc.util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository implements Repository<Employee, Integer> {
    private EmployeeMapper employeeMapper = EmployeeMapper.getInstance();

    @Override
    public List<Employee> findAll() {
        final List<Employee> response = new ArrayList<>();
        try (final Connection connection = JdbcConnection.connect(); final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.SELECT_ALL.getQuery())) {
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                response.add(employeeMapper.toEntity(result));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return response;
    }

    @Override
    public Optional<Employee> findById(final Integer id) {
        try (final Connection connection = JdbcConnection.connect(); final PreparedStatement statement = connection.prepareStatement(EmployeeQuery.SELECT_BY_ID.getQuery())) {
            statement.setInt(1, id);

            final ResultSet result = statement.executeQuery();

            if (result.next()) {
                final Employee employee = employeeMapper.toEntity(result);
                return Optional.of(employee);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(final Integer integer) {
        return findById(integer).isPresent();
    }

    @Override
    public Employee save(final Employee employee) {
        try (Connection connection = JdbcConnection.connect(); PreparedStatement statement = connection.prepareStatement(EmployeeQuery.INSERT_OR_UPDATE_EMPLOYEE.getQuery())) {

            statement.setInt(1, employee.getEmployeeNumber());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getExtension());
            statement.setString(5, employee.getEmail());
            statement.setString(6, employee.getOfficeCode());

            if (employee.getReportsTo() != null) {
                statement.setInt(7, employee.getReportsTo());
            } else {
                statement.setNull(7, java.sql.Types.INTEGER);
            }

            statement.setString(8, employee.getJobTitle());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                return findById(employee.getEmployeeNumber()).orElse(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(final Integer employeeId) {
        try (Connection connection = JdbcConnection.connect(); PreparedStatement statement = connection.prepareStatement(EmployeeQuery.DELETE_BY_ID.getQuery())) {

            statement.setInt(1, employeeId);
            statement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error deleting employee with ID " + employeeId + ": " + e.getMessage());
        }
    }
}
