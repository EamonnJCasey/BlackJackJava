package no.nav.domain;

/*
 * This is a single card in the deck.
 * It is used when getting the shuffeled deck from the Service
 * It is also used to keep track of what the player has in their hand.
 * We will be using a simple ArrayList<CardInDeck> for both.
 */
public class CardInDeck {
    // From the data source.
    public String suit;
    public String value;

    // A one time map from Suit to Value when getting a new deck
    // so I do not have to do that every time during game play.
    public Integer points;
}
