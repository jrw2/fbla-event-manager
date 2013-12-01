package edu.weber.ntm.fblaem.entities;

import java.sql.Date;
import java.util.Calendar;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

public class Student {

	private String FirstName;
	private String LastName;
	private String MiddleName;
	private Date CreateDate;
	private int SchoolID;
	private int ID = -1;
  
	public Student(int id)
	{
		this.ID = id;
	}
	public Student(int id, int schoolid, String firstname, String lastname, String middlename, Date createdate)
	{
		this.ID = id;
		this.SchoolID = schoolid;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.MiddleName = middlename;
		this.CreateDate = createdate;
		
	}
	public Student(int schoolid, String firstname, String lastname, String middlename)
	{
		this.SchoolID = schoolid;
		this.FirstName = firstname;
		this.LastName = lastname;
		this.MiddleName = middlename;
		Calendar cal = Calendar.getInstance();
		this.CreateDate = new Date(cal.getTimeInMillis());
	}
	/*public School School()
	{
		
	}
	public EditName(String firstname, String lastname, String middlename)
	{
		
	}*/
	public void saveNew()
	{
		if(ID == -1)
		{	
			
		}
	}

	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getMiddleName() {
		return MiddleName;
	}
	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}
	public int getSchoolID() {
		return SchoolID;
	}
	public void setSchoolID(int schoolID) {
		SchoolID = schoolID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
}
