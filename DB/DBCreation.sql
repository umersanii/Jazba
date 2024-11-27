-- Create the database
CREATE DATABASE IF NOT EXISTS JazbaDB;
USE JazbaDB;

-- Table: achievement
CREATE TABLE achievement (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    dateUnlocked DATE DEFAULT NULL,
    badge VARCHAR(255) DEFAULT NULL,
    memberID INT DEFAULT NULL,
    unlocked TINYINT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: admin
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL
);

-- Table: exercise
CREATE TABLE exercise (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    muscleGroups VARCHAR(255) DEFAULT NULL,
    equipment VARCHAR(255) DEFAULT NULL,
    difficulty INT DEFAULT NULL,
    instructions TEXT DEFAULT NULL,
    sets INT DEFAULT NULL,
    reps INT DEFAULT NULL,
    weight DECIMAL(10, 2) DEFAULT NULL,
    memberid INT DEFAULT NULL,
    workout_preset_id INT DEFAULT NULL,
    FOREIGN KEY (memberid) REFERENCES member(id),
    FOREIGN KEY (workout_preset_id) REFERENCES workoutpreset(id)
);

-- Table: exercisestats
CREATE TABLE exercisestats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    statsID INT DEFAULT NULL,
    exerciseID INT DEFAULT NULL,
    sets INT DEFAULT NULL,
    reps INT DEFAULT NULL,
    weight DECIMAL(10, 2) DEFAULT NULL,
    duration INT DEFAULT NULL,
    FOREIGN KEY (statsID) REFERENCES stats(id),
    FOREIGN KEY (exerciseID) REFERENCES exercise(id)
);

-- Table: member
CREATE TABLE member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    password VARCHAR(255) DEFAULT NULL,
    membership_status VARCHAR(255) DEFAULT NULL,
    registration_date DATE DEFAULT NULL
);

-- Table: membership
CREATE TABLE membership (
    id INT AUTO_INCREMENT PRIMARY KEY,
    startDate DATE DEFAULT NULL,
    endDate DATE DEFAULT NULL,
    paymentStatus VARCHAR(255) DEFAULT NULL,
    memberID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: notification
CREATE TABLE notification (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) DEFAULT NULL,
    message TEXT DEFAULT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(255) DEFAULT NULL,
    memberID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: payment
CREATE TABLE payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) DEFAULT NULL,
    date DATE DEFAULT NULL,
    type VARCHAR(255) DEFAULT NULL,
    paymentMethod VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    memberID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: profile
CREATE TABLE profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    height INT DEFAULT NULL,
    weight INT DEFAULT NULL,
    age INT DEFAULT NULL,
    fitnessLevel VARCHAR(255) DEFAULT NULL,
    memberID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: stats
CREATE TABLE stats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sets INT DEFAULT NULL,
    reps INT DEFAULT NULL,
    weight DECIMAL(10, 2) DEFAULT NULL,
    workoutID INT DEFAULT NULL,
    exerciseName VARCHAR(255) DEFAULT NULL,
    memberID INT DEFAULT NULL,
    date DATE DEFAULT NULL,
    FOREIGN KEY (workoutID) REFERENCES workout(id),
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: workout
CREATE TABLE workout (
    id INT AUTO_INCREMENT PRIMARY KEY,
    memberID INT DEFAULT NULL,
    date DATE DEFAULT NULL,
    time TIME DEFAULT NULL,
    presetID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id),
    FOREIGN KEY (presetID) REFERENCES workoutpreset(id)
);

-- Table: workoutpreset
CREATE TABLE workoutpreset (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    targetMuscles VARCHAR(255) DEFAULT NULL,
    exercises TEXT DEFAULT NULL,
    memberID INT DEFAULT NULL,
    FOREIGN KEY (memberID) REFERENCES member(id)
);

-- Table: workouttemplate
CREATE TABLE workouttemplate (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) DEFAULT NULL,
    description TEXT DEFAULT NULL,
    targetMuscles VARCHAR(255) DEFAULT NULL,
    repetitions INT DEFAULT NULL,
    exercises TEXT DEFAULT NULL
);
