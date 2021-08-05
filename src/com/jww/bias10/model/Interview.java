package com.jww.bias10.model;
import static java.util.Objects.hash;

public class Interview
{
	private Integer id = null;
	private String name = null;
	
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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String interviewName)
	{
		this.name = interviewName;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return this.getClass().getCanonicalName().equals(other.getClass().getCanonicalName()) &&
				this.getId().equals(((Interview)other).id);
	}
	
	@Override
	public int hashCode()
	{
		return hash(this.getClass().getCanonicalName(), this.getId());
	}
}
