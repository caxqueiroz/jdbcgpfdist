package io.pivotal.spring.xd.jdbcgpfdist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by cq on 27/3/16.
 */
@Configuration
public class Config {

    private DataSource jdbcDataSource;

    @Autowired
    public void setJdbcDataSource(DataSource jdbcDataSource){
        this.jdbcDataSource = jdbcDataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(jdbcDataSource);
    }



}
