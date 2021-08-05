package com.jww.bias10.repo.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface EntityFactory<T>
{
	public abstract T createNew();
	public abstract T fromResultSet(ResultSet r) throws SQLException;
	public abstract List<T> manyFromResultSet(ResultSet r) throws SQLException;
	
}
