package com.jww.bias10.model;

import static java.util.Objects.hash;

public class Utterance
{
	private Integer id;
	private Integer speakerTurnId;
	private Integer uttEnum;	
	private String uttRole;
	private Double startTime;
	private Double endTime;
	private Integer startIndex;
	private Integer endIndex;
	
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
	 * @return the speakerTurnId
	 */
	public Integer getSpeakerTurnId()
	{
		return speakerTurnId;
	}
	/**
	 * @param speakerTurnId the speakerTurnId to set
	 */
	public void setSpeakerTurnId(Integer interviewId)
	{
		this.speakerTurnId = interviewId;
	}
	/**
	 * @return the uttEnum
	 */
	public Integer getUttEnum()
	{
		return uttEnum;
	}
	/**
	 * @param uttEnum the uttEnum to set
	 */
	public void setUttEnum(Integer uttEnum)
	{
		this.uttEnum = uttEnum;
	}
	/**
	 * @return the uttRole
	 */
	public String getUttRole()
	{
		return uttRole;
	}
	/**
	 * @param uttRole the uttRole to set
	 */
	public void setUttRole(String uttRole)
	{
		this.uttRole = uttRole;
	}
	/**
	 * @return the startTime
	 */
	public Double getStartTime()
	{
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Double startTime)
	{
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public Double getEndTime()
	{
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Double endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * @return the startIndex
	 */
	public Integer getStartIndex()
	{
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(Integer startIndex)
	{
		this.startIndex = startIndex;
	}
	/**
	 * @return the endIndex
	 */
	public Integer getEndIndex()
	{
		return endIndex;
	}
	/**
	 * @param endIndex the endIndex to set
	 */
	public void setEndIndex(Integer endIndex)
	{
		this.endIndex = endIndex;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return this.getClass().getCanonicalName().equals(other.getClass().getCanonicalName()) &&
				this.getId().equals(((Utterance)other).id);
	}
	
	@Override
	public int hashCode()
	{
		return hash(this.getClass().getCanonicalName(), this.getId());
	}
	
}
