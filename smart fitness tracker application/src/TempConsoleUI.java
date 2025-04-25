// The application needs to be aging UI, but to help focus on the main functions of the program, we will make the
// program in a console. You buy, then change it to a GUI.

// Import Libraries
import java.util.Scanner;
import java.time.LocalDateTime;

public class TempConsoleUI {

    static Scanner scanner = new Scanner(System.in);

    // Clear Console Method
    public static void ClearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing console: " + e.getMessage());
        }
    }

    // Error Message Method
    public static void ErrorMessage(String message) {
        System.out.println("Error: " + message);
    }
    // Success Message Method
    public static void SuccessMessage(String message) {
        System.out.println("Success: " + message);
    }
    
    public static void FirstLaunchScreen() {
        System.out.println("Welcome to FitSmart!");
        System.out.println("Your personal fitness tracker and goal manager.");
        System.out.println("It seems like this is your first time using the app, lets get you set up!");

        while (true) {
            System.out.println("Please enter your username:");
        
            String username = scanner.nextLine();
    
            // Check if username is valid
            if (Validation.isUsernameEmpty(username)) {
                ErrorMessage("Username cannot be empty.");
            } else if (!Validation.isUsernameUnique(username, SmartFitnessTrackerApplication.existingUsernames)) {
                ErrorMessage("Username already exists. Please choose a different username.");
            } else if (Validation.doesUsernameContainSpecialChars(username)) {
                ErrorMessage("Username cannot contain special characters.");
            } else {
                SuccessMessage("Username is valid and unique.");
                // Proceed with the rest of the setup
                break; // Exit the loop if username is valid
            }    
        }

        while (true) {
            System.out.println("Please enter your PIN (4 digits):");
            String pin = scanner.nextLine();
    
            // Check if pin is valid
            if (Validation.isPinEmpty(pin)) {
                ErrorMessage("PIN cannot be empty.");
            } else if (!Validation.isPinNumeric(pin)) {
                ErrorMessage("PIN must be numeric.");
            } else if (!Validation.isPinValidLength(pin)) {
                ErrorMessage("PIN must be 4 digits.");
            } else {
                SuccessMessage("PIN is valid.");
                // Proceed with the rest of the setup
                break; // Exit the loop if PIN is valid
            }
        }

        while (true) {
            System.out.println("Please enter your date of birth (YYYY-MM-DD):");
            String dobInput = scanner.nextLine();
            LocalDateTime dob = LocalDateTime.parse(dobInput + "T00:00:00"); // Assuming time is not needed
    
            // Check if date of birth is valid
            if (Validation.isDobEmpty(dob)) {
                ErrorMessage("Date of Birth cannot be empty.");
            } else if (!Validation.isDobPast(dob)) {
                ErrorMessage("Date of Birth must be in the past.");
            } else {
                SuccessMessage("Date of Birth is valid.");
                // Proceed with the rest of the setup
                break; // Exit the loop if date of birth is valid
            }
        }

        while (true) {
            System.out.println("Please enter your name:");
            String name = scanner.nextLine();
    
            // Check if name is valid
            if (Validation.isNameEmpty(name)) {
                ErrorMessage("Name cannot be empty.");
            } else if (Validation.doesNameContainSpecialChars(name)) {
                ErrorMessage("Name cannot contain special characters.");
            } else {
                SuccessMessage("Name is valid.");
                // Proceed with the rest of the setup
                break; // Exit the loop if name is valid
            }
        }

        while (true) {
            System.out.println("Please enter your weight (in kg):");
            double weight = scanner.nextDouble();
    
            // Check if weight is valid
            if (Validation.isWeightEmpty(weight)) {
                ErrorMessage("Weight cannot be empty or negative.");
            } else if (!Validation.isWeightPositive(weight)) {
                ErrorMessage("Weight must be positive.");
            } else {
                SuccessMessage("Weight is valid.");
                // Proceed with the rest of the setup
                break; // Exit the loop if weight is valid
            }
        }

    }
}
