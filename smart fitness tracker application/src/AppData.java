// AppData Class
// This class will manage the application data, including user data and workout data.
// It will handle data storage and retrieval, ensuring data integrity and security.

// Import Libraries
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class AppData {
    class SaveData {
        public static void saveUserList(userList list, String filename) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(list);
                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    class LoadData {
        public static userList loadUserList(String filename) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                return (userList) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    class CheckDataExists { // Check if the data file exists
        public static boolean checkDataExists(String filename) {
            File file = new File(filename);
            return file.exists();
        }
    }
}
    