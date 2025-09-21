package snakeladder;

import java.util.List;
import java.util.Map;

public class KickOutRule implements Rule {
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        // KickOut logic is now handled directly in GameEngine for better timing
        // This rule is kept for consistency but doesn't need to do anything
        return false; // This rule doesn't grant extra turns
    }
}
