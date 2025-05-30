package com.forbidden.island;

import com.forbidden.island.view.SplashScreen;

import javax.swing.*;

/**
 * Main application class for the Forbidden Island game.
 * This class serves as the entry point of the game and initializes the game environment.
 */
public class Application {

    /**
     * Main method that starts the game.
     * Uses SwingUtilities.invokeLater to ensure all Swing components are created
     * and modified in the Event Dispatch Thread (EDT), which is a best practice
     * for Swing applications to avoid threading issues.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create and show the splash screen with the title image
            new SplashScreen("/TitleScreen.png");
        });
    }
}