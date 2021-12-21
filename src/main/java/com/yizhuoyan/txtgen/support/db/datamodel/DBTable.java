package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

@Data
public class DBTable {
    String category;
    String schema;
    String name;
    String displayName;
    String comment;
}