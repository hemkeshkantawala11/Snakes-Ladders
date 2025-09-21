package snakeladder;

public interface Player {
    String getName();
    int getPosition();
    void setPosition(int pos);
    boolean isBot();
}
