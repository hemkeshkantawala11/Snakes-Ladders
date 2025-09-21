package snakeladder;

import java.util.List;
import java.util.Map;


public class ExtraTurnRule implements Rule {
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        // Check if this player is denied an extra turn (due to 3 consecutive 6s)
        if (ConsecutiveSixRule.isPlayerDeniedExtraTurn(player)) {
            System.out.println(player.getName() + " rolled a 6 but no extra turn due to 3 consecutive 6s penalty!");
            ConsecutiveSixRule.clearDeniedExtraTurn(); // Clear the denial for next turn
            return false;
        }
        
        if (rolls.contains(6)) {
            System.out.println(player.getName() + " rolled a 6! Gets another turn!");
            return true;
        }
        return false;
    }
}
