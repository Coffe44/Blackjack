# Blackjack

A simple console-based Blackjack game written in Java.

## Requirements

- Java 21 or newer
- Maven, or the included Maven wrapper

## Build

Use the Maven wrapper from the project root:

```powershell
.\mvnw.cmd package
```

On macOS or Linux:

```bash
./mvnw package
```

## Run

After building, run the generated jar:

```powershell
java -jar target\blackjack-1.0.0.jar
```

On macOS or Linux:

```bash
java -jar target/blackjack-1.0.0.jar
```

## How to Play

The goal is to get closer to 21 than the dealer without going over.

- Type `h` or `hit` to draw another card.
- Type `s` or `stand` to end your turn.
- Type `y` or `n` after a round to play again or quit.

## Rules

- Number cards are worth their face value.
- Jacks, queens, and kings are worth 10.
- Aces count as 11 unless that would put the hand over 21, then they count as 1.
- The dealer draws until reaching at least 17.
- The player wins by having a higher hand than the dealer without busting.
- If both hands have the same value, the round is a push.

## Project Structure

```text
src/main/java/org/example/blackjack
```

Main classes:

- `BlackjackApplication` starts the program.
- `BlackjackGame` contains the game loop and round logic.
- `Deck`, `Card`, `Hand`, `Rank`, and `Suit` model the cards and scoring.
