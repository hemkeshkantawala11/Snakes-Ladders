package snakeladder;

import java.util.List;
import java.util.Map;

public class KickOutRule implements Rule {
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        int currentPos = player.getPosition();
        
        // Check if there's another player at the current player's new position
        if (positionMap.containsKey(currentPos)) {
            Player existingPlayer = positionMap.get(currentPos);
            if (existingPlayer != player) {
                // Kick the existing player back to start
                positionMap.remove(currentPos);
                existingPlayer.setPosition(1);
                positionMap.put(1, existingPlayer);
                System.out.println(existingPlayer.getName() + " was kicked back to start by " + player.getName() + "!");
            }
        }
        return false; // This rule doesn't grant extra turns
    }
}
