package com.jww.bias10.repo.factory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jww.bias10.model.Interview;


public class InterviewFactory implements EntityFactory<Interview>
{
	private static volatile InterviewFactory instance;
	
	private InterviewFactory()
	{
		
	}
	
	public static InterviewFactory getInstance()
	{
		if (instance == null)
			synchronized(InterviewFactory.class)
			{
				if (instance == null)
					instance = new InterviewFactory();
			}
		return instance;
	}
	
	/***
	 * Creates a new, empty Interview object
	 * @return Interview
	 */
	@Override
	public Interview createNew()
	{
		return new Interview();
	}
	
	private Interview createNew(Integer id, String name)
	{
		Interview iv = new Interview();
		iv.setId(id);
		iv.setName(name);
		
		return iv;
	}

	/***
	 * Returns a single interview from the next row of ResultSet r
	 * @param r The ResultSet from which to draw
	 * @return Interview if next row in the cursor contains interview data
	 * @throws SQLException if column labels are invalid or if ResultSet r is closed
	 */
	@Override
	public Interview fromResultSet(ResultSet r) throws SQLException
	{
		Interview iv = null;

		if (r.next())
		{
			iv = this.createNew(r.getInt("id"), r.getString("interview_name"));
		}
		
		return iv;
	}
	
	/***
	 * Returns a list of interviews created from the provided result set
	 * @param r The ResultSet from which to draw data
	 * @return List of interviews or empty list for no results
	 * @throws SQLException If the column labels in r are invalid or if operation performed on a closed ResultSet
	 */
	public List<Interview> manyFromResultSet(ResultSet r) throws SQLException
	{
		List<Interview> interviews = new ArrayList<Interview>();
		
		while(r.next())
		{
			interviews.add(this.createNew(r.getInt("interview_id"), r.getString("interview_name")));
		}
		
		return interviews;
	}

}
