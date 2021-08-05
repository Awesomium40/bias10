package com.jww.bias10.repo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class RepositoryBase<T> implements Repository<T>
{
	protected Connection con;
	
	
	public RepositoryBase(Connection con) throws SQLException, ClassNotFoundException
	{
		this.con = con;
	}

	
	@Override
	public abstract T get(Object id) throws SQLException;

	@Override
	public abstract List<T> getAll() throws SQLException;

	@Override
	public abstract Integer insert(T entity) throws SQLException;

	@Override
	public abstract Integer update(T entity) throws SQLException;

	@Override
	public abstract Integer delete(T entity) throws SQLException;
	
	/***
	 * Performs an insert, update, or delete operation using the statement sql and params
	 * @param sql The insert, update, or delete operation to perform
	 * @param flags 0 to return number of rows inserted, Statement.GET_GENERATED_KEYS to get new PK
	 * @param params parameters for the SQL statement
	 * @return Number of rows inserted or PK of entity inserted
	 * @throws SQLException if a database access error occurs
	 */
	protected Integer executeUpdate(String sql, int flags, Object...params) throws SQLException
	{
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql, flags);
		ResultSet rs;
		int toReturn;
		
		if (params != null)
			for (Object o: params)
				ps.setObject(i++, o);

		
		toReturn = ps.executeUpdate();
		
		if (flags == Statement.RETURN_GENERATED_KEYS)
		{
			rs = ps.getGeneratedKeys();
			if (rs.next())
				toReturn = rs.getInt(1);
		}
		
		return toReturn;
	}
	
	/***
	 * Executes a SELECT query against the underlying store
	 * @param sql the statement to execute
	 * @param params the parameters for the query
	 * @return ResultSet of the returned rows
	 * @throws SQLException if a database access error occurs
	 */
	protected ResultSet executeQuery(String sql, Object...params) throws SQLException
	{
		int i = 1;
		PreparedStatement ps = con.prepareStatement(sql);
		if (params != null)
			for (Object o: params)
				ps.setObject(i++, o);
		
		return ps.executeQuery();
	}
}
