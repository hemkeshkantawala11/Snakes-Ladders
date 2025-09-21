package snakeladder;

public class PlayerFactory {
    public static Player createHuman(String name) {
        return new HumanPlayer(name);
    }

    public static Player createBot(String name, BotStrategy strategy) {
        return new BotPlayer(name, strategy);
    }
}
