package com.forbidden.island.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Treasure deck class, extends abstract Deck class, implements specific treasure card logic.
 */
public class TreasureDeck extends Deck{
    /** Currently drawn treasure cards */
    private final ArrayList<Integer> NTreasureCards;

    /**
     * Constructor, initializes treasure deck.
     * Adds treasure cards numbered 0-27 and shuffles them.
     * Cards numbered 25-27 typically correspond to "Water Rise" cards.
     */
    public TreasureDeck() {
        super(2);  // Initialize parent class, default to draw 2 cards
        NTreasureCards = new ArrayList<>();
        // Initialize treasure cards numbered 0-27
        for (int i = 0; i < 28; i++) {
            deck.add(i);
        }
        // Shuffle deck
        Collections.shuffle(deck);
    }

    /**
     * Draws the current required number of treasure cards (Num cards),
     * removes drawn cards from deck, and returns the list of drawn cards.
     * @return list of drawn treasure cards
     */
    public ArrayList<Integer> getCards() {
        // Check if deck has enough cards, replenish if necessary
        CheckAvailability(Num);

        // Clear current draw list, prepare to add newly drawn cards
        NTreasureCards.clear();

        // Take Num cards from top of deck
        NTreasureCards.addAll(deck.subList(0, Num));
        // Remove drawn cards from deck
        deck.subList(0, Num).clear();

        return NTreasureCards;
    }

    /**
     * Method for drawing treasure cards during initialization,
     * players won't draw "Water Rise" cards (numbered 25-27) during initial draw.
     * If Water Rise cards are drawn, they are discarded and deck is reshuffled.
     * @return list of initially drawn treasure cards (excluding Water Rise cards)
     */
    public ArrayList<Integer> getNoRiseCards() {
        // Check if deck has enough cards, replenish if necessary
        CheckAvailability(Num);
        NTreasureCards.clear();
        int count = 0;
        Iterator<Integer> iterator = deck.iterator();
        // Iterate through deck, skip Water Rise cards, collect other treasure cards until count reached
        while (iterator.hasNext()) {
            int treasureCard = iterator.next();
            if (treasureCard >= 25 && treasureCard <= 27) {
                // When encountering Water Rise card, discard directly
                discard(treasureCard);
            } else {
                // Collect normal treasure cards
                NTreasureCards.add(treasureCard);
                count++;
            }

            // Remove current card from deck regardless of collection
            iterator.remove();

            // Stop drawing when required card count is reached
            if (count >= Num) {
                break;
            }
        }

        // Put cards from discard pile back into deck and shuffle
        deck.addAll(discardPile);
        Collections.shuffle(deck);
        return NTreasureCards;
    }

    /**
     * Discard method, adds specified treasure card to discard pile.
     * @param treasureID ID of treasure card to be discarded
     */
    public void discard(int treasureID) {
        discardPile.add(treasureID);
    }
}
