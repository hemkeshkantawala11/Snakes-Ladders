package snakeladder;

public class Ladder implements Entity {
    private final int start;
    private final int end;

    public Ladder(int start, int end) {
        if (start >= end) {
            throw new IllegalArgumentException("Ladder must go up (start < end)");
        }
        this.start = start;
        this.end = end;
    }

    @Override
    public int getStart() { return start; }
    
    @Override
    public int getEnd() { return end; }
    
    @Override
    public String getType() { return "Ladder"; }
    
    @Override
    public int handleMovement(int currentPosition) {
        System.out.println("Great! Found a ladder at " + currentPosition + ", climbing up to " + end);
        return end;
    }
}
