package jasperReports;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class JasperFacade {
    private static class JasperConnection implements Connection {
        List<List<Object>> values;
        List<String> header;

        public JasperConnection(List<String> header, List<List<Object>> values) {
            this.values = values;
            this.header = header;
        }

        public void clearWarnings() throws SQLException {

        }

        public void close() throws SQLException {

        }

        public void commit() throws SQLException {

        }

        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {

            return null;
        }

        public Blob createBlob() throws SQLException {

            return null;
        }

        public Clob createClob() throws SQLException {

            return null;
        }

        public NClob createNClob() throws SQLException {

            return null;
        }

        public SQLXML createSQLXML() throws SQLException {

            return null;
        }

        public Statement createStatement() throws SQLException {

            return null;
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {

            return null;
        }

        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
                throws SQLException {

            return null;
        }

        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {

            return null;
        }

        public boolean getAutoCommit() throws SQLException {

            return false;
        }

        public String getCatalog() throws SQLException {

            return null;
        }

        public Properties getClientInfo() throws SQLException {

            return null;
        }

        public String getClientInfo(String name) throws SQLException {

            return null;
        }

        public int getHoldability() throws SQLException {

            return 0;
        }

        public DatabaseMetaData getMetaData() throws SQLException {

            return null;
        }

        public int getTransactionIsolation() throws SQLException {

            return 0;
        }

        public Map<String, Class<?>> getTypeMap() throws SQLException {

            return null;
        }

        public SQLWarning getWarnings() throws SQLException {

            return null;
        }

        public boolean isClosed() throws SQLException {

            return false;
        }

        public boolean isReadOnly() throws SQLException {

            return false;
        }

        public boolean isValid(int timeout) throws SQLException {

            return false;
        }

        public String nativeSQL(String sql) throws SQLException {

            return null;
        }

        public CallableStatement prepareCall(String sql) throws SQLException {

            return null;
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {

            return null;
        }

        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                int resultSetHoldability) throws SQLException {

            return null;
        }

        public PreparedStatement prepareStatement(String sql) throws SQLException {
            JasperResultSet resultSet = new JasperResultSet(header, values);

            return new JasperPreparedStatement(resultSet);
        }

        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {

            return null;
        }

        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {

            return null;
        }

        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {

            return null;
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
                throws SQLException {

            return null;
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                int resultSetHoldability) throws SQLException {

            return null;
        }

        public void releaseSavepoint(Savepoint savepoint) throws SQLException {

        }

        public void rollback() throws SQLException {

        }

        public void rollback(Savepoint savepoint) throws SQLException {

        }

        public void setAutoCommit(boolean autoCommit) throws SQLException {

        }

        public void setCatalog(String catalog) throws SQLException {

        }

        public void setClientInfo(Properties properties) throws SQLClientInfoException {

        }

        public void setClientInfo(String name, String value) throws SQLClientInfoException {

        }

        public void setHoldability(int holdability) throws SQLException {

        }

        public void setReadOnly(boolean readOnly) throws SQLException {

        }

        public Savepoint setSavepoint() throws SQLException {

            return null;
        }

        public Savepoint setSavepoint(String name) throws SQLException {

            return null;
        }

        public void setTransactionIsolation(int level) throws SQLException {

        }

        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {

            return false;
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {

            return null;
        }

        public void abort(Executor executor) throws SQLException {

        }

        public int getNetworkTimeout() throws SQLException {
            return 0;
        }

        public String getSchema() throws SQLException {
            return null;
        }

        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {

        }

        public void setSchema(String schema) throws SQLException {

        }
    }

    private static class JasperPreparedStatement implements PreparedStatement {
        JasperResultSet resultSet;

        public JasperPreparedStatement(JasperResultSet resultSet) {
            this.resultSet = resultSet;
        }

        public void addBatch() throws SQLException {

        }

        public void clearParameters() throws SQLException {

        }

        public boolean execute() throws SQLException {

            return false;
        }

        public ResultSet executeQuery() throws SQLException {
            return resultSet;
        }

        public int executeUpdate() throws SQLException {

            return 0;
        }

        public ResultSetMetaData getMetaData() throws SQLException {
            return null;
        }

        public ParameterMetaData getParameterMetaData() throws SQLException {

            return null;
        }

        public void setArray(int parameterIndex, Array x) throws SQLException {

        }

        public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {

        }

        public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {

        }

        public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {

        }

        public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {

        }

        public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {

        }

        public void setBlob(int parameterIndex, Blob x) throws SQLException {

        }

        public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {

        }

        public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {

        }

        public void setBoolean(int parameterIndex, boolean x) throws SQLException {

        }

        public void setByte(int parameterIndex, byte x) throws SQLException {

        }

        public void setBytes(int parameterIndex, byte[] x) throws SQLException {

        }

        public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {

        }

        public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {

        }

        public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        public void setClob(int parameterIndex, Clob x) throws SQLException {

        }

        public void setClob(int parameterIndex, Reader reader) throws SQLException {

        }

        public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        public void setDate(int parameterIndex, Date x) throws SQLException {

        }

        public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {

        }

        public void setDouble(int parameterIndex, double x) throws SQLException {

        }

        public void setFloat(int parameterIndex, float x) throws SQLException {

        }

        public void setInt(int parameterIndex, int x) throws SQLException {

        }

        public void setLong(int parameterIndex, long x) throws SQLException {

        }

        public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {

        }

        public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {

        }

        public void setNClob(int parameterIndex, NClob value) throws SQLException {

        }

        public void setNClob(int parameterIndex, Reader reader) throws SQLException {

        }

        public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {

        }

        public void setNString(int parameterIndex, String value) throws SQLException {

        }

        public void setNull(int parameterIndex, int sqlType) throws SQLException {

        }

        public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {

        }

        public void setObject(int parameterIndex, Object x) throws SQLException {

        }

        public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {

        }

        public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {

        }

        public void setRef(int parameterIndex, Ref x) throws SQLException {

        }

        public void setRowId(int parameterIndex, RowId x) throws SQLException {

        }

        public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {

        }

        public void setShort(int parameterIndex, short x) throws SQLException {

        }

        public void setString(int parameterIndex, String x) throws SQLException {

        }

        public void setTime(int parameterIndex, Time x) throws SQLException {

        }

        public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {

        }

        public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {

        }

        public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {

        }

        public void setURL(int parameterIndex, URL x) throws SQLException {

        }

        public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {

        }

        public void addBatch(String arg0) throws SQLException {

        }

        public void cancel() throws SQLException {

        }

        public void clearBatch() throws SQLException {

        }

        public void clearWarnings() throws SQLException {

        }

        public void close() throws SQLException {

        }

        public boolean execute(String arg0) throws SQLException {

            return false;
        }

        public boolean execute(String arg0, int arg1) throws SQLException {

            return false;
        }

        public boolean execute(String arg0, int[] arg1) throws SQLException {

            return false;
        }

        public boolean execute(String arg0, String[] arg1) throws SQLException {

            return false;
        }

        public int[] executeBatch() throws SQLException {

            return null;
        }

        public ResultSet executeQuery(String arg0) throws SQLException {

            return null;
        }

        public int executeUpdate(String arg0) throws SQLException {

            return 0;
        }

        public int executeUpdate(String arg0, int arg1) throws SQLException {

            return 0;
        }

        public int executeUpdate(String arg0, int[] arg1) throws SQLException {

            return 0;
        }

        public int executeUpdate(String arg0, String[] arg1) throws SQLException {

            return 0;
        }

        public Connection getConnection() throws SQLException {

            return null;
        }

        public int getFetchDirection() throws SQLException {

            return 0;
        }

        public int getFetchSize() throws SQLException {

            return 0;
        }

        public ResultSet getGeneratedKeys() throws SQLException {

            return null;
        }

        public int getMaxFieldSize() throws SQLException {

            return 0;
        }

        public int getMaxRows() throws SQLException {

            return 0;
        }

        public boolean getMoreResults() throws SQLException {

            return false;
        }

        public boolean getMoreResults(int arg0) throws SQLException {

            return false;
        }

        public int getQueryTimeout() throws SQLException {

            return 0;
        }

        public ResultSet getResultSet() throws SQLException {

            return null;
        }

        public int getResultSetConcurrency() throws SQLException {

            return 0;
        }

        public int getResultSetHoldability() throws SQLException {

            return 0;
        }

        public int getResultSetType() throws SQLException {

            return 0;
        }

        public int getUpdateCount() throws SQLException {

            return 0;
        }

        public SQLWarning getWarnings() throws SQLException {

            return null;
        }

        public boolean isClosed() throws SQLException {

            return false;
        }

        public boolean isPoolable() throws SQLException {

            return false;
        }

        public void setCursorName(String arg0) throws SQLException {

        }

        public void setEscapeProcessing(boolean arg0) throws SQLException {

        }

        public void setFetchDirection(int arg0) throws SQLException {

        }

        public void setFetchSize(int arg0) throws SQLException {

        }

        public void setMaxFieldSize(int arg0) throws SQLException {

        }

        public void setMaxRows(int arg0) throws SQLException {

        }

        public void setPoolable(boolean arg0) throws SQLException {

        }

        public void setQueryTimeout(int arg0) throws SQLException {

        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {

            return false;
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {

            return null;
        }

        public void closeOnCompletion() throws SQLException {

        }

        public boolean isCloseOnCompletion() throws SQLException {
            return false;
        }
    }

    private static class JasperResultSet implements ResultSet {
        Iterator<List<Object>> params;
        List<Object> current;
        ResultSetMetaData metadata;

        public JasperResultSet(List<String> header, List<List<Object>> params) {
            this.params = params.iterator();
            metadata = new JasperResultSetMetadata(header);
        }

        public boolean absolute(int row) throws SQLException {

            return false;
        }

        public void afterLast() throws SQLException {

        }

        public void beforeFirst() throws SQLException {

        }

        public void cancelRowUpdates() throws SQLException {

        }

        public void clearWarnings() throws SQLException {

        }

        public void close() throws SQLException {

        }

        public void deleteRow() throws SQLException {

        }

        public int findColumn(String columnLabel) throws SQLException {

            return 0;
        }

        public boolean first() throws SQLException {

            return false;
        }

        public Array getArray(int columnIndex) throws SQLException {

            return null;
        }

        public Array getArray(String columnLabel) throws SQLException {

            return null;
        }

        public InputStream getAsciiStream(int columnIndex) throws SQLException {

            return null;
        }

        public InputStream getAsciiStream(String columnLabel) throws SQLException {

            return null;
        }

        public BigDecimal getBigDecimal(int columnIndex) throws SQLException {

            return null;
        }

        public BigDecimal getBigDecimal(String columnLabel) throws SQLException {

            return null;
        }

        public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {

            return null;
        }

        public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {

            return null;
        }

        public InputStream getBinaryStream(int columnIndex) throws SQLException {

            return null;
        }

        public InputStream getBinaryStream(String columnLabel) throws SQLException {

            return null;
        }

        public Blob getBlob(int columnIndex) throws SQLException {

            return null;
        }

        public Blob getBlob(String columnLabel) throws SQLException {

            return null;
        }

        public boolean getBoolean(int columnIndex) throws SQLException {

            return false;
        }

        public boolean getBoolean(String columnLabel) throws SQLException {

            return false;
        }

        public byte getByte(int columnIndex) throws SQLException {

            return 0;
        }

        public byte getByte(String columnLabel) throws SQLException {

            return 0;
        }

        public byte[] getBytes(int columnIndex) throws SQLException {

            return null;
        }

        public byte[] getBytes(String columnLabel) throws SQLException {

            return null;
        }

        public Reader getCharacterStream(int columnIndex) throws SQLException {

            return null;
        }

        public Reader getCharacterStream(String columnLabel) throws SQLException {

            return null;
        }

        public Clob getClob(int columnIndex) throws SQLException {

            return null;
        }

        public Clob getClob(String columnLabel) throws SQLException {

            return null;
        }

        public int getConcurrency() throws SQLException {

            return 0;
        }

        public String getCursorName() throws SQLException {

            return null;
        }

        public Date getDate(int columnIndex) throws SQLException {

            return null;
        }

        public Date getDate(String columnLabel) throws SQLException {

            return null;
        }

        public Date getDate(int columnIndex, Calendar cal) throws SQLException {

            return null;
        }

        public Date getDate(String columnLabel, Calendar cal) throws SQLException {

            return null;
        }

        public double getDouble(int columnIndex) throws SQLException {

            return 0;
        }

        public double getDouble(String columnLabel) throws SQLException {

            return 0;
        }

        public int getFetchDirection() throws SQLException {

            return 0;
        }

        public int getFetchSize() throws SQLException {

            return 0;
        }

        public float getFloat(int columnIndex) throws SQLException {

            return 0;
        }

        public float getFloat(String columnLabel) throws SQLException {

            return 0;
        }

        public int getHoldability() throws SQLException {

            return 0;
        }

        public int getInt(int columnIndex) throws SQLException {

            return 0;
        }

        public int getInt(String columnLabel) throws SQLException {

            return 0;
        }

        public long getLong(int columnIndex) throws SQLException {

            return 0;
        }

        public long getLong(String columnLabel) throws SQLException {

            return 0;
        }

        public ResultSetMetaData getMetaData() throws SQLException {
            return metadata;
        }

        public Reader getNCharacterStream(int columnIndex) throws SQLException {

            return null;
        }

        public Reader getNCharacterStream(String columnLabel) throws SQLException {

            return null;
        }

        public NClob getNClob(int columnIndex) throws SQLException {

            return null;
        }

        public NClob getNClob(String columnLabel) throws SQLException {

            return null;
        }

        public String getNString(int columnIndex) throws SQLException {

            return null;
        }

        public String getNString(String columnLabel) throws SQLException {

            return null;
        }

        public Object getObject(int columnIndex) throws SQLException {

            return null;
        }

        public Object getObject(String columnLabel) throws SQLException {

            return null;
        }

        public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {

            return null;
        }

        public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {

            return null;
        }

        public Ref getRef(int columnIndex) throws SQLException {

            return null;
        }

        public Ref getRef(String columnLabel) throws SQLException {

            return null;
        }

        public int getRow() throws SQLException {

            return 0;
        }

        public RowId getRowId(int columnIndex) throws SQLException {

            return null;
        }

        public RowId getRowId(String columnLabel) throws SQLException {

            return null;
        }

        public SQLXML getSQLXML(int columnIndex) throws SQLException {

            return null;
        }

        public SQLXML getSQLXML(String columnLabel) throws SQLException {

            return null;
        }

        public short getShort(int columnIndex) throws SQLException {

            return 0;
        }

        public short getShort(String columnLabel) throws SQLException {

            return 0;
        }

        public Statement getStatement() throws SQLException {

            return null;
        }

        public String getString(int columnIndex) throws SQLException {
            return current.get(columnIndex - 1) + "";
        }

        public String getString(String columnLabel) throws SQLException {

            return null;
        }

        public Time getTime(int columnIndex) throws SQLException {

            return null;
        }

        public Time getTime(String columnLabel) throws SQLException {

            return null;
        }

        public Time getTime(int columnIndex, Calendar cal) throws SQLException {

            return null;
        }

        public Time getTime(String columnLabel, Calendar cal) throws SQLException {

            return null;
        }

        public Timestamp getTimestamp(int columnIndex) throws SQLException {

            return null;
        }

        public Timestamp getTimestamp(String columnLabel) throws SQLException {

            return null;
        }

        public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {

            return null;
        }

        public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {

            return null;
        }

        public int getType() throws SQLException {

            return 0;
        }

        public URL getURL(int columnIndex) throws SQLException {

            return null;
        }

        public URL getURL(String columnLabel) throws SQLException {

            return null;
        }

        public InputStream getUnicodeStream(int columnIndex) throws SQLException {

            return null;
        }

        public InputStream getUnicodeStream(String columnLabel) throws SQLException {

            return null;
        }

        public SQLWarning getWarnings() throws SQLException {

            return null;
        }

        public void insertRow() throws SQLException {

        }

        public boolean isAfterLast() throws SQLException {

            return false;
        }

        public boolean isBeforeFirst() throws SQLException {

            return false;
        }

        public boolean isClosed() throws SQLException {

            return false;
        }

        public boolean isFirst() throws SQLException {

            return false;
        }

        public boolean isLast() throws SQLException {

            return false;
        }

        public boolean last() throws SQLException {

            return false;
        }

        public void moveToCurrentRow() throws SQLException {

        }

        public void moveToInsertRow() throws SQLException {

        }

        public boolean next() throws SQLException {
            if (params.hasNext()) {
                current = params.next();
                return true;
            }

            return false;
        }

        public boolean previous() throws SQLException {

            return false;
        }

        public void refreshRow() throws SQLException {

        }

        public boolean relative(int rows) throws SQLException {

            return false;
        }

        public boolean rowDeleted() throws SQLException {

            return false;
        }

        public boolean rowInserted() throws SQLException {

            return false;
        }

        public boolean rowUpdated() throws SQLException {

            return false;
        }

        public void setFetchDirection(int direction) throws SQLException {

        }

        public void setFetchSize(int rows) throws SQLException {

        }

        public void updateArray(int columnIndex, Array x) throws SQLException {

        }

        public void updateArray(String columnLabel, Array x) throws SQLException {

        }

        public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {

        }

        public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {

        }

        public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {

        }

        public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {

        }

        public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {

        }

        public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {

        }

        public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {

        }

        public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {

        }

        public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {

        }

        public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {

        }

        public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {

        }

        public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {

        }

        public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {

        }

        public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {

        }

        public void updateBlob(int columnIndex, Blob x) throws SQLException {

        }

        public void updateBlob(String columnLabel, Blob x) throws SQLException {

        }

        public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {

        }

        public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {

        }

        public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {

        }

        public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {

        }

        public void updateBoolean(int columnIndex, boolean x) throws SQLException {

        }

        public void updateBoolean(String columnLabel, boolean x) throws SQLException {

        }

        public void updateByte(int columnIndex, byte x) throws SQLException {

        }

        public void updateByte(String columnLabel, byte x) throws SQLException {

        }

        public void updateBytes(int columnIndex, byte[] x) throws SQLException {

        }

        public void updateBytes(String columnLabel, byte[] x) throws SQLException {

        }

        public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {

        }

        public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {

        }

        public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {

        }

        public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {

        }

        public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

        }

        public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

        }

        public void updateClob(int columnIndex, Clob x) throws SQLException {

        }

        public void updateClob(String columnLabel, Clob x) throws SQLException {

        }

        public void updateClob(int columnIndex, Reader reader) throws SQLException {

        }

        public void updateClob(String columnLabel, Reader reader) throws SQLException {

        }

        public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {

        }

        public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {

        }

        public void updateDate(int columnIndex, Date x) throws SQLException {

        }

        public void updateDate(String columnLabel, Date x) throws SQLException {

        }

        public void updateDouble(int columnIndex, double x) throws SQLException {

        }

        public void updateDouble(String columnLabel, double x) throws SQLException {

        }

        public void updateFloat(int columnIndex, float x) throws SQLException {

        }

        public void updateFloat(String columnLabel, float x) throws SQLException {

        }

        public void updateInt(int columnIndex, int x) throws SQLException {

        }

        public void updateInt(String columnLabel, int x) throws SQLException {

        }

        public void updateLong(int columnIndex, long x) throws SQLException {

        }

        public void updateLong(String columnLabel, long x) throws SQLException {

        }

        public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {

        }

        public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {

        }

        public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

        }

        public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

        }

        public void updateNClob(int columnIndex, NClob clob) throws SQLException {

        }

        public void updateNClob(String columnLabel, NClob clob) throws SQLException {

        }

        public void updateNClob(int columnIndex, Reader reader) throws SQLException {

        }

        public void updateNClob(String columnLabel, Reader reader) throws SQLException {

        }

        public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {

        }

        public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {

        }

        public void updateNString(int columnIndex, String string) throws SQLException {

        }

        public void updateNString(String columnLabel, String string) throws SQLException {

        }

        public void updateNull(int columnIndex) throws SQLException {

        }

        public void updateNull(String columnLabel) throws SQLException {

        }

        public void updateObject(int columnIndex, Object x) throws SQLException {

        }

        public void updateObject(String columnLabel, Object x) throws SQLException {

        }

        public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {

        }

        public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {

        }

        public void updateRef(int columnIndex, Ref x) throws SQLException {

        }

        public void updateRef(String columnLabel, Ref x) throws SQLException {

        }

        public void updateRow() throws SQLException {

        }

        public void updateRowId(int columnIndex, RowId x) throws SQLException {

        }

        public void updateRowId(String columnLabel, RowId x) throws SQLException {

        }

        public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {

        }

        public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {

        }

        public void updateShort(int columnIndex, short x) throws SQLException {

        }

        public void updateShort(String columnLabel, short x) throws SQLException {

        }

        public void updateString(int columnIndex, String x) throws SQLException {

        }

        public void updateString(String columnLabel, String x) throws SQLException {

        }

        public void updateTime(int columnIndex, Time x) throws SQLException {

        }

        public void updateTime(String columnLabel, Time x) throws SQLException {

        }

        public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {

        }

        public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {

        }

        public boolean wasNull() throws SQLException {

            return false;
        }

        public boolean isWrapperFor(Class<?> iface) throws SQLException {

            return false;
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {

            return null;
        }

        public <T> T getObject(int arg0, Class<T> arg1) throws SQLException {
            return null;
        }

        public <T> T getObject(String arg0, Class<T> arg1) throws SQLException {
            return null;
        }

    }

    private static class JasperResultSetMetadata implements ResultSetMetaData {
        List<String> header;

        public JasperResultSetMetadata(List<String> header) {
            this.header = header;
        }

        public String getCatalogName(int column) throws SQLException {

            return null;
        }

        public String getColumnClassName(int column) throws SQLException {

            return null;
        }

        public int getColumnCount() throws SQLException {
            return header.size();
        }

        public int getColumnDisplaySize(int column) throws SQLException {

            return 0;
        }

        public String getColumnLabel(int column) throws SQLException {

            return null;
        }

        public String getColumnName(int column) throws SQLException {
            return header.get(column - 1);
        }

        public int getColumnType(int column) throws SQLException {

            return 0;
        }

        public String getColumnTypeName(int column) throws SQLException {

            return null;
        }

        public int getPrecision(int column) throws SQLException {

            return 0;
        }

        public int getScale(int column) throws SQLException {

            return 0;
        }

        public String getSchemaName(int column) throws SQLException {

            return null;
        }

        public String getTableName(int column) throws SQLException {

            return null;
        }

        public boolean isAutoIncrement(int column) throws SQLException {

            return false;
        }

        public boolean isCaseSensitive(int column) throws SQLException {

            return false;
        }

        public boolean isCurrency(int column) throws SQLException {

            return false;
        }

        public boolean isDefinitelyWritable(int column) throws SQLException {

            return false;
        }

        public int isNullable(int column) throws SQLException {

            return 0;
        }

        public boolean isReadOnly(int column) throws SQLException {

            return false;
        }

        public boolean isSearchable(int column) throws SQLException {

            return false;
        }

        public boolean isSigned(int column) throws SQLException {

            return false;
        }

        public boolean isWritable(int column) throws SQLException {

            return false;
        }

        public boolean isWrapperFor(Class<?> arg0) throws SQLException {

            return false;
        }

        public <T> T unwrap(Class<T> iface) throws SQLException {

            return null;
        }

    }

    public static void runReport(String reportName, Map<String, Object> params, List<String> header,
            List<List<Object>> values) throws Exception {
        // JasperDesign jasperDesign =
        // JRXmlLoader.load(JasperFacade.class.getResourceAsStream(reportName));
        // JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperConnection jasperConnection = new JasperConnection(header, values);
        JasperPrint jasperPrint = JasperFillManager
                .fillReport(JasperFacade.class.getResourceAsStream(reportName + ".jasper"), params, jasperConnection);
        JasperViewer.viewReport(jasperPrint, false);
    }

    public static void main(String[] args) throws Exception {

        Hashtable<String, Object> params = new Hashtable<String, Object>();
        params.put("a1", "hola");
        params.put("b1", "hola");
        params.put("c1", "hola");
        params.put("d1", "hola");
        params.put("e1", "hola");
        params.put("f1", "hola");

        List<List<Object>> values = new ArrayList<List<Object>>();
        ArrayList<Object> row = new ArrayList<Object>();
        row.add("hola1");
        row.add("hola2");
        row.add("hola3");
        row.add("hola4");
        row.add("hola5");
        values.add(row);

        ArrayList<String> header = new ArrayList<String>();
        header.add("clave");
        header.add("nrc");
        header.add("seccion");
        header.add("materia");
        header.add("maestro");

        runReport("horario.jrxml", params, header, values);

    }
}
