# 🐍🪜 Snake and Ladder Game

A comprehensive console-based Snake and Ladder game implemented in Java with advanced features and Object-Oriented Design principles.

## 📋 Table of Contents

- [Features](#features)
- [Game Rules](#game-rules)
- [Architecture](#architecture)
- [Getting Started](#getting-started)
- [How to Play](#how-to-play)
- [Design Patterns](#design-patterns)
- [Project Structure](#project-structure)
- [Advanced Features](#advanced-features)
- [Example Gameplay](#example-gameplay)

## ✨ Features

### Core Game Features
- **Dynamic Board Size**: Customizable n×n board (user-defined)
- **Multiple Dice Support**: Configure 1 or more dice for gameplay
- **Mixed Player Types**: Support for both human and AI bot players (2-6 players)
- **Auto-Generated Board**: Automatically generates (n-3) snakes and (n-3) ladders with cycle prevention
- **Interactive Gameplay**: Turn-based gameplay with user confirmations

### Advanced Dice Features
- **Dual Dice Modes**: 
  - Random dice rolls (traditional)
  - Manual input mode (choose your dice numbers 1-6)
- **Smart Bot Handling**: Bots always use random dice regardless of mode selection

### Game Rules Implementation
- **Extra Turn Rule**: Roll a 6 → Get another turn
- **Consecutive 6s Penalty**: Roll 3 consecutive 6s → Sent back to position 1 (no extra turn)
- **KickOut Rule**: Land on occupied space → Existing player gets kicked back to start
- **Exact Win Rule**: Must land exactly on winning cell (overshoot = stay in place)

### User Experience Features
- **Turn Control**: Press Enter to proceed with each turn (for humans in random mode)
- **Real-time Board Status**: Shows all player positions after each turn
- **Informative Messages**: Clear feedback for snakes, ladders, special rules, and game events
- **Input Validation**: Robust error handling for all user inputs

## 🎮 Game Rules

### Basic Movement
1. Players start at position 1
2. Roll dice and move forward by the sum
3. Landing on a **snake head** → Slide down to snake tail
4. Landing on a **ladder bottom** → Climb up to ladder top
5. First player to reach the final cell wins

### Special Rules
- **🎲 Extra Turn**: Rolling a 6 grants an additional turn
- **⚠️ Three 6s Penalty**: Rolling 3 consecutive 6s sends you back to position 1
- **👢 KickOut**: Landing on another player's position sends them back to start
- **🎯 Exact Win**: Must land exactly on the final cell to win (no overshooting)

## 🏗️ Architecture

The project follows **SOLID principles** and implements several design patterns:

### Core Components

#### Entity System (Polymorphism)
```java
Entity interface → Snake, Ladder implementations
- handleMovement(): Polymorphic behavior for different entities
```

#### Player System (Strategy Pattern)
```java
Player interface → HumanPlayer, BotPlayer
BotStrategy interface → RandomBotStrategy (extensible for different AI types)
```

#### Rule Engine (Rule Pattern)
```java
Rule interface → ExtraTurnRule, ConsecutiveSixRule, KickOutRule, ExactWinRule
- Modular rule application with configurable order
```

#### Game Components
- **Board**: Manages entities and position validation
- **GameEngine**: Core game loop and rule orchestration  
- **DiceSet**: Handles multiple dice configuration
- **PlayerFactory**: Creates players using Factory pattern

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd snakeladder
   ```

2. **Compile the project**
   ```bash
   javac *.java
   ```

3. **Run the game**
   ```bash
   java Main
   ```

## 🎯 How to Play

### Game Setup
1. **Board Size**: Enter `n` for an n×n board (e.g., 10 for 10×10 = 100 cells)
2. **Dice Count**: Choose number of dice (typically 1 or 2)
3. **Players**: Set total players (2-6) and specify human vs bot count
4. **Dice Mode**: Choose between:
   - **Random rolls** (traditional gameplay)
   - **Manual input** (choose your dice numbers for humans)

### During Gameplay
- **Random Mode**: Press Enter when prompted to roll dice
- **Manual Mode**: Enter dice numbers (1-6) when prompted
- Watch for snakes 🐍, ladders 🪜, and special rule messages
- First to reach the final cell wins! 🏆

### Example Session
```
Enter board size (n for n*n): 10
Enter number of dice: 1
Enter number of players (2-6): 2
Enter number of human players: 2

Generating 7 snakes and 7 ladders randomly...
Board setup complete!

=== GAME SETUP ===
Choose dice rolling mode for human players:
1. Random dice rolls (traditional)
2. Manual input (choose your dice numbers 1-6)
Enter your choice (1 or 2): 1

Human1's turn
Press Enter for Human1 to roll the dice...
Human1 rolled [4] -> landed on 5
Human1 final position: 5
```

## 🎨 Design Patterns Used

### 1. **Strategy Pattern**
- `BotStrategy` interface with `RandomBotStrategy`
- Allows easy addition of different AI difficulties

### 2. **Factory Pattern**  
- `PlayerFactory` for creating human/bot players
- Centralizes player creation logic

### 3. **Rule Pattern**
- `Rule` interface with multiple implementations
- Modular and extensible rule system

### 4. **Polymorphism (OCP Compliance)**
- `Entity` interface for Snake/Ladder behavior
- `Player` interface for Human/Bot behavior

### 5. **Template Method**
- `GameEngine` orchestrates the game flow
- Consistent turn structure with varied implementations

## 📁 Project Structure

```
snakeladder/
├── Main.java                  # Entry point and game setup
├── GameEngine.java           # Core game loop and logic
├── Board.java               # Board management and entity generation
├── GameConfig.java          # Configuration management
│
|
│── Entity.java          # Entity interface (OCP compliance)
│── Snake.java          # Snake implementation
│── Ladder.java         # Ladder implementation
│
|
│── Player.java         # Player interface
│── HumanPlayer.java    # Human player implementation  
│── BotPlayer.java      # Bot player implementation
│── PlayerFactory.java  # Factory for player creation
│── BotStrategy.java    # Strategy interface for bots
│── RandomBotStrategy.java # Random AI strategy
│
│── Dice.java           # Dice interface
│── StandardDice.java   # Standard 6-sided dice
│── DiceSet.java        # Multiple dice management
│
|
├── Rule.java           # Rule interface
├── ExtraTurnRule.java  # Extra turn on rolling 6
├── ConsecutiveSixRule.java # 3 consecutive 6s penalty
├── KickOutRule.java    # Player collision handling
└── ExactWinRule.java   # Exact landing win condition
```

## 🔥 Advanced Features

### 1. **Anti-Cycle Entity Generation**
- Prevents infinite loops in snake/ladder chains
- Smart placement algorithm with conflict resolution

### 2. **Intelligent Rule Ordering**
```java
rules = [ExactWinRule, KickOutRule, ConsecutiveSixRule, ExtraTurnRule]
```
- Rules applied in strategic order for proper game flow

### 3. **Dynamic Turn Management**
- `LinkedList` queue for proper extra turn handling
- Immediate vs. queued turn insertion based on rule outcomes

### 4. **State Communication Between Rules**
- `ConsecutiveSixRule` communicates with `ExtraTurnRule`
- Prevents extra turns when 3-6s penalty is applied

### 5. **Comprehensive Input Validation**
- Number format validation
- Range checking (1-6 for dice, 2-6 for players)
- Graceful error handling with retry prompts

## 🎬 Example Gameplay

```
Human1's turn
Press Enter for Human1 to roll the dice...
Human1 rolled [6] -> landed on 15
Great! Found a ladder at 15, climbing up to 28
Human1 final position: 28
Human1 rolled a 6! Gets another turn!

Human1's turn  
Human1 rolled [6] -> landed on 34
Human1 has rolled 2 consecutive 6s
Human1 rolled a 6! Gets another turn!

Human1's turn
Human1 rolled [6] -> landed on 40  
Human1 has rolled 3 consecutive 6s
Human1 rolled 3 consecutive 6s! Sent back to position 1!
Human1 rolled a 6 but no extra turn due to 3 consecutive 6s penalty!
Human1 final position: 1

Board Status:
Human1 is at 1
Human2 is at 23
```

## 🛠️ Future Enhancements

- **GUI Interface**: Java Swing/JavaFX implementation
- **Network Multiplayer**: Socket-based multiplayer support
- **Advanced AI**: Smarter bot strategies (aggressive, defensive)
- **Custom Board Themes**: Different visual themes
- **Statistics Tracking**: Player performance analytics
- **Save/Load Game**: Game state persistence

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🏆 Acknowledgments

- Implements classic Snake and Ladder game rules
- Demonstrates advanced Java OOP concepts
- Showcases clean architecture and design patterns
- Built with extensibility and maintainability in mind

---

**Enjoy the game! 🎮** May the dice be ever in your favor! 🎲