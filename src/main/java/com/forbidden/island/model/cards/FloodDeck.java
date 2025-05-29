package com.forbidden.island.model.cards;

import com.forbidden.island.view.ElementEngine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Flood deck class, extends abstract Deck class, specifically handles flood card logic in the game.
 */
public class FloodDeck extends Deck{
    /** Currently displayed flood cards on the table */
    private final ArrayList<Integer> displayedCards;
    /** Removed flood cards (corresponding to sunken tiles) */
    private final ArrayList<Integer> removedFloodCard;
    /** Whether in initialization state, draws 6 cards fixed during initialization */
    private boolean isInit;

    /**
     * Constructor, initializes flood deck.
     * Adds flood cards numbered 1-24 and shuffles them during initialization.
     * Sets initial state to true, indicating fixed number of cards for first draw.
     */
    public FloodDeck() {
        super(6);   // Initialize parent class, default to draw 6 cards
        displayedCards = new ArrayList<>();
        removedFloodCard = new ArrayList<>();
        isInit = true;

        // Add flood cards numbered 1-24
        for (int i = 1; i <= 24; i++) {
            deck.add(i);
        }

        // Shuffle deck
        Collections.shuffle(deck);
    }

    /**
     * Gets current flood cards to draw (adjusts draw count based on initialization state and water level).
     * @return list of currently displayed flood cards
     */
    public ArrayList<Integer> getCards() {
        if (!isInit) {
            // When not initializing, dynamically get water level to determine draw count
            Num = ElementEngine.getFloodCardCount();
        }

        // Check and replenish deck count
        CheckAvailability(Num);

        // Clear current display list, prepare for update
        displayedCards.clear();

        // When deck and discard pile combined have insufficient cards, show all cards
        if (deck.size() + discardPile.size() < Num) {
            displayedCards.addAll(deck);
        } else {
            // Otherwise show Num cards from top of deck
            displayedCards.addAll(deck.subList(0, Num));
        }
        return displayedCards;
    }

    /**
     * Discard operation, removes currently drawn flood cards from deck and adds them to discard pile.
     */
    public void discard(){
        int count = 0;
        Iterator<Integer> iterator = deck.iterator();
        // Iterate through deck, remove first Num cards to discard pile
        while (iterator.hasNext()) {
            int floodCard = iterator.next();
            if (count < Num) {
                discardPile.add(floodCard);
                iterator.remove();
                count++;
            }
            if (count >= Num) {
                break;
            }
        }
    }

    /**
     * Shuffles flood cards in discard pile and puts them back on top of deck.
     * Typically used when reshuffling to start over.
     */
    public void putBack2Top() {
        if (!discardPile.isEmpty()) {
            Collections.shuffle(discardPile);
            // Put discard pile on top of deck
            discardPile.addAll(deck);
            deck.clear();
            deck.addAll(discardPile);
            discardPile.clear();
        }
    }

    /**
     * When corresponding tile sinks, removes matching flood card from deck and records the removed card.
     * @param removedTile removed tile number (corresponding to flood card number)
     */
    public void removeFloodCard(int removedTile) {
        removedFloodCard.add(removedTile);
        deck.remove((Integer) removedTile);
    }

    /**
     * Sets to non-initialization state, indicating no longer fixed to draw 6 flood cards,
     * but instead dynamically adjusts draw count based on current water level.
     */
    public void set2Norm() {
        this.isInit = false;
    }
}
