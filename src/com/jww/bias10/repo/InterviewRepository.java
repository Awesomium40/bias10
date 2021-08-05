package com.jww.bias10.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jww.bias10.model.Interview;
import com.jww.bias10.repo.factory.InterviewFactory;

public final class InterviewRepository extends RepositoryBase<Interview>
{

	public InterviewRepository(Connection con) throws SQLException, ClassNotFoundException
	{
		super(con);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Retrieves the interview having the specified ID
	 * Interview identified by ID or null if none found
	 */
	@Override
	public Interview get(Object id) throws SQLException
	{
		String query = "SELECT interview.id, interview.interview_name FROM interview WHERE interview.id = ?";
		ResultSet rs = this.executeQuery(query, id);
		
		return InterviewFactory.getInstance().fromResultSet(rs);
	}

	/***
	 * Retrieves a list of all interviews from the underlying store
	 * @return List of interviews
	 */
	@Override
	public List<Interview> getAll() throws SQLException
	{
		List<Interview> results = new ArrayList<Interview>();
		String query = "SELECT interview.id, interview.interview_name FROM interview";
		ResultSet r = this.executeQuery(query);
		while (r.next())
		{
			results.add(InterviewFactory.getInstance().fromResultSet(r));
		}
		
		return results;
	}

	/***
	 * Inserts the interview into the underlying store
	 * @param entity The interview to insert
	 * @return ID of the newly inserted entity
	 * @throws SQLException  if a database access error occurs
	 */
	@Override
	public Integer insert(Interview entity) throws SQLException
	{
		Integer id;
		String sql = "INSERT INTO interview (interview_name) VALUES (?)";
		id = this.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS, entity.getName());
		
		return id;
	}

	/***
	 * Updates the record in the underlying store from data in entity
	 * @param entity The interview to update
	 * @return 1 if record updated, zero otherwise
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public Integer update(Interview entity) throws SQLException
	{
		int rowsInserted;
		String sql = "UPDATE interview SET interview_name = ? WHERE id = ?";
		rowsInserted = this.executeUpdate(sql, 0, entity.getName(), entity.getId());
		return rowsInserted;
	}

	/***
	 * deletes the interview from the underlying store
	 * @param Interview The record to delete
	 * @return Number of affected rows
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public Integer delete(Interview entity) throws SQLException
	{
		int rowsDeleted;
		String sql = "DELETE FROM interview WHERE id = ?";
		rowsDeleted = this.executeUpdate(sql, 0, entity.getId());
		return rowsDeleted;
	}

}
