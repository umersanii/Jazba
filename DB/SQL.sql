-- Use the new database
USE JazbaDB;


-- -- Drop all tables
-- DROP TABLE IF EXISTS ExerciseStats;
-- DROP TABLE IF EXISTS Stats;
-- DROP TABLE IF EXISTS Payment;
-- DROP TABLE IF EXISTS Notification;
-- DROP TABLE IF EXISTS Achievement;
-- DROP TABLE IF EXISTS Membership;
-- DROP TABLE IF EXISTS Profile;
-- DROP TABLE IF EXISTS Workout;
-- DROP TABLE IF EXISTS WorkoutTemplate;
-- DROP TABLE IF EXISTS WorkoutPreset;
-- DROP TABLE IF EXISTS Exercise;
-- DROP TABLE IF EXISTS Member;
-- DROP TABLE IF EXISTS Admin;


-- Admin Table
CREATE TABLE Admin (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255)
);

-- Member Table
CREATE TABLE Member (
    id INT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    membership_status VARCHAR(50),
    registration_date DATE
);

-- Profile Table
CREATE TABLE Profile (
    id INT PRIMARY KEY,
    height INT,
    weight INT,
    age INT,
    fitnessLevel VARCHAR(50),
    memberID INT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Membership Table
CREATE TABLE Membership (
    id INT PRIMARY KEY,
    startDate DATE,
    endDate DATE,
    paymentStatus VARCHAR(50),
    memberID INT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Achievement Table
CREATE TABLE Achievement (
    id INT PRIMARY KEY,
    title VARCHAR(255),
    description TEXT,
    criteria TEXT,
    dateUnlocked DATE,
    badge VARCHAR(255),
    memberID INT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Notification Table
CREATE TABLE Notification (
    id INT PRIMARY KEY,
    type VARCHAR(50),
    message TEXT,
    timestamp DATETIME,
    status VARCHAR(50),
    memberID INT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Payment Table
CREATE TABLE Payment (
    id INT PRIMARY KEY,
    amount FLOAT,
    date DATE,
    type VARCHAR(50),
    paymentMethod VARCHAR(50),
    description TEXT,
    memberID INT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Workout Table
CREATE TABLE Workout (
    id INT PRIMARY KEY,
    memberID INT,
    date DATE,
    time TIME,
    preset VARCHAR(255),
    notes TEXT,
    FOREIGN KEY (memberID) REFERENCES Member(id)
);

-- Stats Table
CREATE TABLE Stats (
    id INT PRIMARY KEY,
    memberID INT,
    workoutID INT,
    date DATE,
    totalSets INT,
    totalReps INT,
    totalWeight FLOAT,
    duration INT, -- workout duration in minutes
    FOREIGN KEY (memberID) REFERENCES Member(id),
    FOREIGN KEY (workoutID) REFERENCES Workout(id)
);


-- Exercise Table
CREATE TABLE Exercise (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    muscleGroups VARCHAR(255),
    equipment VARCHAR(255),
    difficulty INT,
    instructions TEXT,
    sets INT,
    reps INT,
    weight FLOAT
);

-- ExerciseStats Table
CREATE TABLE ExerciseStats (
    id INT PRIMARY KEY,
    statsID INT,
    exerciseID INT,
    sets INT,
    reps INT,
    weight FLOAT,
    duration INT, -- time spent on this exercise
    FOREIGN KEY (statsID) REFERENCES Stats(id),
    FOREIGN KEY (exerciseID) REFERENCES Exercise(id)
);



-- WorkoutTemplate Table
CREATE TABLE WorkoutTemplate (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    targetMuscles VARCHAR(255),
    repetitions INT,
    exercises TEXT
);

-- WorkoutPreset Table
CREATE TABLE WorkoutPreset (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    targetMuscles VARCHAR(255),
    repetitions INT,
    exercises TEXT
);
