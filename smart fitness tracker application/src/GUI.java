// GUI Class
// This class is resposible for creating the GUI for the Smart Fitness Tracker Application.
// It includes methods for: creating the user creation screen, user selection menu, user PIN login, and setting fitness goals.

import java.awt.*;
import java.time.LocalDateTime;
import java.net.URL;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class GUI {

    // Splash screen for first launch
    public static boolean WelcomeSplashScreen() {
        JFrame frame = new JFrame("Welcome to FitSmart");
        URL iconURL = GUI.class.getResource("/Images/logo.png");

        // Load icon from resources
        if (iconURL != null) {
            Image icon = Toolkit.getDefaultToolkit().getImage(iconURL);
            frame.setIconImage(icon);
        } else {
            System.err.println("Icon not found");
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    
        // Load logo
        JLabel logoLabel = new JLabel();
        if (iconURL != null) {
            ImageIcon logoIcon = new ImageIcon(iconURL);
            Image scaledImage = logoIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            logoLabel.setText("Logo not found");
            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    
        // Left panel for welcome text
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel welcomeLabel = new JLabel("Welcome to FitSmart!");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        gbc.gridy = 0;
        leftPanel.add(welcomeLabel, gbc);
    
        JLabel descLabel = new JLabel("<html><p>Track your workouts, monitor your health, and crush your fitness goals with FitSmart.</p></html>");
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        gbc.gridy = 1;
        leftPanel.add(descLabel, gbc);
    
        JButton startButton = new JButton("Let's get started!");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));
        startButton.setBackground(new Color(0, 255, 127)); // Spring Green
        startButton.setFocusPainted(false);
        gbc.gridy = 2;
        leftPanel.add(startButton, gbc);
    
        final boolean[] result = {false};
    
        startButton.addActionListener(e -> {
            result[0] = true;
            frame.dispose();
        });
    
        // Right panel with logo
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(logoLabel, BorderLayout.CENTER);
    
        frame.add(leftPanel, BorderLayout.CENTER);
        frame.add(rightPanel, BorderLayout.EAST);
    
        frame.setVisible(true);
    
        // Wait for the frame to close
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return result[0];
    }
    
    // New user creation screen
    public static User NewUserCreationScreen() {
        JFrame frame = new JFrame("New User Creation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components for user input
        JTextField usernameField = new JTextField(20);
        JPasswordField pinField = new JPasswordField(20);
        JTextField dobField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField weightField = new JTextField(20);

        // Add components to the frame
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("PIN (4 digits):"), gbc);

        gbc.gridx = 1;
        frame.add(pinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);

        gbc.gridx = 1;
        frame.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        frame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Weight (in kg):"), gbc);

        gbc.gridx = 1;
        frame.add(weightField, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        frame.add(submitButton, gbc);

        final User[] user = {null};

        submitButton.addActionListener(e -> {
            String username = usernameField.getText();
            String pin = new String(pinField.getPassword());
            String dobString = dobField.getText();
            String name = nameField.getText();
            String weightString = weightField.getText();

            // Validation
            if (Validation.isUsernameEmpty(username)) {
                JOptionPane.showMessageDialog(frame, "Username cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isUsernameUnique(username, SmartFitnessTrackerApplicationGUI.existingUsernames)) {
                JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validation.doesUsernameContainSpecialChars(username)) {
                JOptionPane.showMessageDialog(frame, "Username cannot contain special characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validation.isPinEmpty(pin)) {
                JOptionPane.showMessageDialog(frame, "PIN cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinNumeric(pin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinValidLength(pin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.canConvertStringToLocalDateTime(dobString)) {
                JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validation.isDobEmpty(Validation.convertStringToLocalDateTime(dobString))) {
                JOptionPane.showMessageDialog(frame, "Date of Birth cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isDobPast(Validation.convertStringToLocalDateTime(dobString))) {
                JOptionPane.showMessageDialog(frame, "Date of Birth must be in the past.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validation.isNameEmpty(name)) {
                JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Validation.doesNameContainSpecialChars(name)) {
                JOptionPane.showMessageDialog(frame, "Name cannot contain special characters.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    double weight = Double.parseDouble(weightString);
                    if (Validation.isWeightEmpty(weight)) {
                        JOptionPane.showMessageDialog(frame, "Weight cannot be empty or negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (!Validation.isWeightPositive(weight)) {
                        JOptionPane.showMessageDialog(frame, "Weight must be positive.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        LocalDateTime dob = Validation.convertStringToLocalDateTime(dobString);
                        user[0] = new User(username, pin, name, dob, weight);
                        JOptionPane.showMessageDialog(frame, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        frame.dispose();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Weight must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);

        // Wait for the user to complete the form
        while (user[0] == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return user[0];
    }

    // User selection menu
    public static int UserSelectionMenu(userList userList) {
        JFrame frame = new JFrame("User Selection");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    
        JPanel topPanel = new JPanel(new FlowLayout());
        JButton createNewUserButton = new JButton("Create New User");
        JButton exitButton = new JButton("Exit");
    
        topPanel.add(createNewUserButton);
        topPanel.add(exitButton);
    
        frame.add(topPanel, BorderLayout.NORTH);
    
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (int i = 0; i < userList.getUsers().length; i++) {
            listModel.addElement(userList.getUsers()[i].getUsername());
        }
    
        JList<String> userJList = new JList<>(listModel);
        userJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(userJList);
        frame.add(scrollPane, BorderLayout.CENTER);
    
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton selectUserButton = new JButton("Select User");
        bottomPanel.add(selectUserButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);
    
        final int[] choice = {-1}; // -1 means no choice made yet
    
        // Exit button action
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                choice[0] = 0;
                frame.dispose();
            }
        });
    
        // Create New User button action
        createNewUserButton.addActionListener(e -> {
            choice[0] = 1;
            frame.dispose();
        });
    
        // Select User button action
        selectUserButton.addActionListener(e -> {
            int selectedIndex = userJList.getSelectedIndex();
            if (selectedIndex != -1) {
            choice[0] = selectedIndex + 2; // +2 because 0 = Exit, 1 = Create New User
            frame.dispose();
            } else {
            JOptionPane.showMessageDialog(frame, "Please select a user from the list.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    
        // Wait for user to make a selection
        while (choice[0] == -1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return choice[0];
    }
   
    // User PIN login screen
    public static String UserPinLogin(String username) {
        JFrame frame = new JFrame("User PIN Login");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel promptLabel = new JLabel("Please enter your PIN for '" + username + "':");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(promptLabel, gbc);
    
        JPasswordField pinField = new JPasswordField(20);
        gbc.gridy = 1;
        frame.add(pinField, gbc);
    
        JButton submitButton = new JButton("Submit");
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        frame.add(submitButton, gbc);
    
        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        frame.add(cancelButton, gbc);
    
        final String[] userPin = {null};
    
        submitButton.addActionListener(e -> {
            String enteredPin = new String(pinField.getPassword());
    
            if (Validation.isPinEmpty(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinNumeric(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinValidLength(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userPin[0] = enteredPin;
                JOptionPane.showMessageDialog(frame, "PIN accepted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
        });
    
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel login?", "Confirm Cancel", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userPin[0] = null;
                frame.dispose();
            }
        });
    
        frame.setVisible(true);
    
        // Wait for the user to either submit or cancel
        while (userPin[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return userPin[0];
    }

    // User PIN validation screen
    public static boolean UserPinValidationScreen(User selectedUser) {
        JFrame frame = new JFrame("User PIN Validation");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel titleLabel = new JLabel("Please enter your PIN for user: " + selectedUser.getUsername());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(titleLabel, gbc);
    
        // PIN field
        JLabel pinLabel = new JLabel("PIN:");
        pinLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        frame.add(pinLabel, gbc);
    
        JPasswordField pinField = new JPasswordField(20);
        gbc.gridx = 1;
        frame.add(pinField, gbc);
    
        // Buttons
        JButton submitButton = new JButton("Submit");
        gbc.gridy = 2;
        gbc.gridx = 0;
        frame.add(submitButton, gbc);
    
        JButton exitButton = new JButton("Exit");
        gbc.gridx = 1;
        frame.add(exitButton, gbc);
    
        final boolean[] pinValidated = {false};
    
        submitButton.addActionListener(e -> {
            String enteredPin = new String(pinField.getPassword());
    
            // Validation checks
            if (Validation.isPinEmpty(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinNumeric(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be numeric.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Validation.isPinValidLength(enteredPin)) {
                JOptionPane.showMessageDialog(frame, "PIN must be 4 digits.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (selectedUser.getPin().equals(enteredPin)) {
                    JOptionPane.showMessageDialog(frame, "PIN validated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    pinValidated[0] = true;
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid PIN. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    pinField.setText(""); // Clear the field
                }
            }
        });
    
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to exit without logging in?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pinValidated[0] = false;
                frame.dispose();
            }
        });
    
        frame.setVisible(true);
    
        // Wait for user action
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return pinValidated[0];
    }
    
    // Set fitness goals screen
    public static FitnessGoal SetFitnessGoals() {
        JFrame frame = new JFrame("Set Fitness Goals");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Create Checkboxes and TextFields
        JCheckBox stepGoalCheck = new JCheckBox("Daily Step Count Goal:");
        JTextField stepGoalField = new JTextField(10);
        stepGoalField.setEnabled(false);
    
        JCheckBox workoutDurationCheck = new JCheckBox("Weekly Workout Duration (min):");
        JTextField workoutDurationField = new JTextField(10);
        workoutDurationField.setEnabled(false);
    
        JCheckBox caloriesBurnedCheck = new JCheckBox("Weekly Calories Burned:");
        JTextField caloriesBurnedField = new JTextField(10);
        caloriesBurnedField.setEnabled(false);
    
        JCheckBox workoutsPerWeekCheck = new JCheckBox("Workouts Per Week:");
        JTextField workoutsPerWeekField = new JTextField(10);
        workoutsPerWeekField.setEnabled(false);
    
        JCheckBox waterIntakeCheck = new JCheckBox("Daily Water Intake (liters):");
        JTextField waterIntakeField = new JTextField(10);
        waterIntakeField.setEnabled(false);
    
        JCheckBox sleepMinutesCheck = new JCheckBox("Sleep Minutes Per Day:");
        JTextField sleepMinutesField = new JTextField(10);
        sleepMinutesField.setEnabled(false);
    
        JCheckBox weightGoalCheck = new JCheckBox("Weight Goal (kg):");
        JTextField weightGoalField = new JTextField(10);
        weightGoalField.setEnabled(false);
    
        JCheckBox strengthGoalCheck = new JCheckBox("Strength Goal (kg):");
        JTextField strengthGoalField = new JTextField(10);
        strengthGoalField.setEnabled(false);
    
        // Listeners to enable/disable fields
        stepGoalCheck.addActionListener(e -> stepGoalField.setEnabled(stepGoalCheck.isSelected()));
        workoutDurationCheck.addActionListener(e -> workoutDurationField.setEnabled(workoutDurationCheck.isSelected()));
        caloriesBurnedCheck.addActionListener(e -> caloriesBurnedField.setEnabled(caloriesBurnedCheck.isSelected()));
        workoutsPerWeekCheck.addActionListener(e -> workoutsPerWeekField.setEnabled(workoutsPerWeekCheck.isSelected()));
        waterIntakeCheck.addActionListener(e -> waterIntakeField.setEnabled(waterIntakeCheck.isSelected()));
        sleepMinutesCheck.addActionListener(e -> sleepMinutesField.setEnabled(sleepMinutesCheck.isSelected()));
        weightGoalCheck.addActionListener(e -> weightGoalField.setEnabled(weightGoalCheck.isSelected()));
        strengthGoalCheck.addActionListener(e -> strengthGoalField.setEnabled(strengthGoalCheck.isSelected()));
    
        // Add components to frame
        int y = 0;
        Component[][] components = {
            {stepGoalCheck, stepGoalField},
            {workoutDurationCheck, workoutDurationField},
            {caloriesBurnedCheck, caloriesBurnedField},
            {workoutsPerWeekCheck, workoutsPerWeekField},
            {waterIntakeCheck, waterIntakeField},
            {sleepMinutesCheck, sleepMinutesField},
            {weightGoalCheck, weightGoalField},
            {strengthGoalCheck, strengthGoalField}
        };
    
        for (Component[] pair : components) {
            gbc.gridx = 0;
            gbc.gridy = y;
            frame.add(pair[0], gbc);
            gbc.gridx = 1;
            frame.add(pair[1], gbc);
            y++;
        }
    
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 1;
        frame.add(submitButton, gbc);
        gbc.gridx = 1;
        frame.add(cancelButton, gbc);
    
        final FitnessGoal[] fitnessGoalResult = {null};
    
        submitButton.addActionListener(e -> {
            try {
                int dailyStepCountGoal = stepGoalCheck.isSelected() ?
                    Validation.validateAndParseInteger(stepGoalField.getText(), 0, 100000, "Daily Step Count") : 0;
    
                int workoutDurationGoal = workoutDurationCheck.isSelected() ?
                    Validation.validateAndParseInteger(workoutDurationField.getText(), 0, 10080, "Workout Duration") : 0;
    
                int caloriesBurnedGoal = caloriesBurnedCheck.isSelected() ?
                    Validation.validateAndParseInteger(caloriesBurnedField.getText(), 0, 1000000, "Calories Burned") : 0;
    
                int workoutsPerWeekGoal = workoutsPerWeekCheck.isSelected() ?
                    Validation.validateAndParseInteger(workoutsPerWeekField.getText(), 0, 100, "Workouts Per Week") : 0;
    
                double dailyWaterIntakeGoal = waterIntakeCheck.isSelected() ?
                    Validation.validateAndParseDouble(waterIntakeField.getText(), 0.0, 50.0, "Water Intake") : 0.0;
    
                int sleepMinutesGoal = sleepMinutesCheck.isSelected() ?
                    Validation.validateAndParseInteger(sleepMinutesField.getText(), 0, 1440, "Sleep Minutes") : 0;
    
                double weightGoal = weightGoalCheck.isSelected() ?
                    Validation.validateAndParseDouble(weightGoalField.getText(), 0.0, 200.0, "Weight Goal") : 0.0;
    
                double strengthGoal = strengthGoalCheck.isSelected() ?
                    Validation.validateAndParseDouble(strengthGoalField.getText(), 0.0, 1000.0, "Strength Goal") : 0.0;
    
                boolean isGoalSet = stepGoalCheck.isSelected() || workoutDurationCheck.isSelected() ||
                                    caloriesBurnedCheck.isSelected() || workoutsPerWeekCheck.isSelected() ||
                                    waterIntakeCheck.isSelected() || sleepMinutesCheck.isSelected() ||
                                    weightGoalCheck.isSelected() || strengthGoalCheck.isSelected();
    
                fitnessGoalResult[0] = new FitnessGoal(
                        dailyStepCountGoal,
                        workoutDurationGoal,
                        caloriesBurnedGoal,
                        workoutsPerWeekGoal,
                        dailyWaterIntakeGoal,
                        sleepMinutesGoal,
                        weightGoal,
                        strengthGoal,
                        isGoalSet
                );
    
                JOptionPane.showMessageDialog(frame, "Goals set successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        cancelButton.addActionListener(e -> {
            fitnessGoalResult[0] = null;
            frame.dispose();
        });
    
        frame.setVisible(true);
    
        // Wait until frame is closed
        while (fitnessGoalResult[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return fitnessGoalResult[0];
    }
    
    // Make GUI for user dashboard
    public static int UserMainMenu(User user) {
        JDialog dialog = new JDialog();
        dialog.setModal(true);
        dialog.setTitle("FitSmart Dashboard");
        dialog.setSize(1000, 800);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(null);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Declare returnChoice BEFORE creating buttons
        final int[] returnChoice = {0};

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] buttonLabels = {
            "View Profile", "Set Goals", "View Goals",
            "Log Workout", "Workout History", "Progress Reports",
            "Log Water", "Log Sleep", "Log Weight",
            "View sleep history", "View water history", "View weight history"
        };

        int[] choices = {
            1, 2, 3,
            4, 5, 6,
            7, 8, 9,
            10, 11, 12
        };

        for (int i = 0; i < buttonLabels.length; i++) {
            if (choices[i] == -1) {
                buttonPanel.add(new JLabel());
                continue;
            }

            JButton btn = new JButton("<html><center>" + buttonLabels[i] + "</center></html>");
            btn.setPreferredSize(new Dimension(200, 80));
            final int choice = choices[i];

            btn.addActionListener(e -> {
                dialog.dispose();
                returnChoice[0] = choice;
            });

            buttonPanel.add(btn);
        }

        dialog.add(new JScrollPane(buttonPanel), BorderLayout.CENTER);

        // Window listener to exit application when the window is closed
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); // Exit the application
            }
        });

        dialog.setVisible(true);

        // Wait for dialog to close
        while (dialog.isVisible()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return returnChoice[0];
    }
    // Make GUI to show users profile
    public static boolean ViewUserProfile(User user) {
        JFrame frame = new JFrame("User Profile - " + user.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add profile information
        frame.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(user.getUsername()), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(user.getName()), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Date of Birth:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(user.getDob().toString()), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(user.getWeight() + " kg"), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Buttons
        JButton backButton = new JButton("Back");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(backButton, gbc);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }

    // Make GUI for user to log their workout
    public static Workout LogWorkout() {
        JFrame frame = new JFrame("Log Workout");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Workout type
        JLabel typeLabel = new JLabel("Workout Type:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(typeLabel, gbc);

        JComboBox<String> typeComboBox = new JComboBox<>(Workout.WORKOUT_TYPES);
        gbc.gridx = 1;
        frame.add(typeComboBox, gbc);

        // Duration
        JLabel durationLabel = new JLabel("Duration (minutes):");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(durationLabel, gbc);

        JTextField durationField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(durationField, gbc);

        // Calories burned
        JLabel caloriesLabel = new JLabel("Calories Burned:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(caloriesLabel, gbc);

        JTextField caloriesField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(caloriesField, gbc);

        // Steps
        JLabel stepsLabel = new JLabel("Steps Taken:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(stepsLabel, gbc);

        JTextField stepsField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(stepsField, gbc);

        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(notesLabel, gbc);

        JTextArea notesArea = new JTextArea(3, 20);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        gbc.gridx = 1;
        frame.add(notesScroll, gbc);

        // Date/time
        JLabel dateLabel = new JLabel("Date/Time:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(dateLabel, gbc);

        JTextField dateField = new JTextField(LocalDateTime.now().toString());
        gbc.gridx = 1;
        frame.add(dateField, gbc);

        // Buttons
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(submitButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        frame.add(cancelButton, gbc);

        final Workout[] workoutResult = {null};

        submitButton.addActionListener(e -> {
            try {
                String type = (String) typeComboBox.getSelectedItem();
                int duration = Integer.parseInt(durationField.getText());
                int calories = Integer.parseInt(caloriesField.getText());
                int steps = Integer.parseInt(stepsField.getText());
                String notes = notesArea.getText();
                LocalDateTime dateTime = LocalDateTime.parse(dateField.getText());

                workoutResult[0] = new Workout(type, duration, calories, steps, dateTime, notes);
                JOptionPane.showMessageDialog(frame, "Workout logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for duration, calories, and steps.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please use the format shown.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);

        // Wait until frame is closed
        while (workoutResult[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return workoutResult[0];
    }

    // Make GUI for user to log their water intake
    public static Water LogWaterIntake() {
        JFrame frame = new JFrame("Log Water Intake");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel amountLabel = new JLabel("Amount of water (liters):");
        JTextField amountField = new JTextField(20);
    
        JLabel useCurrentLabel = new JLabel("Use current date/time?");
        JCheckBox useCurrentCheckbox = new JCheckBox("Yes", true);
    
        JLabel customDateTimeLabel = new JLabel("Custom date/time (YYYY-MM-DD HH:MM):");
        JTextField customDateTimeField = new JTextField(20);
        customDateTimeField.setEnabled(false); // Disabled by default
    
        JLabel notesLabel = new JLabel("Notes:");
        JTextField notesField = new JTextField(20);
    
        JButton submitButton = new JButton("Submit");
        final Water[] loggedWater = {null};
    
        // Toggle custom datetime field based on checkbox
        useCurrentCheckbox.addActionListener(e -> {
            customDateTimeField.setEnabled(!useCurrentCheckbox.isSelected());
        });
    
        gbc.gridx = 0; gbc.gridy = 0;
        frame.add(amountLabel, gbc);
        gbc.gridx = 1;
        frame.add(amountField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 1;
        frame.add(useCurrentLabel, gbc);
        gbc.gridx = 1;
        frame.add(useCurrentCheckbox, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2;
        frame.add(customDateTimeLabel, gbc);
        gbc.gridx = 1;
        frame.add(customDateTimeField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 3;
        frame.add(notesLabel, gbc);
        gbc.gridx = 1;
        frame.add(notesField, gbc);
    
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        frame.add(submitButton, gbc);
    
        submitButton.addActionListener(e -> {
            try {
                // Water amount validation
                double amount = Double.parseDouble(amountField.getText().trim());
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Water intake must be a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Notes validation
                String notes = notesField.getText().trim();
                if (notes.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Notes cannot be empty.", "Missing Notes", JOptionPane.ERROR_MESSAGE);
                    return;
                }
    
                // Date/time handling
                LocalDateTime dateTime;
                if (useCurrentCheckbox.isSelected()) {
                    dateTime = LocalDateTime.now();
                    JOptionPane.showMessageDialog(frame, "Using current date and time: " + dateTime.toString(), "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String customDateTimeStr = customDateTimeField.getText().trim();
                    if (!Validation.canConvertStringToLocalDateTime(customDateTimeStr)) {
                        JOptionPane.showMessageDialog(frame, "Invalid date format. Please use YYYY-MM-DD HH:MM.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    dateTime = Validation.convertStringToLocalDateTime(customDateTimeStr);
                    JOptionPane.showMessageDialog(frame, "Custom date and time set to: " + dateTime.toString(), "Date Set", JOptionPane.INFORMATION_MESSAGE);
                }
    
                // All valid → create Water object
                loggedWater[0] = new Water(amount, dateTime, notes);
                JOptionPane.showMessageDialog(frame, "Water intake logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Amount must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    
        // Wait for the user to submit
        while (loggedWater[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    
        return loggedWater[0];
    }

    // Make GUI for user to log their sleep
    public static Sleep LogSleep() {
        JFrame frame = new JFrame("Log Sleep");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Duration
        JLabel durationLabel = new JLabel("Duration (minutes):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(durationLabel, gbc);

        JTextField durationField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(durationField, gbc);

        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(notesLabel, gbc);

        JTextArea notesArea = new JTextArea(3, 20);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        gbc.gridx = 1;
        frame.add(notesScroll, gbc);

        // Date/time
        JLabel dateLabel = new JLabel("Date/Time:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(dateLabel, gbc);

        JTextField dateField = new JTextField(LocalDateTime.now().toString());
        gbc.gridx = 1;
        frame.add(dateField, gbc);

        // Buttons
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(submitButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        frame.add(cancelButton, gbc);

        final Sleep[] sleepResult = {null};

        submitButton.addActionListener(e -> {
            try {
                int duration = Integer.parseInt(durationField.getText());
                String notes = notesArea.getText();
                LocalDateTime dateTime = LocalDateTime.parse(dateField.getText());

                sleepResult[0] = new Sleep(duration, dateTime, notes);
                JOptionPane.showMessageDialog(frame, "Sleep logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for duration.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please use the format shown.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);

        // Wait until frame is closed
        while (sleepResult[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return sleepResult[0];
    }

    // Make GUI for user to log their weight
    public static Weight LogWeight() {
        JFrame frame = new JFrame("Log Weight");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Weight
        JLabel weightLabel = new JLabel("Weight (kg):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(weightLabel, gbc);

        JTextField weightField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(weightField, gbc);

        // Notes
        JLabel notesLabel = new JLabel("Notes:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(notesLabel, gbc);

        JTextArea notesArea = new JTextArea(3, 20);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        gbc.gridx = 1;
        frame.add(notesScroll, gbc);

        // Date/time
        JLabel dateLabel = new JLabel("Date/Time:");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(dateLabel, gbc);

        JTextField dateField = new JTextField(LocalDateTime.now().toString());
        gbc.gridx = 1;
        frame.add(dateField, gbc);

        // Buttons
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy++;
        frame.add(submitButton, gbc);

        JButton cancelButton = new JButton("Cancel");
        gbc.gridx = 1;
        frame.add(cancelButton, gbc);

        final Weight[] weightResult = {null};

        submitButton.addActionListener(e -> {
            try {
                double weight = Double.parseDouble(weightField.getText());
                String notes = notesArea.getText();
                LocalDateTime dateTime = LocalDateTime.parse(dateField.getText());

                weightResult[0] = new Weight(weight, dateTime, notes);
                JOptionPane.showMessageDialog(frame, "Weight logged successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for weight.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid date/time format. Please use the format shown.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> frame.dispose());

        frame.setVisible(true);

        // Wait until frame is closed
        while (weightResult[0] == null && frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return weightResult[0];
    }

    // Make GUI to show users fitness goals
    public static boolean ViewFitnessGoals(FitnessGoal goals) {
        JFrame frame = new JFrame("Fitness Goals");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Add goals information
        frame.add(new JLabel("Daily Step Count:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getDailyStepCount())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Workout Duration (minutes):"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getWorkoutDuration())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Calories Burned:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getCaloriesBurned())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Workouts Per Week:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getWorkoutsPerWeek())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Daily Water Intake (liters):"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getDailyWaterIntake())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Sleep Minutes:"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getsleepMinutes())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Weight Goal (kg):"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getWeightGoal())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        frame.add(new JLabel("Strength Goal (kg):"), gbc);
        gbc.gridx = 1;
        frame.add(new JLabel(String.valueOf(goals.getStrength())), gbc);
        gbc.gridx = 0;
        gbc.gridy++;

        // Back button
        JButton backButton = new JButton("Back");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(backButton, gbc);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }
    // Make GUI to show users workout history
    public static boolean ViewWorkoutHistory(Workout[] workouts) {
        JFrame frame = new JFrame("Workout History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        if (workouts == null || workouts.length == 0) {
            JOptionPane.showMessageDialog(frame, "No workout history available.", "Information", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return false;
        }

        // Create column names
        String[] columnNames = {"Date", "Type", "Duration (min)", "Calories", "Steps", "Notes"};

        // Create data
        Object[][] data = new Object[workouts.length][6];
        for (int i = 0; i < workouts.length; i++) {
            data[i][0] = workouts[i].getDateTime().toString();
            data[i][1] = workouts[i].getType();
            data[i][2] = workouts[i].getDuration();
            data[i][3] = workouts[i].getCaloriesBurned();
            data[i][4] = workouts[i].getSteps();
            data[i][5] = workouts[i].getNotes();
        }

        // Create table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }
    
    // Make GUI to show users water intake history
    public static boolean ViewWaterIntakeHistory(User user) {
        JFrame frame = new JFrame("Water Intake History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        if (user.getWaterIntake() == null || user.getWaterIntake().length == 0) {
            JOptionPane.showMessageDialog(frame, 
                "No water intake history available.", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return false;
        }

        // Create column names
        String[] columnNames = {"Date/Time", "Amount (liters)", "Notes"};

        // Create data
        Object[][] data = new Object[user.getWaterIntake().length][3];
        for (int i = 0; i < user.getWaterIntake().length; i++) {
            data[i][0] = user.getWaterIntake()[i].getDateTime().toString();
            data[i][1] = user.getWaterIntake()[i].getAmount();
            data[i][2] = user.getWaterIntake()[i].getNotes();
        }

        // Create table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        frame.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }

    // Make GUI to show users sleep history
    public static boolean ViewSleepHistory(User user) {
        JFrame frame = new JFrame("Sleep History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        if (user.getSleep() == null || user.getSleep().length == 0) {
            JOptionPane.showMessageDialog(frame, 
                "No sleep history available.", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return false;
        }

        // Create column names
        String[] columnNames = {"Date/Time", "Duration (minutes)", "Notes"};

        // Create data
        Object[][] data = new Object[user.getSleep().length][3];
        for (int i = 0; i < user.getSleep().length; i++) {
            data[i][0] = user.getSleep()[i].getDateTime().toString();
            data[i][1] = user.getSleep()[i].getDuration();
            data[i][2] = user.getSleep()[i].getNotes();
        }

        // Create table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components
        frame.add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }

    // Make GUI to show users weight history
    public static boolean ViewWeightHistory(User user) {

        JFrame frame = new JFrame("Weight History");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
    
        if (user.getWeightRecords() == null || user.getWeightRecords().length == 0) {
            JOptionPane.showMessageDialog(frame, 
                "No weight history available.", 
                "Information", 
                JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            return false;
        }
    
        // Create column names
        String[] columnNames = {"Date/Time", "Weight (kg)", "Notes"};
    
        // Create data
        Object[][] data = new Object[user.getWeightRecords().length][3];
        for (int i = 0; i < user.getWeightRecords().length; i++) {
            data[i][0] = user.getWeightRecords()[i].getDateTime().toString();
            data[i][1] = user.getWeightRecords()[i].getLogedWeight();
            data[i][2] = user.getWeightRecords()[i].getNotes();
        }
    
        // Create table
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
    
        // Add components
        frame.add(scrollPane, BorderLayout.CENTER);
    
        // Add chart panel
        JPanel chartPanel = new JPanel();
        // Here you could add a chart visualization if desired
        chartPanel.add(new JLabel("Weight Trend Chart would appear here"));
        frame.add(chartPanel, BorderLayout.NORTH);
    
        // Back button
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });
    
        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }
    
    // Make GUI to show users progress reports    
    public static boolean ViewProgressReport(User user) {
        JFrame frame = new JFrame("Progress Report - " + user.getUsername());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Create tabbed pane for different report sections
        JTabbedPane tabbedPane = new JTabbedPane();

        // Summary tab
        JPanel summaryPanel = new JPanel(new BorderLayout());
        JTextArea summaryText = new JTextArea();
        summaryText.setEditable(false);
        summaryText.append("=== USER PROFILE ===\n");
        summaryText.append("Name: " + user.getName() + "\n");
        summaryText.append("Weight: " + user.getWeight() + " kg\n\n");
        
        summaryText.append("=== FITNESS GOALS ===\n");
        FitnessGoal goals = user.getFitnessGoal();
        if (goals != null) {
            summaryText.append("Daily Steps: " + goals.getDailyStepCount() + "\n");
            summaryText.append("Workout Duration: " + goals.getWorkoutDuration() + " min\n");
            summaryText.append("Calories Burned: " + goals.getCaloriesBurned() + "\n");
            summaryText.append("Workouts Per Week: " + goals.getWorkoutsPerWeek() + "\n");
            summaryText.append("Water Intake: " + goals.getDailyWaterIntake() + " liters\n");
            summaryText.append("Sleep: " + goals.getsleepMinutes() + " min\n");
            summaryText.append("Weight Goal: " + goals.getWeightGoal() + " kg\n");
            summaryText.append("Strength Goal: " + goals.getStrength() + " kg\n");
        } else {
            summaryText.append("No fitness goals set.\n");
        }
        summaryPanel.add(new JScrollPane(summaryText), BorderLayout.CENTER);
        tabbedPane.addTab("Summary", summaryPanel);

        // Progress tab
        JPanel progressPanel = new JPanel(new BorderLayout());
        JTextArea progressText = new JTextArea();
        progressText.setEditable(false);
        
        ProgressTracker tracker = new ProgressTracker();
        LocalDateTime now = LocalDateTime.now();
        
        // Steps progress
        progressText.append("=== STEPS ===\n");
        if (user.getWorkouts() != null && user.getWorkouts().length > 0) {
            progressText.append("Today: " + tracker.getStepsTakenInRange(user.getWorkouts(), now.minusDays(1), now) + "\n");
            progressText.append("This Week: " + tracker.getStepsTakenInRange(user.getWorkouts(), now.minusDays(7), now) + "\n");
            progressText.append("This Month: " + tracker.getStepsTakenInRange(user.getWorkouts(), now.minusDays(30), now) + "\n");
            progressText.append("Average Daily: " + tracker.getAverageStepsTakenInRange(user.getWorkouts(), now.minusDays(30), now) + "\n\n");
        } else {
            progressText.append("No workout data available.\n\n");
        }
        
        // Workouts progress
        progressText.append("=== WORKOUTS ===\n");
        if (user.getWorkouts() != null && user.getWorkouts().length > 0) {
            progressText.append("This Week: " + tracker.getTotalWorkoutsInRange(user.getWorkouts(), now.minusDays(7), now) + "\n");
            progressText.append("This Month: " + tracker.getTotalWorkoutsInRange(user.getWorkouts(), now.minusDays(30), now) + "\n");
            progressText.append("Average Weekly: " + tracker.getAverageWorkoutsInRange(user.getWorkouts(), now.minusDays(30), now) + "\n\n");
        } else {
            progressText.append("No workout data available.\n\n");
        }
        
        // Weight progress
        progressText.append("=== WEIGHT ===\n");
        if (user.getWeightRecords() != null && user.getWeightRecords().length > 0) {
            progressText.append("Current: " + user.getWeightRecords()[user.getWeightRecords().length - 1].getLogedWeight() + " kg\n");
            progressText.append("Change (30 days): " + tracker.getWeightChangeInRange(user.getWeightRecords(), now.minusDays(30), now) + " kg\n\n");
        } else {
            progressText.append("No weight records available.\n\n");
        }
        
        progressPanel.add(new JScrollPane(progressText), BorderLayout.CENTER);
        tabbedPane.addTab("Progress", progressPanel);

        // Charts tab (placeholder)
        JPanel chartsPanel = new JPanel();
        chartsPanel.add(new JLabel("Charts and graphs would be displayed here"));
        tabbedPane.addTab("Charts", chartsPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);

        // Back button
        JButton backButton = new JButton("Back");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        final boolean[] goBack = {false};

        backButton.addActionListener(e -> {
            goBack[0] = true;
            frame.dispose();
        });

        frame.setVisible(true);

        // Wait for the user to close the frame
        while (frame.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        return goBack[0];
    }
}