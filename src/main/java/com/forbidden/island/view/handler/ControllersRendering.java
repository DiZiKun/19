package com.forbidden.island.view.handler;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.view.OperatePanel;

import javax.swing.*;

/**
 * ControllersRendering implements IRendering interface,
 * used to control button states (enable or disable) in the game operation panel (OperatePanel),
 * based on whether the game is in a fake round or needs to save operation state.
 */
public class ControllersRendering implements IRendering {

    /**
     * Update operation button states.
     * Called at each round or specific operation phase (like fake round)
     * to enable or disable buttons based on current game logic.
     */
    @Override
    public void update() {

        // If current round is a "fake round" (non-standard game operation round)
        if (ForbiddenIslandGame.isInFakeRound()){

            // Iterate through all operation buttons
            for (JButton controller : OperatePanel.opButtons){

                // Enable all buttons by default unless save is needed; if save is needed, disable them
                controller.setEnabled(!ForbiddenIslandGame.isNeed2save());
            }

            // Special handling: operation button index 1 (usually "Save" button) should be enabled when save is needed
            OperatePanel.opButtons.get(1).setEnabled(ForbiddenIslandGame.isNeed2save());
        }
        else {
            // Non-fake round phase, similarly iterate buttons, enable only when save is not needed
            for (JButton controller : OperatePanel.opButtons){
                controller.setEnabled(!ForbiddenIslandGame.isNeed2save());
            }
        }
    }

    /**
     * Called when game ends, disables all buttons on operation panel to prevent further operations.
     */
    @Override
    public void finish() {
        // Disable all operation buttons
        for (JButton controller : OperatePanel.opButtons){
            controller.setEnabled(false);
        }
    }
}
