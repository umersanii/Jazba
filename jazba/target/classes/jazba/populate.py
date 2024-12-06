import random
from datetime import datetime, timedelta
import pymysql

# Database connection
connection = pymysql.connect(
    host='localhost',       # Change as needed
    user='root',            # Change as needed
    password='2cool4skool',    # Change as needed
    database='JazbaDB'  # Change as needed
)

cursor = connection.cursor()

# Generate random data for the workout table
workouts = [
    ('Upper Body Strength', 'Focus on chest, shoulders, and triceps', 'Chest, Shoulders, Triceps', 'Bench Press, Shoulder Press, Tricep Dips'),
    ('Lower Body Blast', 'Leg-focused workout', 'Quads, Hamstrings, Glutes', 'Squats, Deadlifts, Lunges'),
    ('Full Body Burn', 'Mix of upper and lower body exercises', 'Full Body', 'Burpees, Push-ups, Pull-ups'),
    ('Core Crusher', 'Target core strength and stability', 'Abs, Obliques', 'Plank, Russian Twists, Leg Raises'),
    ('Cardio Endurance', 'Improve cardiovascular health', 'Cardio', 'Running, Jump Rope, Cycling')
]

for name, description, targetMuscles, exercises in workouts:
    cursor.execute(
        "INSERT INTO workout (name, description, targetMuscles, exercises, memberID) VALUES (%s, %s, %s, %s, %s)",
        (name, description, targetMuscles, exercises, 1)
    )

# Get workout IDs
cursor.execute("SELECT id FROM workout WHERE memberID = 1")
workout_ids = [row[0] for row in cursor.fetchall()]

# Generate random stats entries
start_date = datetime(2024, 1, 1)
end_date = datetime(2024, 12, 31)

def random_date(start, end):
    return start + timedelta(days=random.randint(0, (end - start).days))

stats_entries = []
for _ in range(365):
    workout_id = random.choice(workout_ids)
    exercise_name = random.choice(['Bench Press', 'Shoulder Press', 'Tricep Dips', 'Squats', 'Deadlifts', 'Lunges', 'Burpees', 'Push-ups', 'Pull-ups', 'Plank'])
    date = random_date(start_date, end_date).strftime('%Y-%m-%d')
    sets = random.randint(3, 6)
    reps = random.randint(8, 15)
    weight = round(random.uniform(20, 100), 2)

    stats_entries.append((sets, reps, weight, workout_id, exercise_name, 1, date))

cursor.executemany(
    "INSERT INTO stats (sets, reps, weight, workoutID, exerciseName, memberID, date) VALUES (%s, %s, %s, %s, %s, %s, %s)",
    stats_entries
)

# Generate random workout presets
presets = [
    ('Strength Training A', 'A structured plan for strength', 'Chest, Back, Legs', 'Bench Press, Pull-Ups, Squats'),
    ('Endurance Routine', 'Focused on stamina and endurance', 'Full Body', 'Cycling, Jump Rope, Running'),
    ('Core Strength Program', 'Core-focused exercises', 'Abs, Lower Back', 'Plank, Side Plank, Supermans')
]

for name, description, targetMuscles, exercises in presets:
    cursor.execute(
        "INSERT INTO workoutpreset (name, description, targetMuscles, exercises, memberID) VALUES (%s, %s, %s, %s, %s)",
        (name, description, targetMuscles, exercises, 1)
    )

# Commit changes and close connection
connection.commit()
cursor.close()
connection.close()

print("Random data for member with id 1 populated successfully.")
