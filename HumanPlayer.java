package snakeladder;

public class HumanPlayer implements Player {
    private final String name;
    private int position;

    public HumanPlayer(String name) {
        this.name = name;
        this.position = 1;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
    public boolean isBot() { return false; }
}
