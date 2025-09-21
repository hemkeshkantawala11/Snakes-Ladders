package snakeladder;

import java.util.*;

public class DiceSet {
    private final List<StandardDice> diceList;

    public DiceSet(int count, int faces) {
        this.diceList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            diceList.add(new StandardDice(faces));
        }
    }

    public List<Integer> rollAll() {
        List<Integer> results = new ArrayList<>();
        for (StandardDice dice : diceList) {
            results.addAll(dice.roll());
        }
        return results;
    }
}
