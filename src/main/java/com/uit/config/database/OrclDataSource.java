package com.uit.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

//@Configuration
public class OrclDataSource {
	@Bean(name = "orclDSProperties")
	@Primary
	@ConfigurationProperties("spring.oracle.datasource")
	public DataSourceProperties orclDSProperties() {
		return new DataSourceProperties();
	}

	@Bean(name = "orclDS")
	@Primary
	@ConfigurationProperties(prefix = "spring.oracle.datasource.hikari")
	public DataSource orclDS( @Qualifier("orclDSProperties") DataSourceProperties dsProps ) {
		return dsProps.initializeDataSourceBuilder().type(HikariDataSource.class).build();
	}
}
