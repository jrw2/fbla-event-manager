package edu.weber.ntm.fblaem.databaseio;

// Generated Nov 1, 2013 7:05:48 PM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * EventType generated by hbm2java
 */
public class EventType implements java.io.Serializable {

	private Integer id;
	private String typeName;
	private Set events = new HashSet(0);

	public EventType() {
	}

	public EventType(String typeName, Set events) {
		this.typeName = typeName;
		this.events = events;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Set getEvents() {
		return this.events;
	}

	public void setEvents(Set events) {
		this.events = events;
	}

}
