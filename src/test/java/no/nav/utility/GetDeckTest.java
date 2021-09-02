package no.nav.utility;

import junit.framework.TestCase;
import no.nav.config.ConfigReader;
import no.nav.config.Configuration;
import no.nav.domain.CardInDeck;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetDeckTest extends TestCase {

    public void testGetNewDeck() {
        Configuration config = ConfigReader.GetConfiguration();
        ArrayDeque<CardInDeck> deck = GetDeck.GetNewDeck(config);

        // Some sanity check on the return list..
        assertEquals(deck.size(), 52);

        List<CardInDeck> cardsInSuit = deck
                .stream().filter(cards -> cards.suit.equalsIgnoreCase("SPADES"))
                .collect(Collectors.toList());
        assertEquals(cardsInSuit.size(), 13);

        cardsInSuit = deck
                .stream().filter(cards -> cards.value.equalsIgnoreCase("J"))
                .collect(Collectors.toList());
        assertEquals(cardsInSuit.size(), 4);
    }
}