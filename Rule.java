package snakeladder;

import java.util.*;

public interface Rule {
    boolean apply(Player player, List<Integer> rolls, Board board, 
                  Map<Integer, Player> positionMap);
}
