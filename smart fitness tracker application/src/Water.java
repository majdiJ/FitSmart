// Water Class
// This class will store water intake information such as amount, date and time of intake, and optional notes.

// Import Libraries
import java.io.Serializable;
import java.time.LocalDateTime;

public class Water implements Serializable {
    private static final long serialVersionUID = 1L;

    private double amount; // in liters
    private LocalDateTime dateTime; // Date and time of the water intake
    private String notes; // Optional notes about the water intake

    // Constructor
    public Water(double amount, LocalDateTime dateTime, String notes) {
        setAmount(amount);
        setDateTime(dateTime);
        setNotes(notes);
    }

    // Getters and Setters
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Water amount must be positive.");
        } else {
            this.amount = amount;
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
