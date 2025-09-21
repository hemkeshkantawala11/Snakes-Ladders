package snakeladder;

import java.util.List;
import java.util.Map;

public class ExactWinRule implements Rule {
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        // This rule doesn't grant extra turns, it just validates exact win
        // The actual overshoot handling is done in GameEngine
        return false;
    }
}
