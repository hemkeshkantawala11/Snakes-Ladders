package snakeladder;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter board size (n for n*n): ");
        int n = sc.nextInt();

        System.out.print("Enter number of dice: ");
        int diceCount = sc.nextInt();

        System.out.print("Enter number of players (2-6): ");
        int totalPlayers = sc.nextInt();
        if (totalPlayers < 2 || totalPlayers > 6) {
            throw new IllegalArgumentException("Players must be between 2 and 6");
        }

        System.out.print("Enter number of human players: ");
        int humanCount = sc.nextInt();
        int botCount = totalPlayers - humanCount;

        Board board = new Board(n);

        // Automatically generate n-3 snakes and n-3 ladders
        System.out.println("Generating " + (n-3) + " snakes and " + (n-3) + " ladders randomly...");
        board.generateRandomEntities();
        System.out.println("Board setup complete!");
        board.printBoardEntities();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= humanCount; i++) {
            players.add(PlayerFactory.createHuman("Human" + i));
        }
        for (int i = 1; i <= botCount; i++) {
            players.add(PlayerFactory.createBot("Bot" + i, new RandomBotStrategy()));
        }

        DiceSet diceSet = new DiceSet(diceCount, 6);
        GameEngine engine = new GameEngine(board, diceSet, players);
        engine.play();
    }
}
