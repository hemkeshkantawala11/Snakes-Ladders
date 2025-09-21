package snakeladder;

public class GameConfig {
    private final int boardSize;
    private final int diceCount;

    public GameConfig(int boardSize, int diceCount) {
        this.boardSize = boardSize;
        this.diceCount = diceCount;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getDiceCount() {
        return diceCount;
    }
}
