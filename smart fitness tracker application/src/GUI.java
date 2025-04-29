import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class GUI {

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
            } else if (!Validation.isUsernameUnique(username, SmartFitnessTrackerApplication.existingUsernames)) {
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
                JOptionPane.showMessageDialog(frame, "User '" + userList.getUsers()[selectedIndex].getUsername() + "' selected successfully!", "User Selected", JOptionPane.INFORMATION_MESSAGE);
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
        gbc.gridx = 0;
        gbc.gridy = y;
        gbc.gridwidth = 2;
        frame.add(submitButton, gbc);
    
        final FitnessGoal[] fitnessGoalResult = {null};
    
        submitButton.addActionListener(e -> {
            try {
                int dailyStepCountGoal = stepGoalCheck.isSelected() ?
                    validateAndParseInteger(stepGoalField.getText(), 0, 100000, "Daily Step Count") : 0;
    
                int workoutDurationGoal = workoutDurationCheck.isSelected() ?
                    validateAndParseInteger(workoutDurationField.getText(), 0, 10080, "Workout Duration") : 0;
    
                int caloriesBurnedGoal = caloriesBurnedCheck.isSelected() ?
                    validateAndParseInteger(caloriesBurnedField.getText(), 0, 1000000, "Calories Burned") : 0;
    
                int workoutsPerWeekGoal = workoutsPerWeekCheck.isSelected() ?
                    validateAndParseInteger(workoutsPerWeekField.getText(), 0, 100, "Workouts Per Week") : 0;
    
                double dailyWaterIntakeGoal = waterIntakeCheck.isSelected() ?
                    validateAndParseDouble(waterIntakeField.getText(), 0.0, 50.0, "Water Intake") : 0.0;
    
                int sleepMinutesGoal = sleepMinutesCheck.isSelected() ?
                    validateAndParseInteger(sleepMinutesField.getText(), 0, 1440, "Sleep Minutes") : 0;
    
                double weightGoal = weightGoalCheck.isSelected() ?
                    validateAndParseDouble(weightGoalField.getText(), 0.0, 200.0, "Weight Goal") : 0.0;
    
                double strengthGoal = strengthGoalCheck.isSelected() ?
                    validateAndParseDouble(strengthGoalField.getText(), 0.0, 1000.0, "Strength Goal") : 0.0;
    
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
    
    // Validation helper methods
    private static int validateAndParseInteger(String input, int min, int max, String fieldName) {
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
    
    private static double validateAndParseDouble(String input, double min, double max, String fieldName) {
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
        
}
