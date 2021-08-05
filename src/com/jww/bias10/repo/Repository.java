package com.jww.bias10.repo;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T>
{
	T get(Object id) throws SQLException;
	List<T> getAll() throws SQLException;
	Integer insert(T entity) throws SQLException;
	Integer update(T entity) throws SQLException;
	Integer delete(T entity) throws SQLException;
}
