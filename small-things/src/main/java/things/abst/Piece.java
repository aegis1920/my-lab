package things.abst;

public abstract class Piece {

    private final String name;

    protected Piece(String name) {
        this.name = name;
    }

    abstract void move();

    public String getName() {
        return name;
    }
}
