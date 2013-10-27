CREATE DATABASE IF NOT EXISTS FBLAEM;
USE FBLAEM;
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
                (id mediumint NOT NuLL auto_increment,
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
                AltPhone nvarchar(20) NOT NULL,
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
                StartTime datetime NOT NULL, 
                EndTime datetime NOT NULL,
                Location nvarchar(4000),
                PRIMARY KEY(id),
                FOREIGN KEY (EventID)
                        REFERENCES Event(id)
                );
CREATE TABLE StudentEventTeam
                (
                StudentID mediumint,
                EventID mediumint,
                TeamID mediumint,
                EnrollmentDate datetime,
                EnrolledByTeacherID mediumint,
                PRIMARY KEY (StudentID, EventID, TeamID),
                FOREIGN KEY (StudentID)
                        REFERENCES Student(id),
                FOREIGN KEY (EventID)
                        REFERENCES Event(id),
                FOREIGN KEY (TeamID)
                        REFERENCES Team(id),
                FOREIGN KEY (EnrolledByTeacherID)
                        REFERENCES Teacher(id)
                );
