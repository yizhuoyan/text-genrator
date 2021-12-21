package com.yizhuoyan.txtgen.support.db;

import com.yizhuoyan.txtgen.support.db.datamodel.DBTable;
import com.yizhuoyan.txtgen.support.db.datamodel.DBTableColumn;
import com.yizhuoyan.txtgen.support.db.datamodel.DBTableColumnConstrain;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseDao  {
    private final Connection connection;
    public DatabaseDao(Connection connection) {
        this.connection=connection;
    }
    public List<String> selectAllCatalogs() throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        final List<String> result = new ArrayList<>();
        try (final ResultSet rs = metaData.getCatalogs()) {
            if (rs.next()) {
                result.add(rs.getString(1));
            }
        }
        return result;
    }

    public List<String> selectSchemaByCatalog(String catalog) throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        final List<String> result = new ArrayList<>();
        try (final ResultSet rs = metaData.getSchemas(catalog, null)) {
            if (rs.next()) {
                result.add(rs.getString(1));
            }
        }
        return result;
    }

    public List<String> selectTableByCatalogAndSchema(String catalog, String schema) throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        final List<String> result = new ArrayList<>();
        try (final ResultSet rs = metaData.getTables(catalog, schema, null, new String[]{"TABLE"})) {
            if (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }
        }
        return result;
    }
    /*
CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
     */
    public List<DBTableColumn> selectTableColumnsByCatalogAndSchema(String catalog, String schema, String tableName) throws SQLException {
        final DatabaseMetaData metaData = connection.getMetaData();
        final List<DBTableColumn> result = new ArrayList<>();
        final DBTable table=new DBTable();
        table.setSchema(schema);
        table.setCategory(catalog);
        table.setName(tableName);
        Set<String> primaryKeys = selectPrimaryKeyByTable(catalog, schema, tableName);

        try (final ResultSet rs = metaData.getColumns(catalog, schema, tableName, null)) {
            if (rs.next()) {
                DBTableColumn column=new DBTableColumn();
                column.setTable(table);
                column.setName(rs.getString("COLUMN_NAME"));
                column.setDataType(rs.getInt("DATA_TYPE"));
                column.setTypeName(rs.getString("TYPE_NAME"));
                column.setOrdinal(rs.getInt("ORDINAL_POSITION"));
                column.setComment(rs.getString("REMARKS"));
                column.setDefaultValue(rs.getString("COLUMN_DEF"));
                column.setDecimalDigits(rs.getInt("DECIMAL_DIGITS"));
                final DBTableColumnConstrain constrain=new DBTableColumnConstrain();
                constrain.setRequired("YES".equals(rs.getString("IS_NULLABLE")));
                constrain.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT")));
                constrain.setMaxCharLength(rs.getInt("COLUMN_SIZE"));
                constrain.setPk(primaryKeys.contains(column.getName()));
               // column.setConstrain(constrain);
                result.add(column);
            }
        }

        return result;
    }

    private Set<String> selectPrimaryKeyByTable(String catalog, String schema, String tableName)throws SQLException{
        final DatabaseMetaData metaData = connection.getMetaData();
        final Set<String> result = new HashSet<>(4,1);
        try (ResultSet rs = metaData.getPrimaryKeys(catalog, schema, tableName)){
            if(rs.next()){
                result.add(rs.getString("COLUMN_NAME"));
            }
        }
        return result;
    }
}