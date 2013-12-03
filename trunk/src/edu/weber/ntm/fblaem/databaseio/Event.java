package edu.weber.ntm.fblaem.databaseio;

// Generated Nov 1, 2013 7:05:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Event generated by hbm2java
 */
public class Event implements java.io.Serializable {

	private Integer id;
	private EventType eventType;
	private String name;
	private int minTeamSize;
	private int maxTeamSize;
	private int maxEntriesPerSchool;
	private Date createdDate;
	private String details;
	private Set<EventInstance> eventInstances = new HashSet(0);

	public Event() {
	}

	public Event(int minTeamSize, int maxTeamSize, int maxEntriesPerSchool) {
		this.minTeamSize = minTeamSize;
		this.maxTeamSize = maxTeamSize;
		this.maxEntriesPerSchool = maxEntriesPerSchool;
	}

	public Event(EventType eventType, String name, int minTeamSize,
			int maxTeamSize, int maxEntriesPerSchool, Date createdDate,
			String details, Set studentTeams, Set eventInstances) {
		this.eventType = eventType;
		this.name = name;
		this.minTeamSize = minTeamSize;
		this.maxTeamSize = maxTeamSize;
		this.maxEntriesPerSchool = maxEntriesPerSchool;
		this.createdDate = createdDate;
		this.details = details;
		this.eventInstances = eventInstances;
	}
	
	public Event(EventType eventType, String name, int minTeamSize,
			int maxTeamSize, int maxEntriesPerSchool, Date createdDate,
			String details) {
		this.eventType = eventType;
		this.name = name;
		this.minTeamSize = minTeamSize;
		this.maxTeamSize = maxTeamSize;
		this.maxEntriesPerSchool = maxEntriesPerSchool;
		this.createdDate = createdDate;
		this.details = details;
	}	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EventType getEventType() {
		return this.eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMinTeamSize() {
		return this.minTeamSize;
	}

	public void setMinTeamSize(int minTeamSize) {
		this.minTeamSize = minTeamSize;
	}

	public int getMaxTeamSize() {
		return this.maxTeamSize;
	}

	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}

	public int getMaxEntriesPerSchool() {
		return this.maxEntriesPerSchool;
	}

	public void setMaxEntriesPerSchool(int maxEntriesPerSchool) {
		this.maxEntriesPerSchool = maxEntriesPerSchool;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Set<EventInstance> getEventInstances() {
		return this.eventInstances;
	}

	public void setEventInstances(Set eventInstances) {
		this.eventInstances = eventInstances;
	}

}
