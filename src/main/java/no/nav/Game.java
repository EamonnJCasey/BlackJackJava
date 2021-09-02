package no.nav;

import no.nav.config.ConfigReader;
import no.nav.config.Configuration;
import no.nav.domain.CardInDeck;
import no.nav.domain.PlayType;
import no.nav.domain.Player;
import no.nav.utility.GetDeck;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static java.util.Objects.isNull;

/**
 * Plays a game of Black Jack (21).
 */
public class Game {
    /**
     * Ask the user if they wants another card or not.
     * <p>
     * JavaScript does not have APIs for user input from the console.
     * So Node.js APIs are added to the dependencies.
     *
     * @returns (S)tick or (D)eal.
     */
    private char askStickOrDeal() {
        char answer;

        Scanner scanner = new Scanner(System.in);

        do {
            scanner.reset();
            scanner.useLocale(Locale.forLanguageTag("nb-NO"));

            System.out.println("What would you like to do: (S)tick, (D)eal? ");
            answer = scanner.next().charAt(0);
            scanner.close();

        } while (answer != 'D' && answer != 'S');

        return answer;
    }

    /**
     * Display the current state of the game.
     *
     * @param players The list of all players.
     * @param message An option message to display.
     */
    private void printState(ArrayList<Player> players, @Nullable String message) {
        String formattedHand;
        StringBuilder playerState = new StringBuilder();

        System.out.println("=".repeat(30));

        for (Player player : players) {
            formattedHand = getFormattedHand(player.hand);
            playerState.setLength(0);
            playerState.append("Player: ")
                    .append(String.format("%-15s", player.name))
                    .append(player.score)
                    .append(" - ").append(player.playType.name())
                    .append(" - ").append(formattedHand);

            System.out.println(playerState);
        }

        if (message != null) {
            System.out.println();
            System.out.println(message);
            System.out.println();
        }

        System.out.println("=".repeat(30));
    }

    /**
     * Finds the Player with the highest value valid hand.
     *
     * @param players A list of players in the game.
     * @returns Player with the highest hand or "undefined".
     */
    private Player getHighestHand(ArrayList<Player> players) {
        Player highestHand = null;

        for (Player player : players) {
            // If the player is not over 21 then evaluate it as possible highest hand.
            if (player.playType != PlayType.BUST) {
                if (isNull(highestHand)) {
                    highestHand = player;

                } else {
                    if (player.score <= 21 && player.score > highestHand.score) {
                        highestHand = player;
                    }
                }
            }
        }
        return highestHand;
    }

    /**
     * Format a list of cards into a string to make it easier for user to read.
     *
     * @param hand A list of cards the player is holding.
     * @returns Returns the list of cards as a formatted string.
     */
    private String getFormattedHand(ArrayList<CardInDeck> hand) {
        StringBuilder formattedHand = new StringBuilder();

        for (CardInDeck card : hand) {
            formattedHand.append(" [")
                    .append(card.value)
                    .append(" ")
                    .append(card.suit.charAt(0))
                    .append("]");
        }

        return formattedHand.toString().trim();
    }

    /**
     * Plays a round of Black Jack.
     * 1. Deal two players two cards to start the game.
     * 2. Deal one card to Me until I want to stop or I go Bust.
     * 3. Deal one card to Computer until it wants to stop or go Bust.
     * 4. Show who has won.
     * - If one gets Black Jack the game will stop.
     * - If Me goes Bust Computer wins.
     */
    public void playGame() {
        ArrayDeque<CardInDeck> deckOfCards;
        ArrayList<Player> players;
        Player highestHand;
        CardInDeck card;
        Random rand;

        rand = new Random();

        // Read the game configuration
        Configuration config = ConfigReader.GetConfiguration();

        // Get a new shuffle
        deckOfCards = GetDeck.GetNewDeck(config);

        // Setup the players
        players = new ArrayList<>();
        players.add(new Player(true, "Eamonn J."));
        players.add(new Player(false, "Magnus"));

        // 2. Start the game.
        // Everyone gets 2 cards.
        for (Player player : players) {
            card = deckOfCards.poll();
            if (card != null) {
                player.hand.add(card);
                player.score += card.points;
            }

            card = deckOfCards.poll();
            if (card != null) {
                player.hand.add(card);
                player.score += card.points;
            }
        }

        // 3. Show the start position.
        printState(players, null);

        highestHand = getHighestHand(players);
        if (highestHand.score != 21) {
            char response; // Stick, Deal.

            // 4. Start dealing cards.
            // Keep giving cards until IStick or Bust.
            for (Player player : players) {

                // Show players current state
                System.out.println();
                System.out.println("-".repeat(30));
                System.out.println("Player: " + player.name);
                System.out.println();
                System.out.println("  Hand: " + getFormattedHand(player.hand));
                System.out.println(" Score: " + player.score + " - " + player.playType.name());

                while (player.playType == PlayType.DEAL) {

                    response = 'D'; // Default to Deal.

                    if (player.isHuman) {
                        // Get the choice from the human.
                        do {
                            response = askStickOrDeal();
                        } while (response != 'D' && response != 'S');

                    } else {
                        // Get the choice from the machine.
                        if (player.score == 21) {
                            // Definitely want no more cards.
                            response = 'S';
                        } else if (player.score >= 17) {
                            // Stick 70 % of the time.
                            if (rand.nextInt(10) >= 3) {
                                response = 'S';
                            }
                        }
                    }

                    // Deal the card or stick.
                    if (response == 'D') {
                        card = deckOfCards.poll();
                        if (card != null) {
                            player.hand.add(card);
                            player.score += card.points;
                        }
                    } else if (response == 'S') {
                        player.playType = PlayType.STICK;
                    }

                    // Game over for this player.
                    if (player.score > 21) {
                        player.playType = PlayType.BUST;
                    }

                    System.out.println();
                    System.out.println("  Hand: " + getFormattedHand(player.hand));
                    System.out.println(" Score: " + player.score + " - " + player.playType.name());

                }

            }
        }

        // 6. Game over. Show state and winner.

        System.out.println();

        highestHand = getHighestHand(players);
        if (highestHand == null) {
            // No winners. All went bust.
            printState(players, "No winner. All went bust!");
        } else {
            if (highestHand.score == 21) {
                printState(players, "BLACK JACK: " + String.format("%1$19s", highestHand.name));

            } else {
                printState(players, "WINNER: " + String.format("%1$19s", highestHand.name));
            }
        }
        System.out.println();
        System.out.println("Game over !!!");
    }
}
