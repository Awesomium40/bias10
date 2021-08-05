package com.jww.bias10.repo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.jww.bias10.model.Interview;
import com.jww.bias10.model.Utterance;
import com.jww.bias10.repo.factory.UtteranceFactory;

public class UtteranceRepository extends RepositoryBase<Utterance>
{

	public UtteranceRepository(Connection con) throws SQLException, ClassNotFoundException
	{
		super(con);
	}

	@Override
	public Utterance get(Object id) throws SQLException
	{
		String sql = "SELECT utterance.id, utterance.speaker_turn_id, utterance.utt_enum, " + 
					 "utterance.utt_start_time, utterance.utt_end_time, utterance.utt_start_index, " +
				     "utterance.utt_end_index " + 
					 "FROM utterance WHERE utterance.id = ? ";
		ResultSet rs = this.executeQuery(sql, id);
		return UtteranceFactory.getInstance().fromResultSet(rs);
	}

	@Override
	public List<Utterance> getAll() throws SQLException
	{
		String sql = "SELECT utterance.id, utterance.speaker_turn_id, utterance.utt_enum, " + 
				     "utterance.utt_start_time, utterance.utt_end_time, utterance.utt_start_index, " +
			         "utterance.utt_end_index " + 
				     "FROM utterance";
		ResultSet rs = this.executeQuery(sql);
		
		return UtteranceFactory.getInstance().manyFromResultSet(rs);
	}
	
	public List<Utterance> getByInterview(Integer interviewId) throws SQLException
	{
		String sql = "SELECT utterance.id, utterance.speaker_turn_id, utterance.utt_enum, " + 
			     "utterance.utt_start_time, utterance.utt_end_time, utterance.utt_start_index, " +
		         "utterance.utt_end_index " + 
			     "FROM utterance " + 
		         "LEFT JOIN speaker_turn ON utterance.speaker_turn_id = speaker_turn.id " + 
			     "WHERE speaker_turn.interview_id = ? ";
		
		return UtteranceFactory.getInstance().manyFromResultSet(this.executeQuery(sql, interviewId));
		
	}
	
	public List<Utterance> getByInterview(Interview entity) throws SQLException
	{
		return this.getByInterview(entity.getId());
	}

	@Override
	public Integer insert(Utterance entity) throws SQLException
	{
		String sql = "INSERT INTO utterance (speaker_turn_id, utt_enum, utt_start_time, " +
				     "utt_end_time, utt_start_index, utt_end_index) " +  
				     "VALUES (?, ?, ?, ?, ?, ?)";
		
		return executeUpdate(sql, Statement.RETURN_GENERATED_KEYS, 
				entity.getSpeakerTurnId(), entity.getUttEnum(), 
				entity.getStartTime(), entity.getEndTime(), entity.getStartIndex(), 
				entity.getEndIndex());

	}

	@Override
	public Integer update(Utterance entity) throws SQLException
	{
		String sql = "UPDATE utterance SET speaker_turn_id = ?, utt_enum = ?, utt_start_time = ?, " +
			     	 "utt_end_time = ?, utt_start_index = ?, utt_end_index = ? " +  
			     	 "WHERE utterance.id = ? ";
		
		return executeUpdate(sql, 0, 
				entity.getSpeakerTurnId(), entity.getUttEnum(), 
				entity.getStartTime(), entity.getEndTime(), entity.getStartIndex(), 
				entity.getEndIndex(), entity.getId());
	}

	@Override
	public Integer delete(Utterance entity) throws SQLException
	{
		String sql = "DELETE FROM utterance WHERE id = ?";
		
		return executeUpdate(sql, 0, entity.getId());
	}

}
