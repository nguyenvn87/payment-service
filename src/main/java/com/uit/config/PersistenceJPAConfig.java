package com.uit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = {"com.uit.podcast.repository"})
@EntityScan({ "com.uit.entity","com.uit.entity.*" })
public class PersistenceJPAConfig {

    //@Autowired
    //@Qualifier("orclDS")
    //DataSource dataSource;

    @Autowired
    @Qualifier("mysqlDataSource")
    DataSource dataSource;

    @Autowired
    JpaProperties jpaProperties;
}
