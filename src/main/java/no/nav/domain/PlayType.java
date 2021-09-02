package no.nav.domain;

public enum PlayType {
    DEAL("Deal", true),
    BUST("Bust", false),
    STICK("Stick", false);

    String name;
    Boolean inPlay;

    private String playTypeName() { return name; }
    private Boolean inPlay() { return inPlay; }
    
    PlayType(String name, Boolean inPlay) {
        this.name = name;
        this.inPlay = inPlay;
    }
}
