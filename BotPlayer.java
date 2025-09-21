package snakeladder;

public class BotPlayer implements Player {
    private final String name;
    private int position;
    private final BotStrategy strategy;

    public BotPlayer(String name, BotStrategy strategy) {
        this.name = name;
        this.position = 1;
        this.strategy = strategy;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public void setPosition(int pos) { this.position = pos; }
    public boolean isBot() { return true; }

    public BotStrategy getStrategy() {
        return strategy;
    }
}
