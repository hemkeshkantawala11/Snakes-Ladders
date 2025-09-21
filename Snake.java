package snakeladder;

public class Snake implements Entity {
    private final int start;
    private final int end;

    public Snake(int start, int end) {
        if (start <= end) {
            throw new IllegalArgumentException("Snake must go down (start > end)");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public int getStart() { return start; }
    
    @Override
    public int getEnd() { return end; }
    
    @Override
    public String getType() { return "Snake"; }
    
    @Override
    public int handleMovement(int currentPosition) {
        System.out.println("Oops! Hit a snake at " + currentPosition + ", sliding down to " + end);
        return end;
    }
}
