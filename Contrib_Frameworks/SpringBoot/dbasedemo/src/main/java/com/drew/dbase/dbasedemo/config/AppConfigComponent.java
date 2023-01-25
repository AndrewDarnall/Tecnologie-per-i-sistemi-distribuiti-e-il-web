package com.drew.dbase.dbasedemo.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppConfigComponent {
    
    private final JdbcTemplate jdbctemplate;

    public AppConfigComponent(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }

}
// The main config is to 'link' the proper driver, otherwise Spring Boot raises hell!