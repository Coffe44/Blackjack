# Blackjack

A simple graphical Blackjack game written in Java Swing.

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

- Enter starting chip amounts for the player and the house when the game opens.
- Set the bet before starting each new round.
- Click `Hit` to draw another card.
- Click `Stand` to end your turn and let the dealer play.
- Click `New Round` after a round ends.

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
- `BlackjackFrame` contains the graphical interface.
- `BlackjackTable` contains the game state and round logic.
- `Deck`, `Card`, `Hand`, `Rank`, and `Suit` model the cards and scoring.
