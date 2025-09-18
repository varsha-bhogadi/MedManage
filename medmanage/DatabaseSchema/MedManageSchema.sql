-- Creating database MedManage
CREATE DATABASE MedManage;
USE MedManage;

-- Patients Schema
CREATE TABLE Patients (
    PatientID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Gender ENUM('Male', 'Female', 'Other'),
    BloodPressure VARCHAR(50),
    ContactNumber VARCHAR(15),
    Email VARCHAR(50) NOT NULL,
    Address VARCHAR(255),
    State VARCHAR(255)
);

-- Doctors Schema
CREATE TABLE Doctors (
    DoctorID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Specialization VARCHAR(50),
    Gender ENUM('Male','Female','Other'),
    ContactNumber VARCHAR(15),
    Email VARCHAR(50) NOT NULL,
    Address VARCHAR(255),
    State VARCHAR(255)
);

-- Staff Table
CREATE TABLE Staff (
    StaffID INT PRIMARY KEY AUTO_INCREMENT,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Role VARCHAR(255),
    Gender ENUM('Male','Female','Other'),
    ContactNumber VARCHAR(15),
    Email VARCHAR(255),
    Address VARCHAR(255),
    State VARCHAR(255)
);

-- Appointment Table
CREATE TABLE Appointment (
    AppointmentID INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT NOT NULL,
    DoctorID INT NOT NULL,
    AppointmentDate DATE NOT NULL,
    Status ENUM('Scheduled', 'Completed', 'Cancelled'),
    FOREIGN KEY (PatientID) REFERENCES Patients(PatientID) ON DELETE CASCADE,
    FOREIGN KEY (DoctorID) REFERENCES Doctors(DoctorID) ON DELETE CASCADE
); 

-- Payment Table
CREATE TABLE Payment (
    PaymentID INT PRIMARY KEY AUTO_INCREMENT,
    AppointmentID INT NOT NULL,
    PatientID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    PaymentDate DATE NOT NULL,
    Mode ENUM('UPI', 'CASH'),
    FOREIGN KEY (AppointmentID) REFERENCES Appointment(AppointmentID) ON DELETE CASCADE,
    FOREIGN KEY (PatientID) REFERENCES Patients(PatientID) ON DELETE CASCADE
);

-- Inventory Table
CREATE TABLE Inventory (
    StockID INT AUTO_INCREMENT PRIMARY KEY,
    StockName VARCHAR(255) NOT NULL,
    StockQuantity INT NOT NULL,
    StockPrice DECIMAL(10, 2),
    ExpiryDate DATE,
    LastUpdated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
