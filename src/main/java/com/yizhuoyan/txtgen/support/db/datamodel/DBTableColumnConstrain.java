package com.yizhuoyan.txtgen.support.db.datamodel;

import lombok.Data;

@Data
public class DBTableColumnConstrain extends DataConstrain{
    boolean pk;
    DBTableColumn foreignTableColumn;
    String check;
    boolean autoIncrement;
}