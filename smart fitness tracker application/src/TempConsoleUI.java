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

    // Clear any leftover input in the buffer
    public static void ClearBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
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
    
    // First Launch Screen Method
    public static void FirstLaunchScreen() {
        System.out.println("Welcome to FitSmart!");
        System.out.println("Your personal fitness tracker and goal manager.");
        System.out.println("It seems like this is your first time using the app, lets get you set up!");
    }

    // New User Creation Screen Method
    public static User NewUserCreationScreen() {
        String username = "";
        String pin = "";
        String name = "";
        LocalDateTime dob = null;
        double weight = 0.0;

        System.out.println("Let's make a new user!");

        while (true) {
            System.out.println("Please enter your username:");
            username = scanner.nextLine();
    
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
            pin = scanner.nextLine();
    
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
            String stringDOB = scanner.nextLine();

            if (!Validation.canConvertStringToLocalDateTime(stringDOB)) {
                ErrorMessage("Invalid date format. Please use YYYY-MM-DD.");
            } else if (Validation.isDobEmpty(Validation.convertStringToLocalDateTime(stringDOB))) {
                ErrorMessage("Date of Birth cannot be empty.");
            } else if (!Validation.isDobPast(Validation.convertStringToLocalDateTime(stringDOB))) {
                ErrorMessage("Date of Birth must be in the past.");
            } else {
                SuccessMessage("Date of Birth is valid.");
                dob = Validation.convertStringToLocalDateTime(stringDOB);
                // Proceed with the rest of the setup
                break; // Exit the loop if date of birth is valid
            }
        }

        while (true) {
            System.out.println("Please enter your name:");
            name = scanner.nextLine();
    
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
            weight = scanner.nextDouble();
    
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

        // After all validations, create the user object
        User user = new User(username, pin, name, dob, weight);
        // Return the user object to the main method
        return user;
    }

    // User selection menu
    public static int UserSelectionMenu(userList userList) {

        while (true) {
            System.out.println("Select a user:");
            System.out.println("0. Exit");
            System.out.println("1. Create a new user");
            for (int i = 0; i < userList.getUsers().length; i++) {
                System.out.println((i + 2) + ". " + userList.getUsers()[i].getUsername());
            }
            int choice = scanner.nextInt();
            return choice; // Return the user's choice
        }
    }

    // User pin login
    public static String UserPinLogin(String username) {

        while (true) {
            System.out.println("Please enter your PIN for '" + username + "':");
            String userEnteredPin = scanner.nextLine();

            // Validate the PIN
            if (Validation.isPinEmpty(userEnteredPin)) {
                ErrorMessage("PIN cannot be empty.");
                return null; // Return null if the PIN is empty
            } else if (!Validation.isPinNumeric(userEnteredPin)) {
                ErrorMessage("PIN must be numeric.");
                return null; // Return null if the PIN is not numeric
            } else if (!Validation.isPinValidLength(userEnteredPin)) {
                ErrorMessage("PIN must be 4 digits.");
                return null; // Return null if the PIN is not valid length
            } else {
                return userEnteredPin; // Return the entered PIN
            }
        }
    }

    // User set fitness goals
    public static FitnessGoal SetFitnessGoals() {

        System.out.println("Let's set your fitness goals!");
        int dailyStepCountGoal = 0;
        int workoutDurationGoal = 0;
        int caloriesBurnedGoal = 0;
        int workoutsPerWeekGoal = 0;
        double dailyWaterIntakeGoal = 0.0;
        int sleepMinutesGoal = 0;
        double weightGoal = 0.0;
        double strengthGoal = 0.0;

        while (true) {
            System.out.println("Would you like to set a daily step count goal? (y/n)");
            String dailyStepCountChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dailyStepCountChoice) || !Validation.isYesNoValid(dailyStepCountChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dailyStepCountChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your daily step count goal:");
                    String dailyStepCountGoalString = scanner.nextLine();
                    
                    // Validate the daily step count goal is positive integer
                    if (Validation.isInputEmpty(dailyStepCountGoalString)) {
                        ErrorMessage("Daily step count goal cannot be empty.");
                    } else if (!Validation.isInputInteger(dailyStepCountGoalString)) {
                        ErrorMessage("Daily step count goal must be a positive integer.");
                    } else if (!Validation.isInputInRangeInteger(Integer.parseInt(dailyStepCountGoalString), 0, 100000)) {
                        ErrorMessage("Daily step count goal must be between 0 and 100000.");
                    } else {
                        dailyStepCountGoal = Integer.parseInt(dailyStepCountGoalString);
                        SuccessMessage("Daily step count goal set to " + dailyStepCountGoal);
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No daily step count goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }        
        }

        while (true) {
            System.out.println("Would you like to set a weekly workout duration goal? (y/n)");
            String workoutDurationChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(workoutDurationChoice) || !Validation.isYesNoValid(workoutDurationChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (workoutDurationChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your weekly workout duration goal (in minutes):");
                    String workoutDurationGoalString = scanner.nextLine();
                    
                    // Validate the weekly workout duration goal is positive integer
                    if (Validation.isInputEmpty(workoutDurationGoalString)) {
                        ErrorMessage("Weekly workout duration goal cannot be empty.");
                    } else if (!Validation.isInputInteger(workoutDurationGoalString)) {
                        ErrorMessage("Weekly workout duration goal must be a positive integer.");
                    } else if (!Validation.isInputInRangeInteger(Integer.parseInt(workoutDurationGoalString), 0, 10080)) {
                        ErrorMessage("Weekly workout duration goal must be between 0 and 10080 minutes.");
                    } else {
                        workoutDurationGoal = Integer.parseInt(workoutDurationGoalString);
                        SuccessMessage("Weekly workout duration goal set to " + workoutDurationGoal);
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No weekly workout duration goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a weekly calories burned goal? (y/n)");
            String caloriesBurnedChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(caloriesBurnedChoice) || !Validation.isYesNoValid(caloriesBurnedChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (caloriesBurnedChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your weekly calories burned goal:");
                    String caloriesBurnedGoalString = scanner.nextLine();
                    
                    // Validate the weekly calories burned goal is positive integer
                    if (Validation.isInputEmpty(caloriesBurnedGoalString)) {
                        ErrorMessage("Weekly calories burned goal cannot be empty.");
                    } else if (!Validation.isInputInteger(caloriesBurnedGoalString)) {
                        ErrorMessage("Weekly calories burned goal must be a positive integer.");
                    } else if (!Validation.isInputInRangeInteger(Integer.parseInt(caloriesBurnedGoalString), 0, 1000000)) {
                        ErrorMessage("Weekly calories burned goal must be between 0 and 1000000.");
                    } else {
                        caloriesBurnedGoal = Integer.parseInt(caloriesBurnedGoalString);
                        SuccessMessage("Weekly calories burned goal set to " + caloriesBurnedGoal);
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No weekly calories burned goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a weekly workout session per week goal? (y/n)");
            String workoutsPerWeekChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(workoutsPerWeekChoice) || !Validation.isYesNoValid(workoutsPerWeekChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (workoutsPerWeekChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your weekly workout session per week goal:");
                    String workoutsPerWeekGoalString = scanner.nextLine();
                    
                    // Validate the weekly workout session per week goal is positive integer
                    if (Validation.isInputEmpty(workoutsPerWeekGoalString)) {
                        ErrorMessage("Weekly workout session per week goal cannot be empty.");
                    } else if (!Validation.isInputInteger(workoutsPerWeekGoalString)) {
                        ErrorMessage("Weekly workout session per week goal must be a positive integer.");
                    } else if (!Validation.isInputInRangeInteger(Integer.parseInt(workoutsPerWeekGoalString), 0, 100)) {
                        ErrorMessage("Weekly workout session per week goal must be between 0 and 100.");
                    } else {
                        workoutsPerWeekGoal = Integer.parseInt(workoutsPerWeekGoalString);
                        SuccessMessage("Weekly workout session per week goal set to " + workoutsPerWeekGoal);
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No weekly workout session per week goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a daily water intake goal? (y/n)");
            String dailyWaterIntakeChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dailyWaterIntakeChoice) || !Validation.isYesNoValid(dailyWaterIntakeChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dailyWaterIntakeChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your daily water intake goal (in liters):");
                    String dailyWaterIntakeGoalString = scanner.nextLine();
                    
                    // Validate the daily water intake goal is positive double
                    if (Validation.isInputEmpty(dailyWaterIntakeGoalString)) {
                        ErrorMessage("Daily water intake goal cannot be empty.");
                    } else if (!Validation.isInputDouble(dailyWaterIntakeGoalString)) {
                        ErrorMessage("Daily water intake goal must be a positive double.");
                    } else if (!Validation.isInputInRangeDouble(Double.parseDouble(dailyWaterIntakeGoalString), 0.0, 50.0)) {
                        ErrorMessage("Daily water intake goal must be between 0 and 50 liters.");
                    } else {
                        dailyWaterIntakeGoal = Double.parseDouble(dailyWaterIntakeGoalString);
                        SuccessMessage("Daily water intake goal set to " + dailyWaterIntakeGoal + " liters");
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No daily water intake goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a sleep minutes goal? (y/n)");
            String sleepMinutesChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(sleepMinutesChoice) || !Validation.isYesNoValid(sleepMinutesChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (sleepMinutesChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your sleep minutes goal:");
                    String sleepMinutesGoalString = scanner.nextLine();
                    
                    // Validate the sleep minutes goal is positive integer
                    if (Validation.isInputEmpty(sleepMinutesGoalString)) {
                        ErrorMessage("Sleep minutes goal cannot be empty.");
                    } else if (!Validation.isInputInteger(sleepMinutesGoalString)) {
                        ErrorMessage("Sleep minutes goal must be a positive integer.");
                    } else if (!Validation.isInputInRangeInteger(Integer.parseInt(sleepMinutesGoalString), 0, 1440)) {
                        ErrorMessage("Sleep minutes goal must be between 0 and 1440.");
                    } else {
                        sleepMinutesGoal = Integer.parseInt(sleepMinutesGoalString);
                        SuccessMessage("Sleep minutes goal set to " + sleepMinutesGoal);
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No sleep minutes goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a weight goal? (y/n)");
            String weightGoalChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(weightGoalChoice) || !Validation.isYesNoValid(weightGoalChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (weightGoalChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your weight goal:");
                    String weightGoalString = scanner.nextLine();
                    
                    // Validate the weight goal is positive double
                    if (Validation.isInputEmpty(weightGoalString)) {
                        ErrorMessage("Weight goal cannot be empty.");
                    } else if (!Validation.isInputDouble(weightGoalString)) {
                        ErrorMessage("Weight goal must be a positive double.");
                    } else if (!Validation.isInputInRangeDouble(Double.parseDouble(weightGoalString), 0.0, 200.0)) {
                        ErrorMessage("Weight goal must be between 0 and 200 kg.");
                    } else {
                        weightGoal = Double.parseDouble(weightGoalString);
                        SuccessMessage("Weight goal set to " + weightGoal + " kg");
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No weight goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        while (true) {
            System.out.println("Would you like to set a strength goal? (y/n)");
            String strengthGoalChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(strengthGoalChoice) || !Validation.isYesNoValid(strengthGoalChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (strengthGoalChoice.equalsIgnoreCase("y")) {
                
                while (true) {
                    System.out.println("Please enter your strength goal:");
                    String strengthGoalString = scanner.nextLine();
                    
                    // Validate the strength goal is positive double
                    if (Validation.isInputEmpty(strengthGoalString)) {
                        ErrorMessage("Strength goal cannot be empty.");
                    } else if (!Validation.isInputDouble(strengthGoalString)) {
                        ErrorMessage("Strength goal must be a positive double.");
                    } else if (!Validation.isInputInRangeDouble(Double.parseDouble(strengthGoalString), 0.0, 1000.0)) {
                        ErrorMessage("Strength goal must be between 0 and 1000 kg.");
                    } else {
                        strengthGoal = Double.parseDouble(strengthGoalString);
                        SuccessMessage("Strength goal set to " + strengthGoal + " kg");
                        break; // Exit the loop if the goal is set
                    }
                }
                break; // Exit the loop if the goal is set
            } else {
                SuccessMessage("No strength goal set.");
                break; // Exit the loop if the user chooses not to set a goal
            }
        }

        // Create a new FitnessGoal object with the set goals
        FitnessGoal fitnessGoal = new FitnessGoal(dailyStepCountGoal, workoutDurationGoal, caloriesBurnedGoal, workoutsPerWeekGoal, dailyWaterIntakeGoal, sleepMinutesGoal, weightGoal, strengthGoal);
        // Return the fitness goal object to the main method
        return fitnessGoal;
    }
            

    // User main menu
    public static int UserMainMenu() {
        System.out.println("Welcome to the main menu!");
        System.out.println("1. View Profile");
        System.out.println("2. Set Fitness Goals");
        System.out.println("3. View Fitness Goals");
        System.out.println("4. Log Workout");
        System.out.println("5. View Workout History");
        System.out.println("6. View Progress Reports");
        System.out.println("7. Exit");

        while (true) {
            System.out.println("Please select an option:");
            int choice = scanner.nextInt();

            // validate the choice
            if (choice <= 0 || choice >= 7) {
                ErrorMessage("Invalid choice. Please try again.");
            } else {
                // Return the user's choice
                return choice;
            }
        }
    }
}
