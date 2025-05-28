package com.forbidden.island.model.adventurer;

import com.forbidden.island.model.enums.TreasureFigurines;
import com.forbidden.island.utils.Map;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract Adventurer class representing a character in the game.
 * Encapsulates basic information and behaviors of an adventurer including identity,
 * position, movement, hand cards, and captured treasure figurines.
 */
public abstract class Adventurer {
    /** Unique ID of the adventurer */
    protected int id;
    /** Action order of the adventurer */
    protected int order;
    /** Current x coordinate */
    protected int x;
    /** Current y coordinate */
    protected int y;
    /** Target x coordinate for planned movement */
    protected int targetX;
    /** Target y coordinate for planned movement */
    protected int targetY;
    /** Target x coordinate for shore up action */
    protected int shoreUpX;
    /** Target y coordinate for shore up action */
    protected int shoreUpY;
    /** Name of the adventurer */
    protected String name;
    /** Path to the adventurer's pawn image */
    protected String pawnImg;
    /** List of cards in adventurer's hand (represented by card numbers) */
    protected ArrayList<Integer> handCards;
    /** List of treasure figurines captured by the adventurer */
    protected ArrayList<TreasureFigurines> capturedFigurines;

    public Adventurer(int order, String name) {
        this.order = order;
        this.name = name;
        this.pawnImg = "/Pawns/" + this.name + ".png";
        this.handCards = new ArrayList<>();
        this.capturedFigurines = new ArrayList<>();
    }

    /**
     * Sets the adventurer's current position.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the target position for planned movement.
     * @param x target x coordinate
     * @param y target y coordinate
     */
    public void setMoveTarget(int x, int y) {
        this.targetX = x;
        this.targetY = y;
    }

    /**
     * Executes movement operation, updating current position to target position.
     */
    public void Move() {
        this.x = this.targetX;
        this.y = this.targetY;
    }

    /**
     * Sets the target position for shore up action.
     * @param x target x coordinate
     * @param y target y coordinate
     */
    public void setShoreUp(int x, int y) {
        this.shoreUpX = x;
        this.shoreUpY = y;
    }

    /**
     * Adds given cards to current hand cards list.
     * @param newHandCards list of new hand cards to add
     */
    public void setHandCards(ArrayList<Integer> newHandCards) {
        this.handCards.addAll(newHandCards);
    }

    /**
     * Adds a captured treasure figurine.
     * @param figurine captured treasure figurine
     */
    public void addCapturedFigurine(TreasureFigurines figurine) {
        this.capturedFigurines.add(figurine);
    }

    public ArrayList<TreasureFigurines> getCapturedFigurines() {
        return capturedFigurines;
    }

    public ArrayList<Integer> getHandCards() {
        return handCards;
    }

    public String getPawnImg() {
        return pawnImg;
    }

    public int getOrder() {
        return order;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getShoreUpX() {
        return shoreUpX;
    }

    public int getShoreUpY() {
        return shoreUpY;
    }

    /**
     * Gets the map tile number corresponding to adventurer's current position.
     * Uses Map class's numberMatcher to map coordinate string to number.
     * @return current tile number
     */
    public int getPos() {
        return Map.numberMatcher.get(Arrays.toString(new int[]{this.x, this.y}));
    }

    /**
     * Gets the map tile number corresponding to adventurer's shore up target position.
     * @return shore up target tile number
     */
    public int getShoredPos() {
        return Map.numberMatcher.get(Arrays.toString(new int[]{this.shoreUpX, this.shoreUpY}));
    }
}
