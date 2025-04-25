// This part of the code is responsible for drawring the welcome screen of the application on first launch.
// It will display a welcome screen and prompt the user to enter their name, age, and weight.
// It will also provide a button to proceed to the main application.

// Import Libraries
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomeScreen {

    private JFrame frame;
    private int width;
    private int height;
    private JLabel welcomeTitleTextLabel;

    public WelcomeScreen(int w, int h) {
        frame = new JFrame();
        width = w;
        height = h;
        welcomeTitleTextLabel = new JLabel("Welcome to FitSmart", JLabel.CENTER);
    }

    public void display() {
        frame.setSize(width, height);
        frame.setTitle("Welcome to FitSmart");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(welcomeTitleTextLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true); // Show the frame
    }
    
}