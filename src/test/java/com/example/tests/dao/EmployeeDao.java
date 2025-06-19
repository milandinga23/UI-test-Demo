package com.example.tests.dao;

import com.example.tests.dao.rowMappers.EmployeeRowMapper;
import com.example.tests.dto.Employee;
import com.example.tests.exceptions.EmployeeNotFoundException;
import com.example.tests.utils.DatabaseUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class EmployeeDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeDao() {
        this.jdbcTemplate = DatabaseUtil.getNamedJdbcTemplate();
    }

    public Employee getEmployeeNameById(String employeeId) throws EmployeeNotFoundException {
        String sql = "SELECT emp_firstname, emp_lastname FROM hs_hr_employee WHERE employee_id = :id";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", employeeId);

        try {
            return jdbcTemplate.queryForObject(sql, params, new EmployeeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new EmployeeNotFoundException("Zamestnanec s ID " + employeeId + " nebol nájdený v databáze.");
        }
    }
}
