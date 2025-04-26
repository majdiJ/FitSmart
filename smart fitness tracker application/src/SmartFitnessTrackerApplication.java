// FitSmart - Smart Fitness Tracker Application
// This is a Java application that tracks fitness activities and provides insights to users.

// Programing Task 2 group project:  Smart Fitness Tracker Application
// Project Description:
// Team is tasked with creating a Smart Fitness Tracker Application that enables users to track 
// and manage their fitness activities. The system should provide modules for tracking workouts,
// setting goals, monitoring progress, and generating reports. The solution must feature a user-friendly
// GUI, showcase good software design, and demonstrate collaborative problem-solving. Each group will
// present their solution at the end of the project.

// FitSmart - Smart Fitness Tracker Application
// A Java-based fitness tracker that allows users to log workouts, set goals, and view progress reports.

// Core Features:
// - Log workouts (type, duration, calories burned)
// - Set and track fitness goals (e.g., daily/weekly targets)
// - View progress and generate summary reports

// Design:
// - Object-Oriented design with key classes:
//   ● User (name, age, weight, fitness goals)
//   ● Workout (type, duration, calories burned)
//   ● ProgressTracker (calculate progress, generate reports)
// - UML diagrams to plan structure (class/sequence diagrams)

// Implementation:
// - Built in Java using encapsulation, inheritance, polymorphism
// - Uses control structures (loops, conditionals, methods)
// - Handles input errors (e.g., negative values)
// - Stores data using collections (ArrayList, HashMap)

// Team Tools (lightly mentioned):
// - Version control with Git/GitHub

// Example of app usage/steps:
// 1. User opens the application.
// 2. User is prompted to enter their name, age, and weight.
// 3. User is prompted to set fitness goals. (this will be devloped after everything else)
// 4. User data is saved on device, and loaded when the app is next opened.
// 5. User is taken to dashboard where they can log workouts and view progress.
// 6. User can log workouts by entering type, duration, and calories burned.
// 7. User can view progress reports based on logged workouts.
// 8. User can set new goals and track progress.
// 9. User can view summary reports of their fitness journey.

// Team Members:
// - Majdi Jaigirdar (Student ID: 26192802) (GitHub: [majdi-jaigirdar](https://github.com/majdiJ))
// - Jessica Livesley (Student ID: 25658654) (GitHub: [JessicaLivesley](...))
// - Muhammad Fasih (Student ID: 25968823) (GitHub: [fasihmuhammad](...))
// - Robert Fory (Student ID: 25760416) (GitHub: [robertfory](...))

// Import Librarie
import java.util.List;
import java.util.ArrayList;


public class SmartFitnessTrackerApplication {

	// Global list of users
	public static userList userList = new userList();

	// Global list of existing usernames
	public static String[] existingUsernames = new String[0];

	// Call the console UI
	public static void main(String[] args) {
		// Clear the console
		TempConsoleUI.ClearConsole();

		// Check if the data file exists
		String filename = "userData.dat";
		if (AppData.CheckDataExists.checkDataExists(filename)) {
			// Load the user data from the file
			userList = AppData.LoadData.loadUserList(filename);
			if (userList != null) {
				// Display the loaded user data
				System.out.println("User data loaded successfully.");

				// Add existing usernames to the list
				for (User user : userList.getUsers()) {
					existingUsernames = java.util.Arrays.copyOf(existingUsernames, existingUsernames.length + 1);
					existingUsernames[existingUsernames.length - 1] = user.getUsername();
				}
				
				User selectedUser = null;

				while (true) {
					// Display the list of users for user to select
					int UserMenueChoice = TempConsoleUI.UserSelectionMenu(userList);

					if (UserMenueChoice == 0) { // User wnats to exit the application
						System.out.println("Exiting application.");
						System.exit(0);
					} else if (UserMenueChoice == 1) { // Create new user
						User NewUserAccount = TempConsoleUI.NewUserCreationScreen();
						if (NewUserAccount != null) {
							// Save the new user data to userList object
							userList userList = new userList();
							userList.addUser(NewUserAccount);
							// Save the user data to a file
							AppData.SaveData.saveUserList(userList, filename);
							System.out.println("User data saved successfully.");
						} else {
							TempConsoleUI.ErrorMessage("Failed to create user data.");
						}
					} else if (UserMenueChoice > 1 && UserMenueChoice <= userList.getUsers().length + 1) { // User selected an existing user
						int selectedUserIndex = UserMenueChoice - 2; // Adjust for menu options
						selectedUser = java.util.Arrays.asList(userList.getUsers()).get(selectedUserIndex);
						System.out.println("Selected user: " + selectedUser.getUsername());
						// Proceed with the application logic for the selected user by breaking out of the loop
						break;
					} else {
						TempConsoleUI.ErrorMessage("Invalid selection. Please try again.");
					}
				}
				
				// Proceed with the application logic for the selected user
				// for now, just print the selected user's details
				if (selectedUser != null) {
					System.out.println("User Details:");
					System.out.println("Username: " + selectedUser.getUsername());
					System.out.println("Name: " + selectedUser.getName());
					System.out.println("Date of Birth: " + selectedUser.getDob());
					System.out.println("Weight: " + selectedUser.getWeight());
				} else {
					TempConsoleUI.ErrorMessage("No user selected.");
				}
			} else {
				TempConsoleUI.ErrorMessage("Failed to load user data.");
			}
		} else {
			// If the data file does not exist, proceed with first launch setup and create a new user and save the data
			System.out.println("No existing user data found. Starting first launch setup.");

			// Display the first launch screen
			TempConsoleUI.FirstLaunchScreen();
			// Create a new user
			User FirstLaunchUser = TempConsoleUI.NewUserCreationScreen();
			if (FirstLaunchUser != null) {
				// Save the new user data to userList 
				userList.addUser(FirstLaunchUser);
				// Save the user data to a file
				AppData.SaveData.saveUserList(userList, filename);
				System.out.println("User data saved successfully.");
			} else {
				TempConsoleUI.ErrorMessage("Failed to create user data.");
			}
		}
	}
}