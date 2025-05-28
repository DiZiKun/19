package controller;

import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.model.adventurer.Engineer;
import com.forbidden.island.utils.LogUtil;
import com.forbidden.island.view.ElementEngine;
import com.forbidden.island.view.handler.RenderingEngine;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * ForbiddenIslandGame class is the core game controller that manages the game flow and state.
 * It handles round progression, player actions, game phases, and victory/defeat conditions.
 * The class maintains game state including current round, action counts, and special game modes.
 */
public class ForbiddenIslandGame {
    /**
     * Current round number (starting from 0)
     */
    private static int roundNum = 0;

    /**
     * Virtual round number (used during rescue phase)
     */
    private static int fakeRoundNum = -1;

    /**
     * Number of actions taken by current player (maximum 3 per round)
     */
    private static int actionCount = 0;

    /**
     * Virtual action count (used during rescue to limit movement to 2 steps)
     */
    private static int fakeActionCount = 0;

    /**
     * Total number of players in current game
     */
    private static int numOfPlayer;

    /**
     * Indicates if current round has completed treasure card drawing and tile sinking phases
     */
    private static boolean stage23Done = false;

    /**
     * Indicates if game is in rescue mode (players need to be saved from water)
     */
    private static boolean need2save = false;

    /**
     * Indicates if currently in virtual round (handling players in water)
     */
    private static boolean inFakeRound = false;

    /**
     * List of player IDs currently in water
     */
    private static ArrayList<Integer> playerIDinWater;

    /**
     * Initializes the game with specified number of players and water level.
     * Sets up game elements, renders initial interface, and starts first round.
     */
    public static void init(int numOfPlayers, int waterLevel) {
        numOfPlayer = numOfPlayers;
        playerIDinWater = new ArrayList<>();

        // Initialize game element engine (card decks, characters, map, etc.)
        ElementEngine.init(numOfPlayers, waterLevel);
        LogUtil.console("Initialise Players...");

        // Initialize rendering engine (responsible for GUI updates)
        RenderingEngine.init();

        // Initial tile sinking
        LogUtil.console("Island starts to sink...");
        RenderingEngine.getFloodRendering().update();
        ElementEngine.getBoard().sinkTiles(ElementEngine.getFloodDeck().getCards());
        ElementEngine.getFloodDeck().discard(); // Add drawn cards to discard pile
        ElementEngine.getFloodDeck().set2Norm(); // Reset deck to normal mode

        // Game start message
        LogUtil.console("[ Game Start ! ]");
        LogUtil.console("[ Player " + (roundNum + 1) + " ]\n(" + ElementEngine.getAdventurers()[roundNum].getName() + "'s Round)");
        LogUtil.console("Please Take Up To 3 Actions");
    }

    /**
     * Handles phases 2 and 3: Drawing 2 treasure cards and sinking tiles with flood cards.
     * Also processes any water rise cards drawn and updates game state accordingly.
     */
    public static void Stage23() {
        // Draw 2 treasure cards
        ElementEngine.getDisplayedTreasureCard().addAll(ElementEngine.getTreasureDeck().getCards());
        actionCount = 3;    // Mark phase as complete by setting action count to 3

        RenderingEngine.getTreasureRendering().update();

        // Check for water rise cards and handle them
        Iterator<Integer> iterator = ElementEngine.getDisplayedTreasureCard().iterator();
        while (iterator.hasNext()) {
            Integer treasureID = iterator.next();
            if (treasureID == 25 || treasureID == 26 || treasureID == 27) { // Special cards: Water Rise
                ElementEngine.getWaterMeter().WaterRise();  // Increase water level
                ElementEngine.getFloodDeck().putBack2Top(); // Return flood discard pile to top
                ElementEngine.getTreasureDeck().discard(treasureID);     // Discard the card
                iterator.remove();  // Remove card to avoid duplicate processing
            }
        }

        // Update interface displays
        RenderingEngine.getTreasureRendering().update();
        RenderingEngine.getPlayerRendering().update();
        RenderingEngine.getWaterMeterRendering().update();
        RenderingEngine.getBoardRendering().update();

        // Draw flood cards and sink corresponding tiles
        RenderingEngine.getFloodRendering().update();
        ElementEngine.getBoard().sinkTiles(ElementEngine.getFloodDeck().getCards());

        ElementEngine.getFloodDeck().discard(); // Add sunk cards to discard pile

        // Mark phase as complete
        stage23Done = true;
    }

    /**
     * Handles end of current player's round:
     * - Checks and handles card limit
     * - Updates game state
     * - Checks for game over conditions
     * - Prepares for next player's turn
     */
    public static void RoundEnd() {
        // If card count exceeds limit (hand + displayed > 5), require discard first
        if (ElementEngine.getAdventurers()[roundNum].getHandCards().size() + ElementEngine.getDisplayedTreasureCard().size() > 5) {
            LogUtil.console("You Have More Than 5 Cards, Please Discard First!");
            ElementEngine.resetCardsInRound();  // Reset round-related state
            return;
        } else {
            // Merge hand cards and clear display area
            ElementEngine.getAdventurers()[roundNum].getHandCards().addAll(ElementEngine.getDisplayedTreasureCard());
            ElementEngine.getDisplayedTreasureCard().clear();
            ElementEngine.selectPawn(-1);   // Cancel selected character
            ElementEngine.resetCardsInRound();  // Reset round data
            RenderingEngine.getTreasureRendering().update();
            RenderingEngine.getPlayerRendering().update();
        }

        // Check for game failure: all shrines are sunk
        if (ElementEngine.getBoard().isShrinesFlooded()) {
            LogUtil.console("[!] Shrines And Treasures Are Sunk");
            finish(false);    // Game over (failure)
            return;
        }

        // Reset state and prepare for next player's turn
        ElementEngine.selectPawn(-1);
        if (ElementEngine.getAdventurers()[roundNum] instanceof Engineer) {
            ((Engineer) ElementEngine.getAdventurers()[roundNum]).resetShoreUpCount();  // Reset Engineer's special ability usage count
        }
        actionCount = 0;
        roundNum++;
        roundNum = roundNum % numOfPlayer;  // Rotate turn order
        stage23Done = false;

        LogUtil.console("[ Player " + (roundNum + 1) + " ]\n(" + ElementEngine.getAdventurers()[roundNum].getName() + "'s Round)");
        RenderingEngine.getPlayerRendering().update();  // Update player panel
    }

