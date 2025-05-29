package com.forbidden.island.view.handler;

/**
 * IRendering interface defines basic behaviors related to GUI rendering.
 * Classes implementing this interface are responsible for updating game display
 * and handling interface states when the game ends.
 */
public interface IRendering {
    /**
     * Update the graphical interface.
     * Generally called after game state changes (such as character movement, card changes, etc.)
     * to refresh the interface and reflect the latest game state.
     */
    void update();

    /**
     * Disable all interface components when the game ends.
     * Implementing classes should use this method to disable buttons, inputs,
     * and other interactive controls to prevent further operations.
     */
    void finish();
}
