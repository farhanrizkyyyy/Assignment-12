package classes;

// ENCAPSULATION
public class User {
    private final String username;
    private final String name;
    private boolean isActive;

    public User(String username, String name, boolean isActive) {
        this.username = username;
        this.name = name;
        this.isActive = isActive;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public boolean getStatus() {
        return this.isActive;
    }

    public void setStatus(boolean isActive) {
        this.isActive = isActive;
    }
}
