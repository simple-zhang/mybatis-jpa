package com.self.mybatis.jpa.ddl;

public class TableColumn {

    private String name;

    private String type;

    private Integer length;

    private boolean nullable;

    private boolean primaryKey;

    private String defaultValue;

    public TableColumn() {
    }

    public TableColumn(String name, String type, Integer length, boolean nullable, boolean primaryKey, String defaultValue) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.nullable = nullable;
        this.primaryKey = primaryKey;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
