package com.self.mybatis.jpa.ddl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DefaultMysqlDDLGenerator implements DDLGenerator {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMysqlDDLGenerator.class);

    @Override
    public boolean support(DBType type) {
        return type == DBType.MYSQL;
    }

    @Override
    public List<String> generateDDL(Connection connection) {
        try {
            List<String> sql = new ArrayList<>(16);
            DataBase dataBase = this.getDataBse(connection);
            EntitySqlD
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private DataBase getDataBse(Connection connection) throws SQLException {
        DataBase dataBase = new DataBase(connection.getCatalog());
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet resultSet = metaData.getTables(connection.getCatalog(), "%", "%", new String[]{"TABLE"});
        while (resultSet.next()) {
            String tableName = resultSet.getString("TABLE_NAME");
            DBTable table = new DBTable(tableName);
            ResultSet primaryKeyResultSet = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
            HashSet<String> primaryKeys = new HashSet<>();
            while (primaryKeyResultSet.next()) {
                String primaryKeyColumnName = primaryKeyResultSet.getString("COLUMN_NAME");
                primaryKeys.add(primaryKeyColumnName);
            }

            ResultSet columnResultSet = metaData.getColumns(connection.getCatalog(), "%", tableName, "%");

            while (columnResultSet.next()) {
                String columnName = columnResultSet.getString("COLUMN_NAME");
                String columnType = columnResultSet.getString("TYPE_NAME");
                int columnSize = columnResultSet.getInt("COLUMN_SIZE");
                boolean nullable = columnResultSet.getInt("NULLABLE") == 1;
                boolean isPrimaryKey = primaryKeys.contains(columnName);
                String defaultValue = columnResultSet.getString("COLUMN_DEF");
                table.addColumn(new TableColumn(columnName, columnType, columnSize, nullable, isPrimaryKey, defaultValue));
            }

            dataBase.addTable(table);
        }

        return dataBase;
    }
}
