package io.pivotal.spring.xd.jdbcgpfdist.config;

import org.springframework.xd.module.options.spi.ModuleOption;

/**
 * Created by cq on 27/3/16.
 */
public class JDBCGPFDISTOptionsMetadata {

    private int port = 0;

    private int flushCount = 100;

    private int flushTime = 2;

    private int batchTimeout = 4;

    private int batchCount = 100;

    private int batchPeriod = 10;

    private String dbName = "gpadmin";

    private String dbUser = "gpadmin";

    private String dbPassword = "gpadmin";

    private String dbHost = "localhost";

    private int dbPort = 5432;

    private String controlFile;

    private String delimiter = "\n";

    private Character columnDelimiter;

    private String mode;

    private String matchColumns;

    private String updateColumns;

    private String table;

    private int rateInterval = 0;

    private String sqlBefore;

    private String sqlAfter;

    private String sqlQuery;

    private String jdbcUsername;
    private String jdbcpassword;
    private String jdbcUrl;
    private String jdbDriverClassName;


    public int getPort() {
        return port;
    }

    @ModuleOption("gpfdist listen port")
    public void setPort(int port) {
        this.port = port;
    }

    public int getFlushCount() {
        return flushCount;
    }

    @ModuleOption("flush item count")
    public void setFlushCount(int flushCount) {
        this.flushCount = flushCount;
    }

    public int getFlushTime() {
        return flushTime;
    }

    @ModuleOption("flush item time")
    public void setFlushTime(int flushTime) {
        this.flushTime = flushTime;
    }

    public int getBatchTimeout() {
        return batchTimeout;
    }

    @ModuleOption("batch timeout")
    public void setBatchTimeout(int batchTimeout) {
        this.batchTimeout = batchTimeout;
    }

    public int getBatchPeriod() {
        return batchPeriod;
    }

    @ModuleOption("batch period")
    public void setBatchPeriod(int batchPeriod) {
        this.batchPeriod = batchPeriod;
    }

    public int getBatchCount() {
        return batchCount;
    }

    @ModuleOption("batch count")
    public void setBatchCount(int batchCount) {
        this.batchCount = batchCount;
    }

    public String getDbName() {
        return dbName;
    }

    @ModuleOption("database name")
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    @ModuleOption("database user")
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    @ModuleOption("database password")
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getDbHost() {
        return dbHost;
    }

    @ModuleOption("database host")
    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public int getDbPort() {
        return dbPort;
    }

    @ModuleOption("database port")
    public void setDbPort(int dbPort) {
        this.dbPort = dbPort;
    }

    public String getControlFile() {
        return controlFile;
    }

    @ModuleOption("path to yaml control file")
    public void setControlFile(String controlFile) {
        this.controlFile = controlFile;
    }

    public String getDelimiter() {
        return delimiter;
    }

    @ModuleOption("data line delimiter")
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public Character getColumnDelimiter() {
        return columnDelimiter;
    }

    @ModuleOption("column delimiter")
    public void setColumnDelimiter(Character columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }

    public String getMode() {
        return mode;
    }

    @ModuleOption("mode, either insert or update")
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUpdateColumns() {
        return updateColumns;
    }

    @ModuleOption("update columns with update")
    public void setUpdateColumns(String updateColumns) {
        this.updateColumns = updateColumns;
    }

    public String getMatchColumns() {
        return matchColumns;
    }

    @ModuleOption("match columns with update")
    public void setMatchColumns(String matchColumns) {
        this.matchColumns = matchColumns;
    }

    public String getTable() {
        return table;
    }

    @ModuleOption("target database table")
    public void setTable(String table) {
        this.table = table;
    }

    public int getRateInterval() {
        return rateInterval;
    }

    @ModuleOption("enable transfer rate interval")
    public void setRateInterval(int rateInterval) {
        this.rateInterval = rateInterval;
    }

    public String getSqlBefore() {
        return sqlBefore;
    }

    @ModuleOption("sql to run before load")
    public void setSqlBefore(String sqlBefore) {
        this.sqlBefore = sqlBefore;
    }

    public String getSqlAfter() {
        return sqlAfter;
    }

    @ModuleOption("sql to run after load")
    public void setSqlAfter(String sqlAfter) {
        this.sqlAfter = sqlAfter;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    @ModuleOption("jdbc query")
    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    @ModuleOption("jdbc username")
    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcpassword() {
        return jdbcpassword;
    }

    @ModuleOption("jdbc password")
    public void setJdbcpassword(String jdbcpassword) {
        this.jdbcpassword = jdbcpassword;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    @ModuleOption("jdbc uri")
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbDriverClassName() {
        return jdbDriverClassName;
    }

    @ModuleOption("jdbc driver classname")
    public void setJdbDriverClassName(String jdbDriverClassName) {
        this.jdbDriverClassName = jdbDriverClassName;
    }
}
