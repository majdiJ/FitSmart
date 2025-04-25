// FitnessGoal Class
// This class will store fitness goals such as daily step count, workout duration, and calories burned.
public class FitnessGoal {
	private int dailyStepCount;
	private int workoutDuration; // in minutes
	private int caloriesBurned;
    private int workoutsPerWeek; // number of workouts per week
	private double dailyWaterIntake; // in liters
	private int sleepMinutes; // in minutes
	private double weightGoal; // in kg
	private double strength; // in kg

    // Constructor
    public FitnessGoal(int dailyStepCount, int workoutDuration, int caloriesBurned, int workoutsPerWeek, double dailyWaterIntake, int sleepMinutes, double weightGoal, double strength) {
        this.dailyStepCount = dailyStepCount;
        this.workoutDuration = workoutDuration;
        this.caloriesBurned = caloriesBurned;
        this.workoutsPerWeek = workoutsPerWeek;
        this.dailyWaterIntake = dailyWaterIntake;
        this.sleepMinutes = sleepMinutes;
        this.weightGoal = weightGoal;
        this.strength = strength;
    }

    // Getters and Setters
    public int getDailyStepCount() {
        return dailyStepCount;
    }
    public void setDailyStepCount(int dailyStepCount) {
        if (dailyStepCount == 0) { // No goal set
            this.dailyStepCount = dailyStepCount;
        } else if (dailyStepCount < 0) {
            throw new IllegalArgumentException("Daily step count cannot be negative.");
        } else {
            this.dailyStepCount = dailyStepCount;
        } 
    }

    public int getWorkoutDuration() {
        return workoutDuration;
    }
    public void setWorkoutDuration(int workoutDuration) {
        if (workoutDuration == 0) { // No goal set
            this.workoutDuration = workoutDuration;
        } else if (workoutDuration < 0) {
            throw new IllegalArgumentException("Workout duration cannot be negative.");
        } else {
            this.workoutDuration = workoutDuration;
        }
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    public void setCaloriesBurned(int caloriesBurned) {
        if (caloriesBurned == 0) { // No goal set
            this.caloriesBurned = caloriesBurned;
        } else if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories burned cannot be negative.");
        } else {
            this.caloriesBurned = caloriesBurned;
        }
    }

    public int getWorkoutsPerWeek() {
        return workoutsPerWeek;
    }
    public void setWorkoutsPerWeek(int workoutsPerWeek) {
        if (workoutsPerWeek == 0) { // No goal set
            this.workoutsPerWeek = workoutsPerWeek;
        } else if (workoutsPerWeek < 0) {
            throw new IllegalArgumentException("Workouts per week cannot be negative.");
        } else {
            this.workoutsPerWeek = workoutsPerWeek;
        }
    }

    public double getDailyWaterIntake() {
        return dailyWaterIntake;
    }
    public void setDailyWaterIntake(double dailyWaterIntake) {
        if (dailyWaterIntake == 0) { // No goal set
            this.dailyWaterIntake = dailyWaterIntake;
        } else if (dailyWaterIntake < 0) {
            throw new IllegalArgumentException("Daily water intake cannot be negative.");
        } else {
            this.dailyWaterIntake = dailyWaterIntake;
        }
    }

    public int getsleepMinutes() {
        return sleepMinutes;
    }
    public void setsleepMinutes(int sleepMinutes) {
        if (sleepMinutes == 0) { // No goal set
            this.sleepMinutes = sleepMinutes;
        } else if (sleepMinutes < 0) {
            throw new IllegalArgumentException("Sleep minutes cannot be negative.");
        } else {
            this.sleepMinutes = sleepMinutes;
        }
    }

    public double getWeightGoal() {
        return weightGoal;
    }
    public void setWeightGoal(double weightGoal) {
        if (weightGoal == 0) { // No goal set
            this.weightGoal = weightGoal;
        } else if (weightGoal < 0) {
            throw new IllegalArgumentException("Weight goal cannot be negative.");
        } else {
            this.weightGoal = weightGoal;
        }
    }

    public double getStrength() {
        return strength;
    }
    public void setStrength(double strength) {
        if (strength == 0) { // No goal set
            this.strength = strength;
        } else if (strength < 0) {
            throw new IllegalArgumentException("Strength cannot be negative.");
        } else {
            this.strength = strength;
        }
    }
}
