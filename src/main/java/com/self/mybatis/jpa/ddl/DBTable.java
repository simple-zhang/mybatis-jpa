package com.self.mybatis.jpa.ddl;

import java.util.Map;
import java.util.TreeMap;

public class DBTable {

    private String tableName;

    private Map<String, TableColumn> tableColumnMap = new TreeMap<>();

    public DBTable() {
    }

    public DBTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, TableColumn> getTableColumnMap() {
        return tableColumnMap;
    }

    public void setTableColumnMap(Map<String, TableColumn> tableColumnMap) {
        this.tableColumnMap = tableColumnMap;
    }

    public TableColumn getColumn(String columnName) {
        return this.tableColumnMap.get(columnName);
    }

    public void addColumn(TableColumn column) {
        tableColumnMap.put(column.getName(), column);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(tableName).append("{\n");
        for (TableColumn column : this.tableColumnMap.values()) {
            builder.append("\t ")
                    .append(column.toString())
                    .append(",\n");
        }
        builder.append("}\n");
        return builder.toString();
    }
}
