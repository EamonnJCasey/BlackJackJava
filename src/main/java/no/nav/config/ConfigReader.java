package no.nav.config;

import java.util.ArrayList;

// TODO: Use a configuration file instead of hard-coding.
public class ConfigReader {
    static public <value> Configuration GetConfiguration() {
        //ObjectMapper objectMapper = new ObjectMapper();
        Configuration config = new Configuration();
        config.shuffleURI = "http://nav-deckofcards.herokuapp.com/shuffle";

        config.valueMap = new ArrayList<ValueMap>() {{
            add(new ValueMap("1", 1));
            add(new ValueMap("2", 2));
            add(new ValueMap("3", 3));
            add(new ValueMap("4", 4));
            add(new ValueMap("5", 5));
            add(new ValueMap("6", 6));
            add(new ValueMap("7", 7));
            add(new ValueMap("8", 8));
            add(new ValueMap("9", 9));
            add(new ValueMap("10", 10));
            add(new ValueMap("J", 10));
            add(new ValueMap("Q", 10));
            add(new ValueMap("K", 10));
            add(new ValueMap("A", 11));
        }};

        /*
        try {
            config = objectMapper.readValue(Paths.get("ComeOnBlackJack.json").toFile(), Configuration.class);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return config;
    }
}
