package snakeladder;

import java.util.*;

public class StandardDice implements Dice {
    private final int faces;
    private final Random random;

    public StandardDice(int faces) {
        this.faces = faces;
        this.random = new Random();
    }

    @Override
    public List<Integer> roll() {
        return Collections.singletonList(random.nextInt(faces) + 1);
    }
}
