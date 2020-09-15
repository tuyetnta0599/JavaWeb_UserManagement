USE master
GO
CREATE DATABASE UserManagement
GO
USE UserManagement
GO

CREATE TABLE [users]
(
	[id] VARCHAR(30) PRIMARY KEY,
	[password] VARCHAR(64) NOT NULL,
	[email] VARCHAR(50) NOT NULL,
	[fullname] NVARCHAR(30) NOT NULL,
	[phone] VARCHAR(13),
	[photo] VARCHAR(50),
	[idRole] INT NOT NULL,
	[status] VARCHAR(10) NOT NULL
)
GO
CREATE TABLE [roles]
(
	[id] INT PRIMARY KEY,
	[name] VARCHAR(6) NOT NULL,
)
GO 
CREATE TABLE [promotionList]
(
 [userID] VARCHAR(30) PRIMARY KEY,
 [rank] INT NOT NULL
)
GO 
ALTER TABLE [dbo].[promotionList]
ADD FOREIGN KEY (userID) REFERENCES dbo.users(id)

ALTER TABLE [dbo].[users]
ADD FOREIGN KEY (idRole) REFERENCES [dbo].[roles](id)


INSERT INTO dbo.roles
        ( id, name )
VALUES  ( 1, -- id - int
          'admin'  -- name - varchar(6)
          )
INSERT INTO dbo.roles
        ( id, name )
VALUES  ( 2, -- id - int
          'sub'  -- name - varchar(6)
          )
INSERT INTO [dbo].[users]
        ( [id] ,
          [password] ,
          [email] ,
          [fullname] ,
          [phone] ,
          [photo] ,
          [idRole] ,
          [status]
        )
VALUES  ( 'u1' , -- id - varchar(30)
          '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b' , -- password - varchar(30)
          'tuyetnta@gmail.com' , -- email - varchar(50)
          N'nguyen thi anh tuyet' , -- fullname - nvarchar(30)
          '00363695059' , -- phone - varchar(13)
          'tuyet' , -- photo - varchar(50)
          1 , -- idRole - varchar(6)
          'active'  -- status - varchar(5)
        )
INSERT INTO [dbo].[users]
        ( [id] ,
          [password] ,
          [email] ,
          [fullname] ,
          [phone] ,
          [photo] ,
          [idRole] ,
          [status]
        )
VALUES  ( 'u2' , -- id - varchar(30)
          '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b' , -- password - varchar(30)
          'hoangnm@gmail.com' , -- email - varchar(50)
          N'nguyen minh hoang' , -- fullname - nvarchar(30)
          '123456789' , -- phone - varchar(13)
          'hoang' , -- photo - varchar(50)
		   2 , -- idRole - varchar(6)
          'active'  -- status - varchar(5)
        )

INSERT INTO dbo.users
        ( id ,
          password ,
          email ,
          fullname ,
          phone ,
          photo ,
          idRole ,
          status
        )
VALUES  ( 'u3' , -- id - varchar(30)
          '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b' , -- password - varchar(64)
          'trang@gmail.com' , -- email - varchar(50)
          N'cao quynh trang' , -- fullname - nvarchar(30)
          '123456789' , -- phone - varchar(13)
          '123' , -- photo - varchar(256)
          2 , -- idRole - int
          'active'  -- status - varchar(10)
        )
INSERT INTO dbo.users
        ( id ,
          password ,
          email ,
          fullname ,
          phone ,
          photo ,
          idRole ,
          status
        )
VALUES  ( 'u4' , -- id - varchar(30)
          '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b' , -- password - varchar(64)
          'nhu@gmail.com' , -- email - varchar(50)
          N'nguyen ngoc quynh nhu' , -- fullname - nvarchar(30)
          '123456789' , -- phone - varchar(13)
          '123' , -- photo - varchar(256)
          2 , -- idRole - int
          'active'  -- status - varchar(10)
        )
INSERT INTO dbo.users
        ( id ,
          password ,
          email ,
          fullname ,
          phone ,
          photo ,
          idRole ,
          status
        )
VALUES  ( 'u5' , -- id - varchar(30)
          '6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b' , -- password - varchar(64)
          'cuong@gmail.com' , -- email - varchar(50)
          N'nguyen viet cuong' , -- fullname - nvarchar(30)
          '123456789' , -- phone - varchar(13)
          'cuong' , -- photo - varchar(256)
          1 , -- idRole - int
          'active'  -- status - varchar(10)
        )
