package com.self.mybatis.jpa.ddl;

import java.sql.Connection;
import java.util.List;

public interface DDLGenerator {

    boolean support(DBType type);

    List<String> generateDDL(Connection connection);
}
