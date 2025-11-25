USE Trabea;
GO


INSERT INTO Users VALUES
('aldi.danis@trabea.co.id',		'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('anna.danis@trabea.co.id',		'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('muh.danis@trabea.co.id',		'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('peter.johnson@trabea.co.id',	'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('patra.tobias@trabea.co.id',	'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('alan.conway@trabea.co.id',	'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('ali.jonathan@trabea.co.id',	'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW'),
('robert.tan@trabea.co.id',		'$2a$11$AEPkRqjVftmjTWPvRvmBieyhuRSk8wbmf47a8xr6tplfk7MjKQgJW')

INSERT INTO Roles VALUES
('Administrator'), ('Manager'), ('PartTimer')

INSERT INTO UserRoles(UserId, RoleId) VALUES
('aldi.danis@trabea.co.id',		1),
('anna.danis@trabea.co.id',		1),
('aldi.danis@trabea.co.id',		2),
('muh.danis@trabea.co.id',		2),
('peter.johnson@trabea.co.id',	3),
('patra.tobias@trabea.co.id',	3),
('alan.conway@trabea.co.id',	3),
('ali.jonathan@trabea.co.id',	3),
('robert.tan@trabea.co.id',		3)


SET IDENTITY_INSERT Employees ON;
INSERT INTO Employees(Id, FirstName, LastName, WorkEmail) VALUES
(1,		'Aldi',	'Danis',	'aldi.danis@trabea.co.id'),
(2,		'Anna',	'Danis',	'anna.danis@trabea.co.id'),
(3,		'Muh',	'Danis',	'muh.danis@trabea.co.id')
SET IDENTITY_INSERT Employees OFF;


SET IDENTITY_INSERT PartTimeEmployees ON;
INSERT INTO PartTimeEmployees (Id, FirstName, LastName, PersonalEmail, 
								PersonalPhoneNumber, LastEducation, 
								OnGoingEducation, JoinDate, WorkEmail)VALUES 
(1,		'Peter',			'Johnson',		'peter.johnson@gmail.com',		
		'1234567821',		'Master',
		'Doctorate',		DATEADD(YEAR, -1, GETDATE()),		'peter.johnson@trabea.co.id'),
(2,		'Patra',			'Tobias',		'patra.tobias@hotmail.com', 
		'8761231677',		'HighSchool',
		'Bachelor',			DATEADD(YEAR, -2, GETDATE()),		'patra.tobias@trabea.co.id'),
(3,		'Alan',				'Conway',		'alan.conway@gmail.com',
		'44117123151',		'Bachelor',
		null,				DATEADD(MONTH, -5, GETDATE()),		'alan.conway@trabea.co.id'),
(4,		'Ali',				'Jonathan',		'ali.jonathan@yahoo.com',
		'92132561725',		'Bachelor',
		'Master',			DATEADD(MONTH, -2, GETDATE()),		'ali.jonathan@trabea.co.id'
	),
(5,		'Robert',			'Tan',			'robert.tan@gmail.co.id',
		'6281249812525',	'Bachelor',
		null,				DATEADD(MONTH, -2, GETDATE()),		'robert.tan@trabea.co.id'
	)
SET IDENTITY_INSERT PartTimeEmployees OFF;


INSERT INTO WorkShifts(Id, StartTime, EndTime) VALUES
(1,		'08:00',	'12:00'),
(2,		'12:00',	'16:00'),
(3,		'16:00',	'20:00'),
(4,		'20:00',	'00:00')

--contoh telah di approve dan telah di reject
INSERT INTO WorkSchedules(WorkDate, WorkShiftId, 
	ManagerId,		PartTimeEmployeeId, IsApproved) VALUES
(GETDATE(),						1,
	1,							1,		1),
(GETDATE(),						2,
	1,							1,		1),
(GETDATE(),						3,
	1,							2,		1),
(GETDATE(),						4,
	1,							2,		1),
(DATEADD(DAY, 1, GETDATE()),	3,
	1,							2,		1),
(DATEADD(DAY, 1, GETDATE()),	4,
	1,							2,		1),
(DATEADD(DAY, 4, GETDATE()),	1,
	3,							3,		1),
(DATEADD(DAY, 4, GETDATE()),	1,
	3,							4,		1),
(DATEADD(DAY, 4, GETDATE()),	2,
	3,							4,		1),
(DATEADD(DAY, 4, GETDATE()),	2,
	3,							5,		0),							--disapproved
(DATEADD(DAY, 4, GETDATE()),	3,
	3,							5,		1),
(DATEADD(DAY, 4, GETDATE()),	4,
	3,							5,		1)

--contoh kasus yang belum di cek sama manager:
INSERT INTO WorkSchedules
(WorkDate,						WorkShiftId,	PartTimeEmployeeId) VALUES
(DATEADD(DAY, 7, GETDATE()),		1,		1),
(DATEADD(DAY, 7, GETDATE()),		2,		1),
(DATEADD(DAY, 7, GETDATE()),		1,		2),
(DATEADD(DAY, 7, GETDATE()),		2,		2),
(DATEADD(DAY, 7, GETDATE()),		3,		2),
(DATEADD(DAY, 7, GETDATE()),		3,		3),
(DATEADD(DAY, 7, GETDATE()),		4,		3),
(DATEADD(DAY, 9, GETDATE()),		1,		1),
(DATEADD(DAY, 9, GETDATE()),		2,		1),
(DATEADD(DAY, 9, GETDATE()),		1,		2),
(DATEADD(DAY, 9, GETDATE()),		2,		2),
(DATEADD(DAY, 9, GETDATE()),		3,		2),
(DATEADD(DAY, 9, GETDATE()),		3,		3),
(DATEADD(DAY, 9, GETDATE()),		4,		3)