package things.advancedstream;

public class User {
    private final String name;
    private final int age;
    private final String authority;

    public User(String name, int age, String authority) {
        this.name = name;
        this.age = age;
        this.authority = authority;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAuthority() {
        return authority;
    }
}