    /**
     * Initiates rescue rounds when players have fallen into water.
     * Allows them to move to adjacent tiles to save themselves.
     */
    public static void SavePlayersRound() {
        // All players rescued, return to normal round
        if (playerIDinWater.isEmpty()) {
            roundNum = fakeRoundNum;
            fakeRoundNum = -1;
            actionCount = 3;
            need2save = false;
            inFakeRound = false;
            RenderingEngine.getControllersRendering().update();
            return;
        }

        // Handle next player in water
        for (Adventurer adventurer : ElementEngine.getAdventurers()) {
            if (playerIDinWater.contains(adventurer.getId())) {
                roundNum = adventurer.getOrder();
                actionCount = 2; // Players in water can only move 2 steps
                RenderingEngine.getControllersRendering().update();
                playerIDinWater.remove((Integer) adventurer.getId());

                int x = adventurer.getX();
                int y = adventurer.getY();

                // Non-special characters must have at least one adjacent tile to swim to
                boolean canSwim = checkCanSwim(x, y, adventurer.getName());
                if (!canSwim) {
                    finish(false);
                    LogUtil.console("[!] No Adjacent Tile To Swim To");
                    return;
                }
                return;
            }
        }
    }

    /**
     * Checks if there are any adjacent tiles the player can swim to from current position.
     * Considers boundary conditions and Explorer's diagonal movement ability.
     */
    private static boolean checkCanSwim(int x, int y, String name) {
        boolean isExplorer = name.equals("Explorer");

        // Check four basic directions
        boolean up = x > 0 && ElementEngine.getBoard().getTile(x - 1, y).isExist();
        boolean down = x < 5 && ElementEngine.getBoard().getTile(x + 1, y).isExist();
        boolean left = y > 0 && ElementEngine.getBoard().getTile(x, y - 1).isExist();
        boolean right = y < 5 && ElementEngine.getBoard().getTile(x, y + 1).isExist();

        // Explorer can also check diagonals
        boolean upLeft = isExplorer && x > 0 && y > 0 && ElementEngine.getBoard().getTile(x - 1, y - 1).isExist();
        boolean upRight = isExplorer && x > 0 && y < 5 && ElementEngine.getBoard().getTile(x - 1, y + 1).isExist();
        boolean downLeft = isExplorer && x < 5 && y > 0 && ElementEngine.getBoard().getTile(x + 1, y - 1).isExist();
        boolean downRight = isExplorer && x < 5 && y < 5 && ElementEngine.getBoard().getTile(x + 1, y + 1).isExist();

        return up || down || left || right || upLeft || upRight || downLeft || downRight;
    }

    /**
     * Game end logic (true for victory, false for failure)
     * Updates the interface and displays appropriate message.
     */
    public static void finish(boolean isWin) {
        if (isWin) {
            System.out.println("Game Success");
            LogUtil.console("[Congrats!] Game Success!");
        } else {
            System.out.println("Game failed");
            LogUtil.console("[Oops!] Game failed...");
        }
        try {
            Thread.sleep(1000);
            RenderingEngine.getBoardRendering().finish();
            RenderingEngine.getTreasureRendering().finish();
            RenderingEngine.getWaterMeterRendering().finish();
            RenderingEngine.getFloodRendering().finish();
            RenderingEngine.getControllersRendering().finish();
            RenderingEngine.getPlayerRendering().finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increment action count when player takes an action
     */
    public static void doAction() {
        actionCount += 1;
    }

    /**
     * Decrement action count (used for special abilities that grant extra actions)
     */
    public static void moreAction() {
        actionCount -= 1;
    }

    // Getters and setters
    public static void setPlayerIDinWater(ArrayList<Integer> playerIDinWater) {
        ForbiddenIslandGame.playerIDinWater.addAll(playerIDinWater);
    }

    public static int getNumOfPlayer() {
        return numOfPlayer;
    }

    public static int getActionCount() {
        return actionCount;
    }

    public static void setActionCount(int num) {
        actionCount = num;
    }

    public static int getFakeActionCount() {
        return fakeActionCount;
    }

    public static void setFakeActionCount(int fakeActionCount) {
        ForbiddenIslandGame.fakeActionCount = fakeActionCount;
    }

    public static int getRoundNum() {
        return roundNum;
    }

    public static void setRoundNum(int roundNum) {
        ForbiddenIslandGame.roundNum = roundNum;
    }

    public static int getFakeRoundNum() {
        return fakeRoundNum;
    }

    public static void setFakeRoundNum(int fakeRoundNum) {
        ForbiddenIslandGame.fakeRoundNum = fakeRoundNum;
    }

    public static boolean isStage23Done() {
        return stage23Done;
    }

    public static boolean isNeed2save() {
        return need2save;
    }

    public static void setNeed2save(boolean need2saveFlag) {
        need2save = need2saveFlag;
    }

    public static boolean isInFakeRound() {
        return inFakeRound;
    }

    public static void setInFakeRound(boolean inFakeRound) {
        ForbiddenIslandGame.inFakeRound = inFakeRound;
    }
}
