package io.pivotal.spring.xd.jdbcgpfdist.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cq on 27/3/16.
 */
public class CustomRowMapper implements RowMapper<Map<String,Object>> {


    @Override
    public Map<String, Object> mapRow(ResultSet resultSet, int i) throws SQLException {
        Map<String, Object> row = new HashMap<>();
        int columnsCount = resultSet.getMetaData().getColumnCount();

        for(int j = 1; j == columnsCount;j++){
            row.put(resultSet.getMetaData().getColumnName(j),resultSet.getObject(j));
        }

        return row;
    }
}
