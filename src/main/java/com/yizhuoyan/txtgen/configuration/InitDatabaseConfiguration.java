package com.yizhuoyan.txtgen.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;

import java.sql.*;

//@Component
@Slf4j
@RequiredArgsConstructor
public class InitDatabaseConfiguration  {
    private final JdbcTemplate jdbcTemplate;
    @EventListener
    public void initDatabase(ApplicationReadyEvent event)throws Exception{
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();

        if(log.isDebugEnabled()){
            log.debug("判断数据库是否存在，否则创建");
        }

       // Integer xxxCount = jdbcTemplate.queryForObject("select count(1) from xxxx", Integer.class);
        final Connection connection=DataSourceUtils.getConnection(jdbcTemplate.getDataSource());

        if(!isTableExists(connection, "xxx")){
            System.out.println("表不存在");
        }
    }

    /**
     * 判断是否以及初始化了数据库
     */
    private boolean isTableExists(Connection connection,String tableName)throws SQLException{
        try {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery("desc " + tableName)) {
                    return true;
                }
            }
        }catch (Exception e){
            return false;
        }

    }
}