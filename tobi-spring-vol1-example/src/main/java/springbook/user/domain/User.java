package springbook.user.domain;

public class User {
    String id;
    String name;
    String password;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public User setId(String id) {
        this.id = id;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
}
