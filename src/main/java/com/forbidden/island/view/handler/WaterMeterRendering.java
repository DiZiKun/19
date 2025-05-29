package com.forbidden.island.view.handler;

import com.forbidden.island.view.TreasurePanel;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.utils.ImageUtil;
import com.forbidden.island.utils.Constant;

import javax.swing.*;

/**
 * WaterMeterRendering class implements IRendering interface,
 * responsible for rendering and updating the water meter (water level gauge)
 * display in the game interface.
 */
public class WaterMeterRendering implements IRendering {

    /**
     * Constructor
     * Sets the water meter icon during initialization, showing initial state.
     */
    public WaterMeterRendering() {
        // Get current water meter image path through ElementEngine
        // Set icon for waterMeter component in TreasurePanel, specifying image width and height
        TreasurePanel.waterMeter.setIcon(new ImageIcon(
                ImageUtil.getImage(ElementEngine.getWaterMeterImg(),
                        Constant.WATER_METER_WIDTH,
                        Constant.WATER_METER_HEIGHT)));
    }

    /**
     * update method
     * Called when game state changes water level, refreshes water meter icon
     * to reflect latest water level state.
     */
    @Override
    public void update() {
        // Get current water meter image and update icon display
        TreasurePanel.waterMeter.setIcon(new ImageIcon(
                ImageUtil.getImage(ElementEngine.getWaterMeterImg(),
                        Constant.WATER_METER_WIDTH,
                        Constant.WATER_METER_HEIGHT)));
    }

    /**
     * finish method
     * Called when game ends or water meter no longer needs to be displayed,
     * hides the water meter component.
     */
    @Override
    public void finish() {
        // Set water meter component invisible, hide from interface
        TreasurePanel.waterMeter.setVisible(false);
    }
}
