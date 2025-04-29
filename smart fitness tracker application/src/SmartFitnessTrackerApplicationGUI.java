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
// - Jessica Livesley (Student ID: 25658654) (GitHub: [JessicaLivesley](https://github.com/JessLivesley))
// - Muhammad Fasih (Student ID: 25968823) (GitHub: [fasihmuhammad](https://github.com/FazzyM)) 
// - Robert Fory (Student ID: 25760416) (GitHub: [robertfory](https://github.com/...))

// Import Libraries

public class SmartFitnessTrackerApplicationGUI {

	// Global list of users
	public static userList userList = new userList();

	// Global list of existing usernames
	public static String[] existingUsernames = new String[0];

	// Call the console UI
	public static void main(String[] args) {

		// User accepted terms and conditions
		Boolean UserAcceptedTerms = false;

		while (true) {
			// Clear the console
			TempConsoleUI.ClearConsole();

			while (UserAcceptedTerms == false) {
				if (!UserAcceptedTerms) {
					UserAcceptedTerms = GUI.WelcomeSplashScreen();
				} else {
					// User accepted the terms and conditions
					System.out.println("Terms and conditions accepted. Proceeding with the application.");
				}
	
				if (!UserAcceptedTerms) {
					// User did not accept the terms and conditions
					System.out.println("You must accept the terms and conditions to use this application.");
					System.exit(0);
				} else {
					// User accepted the terms and conditions
					System.out.println("Terms and conditions accepted. Proceeding with the application.");
				}
			}

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
						int UserMenueChoice = GUI.UserSelectionMenu(userList);

						if (UserMenueChoice == 0) { // User wnats to exit the application
							System.out.println("Exiting application.");
							System.exit(0);
						} else if (UserMenueChoice == 1) { // Create new user
							User NewUserAccount = GUI.NewUserCreationScreen();
							if (NewUserAccount != null) {
								// Save the new user data to userList object
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
							// Proceed with the application logic for the selected user by breaking out of
							// the loop
							break;
						} else {
							TempConsoleUI.ErrorMessage("Invalid selection. Please try again.");
						}
					}

					// Ask the user to enter their pin
					while (true) {

						// Display the user login screen
						String pin = GUI.UserPinLogin(selectedUser.getUsername());
						if (pin != null) {
							// Validate the pin
							if (selectedUser.getPin().equals(pin)) {
								System.out.println("PIN validated successfully.");
								break; // Exit the loop if the PIN is valid
							} else {
								TempConsoleUI.ErrorMessage("Invalid PIN. Please try again.");
							}
						} else {
							TempConsoleUI.ErrorMessage("Failed to validate PIN.");
						}
					}

					// Check if fitness goals have been set
					FitnessGoal fitnessGoal = selectedUser.getFitnessGoal();

					if (fitnessGoal == null || !fitnessGoal.isGoalSet()) {
						// Prompt user to set fitness goals
						fitnessGoal = GUI.SetFitnessGoals();

						if (fitnessGoal != null) {
							selectedUser.setFitnessGoal(fitnessGoal);
							System.out.println("Fitness goals set successfully.");

							// Save the updated user data to a file
							AppData.SaveData.saveUserList(userList, filename);
							System.out.println("User data saved successfully.");
						} else {
							TempConsoleUI.ErrorMessage("Failed to set fitness goals.");
						}
					} else {
						System.out.println("Fitness goals are already set. Skipping this step.");
					}

					while (true) {

						// Proceed with the application logic for the selected user
						int choice = GUI.UserMainMenu(selectedUser);

						// Switch case for user choice
						switch (choice) {
							case 1: // View profile
								TempConsoleUI.ClearConsole();
								Boolean UserReturnToMainMenu = GUI.ViewUserProfile(selectedUser);
								if (UserReturnToMainMenu) {
									break; // Return to main menu
								}
								break;

							case 2: // Set fitness goals
								TempConsoleUI.ClearConsole();
								fitnessGoal = TempConsoleUI.SetFitnessGoals();
								if (fitnessGoal != null) {
									selectedUser.setFitnessGoal(fitnessGoal);
									System.out.println("Fitness goals set successfully.");
									// Save the updated user data to a file
									AppData.SaveData.saveUserList(userList, filename);
									System.out.println("User data saved successfully.");
								} else {
									TempConsoleUI.ErrorMessage("Failed to set fitness goals.");
								}
								break;

							case 3: // View fitness goals
								TempConsoleUI.ClearConsole();
								TempConsoleUI.ViewFitnessGoals(selectedUser.getFitnessGoal());
								break;

							case 4: // Log workout
								TempConsoleUI.ClearConsole();
								Workout workout = TempConsoleUI.LogWorkout();
								if (workout != null) {
									selectedUser.addWorkout(workout);
									System.out.println("Workout logged successfully.");
									// Save the updated user data to a file
									AppData.SaveData.saveUserList(userList, filename);
									System.out.println("User data saved successfully.");
								} else {
									TempConsoleUI.ErrorMessage("Failed to log workout.");
								}
								break;
							
							case 5: // View workout history
								TempConsoleUI.ClearConsole();
								TempConsoleUI.ViewWorkoutHistory(selectedUser.getWorkouts());
								break;

							case 6: // View progress report
								TempConsoleUI.ClearConsole();
								TempConsoleUI.ViewProgressReport(selectedUser);
								break;

							case 8: // Log water intake
								TempConsoleUI.ClearConsole();
								Water water = TempConsoleUI.LogWaterIntake();
								if (water != null) {
									selectedUser.addWaterIntake(water);
									System.out.println("Water intake logged successfully.");
									// Save the updated user data to a file
									AppData.SaveData.saveUserList(userList, filename);
									System.out.println("User data saved successfully.");
								} else {
									TempConsoleUI.ErrorMessage("Failed to log water intake.");
								}
								break;
							
							case 9: // Log sleep
								TempConsoleUI.ClearConsole();
								Sleep sleep = TempConsoleUI.LogSleep();
								if (sleep != null) {
									selectedUser.addSleep(sleep);
									System.out.println("Sleep logged successfully.");
									// Save the updated user data to a file
									AppData.SaveData.saveUserList(userList, filename);
									System.out.println("User data saved successfully.");
								} else {
									TempConsoleUI.ErrorMessage("Failed to log sleep.");
								}
								break;
							
							case 10: // Log weight
								TempConsoleUI.ClearConsole();
								Weight weight = TempConsoleUI.LogWeight();
								if (weight != null) {
									selectedUser.addWeightRecord(weight);
									System.out.println("Weight logged successfully.");
									// Save the updated user data to a file
									AppData.SaveData.saveUserList(userList, filename);
									System.out.println("User data saved successfully.");
								} else {
									TempConsoleUI.ErrorMessage("Failed to log weight.");
								}
								break;
							
							case 11: // water, sleep, weight history
								TempConsoleUI.ClearConsole();
								TempConsoleUI.ViewWaterSleepWeightHistory(selectedUser);
								break;
							
							case 12: // Exit
								TempConsoleUI.ClearConsole();
								System.out.println("Exiting application.");
								System.exit(0);
						}
					}
				} else {
					TempConsoleUI.ErrorMessage("Failed to load user data.");
				}
			} else {
				// If the data file does not exist, proceed with first launch setup and create a
				// new user and save the data
				System.out.println("No existing user data found. Starting first launch setup.");

				// Display the first launch screen
				TempConsoleUI.FirstLaunchScreen();
				// Create a new user
				User FirstLaunchUser = GUI.NewUserCreationScreen();
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
}