-- Script tạo DB cho bài 5 (SQL Server)
IF DB_ID(N'QuanLyNhanSu') IS NULL
BEGIN
    CREATE DATABASE QuanLyNhanSu;
END
GO

USE QuanLyNhanSu;
GO

IF OBJECT_ID(N'dbo.job_title', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.job_title (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        job_code VARCHAR(20) NOT NULL UNIQUE,
        job_title VARCHAR(100) NOT NULL,
        job_description VARCHAR(500) NULL,
        job_specification NVARCHAR(255) NULL,
        note VARCHAR(500) NULL,
        created_at DATETIME2 DEFAULT SYSUTCDATETIME()
    );
END
GO
