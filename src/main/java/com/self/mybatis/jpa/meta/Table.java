package com.self.mybatis.jpa.meta;

public class Table {
    public static final String ALL_COLUMNS = "all_columns";
    public static final String ALL_COLUMNS_UPPERCASE = ALL_COLUMNS.toUpperCase();

    private String name;
    private String catalog;
    private Class entity;
    private String schema;
    private LIst<Index> indexes;

    public String getName() {

    }
}
