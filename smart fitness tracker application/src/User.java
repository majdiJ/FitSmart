// User Class:
// This class will store user information such as username, pin, name, age (dob), weight(kg), and fitness goals, workouts.

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

    private String username;
    private String pin;
    private String name;
    private LocalDateTime dob; // Date of Birth
    private double weight; // in kg
    private FitnessGoal fitnessGoal;
    private Workout[] workouts; // Array of workouts
    private Water[] waterIntake; // Array of water intake
    private Sleep[] sleep; // Array of sleep records
    private Weight[] weightRecords; // Array of weight records

    // Constructor
    public User(String username, String pin, String name, LocalDateTime dob, double weight) {
        this.username = username;
        this.pin = pin;
        this.name = name;
        this.dob = dob;
        this.weight = weight;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username, String[] existingUsernames) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        } 
        for (String existingUsername : existingUsernames) {
            if (username.equals(existingUsername)) {
                throw new IllegalArgumentException("Username already exists.");
            }
        }
        this.username = username;
    }

    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        if (pin == null || pin.isEmpty()) {
            throw new IllegalArgumentException("PIN cannot be null or empty.");
        } else if (pin.length() != 4) { // pin has to be 4 digits
            throw new IllegalArgumentException("PIN must be 4 digits.");
        } else {
            this.pin = pin;
        }
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        } else {
            this.name = name;
        }
    }

    public LocalDateTime getDob() {
        return dob;
    }
    public void setDob(LocalDateTime dob) {
        if (dob == null) {
            throw new IllegalArgumentException("Date of Birth cannot be null.");
        } else {
            this.dob = dob;
        }
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be a positive number.");
        } else {
            this.weight = weight;
        }
    }

    public FitnessGoal getFitnessGoal() {
        return fitnessGoal;
    }
    public void setFitnessGoal(FitnessGoal fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public Workout[] getWorkouts() {
        return workouts;
    }
    public void setWorkouts(Workout[] workouts) {
        this.workouts = workouts;
    }
    public void addWorkout(Workout workout) {
        if (workout == null) {
            throw new IllegalArgumentException("Workout cannot be null.");
        }
        if (workouts == null) {
            workouts = new Workout[] { workout };
        } else {
            Workout[] newWorkouts = new Workout[workouts.length + 1];
            System.arraycopy(workouts, 0, newWorkouts, 0, workouts.length);
            newWorkouts[workouts.length] = workout;
            workouts = newWorkouts;
        }
    }
    public void removeWorkout(Workout workout) {
        if (workout == null) {
            throw new IllegalArgumentException("Workout cannot be null.");
        }
        if (workouts != null) {
            Workout[] newWorkouts = new Workout[workouts.length - 1];
            int index = 0;
            for (Workout w : workouts) {
                if (!w.equals(workout)) {
                    newWorkouts[index++] = w;
                }
            }
            workouts = newWorkouts;
        }
    }

    public Water[] getWaterIntake() {
        return waterIntake;
    }
    public void setWaterIntake(Water[] waterIntake) {
        this.waterIntake = waterIntake;
    }
    public void addWaterIntake(Water water) {
        if (water == null) {
            throw new IllegalArgumentException("Water intake cannot be null.");
        }
        if (waterIntake == null) {
            waterIntake = new Water[] { water };
        } else {
            Water[] newWaterIntake = new Water[waterIntake.length + 1];
            System.arraycopy(waterIntake, 0, newWaterIntake, 0, waterIntake.length);
            newWaterIntake[waterIntake.length] = water;
            waterIntake = newWaterIntake;
        }
    }

    public Sleep[] getSleep() {
        return sleep;
    }
    public void setSleep(Sleep[] sleep) {
        this.sleep = sleep;
    }
    public void addSleep(Sleep sleep) {
        if (sleep == null) {
            throw new IllegalArgumentException("Sleep record cannot be null.");
        }
        if (this.sleep == null) {
            this.sleep = new Sleep[] { sleep };
        } else {
            Sleep[] newSleep = new Sleep[this.sleep.length + 1];
            System.arraycopy(this.sleep, 0, newSleep, 0, this.sleep.length);
            newSleep[this.sleep.length] = sleep;
            this.sleep = newSleep;
        }
    }

    public Weight[] getWeightRecords() {
        return weightRecords;
    }
    public void setWeightRecords(Weight[] weightRecords) {
        this.weightRecords = weightRecords;
    }
    public void addWeightRecord(Weight weight) {
        if (weight == null) {
            throw new IllegalArgumentException("Weight record cannot be null.");
        }
        if (this.weightRecords == null) {
            this.weightRecords = new Weight[] { weight };
        } else {
            Weight[] newWeightRecords = new Weight[this.weightRecords.length + 1];
            System.arraycopy(this.weightRecords, 0, newWeightRecords, 0, this.weightRecords.length);
            newWeightRecords[this.weightRecords.length] = weight;
            this.weightRecords = newWeightRecords;
        }
    }
}
