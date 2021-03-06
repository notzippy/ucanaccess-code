/*
Copyright (c) 2012 Marco Amadei.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
USA

You can contact Marco Amadei at amadei.mar@gmail.com.

 */
package net.ucanaccess.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import org.hsqldb.jdbc.JDBCPreparedStatement;
import org.hsqldb.jdbc.JDBCStatement;

import net.ucanaccess.converters.SQLConverter;

public class UcanaccessStatement implements Statement {
	private UcanaccessConnection connection;
	private Statement wrapped;

	public UcanaccessStatement(Statement wrapped, UcanaccessConnection conn)
			throws SQLException {
		this.wrapped = wrapped;
		this.connection = conn;
	}

	public void addBatch(String batch) throws SQLException {
		try {
			wrapped.addBatch(batch);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void cancel() throws SQLException {
		try {
			wrapped.cancel();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void clearBatch() throws SQLException {
		try {
			wrapped.clearBatch();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void clearWarnings() throws SQLException {
		try {
			wrapped.clearWarnings();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void close() throws SQLException {
		try {
			wrapped.close();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void closeOnCompletion() throws SQLException {
		try {
			if (wrapped instanceof JDBCStatement) {
				((JDBCStatement) wrapped).closeOnCompletion();
			} else {
				if (wrapped instanceof JDBCPreparedStatement) {
					((JDBCPreparedStatement) wrapped).closeOnCompletion();
				} else {
					throw new SQLException(
							"Internal error. Object 'wrapped' is neither a JDBCStatement nor a JDBCPreparedStatement");
				}
			}
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean execute(String sql) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql, this.connection);
			return new Execute(this, sql).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean execute(String sql, int autoGeneratedKeys)
			throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql, this.connection);
			return new Execute(this, sql, autoGeneratedKeys).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean execute(String sql, int[] indexes) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql, this.connection);
			return new Execute(this, sql, indexes).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean execute(String sql, String[] columnNames)
			throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql, this.connection);
			return new Execute(this, sql, columnNames).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int[] executeBatch() throws SQLException {
		try {
			return new ExecuteUpdate(this).executeBatch();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql, this.connection);
			return new UcanaccessResultSet(wrapped.executeQuery(sql), this);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int executeUpdate(String sql) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql);
			return new ExecuteUpdate(this, sql).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int executeUpdate(String sql, int arg) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql);
			return new ExecuteUpdate(this, sql, arg).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int executeUpdate(String sql, int[] arg) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql);
			return new ExecuteUpdate(this, sql, arg).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int executeUpdate(String sql, String[] arg) throws SQLException {
		try {
			sql = SQLConverter.convertSQL(sql);
			return new ExecuteUpdate(this, sql, arg).execute();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return this.connection;
	}

	public int getFetchDirection() throws SQLException {
		try {
			return wrapped.getFetchDirection();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getFetchSize() throws SQLException {
		try {
			return wrapped.getFetchSize();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		try {
			return wrapped.getGeneratedKeys();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getMaxFieldSize() throws SQLException {
		try {
			return wrapped.getMaxFieldSize();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getMaxRows() throws SQLException {
		try {
			return wrapped.getMaxRows();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean getMoreResults() throws SQLException {
		try {
			return wrapped.getMoreResults();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean getMoreResults(int arg0) throws SQLException {
		try {
			return wrapped.getMoreResults(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getQueryTimeout() throws SQLException {
		try {
			return wrapped.getQueryTimeout();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public ResultSet getResultSet() throws SQLException {
		try {
			ResultSet rs = wrapped.getResultSet();
			if (wrapped == null || rs == null)
				return null;
			return new UcanaccessResultSet(rs, this);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getResultSetConcurrency() throws SQLException {
		try {
			return wrapped.getResultSetConcurrency();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getResultSetHoldability() throws SQLException {
		try {
			return wrapped.getResultSetHoldability();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getResultSetType() throws SQLException {
		try {
			return wrapped.getResultSetType();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public int getUpdateCount() throws SQLException {
		try {
			return wrapped.getUpdateCount();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public SQLWarning getWarnings() throws SQLException {
		try {
			return wrapped.getWarnings();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	Statement getWrapped() {
		return wrapped;
	}

	public boolean isClosed() throws SQLException {
		try {
			return wrapped.isClosed();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean isCloseOnCompletion() throws SQLException {
		try {
			return ((JDBCStatement) wrapped).isCloseOnCompletion();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean isPoolable() throws SQLException {
		try {
			return wrapped.isPoolable();
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		try {
			return wrapped.isWrapperFor(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setCursorName(String arg0) throws SQLException {
		try {
			wrapped.setCursorName(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setEscapeProcessing(boolean arg0) throws SQLException {
		try {
			wrapped.setEscapeProcessing(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setFetchDirection(int arg0) throws SQLException {
		try {
			wrapped.setFetchDirection(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setFetchSize(int arg0) throws SQLException {
		try {
			wrapped.setFetchSize(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setMaxFieldSize(int arg0) throws SQLException {
		try {
			wrapped.setMaxFieldSize(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setMaxRows(int arg0) throws SQLException {
		try {
			wrapped.setMaxRows(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setPoolable(boolean arg0) throws SQLException {
		try {
			wrapped.setPoolable(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public void setQueryTimeout(int arg0) throws SQLException {
		try {
			wrapped.setQueryTimeout(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}

	public <T> T unwrap(Class<T> arg0) throws SQLException {
		try {
			return wrapped.unwrap(arg0);
		} catch (SQLException e) {
			throw new UcanaccessSQLException(e);
		}
	}
}
