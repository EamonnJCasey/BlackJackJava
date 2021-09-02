package no.nav.utility;

/*
 * Small utility class to get the next new shuffled deck.
 * We could use Spring / Interfaces to enable swapping data sources.
 * We could use data-driven configuration for the data source.
 * Jackson is used because it seems to be very popular. Community and delivery cadence.
 *
 * TODO:
 *  Add retry
 */

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nav.config.Configuration;
import no.nav.config.ValueMap;
import no.nav.domain.CardInDeck;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class GetDeck {
    /*
    Get a newly shuffled deck of cards from the data source.
     */
    static public ArrayDeque<CardInDeck> GetNewDeck(Configuration config) {
        ArrayDeque<CardInDeck> shuffeledDeck;
        HttpResponse<String> response = null;
        CardInDeck[] arrayDeck = null;
        ObjectMapper objectMapper;

        shuffeledDeck = new ArrayDeque<>();
        objectMapper = new ObjectMapper();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(config.shuffleURI))
                .header("accept", "application/json")
                .build();

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        try {
            arrayDeck = objectMapper.readValue(response.body(), CardInDeck[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (null != arrayDeck) {
            Collections.addAll(shuffeledDeck, arrayDeck);

            /* 2. Go through the config and set the points for each suit with the same value.
                  This is another example how Stream API is more complex then it should be.
                  .collect(Collectors.toList() -- C# does this for you.
             */
            for (ValueMap valueMap : config.valueMap) {
                List<CardInDeck> cardsOfSameValue = shuffeledDeck.stream().filter(
                        c -> c.value.equalsIgnoreCase(valueMap.value)
                ).collect(Collectors.toList());
                for (CardInDeck card : cardsOfSameValue) {
                    card.points = valueMap.points;
                }
            }
        }

        return shuffeledDeck;
    }
}
