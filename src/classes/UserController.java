package classes;

import java.util.ArrayList;
import java.util.List;

public class UserController {
    private final List<User> users = new ArrayList<>(); // list to store users data

    public void addUser(String username, String name) { // function to add new user to users list
        User user = new User(username, name, true);
        this.users.add(user);
    }

    public User getUserByUsername(String username) { // function to get 1 user from given username
        User userResult = null;

        for (User user : this.users) {
            if (user.getStatus() && user.getUsername().equalsIgnoreCase(username)) {
                userResult = user;
                break;
            }
        }
        return userResult;
    }

    public boolean isUsersEmpty() { // function to check is users list empty or not
        return this.users.size() == 0;
    }

    public void showUsers(boolean activeOnly) { // function to show users list to CLI with filter option, activeOnly
        if (activeOnly) {
            List<User> activeUsers = new ArrayList<>();

            for (User user : this.users) {
                if (user.getStatus()) activeUsers.add(user);
            }

            for (int i = 0; i < activeUsers.size(); i++) {
                User user = activeUsers.get(i);
                System.out.println(i + 1 + ". " + user.getUsername() + " - " + user.getName());
            }
        } else {
            for (int i = 0; i < this.users.size(); i++) {
                User user = this.users.get(i);
                System.out.print(i + 1 + ". " + user.getUsername() + " - " + user.getName());
                if (!user.getStatus()) System.out.println(" - Inactive.");
                else System.out.println();
            }
        }
    }

    public void removeUser(User user) { // function to change user active status to false
        user.setStatus(false);
    }

    public void exportToCsv() {
        FileController controller = new FileController(); // initiate fileController object
        controller.saveUsers(this.users); // export users from list to .csv format
    }

    public void importFromCsv() {
        FileController controller = new FileController(); // initiate fileController object
        this.users.clear(); // remove all user entry from users list
        controller.loadUsers(this.users); // import all data entry from users.csv to users list
    }

    public void addDummies() { // function to add dummy data when program started
        this.users.add(new User("farhan1", "Farhan", true));
        this.users.add(new User("farhan2", "Farhan", true));
        this.users.add(new User("farhan3", "Farhan", true));
        this.users.add(new User("farhan4", "Farhan", true));
    }
}
