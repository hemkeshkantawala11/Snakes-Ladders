package snakeladder;

import java.util.*;

public class GameEngine {
    private final Board board;
    private final DiceSet diceSet;
    private final List<Player> players;
    private final Map<Integer, Player> positionMap;
    private final List<Rule> rules;
    private final Scanner scanner;
    private boolean useManualDice; // true = manual input, false = random rolls

    public GameEngine(Board board, DiceSet diceSet, List<Player> players) {
        this.board = board;
        this.diceSet = diceSet;
        this.players = players;
        this.positionMap = new HashMap<>();
        this.rules = List.of(
                new ExactWinRule(),
                new KickOutRule(),
                new ConsecutiveSixRule(),
                new ExtraTurnRule()
        );
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        // Setup dice mode preference before starting the game
        setupDiceMode();
        
        boolean finished = false;
        LinkedList<Player> turnQueue = new LinkedList<>(players);

        while (!finished) {
            Player current = turnQueue.poll();
            System.out.println("\n" + current.getName() + "'s turn");
            
            // Only wait for Enter if using random dice (bots always, humans in random mode)
            if (current.isBot() || !useManualDice) {
                waitForUserInput(current.getName());
            }

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
        
        // Close scanner when game ends
        scanner.close();
    }

    private boolean takeTurn(Player player) {
        List<Integer> rolls;
        
        if (player.isBot()) {
            // Bots always use random dice rolls
            rolls = diceSet.rollAll();
        } else {
            // Human players use the selected dice mode
            if (useManualDice) {
                rolls = getUserDiceInput(player);
            } else {
                rolls = diceSet.rollAll();
            }
        }
        
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
        
        // Check if there's already a player at the final position before adding the current player
        if (positionMap.containsKey(finalPos)) {
            Player existingPlayer = positionMap.get(finalPos);
            // Remove the existing player and kick them back to start
            positionMap.remove(finalPos);
            existingPlayer.setPosition(1);
            positionMap.put(1, existingPlayer);
            System.out.println(existingPlayer.getName() + " was kicked back to start by " + player.getName() + "!");
        }
        
        // Now add the current player to their position
        positionMap.put(finalPos, player);

        if (finalPos != initialPos) {
            System.out.println(player.getName() + " final position: " + finalPos);
        }

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

    private void waitForUserInput(String playerName) {
        System.out.print("Press Enter for " + playerName + " to roll the dice...");
        scanner.nextLine();
    }

    private List<Integer> getUserDiceInput(Player player) {
        List<Integer> userRolls = new ArrayList<>();
        
        // Get the number of dice from diceSet
        int diceCount = diceSet.getDiceCount();
        
        for (int i = 0; i < diceCount; i++) {
            int diceValue = 0;
            boolean validInput = false;
            
            while (!validInput) {
                try {
                    if (diceCount == 1) {
                        System.out.print(player.getName() + ", enter your dice number (1-6): ");
                    } else {
                        System.out.print(player.getName() + ", enter dice " + (i + 1) + " number (1-6): ");
                    }
                    
                    diceValue = Integer.parseInt(scanner.nextLine().trim());
                    
                    if (diceValue >= 1 && diceValue <= 6) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input! Please enter a number between 1 and 6.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please enter a valid number between 1 and 6.");
                }
            }
            
            userRolls.add(diceValue);
        }
        
        return userRolls;
    }

    private void setupDiceMode() {
        System.out.println("\n=== GAME SETUP ===");
        System.out.println("Choose dice rolling mode for human players:");
        System.out.println("1. Random dice rolls (traditional)");
        System.out.println("2. Manual input (choose your dice numbers 1-6)");
        
        boolean validChoice = false;
        while (!validChoice) {
            try {
                System.out.print("Enter your choice (1 or 2): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                if (choice == 1) {
                    useManualDice = false;
                    System.out.println("✓ Random dice rolls selected for human players");
                    validChoice = true;
                } else if (choice == 2) {
                    useManualDice = true;
                    System.out.println("✓ Manual dice input selected for human players");
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice! Please enter 1 or 2.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter 1 or 2.");
            }
        }
        System.out.println("==================\n");
    }
}
