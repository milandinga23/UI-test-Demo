package com.example.tests.dao.rowMappers;

import com.example.tests.dto.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        String firstName = rs.getString("emp_firstname");
        String lastName = rs.getString("emp_lastname");

        return new Employee(
                firstName != null ? firstName : "",
                lastName != null ? lastName : ""
        );
    }
}
