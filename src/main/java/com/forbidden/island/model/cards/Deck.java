package com.forbidden.island.model.cards;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract class representing the basic structure and operations of a deck of cards.
 * Cards are identified by integers, and both the deck and discard pile are stored using ArrayList.
 * ArrayList is chosen over Stack to facilitate adding discarded cards to the bottom.
 */
public abstract class Deck {
    /** Current deck storing available cards */
    protected ArrayList<Integer> deck;
    /** Discard pile storing used cards, waiting to be recycled back to deck */
    protected ArrayList<Integer> discardPile;
    /** Total number of cards (used during initialization) */
    protected int Num;

    /**
     * Constructor, initializes the deck and discard pile.
     * @param num total number of cards
     */
    public Deck(int num) {
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        Num = num;
    }

    /**
     * Draws specified number of cards (implemented by subclasses)
     * @return list of drawn cards
     */
    protected abstract ArrayList<Integer> getCards();

    /**
     * Checks if current deck has enough cards for drawing.
     * If deck size is insufficient, shuffles discard pile and adds it to bottom of deck,
     * then clears discard pile.
     * @param n number of cards needed to draw
     */
    protected void CheckAvailability(int n) {
        // If deck doesn't have enough cards
        if (deck.size() < n) {
            // Shuffle discard pile
            Collections.shuffle(discardPile);
            // Add discard pile to bottom of deck
            deck.addAll(discardPile);
            // Clear discard pile
            discardPile.clear();
        }
    }
}
