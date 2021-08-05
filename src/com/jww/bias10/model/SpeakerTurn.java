package com.jww.bias10.model;

import static java.util.Objects.hash;

public class SpeakerTurn
{
	private Integer id = null;
	private Integer interviewId = null;
	private Integer line = null;
	private String speakerRole = null;
	private String text = null;
	
	/**
	 * @return the line
	 */
	public Integer getLine()
	{
		return line;
	}
	/**
	 * @param line the line to set
	 */
	public void setLine(Integer line)
	{
		this.line = line;
	}
	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id)
	{
		this.id = id;
	}
	/**
	 * @return the interviewId
	 */
	public Integer getInterviewId()
	{
		return interviewId;
	}
	/**
	 * @param interviewId the interviewId to set
	 */
	public void setInterviewId(Integer interviewId)
	{
		this.interviewId = interviewId;
	}
	/**
	 * @return the speakerRole
	 */
	public String getSpeakerRole()
	{
		return speakerRole;
	}
	/**
	 * @param speakerRole the speakerRole to set
	 */
	public void setSpeakerRole(String speakerRole)
	{
		this.speakerRole = speakerRole;
	}
	/**
	 * @return the uttText
	 */
	public String getText()
	{
		return text;
	}
	/**
	 * @param uttText the uttText to set
	 */
	public void setText(String uttText)
	{
		this.text = uttText;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return this.getClass().getCanonicalName().equals(other.getClass().getCanonicalName()) &&
				this.getId().equals(((SpeakerTurn)other).id);
	}
	
	@Override
	public int hashCode()
	{
		return hash(this.getClass().getCanonicalName(), this.getId());
	}
}
