// Validation Class
// // This class will validate user input and ensure that the data entered is correct and within acceptable ranges.

// Impor Libraries
import java.time.LocalDateTime;

public class Validation {

    // Username Validation
    public static boolean isUsernameEmpty(String username) {
        return username == null || username.isEmpty();
    }
    public static boolean isUsernameUnique(String username, String[] existingUsernames) {
        for (String existingUsername : existingUsernames) {
            if (username.equals(existingUsername)) {
                return false; // Username already exists
            }
        }
        return true; // Username is unique
    }
    public static boolean doesUsernameContainSpecialChars(String username) {
        return !username.matches("[a-zA-Z0-9]+");
    }

    // PIN Validation
    public static boolean isPinEmpty(String pin) {
        return pin == null || pin.isEmpty();
    }
    public static boolean isPinValidLength(String pin) {
        return pin.length() == 4; // PIN must be 4 digits
    }
    public static boolean isPinNumeric(String pin) {
        return pin.matches("\\d+"); // PIN must be numeric
    }

    // Date of Birth Validation
    public static boolean canConvertStringToLocalDateTime(String dobInput) {
        try {
            LocalDateTime.parse(dobInput + "T00:00:00");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // String to LocalDateTime conversion (in YYYY-MM-DD format)
    public static LocalDateTime convertStringToLocalDateTime(String dobInput) {
        return LocalDateTime.parse(dobInput + "T00:00:00");
    }
    public static boolean isDobEmpty(LocalDateTime dob) {
        return dob == null;
    }
    public static boolean isDobPast(LocalDateTime dob) {
        return !dob.isAfter(LocalDateTime.now());
    }

    // Name Validation
    public static boolean isNameEmpty(String name) {
        return name == null || name.isEmpty();
    }
    public static boolean doesNameContainSpecialChars(String name) {
        return !name.matches("[a-zA-Z ]+");
    }

    // Weight Validation
    public static boolean isWeightEmpty(double weight) {
        return weight <= 0 || Double.isNaN(weight); // Weight must be positive and not NaN
    }
    public static boolean isWeightPositive(double weight) {
        return weight > 0; // Weight must be positive
    }



    
}
