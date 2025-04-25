// User Class:
// This class will store user information such as username, pin, name, age (dob), weight(kg), and fitness goals.

import java.time.LocalDateTime;

public class User {
    private String username;
    private String pin;
    private String name;
    private LocalDateTime dob; // Date of Birth
    private int weight; // in kg
    private FitnessGoal fitnessGoal;

    // Constructor
    public User(String username, String pin, String name, LocalDateTime dob, int weight, FitnessGoal fitnessGoal) {
        this.username = username;
        this.pin = pin;
        this.name = name;
        this.dob = dob;
        this.weight = weight;
        this.fitnessGoal = fitnessGoal;
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

    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
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
}
