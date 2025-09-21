package snakeladder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsecutiveSixRule implements Rule {
    private Map<Player, Integer> consecutiveSixCount;
    private static Player playerDeniedExtraTurn = null; // Static field to communicate with ExtraTurnRule
    
    public ConsecutiveSixRule() {
        this.consecutiveSixCount = new HashMap<>();
    }
    
    public static boolean isPlayerDeniedExtraTurn(Player player) {
        return playerDeniedExtraTurn == player;
    }
    
    public static void clearDeniedExtraTurn() {
        playerDeniedExtraTurn = null;
    }
    
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        // Check if the player rolled at least one 6
        boolean rolledSix = rolls.contains(6);
        
        if (rolledSix) {
            // Increment consecutive 6s count for this player
            int currentCount = consecutiveSixCount.getOrDefault(player, 0);
            currentCount++;
            consecutiveSixCount.put(player, currentCount);
            
            System.out.println(player.getName() + " has rolled " + currentCount + " consecutive 6s");
            
            // If player has rolled 3 consecutive 6s, send them back to position 1
            if (currentCount >= 3) {
                System.out.println(player.getName() + " rolled 3 consecutive 6s! Sent back to position 1!");
                
                // Remove player from current position
                positionMap.remove(player.getPosition());
                
                // Set player position to 1
                player.setPosition(1);
                positionMap.put(1, player);
                
                // Reset the consecutive 6s count
                consecutiveSixCount.put(player, 0);
                
                // Mark this player as denied extra turn for this round
                playerDeniedExtraTurn = player;
                
                return false; // No extra turn when sent back to position 1
            }
        } else {
            // Reset consecutive 6s count if they didn't roll a 6
            consecutiveSixCount.put(player, 0);
        }
        
        return false; // This rule doesn't provide extra turns
    }
}