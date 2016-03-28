package io.pivotal.spring.xd.jdbcgpfdist.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cq on 27/3/16.
 */
public class CustomRowMapper implements RowMapper<String> {


    private final Log log = LogFactory.getLog(CustomRowMapper.class);


    @Override
    public String mapRow(ResultSet resultSet, int i) throws SQLException {
        StringBuilder sb = new StringBuilder();
        int columnsCount = resultSet.getMetaData().getColumnCount();

        for(int j = 1; j <= columnsCount;j++){
            sb.append(resultSet.getObject(j));
            sb.append("\t");

        }

        String row = sb.toString();
        log.info(row);
        return row;
    }
}
