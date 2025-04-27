// Workout Class
// This class will store workout information such as type, duration, and calories burned and time and date of workout.
// Additionally, it will allow users to add notes to their workouts.

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class Workout implements Serializable {
	private static final long serialVersionUID = 1L;

    private String type; // e.g., running, cycling
    private int duration; // in minutes
    private int caloriesBurned;
    private LocalDateTime dateTime; // Date and time of the workout
    private String notes; // Optional notes for the workout

    // Constructor
    public Workout(String type, int duration, int caloriesBurned, LocalDateTime dateTime, String notes) {
        this.type = type;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    // Predefined workout types
    public static final String[] WORKOUT_TYPES = {
        "Running",
        "Cycling",
        "Swimming",
        "Yoga",
        "Weightlifting",
        "Hiking",
        "Dancing",
        "Rowing",
        "Pilates",
        "Walking",
        "Aerobics",
        "Boxing",
        "Rock Climbing",
        "CrossFit",
        "Martial Arts",
        "Jump Rope",
        "Other"
    };

    // Getters and Setters
    public String getType() {
        return type;
    }
    public void setType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Workout type cannot be null or empty.");
        } else {
            this.type = type;
        }
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Workout duration must be positive.");
        } else {
            this.duration = duration;
        }
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }
    public void setCaloriesBurned(int caloriesBurned) {
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories burned cannot be negative.");
        } else {
            this.caloriesBurned = caloriesBurned;
        }
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException("Date and time cannot be null.");
        } else {
            this.dateTime = dateTime;
        }
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes; // Optional, can be null
    }
}
