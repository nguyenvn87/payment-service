package com.uit.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MySqlConfig {

	@Bean
	@Primary
	@ConfigurationProperties("spring.mysql.datasource")
	public DataSourceProperties mysqlDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "mysqlDataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public DataSource mysqlDataSource() {
		return this.mysqlDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
