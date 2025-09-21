package snakeladder;

public interface Entity {
    int getStart();
    int getEnd();
    String getType();
    int handleMovement(int currentPosition);
}
