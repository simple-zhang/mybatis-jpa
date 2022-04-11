package com.self.mybatis.jpa.meta;

public class Index {

    private String name;
    private String columns;
    private boolean unique;

    public Index() {
    }

    public Index(String name, String columns, boolean unique) {
        this.name = name;
        this.columns = columns;
        this.unique = unique;
    }
}
