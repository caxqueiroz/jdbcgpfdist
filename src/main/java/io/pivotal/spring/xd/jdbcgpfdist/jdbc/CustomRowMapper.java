package io.pivotal.spring.xd.jdbcgpfdist.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cq on 27/3/16.
 */
public class CustomRowMapper implements RowMapper<String> {


    private final Log log = LogFactory.getLog(CustomRowMapper.class);

    private String columnDelimiter;


    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        StringBuilder sb = new StringBuilder();
        int columnsCount = resultSet.getMetaData().getColumnCount();

        for(int j = 1; j < columnsCount;j++){
            sb.append(columnDelimiter);
            sb.append(resultSet.getObject(j));
        }

        String row = sb.append(resultSet.getObject(columnsCount)).append("\n").toString();
        log.info(row);
        return row;
    }

    public String getColumnDelimiter() {
        return columnDelimiter;
    }

    @Autowired
    public void setColumnDelimiter(String columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }
}
