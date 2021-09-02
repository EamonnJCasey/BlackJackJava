package no.nav.domain;

import java.util.ArrayList;

public class Player {
    public boolean isHuman;
    public String name;
    public int score;
    public ArrayList<CardInDeck> hand;
    public PlayType playType;

    public Player(boolean isHuman, String name) {
        this.isHuman = isHuman;
        this.name = name;

        this.score = 0;
        this.hand = new ArrayList<CardInDeck>();
        this.playType = PlayType.DEAL;
    }
}
