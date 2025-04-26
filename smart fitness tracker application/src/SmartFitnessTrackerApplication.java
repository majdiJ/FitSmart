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


public class SmartFitnessTrackerApplication {

	// Tempory string array holding existing usernames for testing purposes
	public static String[] existingUsernames = { "user1", "user2", "user3" };

	// Call the console UI
	public static void main(String[] args) {
		// Clear the console
		TempConsoleUI.ClearConsole();

		// Call the first launch screen
		User FirstLaunchUser = TempConsoleUI.FirstLaunchScreen();

		// Save the user data to a file
	}
}