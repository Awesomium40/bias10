package com.jww.bias10.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jww.bias10.model.Interview;
import com.jww.bias10.model.SpeakerTurn;
import com.jww.bias10.repo.factory.SpeakerTurnFactory;

public final class SpeakerTurnRepository extends RepositoryBase<SpeakerTurn>
{

	public SpeakerTurnRepository(Connection con) throws SQLException, ClassNotFoundException
	{
		super(con);
		// TODO Auto-generated constructor stub
	}

	/***
	 * Retrieves the SpeakerTurn having the specified ID
	 * SpeakerTurn identified by ID or null if none found
	 */
	@Override
	public SpeakerTurn get(Object id) throws SQLException
	{
		String query = "SELECT id, interview_id, st_line, speaker_role, st_text FROM speaker_turn WHERE id = ?";
		ResultSet rs = this.executeQuery(query, id);
		
		return SpeakerTurnFactory.getInstance().fromResultSet(rs);
	}
	
	public List<SpeakerTurn> getByInterview(Integer interviewId) throws SQLException
	{
		List<SpeakerTurn> results = new ArrayList<SpeakerTurn>();
		String query = "SELECT id, interview_id, st_line, speaker_role, st_text FROM speaker_turn WHERE interview_id = ?";
		ResultSet rs = this.executeQuery(query	,interviewId);
		while(rs.next())
			results.add(SpeakerTurnFactory.getInstance().fromResultSet(rs));
		
		return results;
	}
	
	public List<SpeakerTurn> getByInterview(Interview interview) throws SQLException
	{
		return this.getByInterview(interview.getId());
	}

	/***
	 * Retrieves a list of all SpeakerTurns from the underlying store
	 * @return List of SpeakerTurns
	 */
	@Override
	public List<SpeakerTurn> getAll() throws SQLException
	{
		List<SpeakerTurn> results = new ArrayList<SpeakerTurn>();
		String query = "SELECT id, interview_id, st_line, speaker_role, st_text FROM speaker_turn";
		ResultSet r = this.executeQuery(query);
		while (r.next())
		{
			results.add(SpeakerTurnFactory.getInstance().fromResultSet(r));
		}
		
		return results;
	}

	/***
	 * Inserts the SpeakerTurn into the underlying store
	 * @param entity The SpeakerTurn to insert
	 * @return ID of the newly inserted entity
	 * @throws SQLException  if a database access error occurs
	 */
	@Override
	public Integer insert(SpeakerTurn entity) throws SQLException
	{
		Integer id;
		String sql = "INSERT INTO speaker_turn (interview_id, st_line, speaker_role, st_text) VALUES (?, ?, ?, ?)";
		id = this.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS, 
				entity.getInterviewId(), entity.getLine(), entity.getSpeakerRole(), entity.getText());
		
		return id;
	}

	/***
	 * Updates the record in the underlying store from data in entity
	 * @param entity The SpeakerTurn to update
	 * @return 1 if record updated, zero otherwise
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public Integer update(SpeakerTurn entity) throws SQLException
	{
		int rowsInserted;
		String sql = "UPDATE speaker_turn SET interview_id = ?, st_line = ?, speaker_role = ?, st_text = ?  WHERE id = ?";
		rowsInserted = this.executeUpdate(sql, 0, 
				entity.getInterviewId(), entity.getLine(), entity.getSpeakerRole(), entity.getText(), entity.getId());
		return rowsInserted;
	}

	/***
	 * deletes the SpeakerTurn from the underlying store
	 * @param SpeakerTurn The record to delete
	 * @return Number of affected rows
	 * @throws SQLException if a database access error occurs
	 */
	@Override
	public Integer delete(SpeakerTurn entity) throws SQLException
	{
		int rowsDeleted;
		String sql = "DELETE FROM speaker_turn WHERE id = ?";
		rowsDeleted = this.executeUpdate(sql, 0, entity.getId());
		return rowsDeleted;
	}

}
