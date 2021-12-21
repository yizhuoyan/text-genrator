package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

@Data
public class DBTableColumn {
    String name;
    String comment;
    String displayName;
    String typeName;
    //java.sql.
    int dataType;
    DBTable table;
    int ordinal;
    String defaultValue;
    int decimalDigits;
}