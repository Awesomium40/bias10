package com.jww.bias10.repo.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jww.bias10.model.Utterance;

public class UtteranceFactory implements EntityFactory<Utterance>
{
	private static volatile UtteranceFactory instance;
	
	private UtteranceFactory()
	{
		
	}

	@Override
	public Utterance createNew()
	{
		return new Utterance();
	}

	public static UtteranceFactory getInstance()
	{
		if (instance == null)
			synchronized(UtteranceFactory.class)
			{
				if (instance == null)
				{
					instance = new UtteranceFactory();
				}
			}
		
		return instance;
	}
	
	private Utterance createNew(Integer id, Integer speakerTurnId, Integer uttEnum, 
			Double startTime, Double endTime, Integer startIndex, Integer endIndex)
	{
		Utterance u = this.createNew();
		u.setId(id);
		u.setSpeakerTurnId(speakerTurnId);
		u.setUttEnum(uttEnum);
		u.setStartTime(startTime);
		u.setEndTime(endTime);
		u.setStartIndex(startIndex);
		u.setEndIndex(endIndex);
		
		return u;
		
	}

	@Override
	public Utterance fromResultSet(ResultSet r) throws SQLException
	{
		Utterance u = null;
		if (r.next())
			u = this.createNew(r.getInt("id"), r.getInt("speaker_turn_id"), r.getInt("utt_enum"),
					r.getDouble("utt_start_time"), r.getDouble("utt_end_time"), 
					r.getInt("utt_start_index"), r.getInt("utt_end_index"));
		
		return u;
	}

	@Override
	public List<Utterance> manyFromResultSet(ResultSet r) throws SQLException
	{
		List<Utterance> results = new ArrayList<Utterance>();
		while(r.next())
			results.add(this.createNew(r.getInt("id"), r.getInt("speaker_turn_id"), r.getInt("utt_enum"),
					r.getDouble("utt_start_time"), r.getDouble("utt_end_time"), 
					r.getInt("utt_start_index"), r.getInt("utt_end_index")));
		// TODO Auto-generated method stub
		return results;
	}

}
