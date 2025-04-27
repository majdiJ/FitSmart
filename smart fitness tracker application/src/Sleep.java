// Sleep Class
// This class will store sleep information such as duration, date and time of sleep, and optional notes.

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class Sleep implements Serializable {
    private static final long serialVersionUID = 1L;

    private int duration; // in minutes
    private LocalDateTime dateTime; // Date and time of sleep
    private String notes; // Optional notes about the sleep

    // Constructor
    public Sleep(int duration, LocalDateTime dateTime, String notes) {
        setDuration(duration);
        setDateTime(dateTime);
        setNotes(notes);
    }

    // Getters and Setters
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            throw new IllegalArgumentException("Sleep duration must be positive.");
        } else {
            this.duration = duration;
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
