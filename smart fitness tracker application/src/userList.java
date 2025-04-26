// userList Class
// This class will manage a list of users, allowing for adding, removing, and finding users by username.
// This class will be saved on the devicem using serialization.

import java.io.Serializable;

public class userList implements Serializable {
	private static final long serialVersionUID = 1L;

    private User[] users;

	// Constructor
	public userList() {
		this.users = new User[0]; // Initialize with an empty array
	}

	// Method to add a user
	public void addUser(User user) {
		User[] newUsers = new User[users.length + 1];
		System.arraycopy(users, 0, newUsers, 0, users.length);
		newUsers[users.length] = user;
		users = newUsers;
	}

	// Method to get all users
	public User[] getUsers() {
		return users;
	}

	// Method to find a user by username
	public User findUserByUsername(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		return null; // User not found
	}

	// Method to remove a user by username
	public void removeUserByUsername(String username) {
		User[] newUsers = new User[users.length - 1];
		int index = 0;
		for (User user : users) {
			if (!user.getUsername().equals(username)) {
				newUsers[index++] = user;
			}
		}
		users = newUsers;
	}

}