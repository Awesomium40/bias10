package com.jww.bias10.model;

import static java.util.Objects.hash;

public class SpeakerRole
{
	private String speakerRole;
	private String speakerDescription;
	
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
	 * @return the speakerDescription
	 */
	public String getSpeakerDescription()
	{
		return speakerDescription;
	}
	/**
	 * @param speakerDescription the speakerDescription to set
	 */
	public void setSpeakerDescription(String speakerDescription)
	{
		this.speakerDescription = speakerDescription;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return this.getClass().getCanonicalName().equals(other.getClass().getCanonicalName()) &&
				this.getSpeakerRole().equals(((SpeakerRole)other).getSpeakerRole());
	}
	
	@Override
	public int hashCode()
	{
		return hash(this.getClass().getCanonicalName(), this.getSpeakerRole());
	}
}
