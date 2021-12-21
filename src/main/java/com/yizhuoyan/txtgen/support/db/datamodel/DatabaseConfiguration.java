package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

@Data
public class DatabaseConfiguration {
    String url;
    String driverName;
    String userName;
    String password;
}