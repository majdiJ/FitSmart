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
        boolean isGoalSet = false;

        while (true) {
            System.out.println("Would you like to set a daily step count goal? (y/n)");
            String dailyStepCountChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dailyStepCountChoice) || !Validation.isYesNoValid(dailyStepCountChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dailyStepCountChoice.equalsIgnoreCase("y")) {

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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

                isGoalSet = true; // Set the goal flag to true
                
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
        FitnessGoal fitnessGoal = new FitnessGoal(dailyStepCountGoal, workoutDurationGoal, caloriesBurnedGoal, workoutsPerWeekGoal, dailyWaterIntakeGoal, sleepMinutesGoal, weightGoal, strengthGoal, isGoalSet);
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
        System.out.println("8. Log Water Intake");
        System.out.println("9. Log sleep");
        System.out.println("10. Log weight");
        System.out.println("11. View water, sleep, and weight history");
        System.out.println("12. Exit Application");

        while (true) {
            System.out.println("Please select an option:");
            int choice = scanner.nextInt();

            // validate the choice
            if (choice <= 0 || choice >= 12) {
                ErrorMessage("Invalid choice. Please try again.");
            } else {
                // Return the user's choice
                return choice;
            }
        }
    }

    // View user profile
    public static void ViewUserProfile(User user) {
        System.out.println("User Profile:");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Date of Birth: " + user.getDob());
        System.out.println("Weight: " + user.getWeight() + " kg");
        System.out.println("Fitness Goals: " + user.getFitnessGoal());
    }

    // View fitness goals
    public static void ViewFitnessGoals(FitnessGoal fitnessGoal) {
        System.out.println("Fitness Goals:");
        System.out.println("Daily Step Count: " + fitnessGoal.getDailyStepCount());
        System.out.println("Workout Duration: " + fitnessGoal.getWorkoutDuration() + " minutes");
        System.out.println("Calories Burned: " + fitnessGoal.getCaloriesBurned());
        System.out.println("Workouts Per Week: " + fitnessGoal.getWorkoutsPerWeek());
        System.out.println("Daily Water Intake: " + fitnessGoal.getDailyWaterIntake() + " liters");
        System.out.println("Sleep Minutes: " + fitnessGoal.getsleepMinutes() + " minutes");
        System.out.println("Weight Goal: " + fitnessGoal.getWeightGoal() + " kg");
        System.out.println("Strength Goal: " + fitnessGoal.getStrength() + " kg");
    }

    // Log workout
    public static Workout LogWorkout() {
        System.out.println("Log Workout:");

        System.out.println("Please select a workout type:");
        for (int i = 0; i < Workout.WORKOUT_TYPES.length; i++) {
            System.out.println((i + 1) + ". " + Workout.WORKOUT_TYPES[i]);
        }

        int workoutTypeChoice = 0;

        while (true) {
            System.out.println("Please select a workout type:");
            workoutTypeChoice = scanner.nextInt();

            // Validate the workout type choice
            if (workoutTypeChoice <= 0 || workoutTypeChoice > Workout.WORKOUT_TYPES.length) {
                ErrorMessage("Invalid choice. Please try again.");
            } else {
                String workoutType = Workout.WORKOUT_TYPES[workoutTypeChoice - 1];
                System.out.println("You selected: " + workoutType);
                break; // Exit the loop if the choice is valid
            }
        }

        // Convert user intger option to string
        String workoutType = Workout.WORKOUT_TYPES[workoutTypeChoice - 1];

        int duration = 0;

        while (true) {
            System.out.println("Please enter the workout duration (in minutes):");
            duration = scanner.nextInt();

            // Validate the workout duration
            if (duration <= 0) {
                ErrorMessage("Workout duration must be positive.");
            } else {
                SuccessMessage("Workout duration is valid.");
                break; // Exit the loop if the duration is valid
            }
        }

        int caloriesBurned = 0;

        while (true) {
            System.out.println("Please enter the calories burned:");
            caloriesBurned = scanner.nextInt();

            // Validate the calories burned
            if (caloriesBurned < 0) {
                ErrorMessage("Calories burned cannot be negative.");
            } else {
                SuccessMessage("Calories burned is valid.");
                break; // Exit the loop if the calories burned is valid
            }
        }

        int steps = 0;

        while (true) {
            System.out.println("Please enter the number of steps taken:");
            steps = scanner.nextInt();

            // Validate the steps
            if (steps < 0) {
                ErrorMessage("Steps cannot be negative.");
            } else {
                SuccessMessage("Steps are valid.");
                break; // Exit the loop if the steps are valid
            }
            
        }

        // Get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        // Ask the user if they want to use the current date and timw or enter a custom date and time
        while (true) {
            System.out.println("Would you like to use the current date and time? (y/n):");
            String dateTimeChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dateTimeChoice) || !Validation.isYesNoValid(dateTimeChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dateTimeChoice.equalsIgnoreCase("y")) {
                SuccessMessage("Using current date and time: " + dateTime);
                break; // Exit the loop if the user chooses to use the current date and time
            } else {
                System.out.println("Please enter the custom date and time (YYYY-MM-DD HH:MM):");
                String customDateTimeString = scanner.nextLine();
                // Validate the custom date and time
                if (!Validation.canConvertStringToLocalDateTime(customDateTimeString)) {
                    ErrorMessage("Invalid date format. Please use YYYY-MM-DD HH:MM.");
                } else {
                    dateTime = Validation.convertStringToLocalDateTime(customDateTimeString);
                    SuccessMessage("Custom date and time set to: " + dateTime);
                    break; // Exit the loop if the custom date and time is valid
                }
            }
        }

        String notes = "";

        while (true) {
            System.out.println("Please enter any notes for the workout (optional):");
            notes = scanner.nextLine();

            // Validate the notes
            if (notes == null || notes.isEmpty()) {
                ErrorMessage("Notes cannot be empty.");
            } else {
                SuccessMessage("Notes are valid.");
                break; // Exit the loop if the notes are valid
            }
        }

        // Create a new Workout object with the entered details
        Workout workout = new Workout(workoutType, duration, caloriesBurned, steps, dateTime, notes);
        // Return the workout object to the main method
        return workout;
    }

    // View workout history
    public static void ViewWorkoutHistory(Workout[] workoutHistory) {
        System.out.println("Workout History:");
        for (int i = 0; i < workoutHistory.length; i++) {
            System.out.println((i + 1) + ". " + workoutHistory[i].getType() + " - " + workoutHistory[i].getDuration() + " minutes - " + workoutHistory[i].getCaloriesBurned() + " calories burned - " + workoutHistory[i].getSteps() + " steps - " + workoutHistory[i].getDateTime());
        }
    }

    // View progress reports
    public static void ViewProgressReport(User user) {
        System.out.println("Progress Report:");
        System.out.println("--- Your Profile ---");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Name: " + user.getName());
        System.out.println("Date of Birth: " + user.getDob());
        System.out.println("Weight: " + user.getWeight() + " kg");
        System.out.println("--- Your Fitness Goals ---");
        System.out.println("Daily Step Count: " + user.getFitnessGoal().getDailyStepCount());
        System.out.println("Workout Duration: " + user.getFitnessGoal().getWorkoutDuration() + " minutes");
        System.out.println("Calories Burned: " + user.getFitnessGoal().getCaloriesBurned());
        System.out.println("Workouts Per Week: " + user.getFitnessGoal().getWorkoutsPerWeek());
        System.out.println("Daily Water Intake: " + user.getFitnessGoal().getDailyWaterIntake() + " liters");
        System.out.println("Sleep Minutes: " + user.getFitnessGoal().getsleepMinutes() + " minutes");
        System.out.println("Weight Goal: " + user.getFitnessGoal().getWeightGoal() + " kg");
        System.out.println("Strength Goal: " + user.getFitnessGoal().getStrength() + " kg");
        System.out.println("--- Your Workout History ---");
        for (int i = 0; i < user.getWorkouts().length; i++) {
            System.out.println((i + 1) + ". " + user.getWorkouts()[i].getType() + " - " + user.getWorkouts()[i].getDuration() + " minutes - " + user.getWorkouts()[i].getCaloriesBurned() + " calories burned - " + user.getWorkouts()[i].getSteps() + " steps - " + user.getWorkouts()[i].getDateTime());
        }
        System.out.println("--- Your Water Intake History ---");
        for (int i = 0; i < user.getWaterIntake().length; i++) {
            System.out.println((i + 1) + ". " + user.getWaterIntake()[i].getAmount() + " liters - " + user.getWaterIntake()[i].getDateTime());
        }
        System.out.println("--- Your Sleep History ---");
        for (int i = 0; i < user.getSleep().length; i++) {
            System.out.println((i + 1) + ". " + user.getSleep()[i].getDuration() + " minutes - " + user.getSleep()[i].getDateTime());
        }
        System.out.println("--- Your Weight History ---");
        for (int i = 0; i < user.getWeightRecords().length; i++) {
            System.out.println((i + 1) + ". " + user.getWeightRecords()[i].getLogedWeight() + " kg - " + user.getWeightRecords()[i].getDateTime());
        }
        System.out.println("--- Your Fitness Goals ---");
        System.out.println("Daily Step Count: " + user.getFitnessGoal().getDailyStepCount());
        System.out.println("Workout Duration: " + user.getFitnessGoal().getWorkoutDuration() + " minutes");
        System.out.println("Calories Burned: " + user.getFitnessGoal().getCaloriesBurned());
        System.out.println("Workouts Per Week: " + user.getFitnessGoal().getWorkoutsPerWeek());
        System.out.println("Daily Water Intake: " + user.getFitnessGoal().getDailyWaterIntake() + " liters");
        System.out.println("Sleep Minutes: " + user.getFitnessGoal().getsleepMinutes() + " minutes");
        System.out.println("Weight Goal: " + user.getFitnessGoal().getWeightGoal() + " kg");
        // System.out.println("Strength Goal: " + user.getFitnessGoal().getStrength() + " kg");

        System.out.println("\n\n--- Your Progress Report ---");
        System.out.println("\n=== STEPS ===");
        ProgressTracker progressTracker = new ProgressTracker();
        System.out.println("Steps today: " + progressTracker.getStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        System.out.println("Steps this week: " + progressTracker.getStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()));
        System.out.println("Steps this month: " + progressTracker.getStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Steps this year: " + progressTracker.getStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()));
        System.out.println("Average steps per day: " + progressTracker.getAverageStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Average steps per week: " + progressTracker.getAverageStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()));
        System.out.println("Average steps per month: " + progressTracker.getAverageStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Average steps per year: " + progressTracker.getAverageStepsTakenInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()));
        
    System.out.println("\n=== NUMBER OF WORKOUTS ===");
        System.out.println("Number of workouts today: " + progressTracker.getTotalWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(1), LocalDateTime.now()));
        System.out.println("Number of workouts this week: " + progressTracker.getTotalWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()));
        System.out.println("Number of workouts this month: " + progressTracker.getTotalWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Number of workouts this year: " + progressTracker.getTotalWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()));
        System.out.println("Average workouts per day: " + progressTracker.getAverageWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Average workouts per week: " + progressTracker.getAverageWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()));
        System.out.println("Average workouts per month: " + progressTracker.getAverageWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()));
        System.out.println("Average workouts per year: " + progressTracker.getAverageWorkoutsInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()));
        
        System.out.println("\n=== WORKOUTS DURATION ===");
        System.out.println("Workout duration today: " + progressTracker.getTotalWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(1), LocalDateTime.now()) + " minutes");
        System.out.println("Workout duration this week: " + progressTracker.getTotalWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " minutes");
        System.out.println("Workout duration this month: " + progressTracker.getTotalWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Workout duration this year: " + progressTracker.getTotalWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " minutes");
        System.out.println("Average workout duration per day: " + progressTracker.getAverageWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Average workout duration per week: " + progressTracker.getAverageWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " minutes");
        System.out.println("Average workout duration per month: " + progressTracker.getAverageWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Average workout duration per year: " + progressTracker.getAverageWorkoutDurationInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " minutes");
        
        System.out.println("\n=== CALORIES BURNED ===");
        System.out.println("Calories burned today: " + progressTracker.getTotalCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(1), LocalDateTime.now()) + " calories");
        System.out.println("Calories burned this week: " + progressTracker.getTotalCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " calories");
        System.out.println("Calories burned this month: " + progressTracker.getTotalCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " calories");
        System.out.println("Calories burned this year: " + progressTracker.getTotalCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " calories");
        System.out.println("Average calories burned per day: " + progressTracker.getAverageCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " calories");
        System.out.println("Average calories burned per week: " + progressTracker.getAverageCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " calories");
        System.out.println("Average calories burned per month: " + progressTracker.getAverageCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " calories");
        System.out.println("Average calories burned per year: " + progressTracker.getAverageCaloriesBurnedInRange(user.getWorkouts(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " calories");
        
        System.out.println("\n=== WEIGHT ===");
        System.out.println("Average weight today: " + progressTracker.getAverageWeightInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(1), LocalDateTime.now()) + " kg");
        System.out.println("Average weight this week: " + progressTracker.getAverageWeightInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " kg");
        System.out.println("Average weight this month: " + progressTracker.getAverageWeightInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " kg");
        System.out.println("Average weight this year: " + progressTracker.getAverageWeightInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " kg");
        System.out.println("Weight change this week: " + progressTracker.getWeightChangeInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " kg");
        System.out.println("Weight change this month: " + progressTracker.getWeightChangeInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " kg");
        System.out.println("Weight change this year: " + progressTracker.getWeightChangeInRange(user.getWeightRecords(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " kg");

        System.out.println("\n=== WATER INTAKE ===");
        System.out.println("Water intake today: " + progressTracker.getWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(1), LocalDateTime.now()) + " liters");
        System.out.println("Water intake this week: " + progressTracker.getWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " liters");
        System.out.println("Water intake this month: " + progressTracker.getWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " liters");
        System.out.println("Water intake this year: " + progressTracker.getWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " liters");
        System.out.println("Average water intake per day: " + progressTracker.getAverageWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " liters");
        System.out.println("Average water intake per week: " + progressTracker.getAverageWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " liters");
        System.out.println("Average water intake per month: " + progressTracker.getAverageWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " liters");
        System.out.println("Average water intake per year: " + progressTracker.getAverageWaterIntakeInRange(user.getWaterIntake(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " liters");
        
        System.out.println("\n=== SLEEP ===");
        System.out.println("Sleep today: " + progressTracker.getTotalSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(1), LocalDateTime.now()) + " minutes");
        System.out.println("Sleep this week: " + progressTracker.getTotalSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " minutes");
        System.out.println("Sleep this month: " + progressTracker.getTotalSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Sleep this year: " + progressTracker.getTotalSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " minutes");
        System.out.println("Average sleep per day: " + progressTracker.getAverageSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Average sleep per week: " + progressTracker.getAverageSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(7), LocalDateTime.now()) + " minutes");
        System.out.println("Average sleep per month: " + progressTracker.getAverageSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(30), LocalDateTime.now()) + " minutes");
        System.out.println("Average sleep per year: " + progressTracker.getAverageSleepMinutesInRange(user.getSleep(), LocalDateTime.now().minusDays(365), LocalDateTime.now()) + " minutes");

        // Add more details as needed
    }

    // Log water intake
    public static Water LogWaterIntake() {
        System.out.println("Log Water Intake:");

        double amount = 0.0;

        while (true) {
            System.out.println("Please enter the amount of water intake (in liters):");
            amount = scanner.nextDouble();

            // Validate the water intake amount
            if (amount <= 0) {
                ErrorMessage("Water intake amount must be positive.");
            } else {
                SuccessMessage("Water intake amount is valid.");
                break; // Exit the loop if the amount is valid
            }
        }

        // Get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        // Ask the user if they want to use the current date and time or enter a custom date and time
        while (true) {
            System.out.println("Would you like to use the current date and time? (y/n):");
            String dateTimeChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dateTimeChoice) || !Validation.isYesNoValid(dateTimeChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dateTimeChoice.equalsIgnoreCase("y")) {
                SuccessMessage("Using current date and time: " + dateTime);
                break; // Exit the loop if the user chooses to use the current date and time
            } else {
                System.out.println("Please enter the custom date and time (YYYY-MM-DD HH:MM):");
                String customDateTimeString = scanner.nextLine();
                // Validate the custom date and time
                if (!Validation.canConvertStringToLocalDateTime(customDateTimeString)) {
                    ErrorMessage("Invalid date format. Please use YYYY-MM-DD HH:MM.");
                } else {
                    dateTime = Validation.convertStringToLocalDateTime(customDateTimeString);
                    SuccessMessage("Custom date and time set to: " + dateTime);
                    break; // Exit the loop if the custom date and time is valid
                }
            }
        }

        while (true) {
            System.out.println("Please enter any notes for the water intake (optional):");
            String notes = scanner.nextLine();

            // Validate the notes
            if (notes == null || notes.isEmpty()) {
                ErrorMessage("Notes cannot be empty.");
            } else {
                SuccessMessage("Notes are valid.");
                break; // Exit the loop if the notes are valid
            }
        }

        // Create a new Water object with the entered details
        Water water = new Water(amount, dateTime, null);
        // Return the water object to the main method
        return water;
    }

    // Log sleep
    public static Sleep LogSleep() {
        System.out.println("Log Sleep:");

        int duration = 0;

        while (true) {
            System.out.println("Please enter the sleep duration (in minutes):");
            duration = scanner.nextInt();

            // Validate the sleep duration
            if (duration <= 0) {
                ErrorMessage("Sleep duration must be positive.");
            } else {
                SuccessMessage("Sleep duration is valid.");
                break; // Exit the loop if the duration is valid
            }
        }

        // Get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        // Ask the user if they want to use the current date and time or enter a custom date and time
        while (true) {
            System.out.println("Would you like to use the current date and time? (y/n):");
            String dateTimeChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dateTimeChoice) || !Validation.isYesNoValid(dateTimeChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dateTimeChoice.equalsIgnoreCase("y")) {
                SuccessMessage("Using current date and time: " + dateTime);
                break; // Exit the loop if the user chooses to use the current date and time
            } else {
                System.out.println("Please enter the custom date and time (YYYY-MM-DD HH:MM):");
                String customDateTimeString = scanner.nextLine();
                // Validate the custom date and time
                if (!Validation.canConvertStringToLocalDateTime(customDateTimeString)) {
                    ErrorMessage("Invalid date format. Please use YYYY-MM-DD HH:MM.");
                } else {
                    dateTime = Validation.convertStringToLocalDateTime(customDateTimeString);
                    SuccessMessage("Custom date and time set to: " + dateTime);
                    break; // Exit the loop if the custom date and time is valid
                }
            }
        }

        while (true) {
            System.out.println("Please enter any notes for the sleep (optional):");
            String notes = scanner.nextLine();

            // Validate the notes
            if (notes == null || notes.isEmpty()) {
                ErrorMessage("Notes cannot be empty.");
            } else {
                SuccessMessage("Notes are valid.");
                break; // Exit the loop if the notes are valid
            }
        }

        // Create a new Sleep object with the entered details
        Sleep sleep = new Sleep(duration, dateTime, null);
        // Return the sleep object to the main method
        return sleep;
    }

    // Log weight
    public static Weight LogWeight() {
        System.out.println("Log Weight:");

        double weight = 0.0;

        while (true) {
            System.out.println("Please enter the weight (in kg):");
            weight = scanner.nextDouble();

            // Validate the weight
            if (weight <= 0) {
                ErrorMessage("Weight must be positive.");
            } else {
                SuccessMessage("Weight is valid.");
                break; // Exit the loop if the weight is valid
            }
        }

        // Get the current date and time
        LocalDateTime dateTime = LocalDateTime.now();
        // Ask the user if they want to use the current date and time or enter a custom date and time
        while (true) {
            System.out.println("Would you like to use the current date and time? (y/n):");
            String dateTimeChoice = scanner.nextLine();

            if (Validation.isYesNoEmpty(dateTimeChoice) || !Validation.isYesNoValid(dateTimeChoice)) {
                ErrorMessage("Invalid choice. Please enter 'y' or 'n'.");
            } else if (dateTimeChoice.equalsIgnoreCase("y")) {
                SuccessMessage("Using current date and time: " + dateTime);
                break; // Exit the loop if the user chooses to use the current date and time
            } else {
                System.out.println("Please enter the custom date and time (YYYY-MM-DD HH:MM):");
                String customDateTimeString = scanner.nextLine();
                // Validate the custom date and time
                if (!Validation.canConvertStringToLocalDateTime(customDateTimeString)) {
                    ErrorMessage("Invalid date format. Please use YYYY-MM-DD HH:MM.");
                } else {
                    dateTime = Validation.convertStringToLocalDateTime(customDateTimeString);
                    SuccessMessage("Custom date and time set to: " + dateTime);
                    break; // Exit the loop if the custom date and time is valid
                }
            }
        }

        while (true) {
            System.out.println("Please enter any notes for the weight log (optional):");
            String notes = scanner.nextLine();

            // Validate the notes
            if (notes == null || notes.isEmpty()) {
                ErrorMessage("Notes cannot be empty.");
            } else {
                SuccessMessage("Notes are valid.");
                break; // Exit the loop if the notes are valid
            }
        }

        // Create a new Weight object with the entered details
        Weight weightLog = new Weight(weight, dateTime, null);
        // Return the weight object to the main method
        return weightLog;
    }

    // View water, sleep, and weight history
    public static void ViewWaterSleepWeightHistory(User user) {
        System.out.println("Water, Sleep, and Weight History:");

        System.out.println("Water Intake History:");
        if (user.getWaterIntake() == null || user.getWaterIntake().length == 0) {
            System.out.println("No water intake records saved.");
        } else {
            for (Water water : user.getWaterIntake()) {
                System.out.println(water.getAmount() + " liters - " + water.getDateTime());
            }
        }

        System.out.println("Sleep History:");
        if (user.getSleep() == null || user.getSleep().length == 0) {
            System.out.println("No sleep records saved.");
        } else {
            for (Sleep sleep : user.getSleep()) {
                System.out.println(sleep.getDuration() + " minutes - " + sleep.getDateTime());
            }
        }

        System.out.println("Weight History:");
        if (user.getWeightRecords() == null || user.getWeightRecords().length == 0) {
            System.out.println("No weight records saved.");
        } else {
            for (Weight weight : user.getWeightRecords()) {
                System.out.println(weight.getLogedWeight() + " kg - " + weight.getDateTime());
            }
        }
    }
}