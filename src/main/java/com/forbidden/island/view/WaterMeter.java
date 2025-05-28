package com.forbidden.island.view;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.utils.LogUtil;

/**
 * WaterMeter class represents the "water meter" in the Forbidden Island game.
 * It tracks the current water level and determines the number of flood cards to draw each round based on the level.
 */
public class WaterMeter {
    // Current water level (typically ranges from 1-10)
    private int waterLevel;

    // Current number of flood cards to draw per round
    private int floodCardCount;

    // Image path corresponding to current water level
    private String img;

    /**
     * Constructor: Initialize water meter.
     *
     * @param waterLevel Initial water level (determined by player-selected difficulty)
     */
    public WaterMeter(int waterLevel) {
        this.waterLevel = waterLevel;
        // Set water level image path (for UI display)
        this.img = "/WaterMeter/" + waterLevel + ".png";

        // Set number of flood cards to draw based on water level
        setFloodCardCount();
    }

    /**
     * Execute water level rise operation (called when "Water Rise!" card is drawn).
     * Increases water level and updates image and flood card count.
     * If water level rises to skull level (10), game is lost.
     */
    public void WaterRise() {
        // Increase water level
        waterLevel += 1;

        // Update image path
        img = "/WaterMeter/" + waterLevel + ".png";

        // Update current flood card count per round
        setFloodCardCount();

        // If water level reaches maximum (skull icon), end game
        if (waterLevel == 10) {
            LogUtil.console("[!] Water Level Reaches The Skull And Crossbones");

            // Trigger game failure (false indicates failure)
            ForbiddenIslandGame.finish(false);
        }
    }

    /**
     * Set number of flood cards to draw per round based on current water level.
     */
    private void setFloodCardCount() {
        switch (waterLevel) {
            case 1:
            case 2:
                floodCardCount = 2; // Lowest level: draw 2 cards per round
                break;
            case 3:
            case 4:
            case 5:
                floodCardCount = 3; // Medium water level: draw 3 cards
                break;
            case 6:
            case 7:
                floodCardCount = 4; // High water level: draw 4 cards
                break;
            case 8:
            case 9:
                floodCardCount = 5; // Extreme water level: draw 5 cards
                break;

            // Water level 10 (skull) no longer draws cards as game ends
        }
    }

    public int getFloodCardCount() {
        return floodCardCount;
    }

    public String getImg() {
        return img;
    }
}
