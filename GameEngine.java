package snakeladder;

import java.util.*;

public class GameEngine {
    private final Board board;
    private final DiceSet diceSet;
    private final List<Player> players;
    private final Map<Integer, Player> positionMap;
    private final List<Rule> rules;

    public GameEngine(Board board, DiceSet diceSet, List<Player> players) {
        this.board = board;
        this.diceSet = diceSet;
        this.players = players;
        this.positionMap = new HashMap<>();
        this.rules = List.of(
                new ExactWinRule(),
                new KickOutRule(),
                new ExtraTurnRule()
        );
    }

    public void play() {
        boolean finished = false;
        LinkedList<Player> turnQueue = new LinkedList<>(players);

        while (!finished) {
            Player current = turnQueue.poll();
            System.out.println("\n" + current.getName() + "'s turn");

            boolean extraTurn = takeTurn(current);

            if (current.getPosition() == board.getWinningCell()) {
                System.out.println("\n" + current.getName() + " wins the game!");
                finished = true;
            } else {
                if (extraTurn) {
                    turnQueue.addFirst(current); // Add to front for immediate extra turn
                } else {
                    turnQueue.offer(current); // Add to back for normal turn order
                }
            }
            printBoardStatus();
        }
    }

    private boolean takeTurn(Player player) {
        List<Integer> rolls = diceSet.rollAll();
        int sum = rolls.stream().mapToInt(Integer::intValue).sum();

        int initialPos = player.getPosition() + sum;
        if (initialPos > board.getWinningCell()) {
            System.out.println(player.getName() + " rolled " + rolls + " -> overshoot, staying at " + player.getPosition());
            // Check for extra turn rule even when overshooting
            return applyExtraTurnRule(player, rolls);
        }

        System.out.println(player.getName() + " rolled " + rolls + " -> landed on " + initialPos);
        int finalPos = board.getNextPosition(initialPos);

        positionMap.remove(player.getPosition());
        player.setPosition(finalPos);
        positionMap.put(finalPos, player);

        if (finalPos != initialPos) {
            System.out.println(player.getName() + " final position: " + finalPos);
        }

        // Apply all rules
        return applyRules(player, rolls);
    }

    private boolean applyRules(Player player, List<Integer> rolls) {
        boolean extraTurn = false;
        for (Rule rule : rules) {
            if (rule.apply(player, rolls, board, positionMap)) {
                extraTurn = true;
            }
        }
        return extraTurn;
    }

    private boolean applyExtraTurnRule(Player player, List<Integer> rolls) {
        // Only apply extra turn rule when overshooting
        for (Rule rule : rules) {
            if (rule instanceof ExtraTurnRule && rule.apply(player, rolls, board, positionMap)) {
                return true;
            }
        }
        return false;
    }

    private void printBoardStatus() {
        System.out.println("Board Status:");
        for (Player p : players) {
            System.out.println(p.getName() + " is at " + p.getPosition());
        }
    }
}
