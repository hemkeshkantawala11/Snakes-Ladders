package snakeladder;

import java.util.List;

public class RandomBotStrategy implements BotStrategy {
    @Override
    public void takeTurn(BotPlayer player, DiceSet dice, Board board) {
        List<Integer> rolls = dice.rollAll();
        int sum = rolls.stream().mapToInt(Integer::intValue).sum();
        int newPos = player.getPosition() + sum;

        if (newPos <= board.getWinningCell()) {
            newPos = board.getNextPosition(newPos);
            player.setPosition(newPos);
        }
        System.out.println(player.getName() + " rolled " + rolls + " -> moved to " + player.getPosition());
    }
}
