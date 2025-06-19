package com.example.tests.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class DatabaseUtil {

    private static final JdbcTemplate jdbcTemplate;
    private static final NamedParameterJdbcTemplate namedJdbcTemplate;

    static {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                "jdbc:mysql://localhost:3306/orangehrm",
                "root", // DB user
                ""      // DB password
        );
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        jdbcTemplate = new JdbcTemplate(dataSource);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public static JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public static NamedParameterJdbcTemplate getNamedJdbcTemplate() {
        return namedJdbcTemplate;
    }
}
