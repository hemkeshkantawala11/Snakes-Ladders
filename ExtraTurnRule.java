package snakeladder;

import java.util.List;
import java.util.Map;


public class ExtraTurnRule implements Rule {
    @Override
    public boolean apply(Player player, List<Integer> rolls, Board board, 
                         Map<Integer, Player> positionMap) {
        if (rolls.contains(6)) {
            System.out.println(player.getName() + " rolled a 6! Gets another turn!");
            return true;
        }
        return false;
    }
}
