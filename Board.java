package snakeladder;

import java.util.*;

public class Board {
    private final int size;
    private final int winningCell;
    private final Map<Integer, Entity> entities;
    private final Random random;

    public Board(int size) {
        this.size = size;
        this.winningCell = size * size;
        this.entities = new HashMap<>();
        this.random = new Random();
    }

    public void addSnake(Snake snake) {
        validateEntity(snake);
        entities.put(snake.getStart(), snake);
    }

    public void addLadder(Ladder ladder) {
        validateEntity(ladder);
        entities.put(ladder.getStart(), ladder);
    }

    private void validateEntity(Entity entity) {
        if (entity.getStart() <= 1 || entity.getEnd() <= 1 || 
            entity.getStart() >= winningCell || entity.getEnd() >= winningCell) {
            throw new IllegalArgumentException("Entity cannot start or end on start/end cells.");
        }
        if (entities.containsKey(entity.getStart())) {
            throw new IllegalArgumentException("Cell already occupied by another entity.");
        }
    }

    public int getNextPosition(int currentPosition) {
        if (entities.containsKey(currentPosition)) {
            Entity entity = entities.get(currentPosition);
            return entity.handleMovement(currentPosition);
        }
        return currentPosition;
    }

    public int getWinningCell() {
        return winningCell;
    }

    public void generateRandomEntities() {
        int entityCount = size - 3; 
        
        for (int i = 0; i < entityCount; i++) {
            generateRandomSnake();
        }
        
        for (int i = 0; i < entityCount; i++) {
            generateRandomLadder();
        }
    }

    private void generateRandomSnake() {
        int maxAttempts = 100; 
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int minStart = size + 2; // At least second row
            int maxStart = winningCell - 2; // Not on the last row
            int start = random.nextInt(maxStart - minStart + 1) + minStart;
            
            int minEnd = 2;
            int maxEnd = Math.min(start - 5, winningCell - 1);
            
            if (maxEnd < minEnd) continue; 
            
            int end = random.nextInt(maxEnd - minEnd + 1) + minEnd;
            
            if (!entities.containsKey(start) && !entities.containsKey(end) && 
                start != end && !wouldCreateCycle(start, end)) {
                try {
                    addSnake(new Snake(start, end));
                    return;
                } catch (IllegalArgumentException e) {
                }
            }
        }
    }

    private void generateRandomLadder() {
        int maxAttempts = 100;
        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            // Ladder start should be in lower part of board
            int minStart = 2;
            int maxStart = winningCell - size - 2; 
            int start = random.nextInt(maxStart - minStart + 1) + minStart;
            
            
            int minEnd = start + 5;
            int maxEnd = winningCell - 1;
            
            if (minEnd > maxEnd) continue; 
            
            int end = random.nextInt(maxEnd - minEnd + 1) + minEnd;
            
            
            if (!entities.containsKey(start) && !entities.containsKey(end) && 
                start != end && !wouldCreateCycle(start, end)) {
                try {
                    addLadder(new Ladder(start, end));
                    return; 
                } catch (IllegalArgumentException e) {
                }
            }
        }
    }

    private boolean wouldCreateCycle(int start, int end) {
        Set<Integer> visited = new HashSet<>();
        int current = end;
        
        while (entities.containsKey(current) && !visited.contains(current)) {
            visited.add(current);
            current = entities.get(current).getEnd();
            if (current == start) {
                return true; 
            }
        }
        return false;
    }

    public void printBoardEntities() {
        System.out.println("\nBoard Entities:");
        int snakeCount = 0, ladderCount = 0;
        for (Map.Entry<Integer, Entity> entry : entities.entrySet()) {
            Entity entity = entry.getValue();
            String type = entity.getType();
            if ("Snake".equals(type)) {
                snakeCount++;
            } else if ("Ladder".equals(type)) {
                ladderCount++;
            }
            System.out.println(type + ": " + entry.getKey() + " -> " + entity.getEnd());
        }
        System.out.println("Total: " + snakeCount + " snakes, " + ladderCount + " ladders");
    }
}
