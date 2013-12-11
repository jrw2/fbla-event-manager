USE fblaem;
DROP VIEW IF EXISTS User_View;
DROP VIEW IF EXISTS Group_View;
DROP TABLE IF EXISTS StudentTeam;
DROP TABLE IF EXISTS Team;
DROP TABLE IF EXISTS Login;
DROP TABLE IF EXISTS Teacher;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS Student;
DROP TABLE IF EXISTS School;
DROP TABLE IF EXISTS EventInstance;
DROP TABLE IF EXISTS Event;
DROP TABLE IF EXISTS EventType;

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
		REFERENCES School(id) ON DELETE CASCADE ON UPDATE CASCADE
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
		REFERENCES School(id) ON DELETE CASCADE ON UPDATE CASCADE
	);
CREATE TABLE `Login` (
  `id` mediumint(9) NOT NULL AUTO_INCREMENT,
  `TeacherID` mediumint(9) NOT NULL,
  `RoleID` mediumint(9) NOT NULL,
  `Username` varchar(255) CHARACTER SET utf8 NOT NULL,
  `Password` varchar(1000) CHARACTER SET utf8 NOT NULL,
  `LastLoginDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `Username_UNIQUE` (`Username`),
  KEY `TeacherID` (`TeacherID`),
  KEY `RoleID` (`RoleID`),
  CONSTRAINT `Login_ibfk_2` FOREIGN KEY (`RoleID`) REFERENCES `Role` (`id`)
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
		REFERENCES Event(id) ON DELETE CASCADE ON UPDATE CASCADE
	);
CREATE TABLE Team
	(id mediumint NOT NULL auto_increment,
	Name nvarchar(255),
	MaxIndividuals nvarchar(255),
	CreatedDate datetime,
	EventInstanceID mediumint NOT NULL,
	SchoolId int NOT NULL,
	FOREIGN KEY (EventInstanceID)
		REFERENCES EventInstance(id) ON DELETE CASCADE ON UPDATE CASCADE,
	PRIMARY KEY (id)
	);
CREATE TABLE StudentTeam
	(
	StudentID mediumint,
	TeamID mediumint,
	EnrollmentDate datetime,
	EnrolledByTeacherID mediumint,
	PRIMARY KEY (StudentID, TeamID),
	FOREIGN KEY (StudentID)
		REFERENCES Student(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (TeamID)
		REFERENCES Team(id) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (EnrolledByTeacherID)
		REFERENCES Teacher(id) ON DELETE CASCADE ON UPDATE CASCADE
	);
CREATE VIEW `fblaem`.`Group_View` AS
    select 
        `l`.`Username` AS `Username`, `r`.`RoleName` AS `RoleName`
    from
        (`fblaem`.`Login` `l`
        join `fblaem`.`Role` `r` ON ((`l`.`RoleID` = `r`.`id`)));
CREATE VIEW `fblaem`.`User_View` AS
    select 
        `fblaem`.`Login`.`Username` AS `Username`,
        `fblaem`.`Login`.`Password` AS `Password`
    from
        `fblaem`.`Login`;

INSERT INTO `fblaem`.`Role`(`RoleName`)VALUES("Administrator");
INSERT INTO `fblaem`.`Role`(`RoleName`)VALUES("Teacher");
INSERT INTO `fblaem`.`School`(`Name`,`StreetAddress`,`City`,`State`,`Zip`,`Phone`,`CreatedDate`)VALUES("Test School","123 fake st","Clearfield","UT","84015","1234567890",now());
INSERT INTO `fblaem`.`School`(`Name`,`StreetAddress`,`City`,`State`,`Zip`,`Phone`,`CreatedDate`)VALUES("Test School2","123 fake st","Layton","UT","84444","1234567890",now());
INSERT INTO `fblaem`.`Teacher`(`SchoolID`,`Email`,`FirstName`,`LastName`,`Phone`,`CreateDate`)VALUES("1","teacher@nowhere.com","AdminUser","AdminUser","1234567890",now());
INSERT INTO `fblaem`.`Teacher`(`SchoolID`,`Email`,`FirstName`,`LastName`,`Phone`,`CreateDate`)VALUES("2","teacher2@nowhere.com","TestTeacherfname","TestTeacherlname","1234567890",now());
INSERT INTO `fblaem`.`Student`(`SchoolID`,`FirstName`,`LastName`,`CreateDate`)VALUES("1","testStudentfname","testStudentlname",now());
INSERT INTO `fblaem`.`Student`(`SchoolID`,`FirstName`,`LastName`,`CreateDate`)VALUES("1","test2Studentfname","test2Studentlname",now());
INSERT INTO `fblaem`.`Login`(`TeacherID`,`RoleID`,`Username`,`Password`)VALUES("1","1","admin","8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918");
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
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(1,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(2,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(3,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(4,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(5,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(6,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(7,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(8,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(9,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(10,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(11,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(12,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(13,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(14,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(15,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(16,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(17,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(18,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(19,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(20,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(21,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(22,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(23,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(24,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(25,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(26,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(27,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(28,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(29,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(30,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(31,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(32,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(33,now(),now(),"");
INSERT INTO `fblaem`.`EventInstance`(`EventID`,`CreatedDate`,`StartTime`,`Location`)VALUES(34,now(),now(),"");