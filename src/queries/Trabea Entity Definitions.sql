CREATE DATABASE Trabea
GO

USE Trabea;
GO

CREATE TABLE Users(
	WorkEmail varchar(100) PRIMARY KEY, 
	[Password] varchar(255) NOT NULL
)

CREATE TABLE Roles(
	Id int PRIMARY KEY IDENTITY(1,1),
	[Name] varchar(50) NOT NULL UNIQUE
)

CREATE TABLE UserRoles(
	UserId varchar(100) REFERENCES Users(WorkEmail),
	RoleId int REFERENCES Roles(Id),
	PRIMARY KEY(UserId, RoleId)
)

CREATE TABLE Employees(
	Id int PRIMARY KEY IDENTITY(1,1),
	FirstName varchar(25) NOT NULL,
	LastName varchar(50) NOT NULL,
	WorkEmail varchar(100) NOT NULL REFERENCES Users(WorkEmail),
)

CREATE TABLE PartTimeEmployees(
	Id int PRIMARY KEY IDENTITY(1,1),
	FirstName varchar(25) NOT NULL,
	LastName varchar(50) NOT NULL,
	PersonalEmail varchar(100) NOT NULL UNIQUE,
	PersonalPhoneNumber varchar(20) NOT NULL UNIQUE,
	LastEducation varchar(50) NOT NULL,
	OnGoingEducation varchar(50),
	JoinDate datetime NOT NULL DEFAULT(GETDATE()),
	ResignDate datetime,
	WorkEmail varchar(100) NOT NULL REFERENCES Users(WorkEmail),
)


CREATE TABLE WorkShifts(
	Id int PRIMARY KEY,
	StartTime time NOT NULL,
	EndTime time NOT NULL
)

CREATE TABLE WorkSchedules(
	Id int PRIMARY KEY IDENTITY(1,1),
	WorkDate date NOT NULL,
	IsApproved bit,
	ManagerId int REFERENCES Employees(Id),
	PartTimeEmployeeId int NOT NULL REFERENCES PartTimeEmployees(Id),
	WorkShiftId int NOT NULL REFERENCES WorkShifts(Id)
)