package edu.weber.ntm.fblaem.entities;

import java.sql.Date;

public class Event {

	private int ID = -1;
	private String Name;
	private int EventTypeID;
	private int MinTeamSize;
	private int MaxTeamSize;
	private int MaxEntriesPerScool;
	private Date CreatedDate;
	private String Details;
	
	public Event(int id, String name, int eventtypeid, int minteamsize, 
			int maxteamsize, int maxentriesperschool, Date createddate, String details)
	{
		this.ID = id;
		this.Name = name;
		this.EventTypeID = eventtypeid;
		this.MinTeamSize = minteamsize;
		this.MaxTeamSize = maxteamsize;
		this.MaxEntriesPerScool = maxentriesperschool;
		this.CreatedDate = createddate;
		this.Details = details;
	}
	public Event(String name, int eventtypeid, int minteamsize, 
			int maxteamsize, int maxentriesperschool, Date createddate, String details)
	{
		//this.ID = id;
		this.Name = name;
		this.EventTypeID = eventtypeid;
		this.MinTeamSize = minteamsize;
		this.MaxTeamSize = maxteamsize;
		this.MaxEntriesPerScool = maxentriesperschool;
		this.CreatedDate = createddate;
		this.Details = details;
	}
	public EventType EventType()
	{
		return null;
	}
}
