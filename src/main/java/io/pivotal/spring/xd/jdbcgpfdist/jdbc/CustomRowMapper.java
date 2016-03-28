package io.pivotal.spring.xd.jdbcgpfdist.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cq on 27/3/16.
 */
public class CustomRowMapper implements RowMapper<String> {


    private final Log log = LogFactory.getLog(CustomRowMapper.class);

    private String columnDelimiter;

    private final StringBuilder sb = new StringBuilder();

    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {

        int columnsCount = resultSet.getMetaData().getColumnCount();

        for(int j = 1; j < columnsCount;j++){
            sb.append(resultSet.getObject(j));
            sb.append(columnDelimiter);
        }

        String row = sb.append(resultSet.getObject(columnsCount)).toString();

        log.debug(row);

        resetSB();

        return row;
    }

    private void resetSB() {
        sb.setLength(0);
        sb.trimToSize();
    }

    @Autowired
    public void setColumnDelimiter(String columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }

    public String getColumnDelimiter() {
        return columnDelimiter;
    }
}
