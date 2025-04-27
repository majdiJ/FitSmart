// Weight. Class
// This class will store weight information such as current weight, target weight, and date of measurement.

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class Weight implements Serializable {
    private static final long serialVersionUID = 1L;

    private double logedWeight; // in kg
    private LocalDateTime dateTime; // Date and time of the weight measurement
    private String notes; // Optional notes about the weight measurement

    // Constructor
    public Weight(double logedWeight, LocalDateTime dateTime, String notes) {
        setLogedWeight(logedWeight);
        setDateTime(dateTime);
        setNotes(notes);
    }

    // Getters and Setters
    public double getLogedWeight() {
        return logedWeight;
    }
    public void setLogedWeight(double logedWeight) {
        if (logedWeight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        } else {
            this.logedWeight = logedWeight;
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
