package snakeladder;

public interface BotStrategy {
    void takeTurn(BotPlayer player, DiceSet dice, Board board);
}
