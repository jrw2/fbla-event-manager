USE fblaem;
DROP TABLE IF EXISTS StudentEventTeam;
DROP TABLE IF EXISTS EventInstance;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS Login;
DROP TABLE IF EXISTS Teacher;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS EventType;
DROP TABLE IF EXISTS School;

CREATE TABLE School
	(id mediumint NOT NULL auto_increment,
	Name nvarchar(255) NOT NULL,
	StreetAddress nvarchar(255) NOT NULL,
	City nvarchar(255) NOT NULL,
	State nvarchar(255) NOT NULL,
	Zip nvarchar(255) NOT NULL,
	Phone nvarchar(20) NOT NULL,
	CreatedDate datetime,
	PRIMARY KEY (id)
	);
CREATE TABLE EventType
	(id mediumint NOT NULL auto_increment,
	TypeName nvarchar(255),
	PRIMARY KEY (id)
	);
CREATE TABLE Student 
	(id mediumint NOT NULL auto_increment, 
	SchoolID mediumint NOT NULL, 
	FirstName nvarchar(255) NOT NULL, 
	LastName nvarchar(255) NOT NULL, 
	MiddleName nvarchar(255), 
	CreateDate datetime,
	PRIMARY KEY (id),
	FOREIGN KEY (SchoolID)
		REFERENCES School(id)
	);
CREATE TABLE Role
	(id mediumint NOT NULL auto_increment,
	RoleName nvarchar(255) NOT NULL,
	PRIMARY KEY (id)
	);
CREATE TABLE Teacher
	(id mediumint NOT NULL auto_increment,
	SchoolID mediumint NOT NULL,
	Email nvarchar(255) NOT NULL,
	FirstName nvarchar(255) NOT NULL,
	LastName nvarchar(255) NOT NULL,
	Phone nvarchar(20) NOT NULL,
	AltPhone nvarchar(20),
	CreateDate datetime,
	PRIMARY KEY (id),
	FOREIGN KEY (SchoolID)
		REFERENCES School(id)
	);
CREATE TABLE Login
	(id mediumint NOT NULL auto_increment,
	TeacherID mediumint NOT NULL,
	RoleID mediumint NOT NULL,
	Username nvarchar(255) NOT NULL,
	Password nvarchar(1000) NOT NULL,
	Salt nvarchar(20),
	LastLoginDate datetime,
	PRIMARY KEY (id),
	FOREIGN KEY (TeacherID)
		REFERENCES Teacher(id),
	FOREIGN KEY (RoleID)
		REFERENCES Role(id)
	);
CREATE TABLE Team
	(id mediumint NOT NULL auto_increment,
	Name nvarchar(255),
	MaxIndividuals nvarchar(255),
	CreatedDate datetime,
	PRIMARY KEY (id)
	);
CREATE TABLE Event
	(id mediumint NOT NULL auto_increment,
	Name nvarchar(255),
	EventTypeID mediumint,
	MinTeamSize int NOT NULL,
	MaxTeamSize int NOT NULL,
	MaxEntriesPerSchool int NOT NULL,
	CreatedDate datetime,
	Details nvarchar(4000),
	PRIMARY KEY (id),
	FOREIGN KEY (EventTypeID)
		REFERENCES EventType(id)
	);
CREATE TABLE EventInstance
	(id mediumint NOT NULL auto_increment,
	EventID mediumint NOT NULL,
	CreatedDate datetime,
	StartTime datetime, 
	EndTime datetime,
	Location nvarchar(4000),
	PRIMARY KEY(id),
	FOREIGN KEY (EventID)
		REFERENCES Event(id)
	);
CREATE TABLE StudentEventTeam
	(
	StudentID mediumint,
	EventInstanceID mediumint,
	TeamID mediumint,
	EnrollmentDate datetime,
	EnrolledByTeacherID mediumint,
	PRIMARY KEY (StudentID, EventInstanceID, TeamID),
	FOREIGN KEY (StudentID)
		REFERENCES Student(id),
	FOREIGN KEY (EventInstanceID)
		REFERENCES EventInstance(id),
	FOREIGN KEY (TeamID)
		REFERENCES Team(id),
	FOREIGN KEY (EnrolledByTeacherID)
		REFERENCES Teacher(id)
	);
INSERT INTO `fblaem`.`Role`(`RoleName`)VALUES("Administrator");
INSERT INTO `fblaem`.`Role`(`RoleName`)VALUES("Teacher");
INSERT INTO `fblaem`.`School`(`Name`,`StreetAddress`,`City`,`State`,`Zip`,`Phone`,`CreatedDate`)VALUES("Test School","123 fake st","Clearfield","UT","84015","1234567890",now());
INSERT INTO `fblaem`.`School`(`Name`,`StreetAddress`,`City`,`State`,`Zip`,`Phone`,`CreatedDate`)VALUES("Test School2","123 fake st","Layton","UT","84444","1234567890",now());
INSERT INTO `fblaem`.`Teacher`(`SchoolID`,`Email`,`FirstName`,`LastName`,`Phone`,`CreateDate`)VALUES("1","teacher@nowhere.com","AdminUser","AdminUser","1234567890",now());
INSERT INTO `fblaem`.`Teacher`(`SchoolID`,`Email`,`FirstName`,`LastName`,`Phone`,`CreateDate`)VALUES("2","teacher2@nowhere.com","TestTeacherfname","TestTeacherlname","1234567890",now());
INSERT INTO `fblaem`.`Student`(`SchoolID`,`FirstName`,`LastName`,`CreateDate`)VALUES("1","testStudentfname","testStudentlname",now());
INSERT INTO `fblaem`.`Login`(`TeacherID`,`RoleID`,`Username`,`Password`,`Salt`)VALUES("1","1","admin","password","salt");
INSERT INTO `fblaem`.`EventType`(`TypeName`)VALUES("Individual");
INSERT INTO `fblaem`.`EventType`(`TypeName`)VALUES("Team");
INSERT INTO `fblaem`.`EventType`(`TypeName`)VALUES("Team/Individual");
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Accounting I","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Accounting II","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Banking & Financial Systems","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Calculations","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Communication","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Ethics","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Law","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Math","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Presentation","1","3","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Business Procedures","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Client Service","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Computer Applications","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Computer Game & Simulation Program","1","3","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Computer Problem Solving","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Cyber Security","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Desktop Publishing","2","2","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Digital Design & Promotion","1","2","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Digital Video Production","1","3","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("E-business","1","3","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Economics","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Emerging Business Issues","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Entrepreneurship","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("FBLA Principles & Procedures","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Global Business","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Health Care Administration","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Help Desk","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Hospitality Management","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Impromptu Speaking","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Introduction to Business","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Introduction to Business Communication","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Introduction to Parliamentary Procedure","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Introduction to Technology Concepts","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Job Interview","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Management Decision Making","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Marketing","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Network Design","2","3","2","2",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Networking Concepts","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Parliamentary Procedures","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Personal Finance","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Public Speaking II","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Sports Management","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Spreadsheet Applications","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Technology Concepts","1","1","20","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Web Site Design","1","3","2","3",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Word Processing I","1","1","2","1",now());
INSERT INTO `fblaem`.`Event`(`Name`,`MinTeamSize`,`MaxTeamSize`,`MaxEntriesPerSchool`,`EventTypeID`,`CreatedDate`)VALUES("Word Processing II","1","1","2","1",now());