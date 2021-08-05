package com.jww.bias10.repo.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jww.bias10.model.SpeakerTurn;

public final class SpeakerTurnFactory implements EntityFactory<SpeakerTurn>
{
	private static volatile SpeakerTurnFactory instance;
	
	private SpeakerTurnFactory()
	{
		
	}

	@Override
	public SpeakerTurn createNew()
	{
		return new SpeakerTurn();
	}
	
	protected SpeakerTurn createNew(Integer interviewId, Integer line, String role, String text)
	{
		SpeakerTurn s = this.createNew();
		s.setInterviewId(interviewId);
		s.setLine(line);
		s.setSpeakerRole(role);
		s.setText(text);
		
		return s;
	}

	public static SpeakerTurnFactory getInstance()
	{
		if (instance == null)
			synchronized(SpeakerTurnFactory.class)
			{
				if (instance == null)
				{
					instance = new SpeakerTurnFactory();
				}
			}
		
		return instance;
	}

	@Override
	public SpeakerTurn fromResultSet(ResultSet r) throws SQLException
	{
		SpeakerTurn s = null;
		if (r.next())
			s = this.createNew(r.getInt("id"), r.getInt("st_line"), r.getString("speaker_role"), 
					r.getString("st_text"));
		
		return s;
	}

	@Override
	public List<SpeakerTurn> manyFromResultSet(ResultSet r) throws SQLException
	{
		List<SpeakerTurn> results = new ArrayList<SpeakerTurn>();
		while (r.next())
			results.add(this.createNew(r.getInt("id"), r.getInt("st_line"), r.getString("speaker_role"), 
					r.getString("st_text")));
		
		return results;
	}
}
