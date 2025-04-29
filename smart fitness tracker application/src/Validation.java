// Validation Class
// // This class will validate user input and ensure that the data entered is correct and within acceptable ranges.

// Impor Libraries
import java.security.PublicKey;
import java.time.LocalDateTime;

public class Validation {

    // General Validation
    public static boolean isInputEmpty(String input) {
        return input == null || input.isEmpty();
    }
    public static boolean isInputNumeric(String input) {
        return input.matches("\\d+"); // Input must be numeric
    }
    public static boolean isInputInRangeInteger(int input, int min, int max) {
        return input >= min && input <= max; // Input must be within the specified range
    }
    public static boolean isInputInRangeDouble(Double input, Double min, Double max) {
        return input >= min && input <= max; // Input must be within the specified range
    }
    public static boolean isInputInteger(String input) {
        try {
            Integer.parseInt(input);
            return true; // Input is a valid integer
        } catch (NumberFormatException e) {
            return false; // Input is not a valid integer
        }
    }
    public static boolean isInputDouble(String input) {
        try {
            Double.parseDouble(input);
            return true; // Input is a valid double
        } catch (NumberFormatException e) {
            return false; // Input is not a valid double
        }
    }

    // Gender Validation
    public static int validateAndParseInteger(String input, int min, int max, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        int value;
        try {
            value = Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid integer.");
        }
        if (value < min || value > max) {
            throw new IllegalArgumentException(fieldName + " must be between " + min + " and " + max + ".");
        }
        return value;
    }

    // Gender Validation
    public static double validateAndParseDouble(String input, double min, double max, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        double value;
        try {
            value = Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " must be a valid number.");
        }
        if (value < min || value > max) {
            throw new IllegalArgumentException(fieldName + " must be between " + min + " and " + max + ".");
        }
        return value;
    }

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

    // Yes/No (Y/N) Validation
    public static boolean isYesNoEmpty(String input) {
        return input == null || input.isEmpty();
    }
    public static boolean isYesNoValid(String input) {
        return input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("N");
    }



    
}
