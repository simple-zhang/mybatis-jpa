package com.self.mybatis.jpa.ddl;

import java.util.Map;
import java.util.TreeMap;

public class DataBase {

    private String dataBase;

    private Map<String, DBTable> tableMap = new TreeMap<>();

    public DataBase() {
    }

    public DataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public Map<String, DBTable> getTableMap() {
        return tableMap;
    }

    public void setTableMap(Map<String, DBTable> tableMap) {
        this.tableMap = tableMap;
    }

    public int size() {
        return this.tableMap.size();
    }

    public DBTable getTable(String tableName) {
        return this.tableMap.get(tableName);
    }

    public void addTable(DBTable table) {
        this.tableMap.put(table.getTableName(), table);
    }
}
