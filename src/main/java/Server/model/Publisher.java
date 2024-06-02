package Server.model;

public class Publisher {
    private String name;
    private String email;

    public Publisher(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}