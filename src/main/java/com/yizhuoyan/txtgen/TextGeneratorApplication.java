package com.yizhuoyan.txtgen;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.concurrent.locks.LockSupport;

@SpringBootApplication
@Slf4j
public class TextGeneratorApplication  {

	public static void main(String[] args) {
		 new SpringApplication(TextGeneratorApplication.class)
				 .run(args);
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.setMaxRows(1000);
		jdbcTemplate.setFetchSize(10);
		jdbcTemplate.setQueryTimeout(1000);
		jdbcTemplate.setResultsMapCaseInsensitive(true);
		jdbcTemplate.setIgnoreWarnings(false);
		jdbcTemplate.setSkipResultsProcessing(true);
		jdbcTemplate.setSkipUndeclaredResults(true);
		return jdbcTemplate;
	}

}