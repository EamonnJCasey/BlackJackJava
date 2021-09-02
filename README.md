# BlackJackJava
This project is a simple implementation av Black Jack written in Java.
## The Task
Plays a round of Black Jack. 2 players. 1 person. 1 computer.
1. Deal both players two cards to start the game.
2. Deal one card to Human until Stick or Bust.
3. Deal one card to Computer until Stick or Bust.
4. Show who has won.
- If anyone gets Black Jack the game will stop.
- If Human goes Bust the Computer wins.
## Environment
The development environment includes:
- IntelliJ IDEA 2021.1.3 (Community Edition)![image](https://user-images.githubusercontent.com/4331600/131847351-174b48a0-233e-42c2-bd8a-60bc4e7f4690.png)
- Java version "16.0.1" 2021-04-20![image](https://user-images.githubusercontent.com/4331600/131847293-2d518a56-7a95-49c4-941c-d0db8244f8b7.png)
- Jackson v2.12.4
- Maven JUint v4.11
## Structure
\src - Contains both code and test cases.  
App.java - Contains the main() entry point.  
Game.java - This is thegame logic.  
\config - Reads the configuration. **NB:** Currently the configuration is hard-coded. The intent was to use a JSON file under \resources.
\domain - Data classes used in the game.
\utility - Plumbing code like getting a new shuffled desk from the server.
