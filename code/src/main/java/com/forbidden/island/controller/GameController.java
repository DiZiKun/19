package com.forbidden.island.controller;

import com.forbidden.island.model.adventurer.Engineer;
import com.forbidden.island.model.enums.TreasureFigurines;
import com.forbidden.island.view.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Game controller class responsible for binding all player operation buttons with specific game logic events,
 * including movement, shore up flooded tiles, card passing, treasure capture, lift off, and special actions.
 * Implements player behaviors in the game by listening to button events on the operation panel.
 */
public class GameController {
    private static GameController instance;

    /**
     * Constructor initializes and binds all operation button controllers,
     * enabling game behaviors to be triggered when players click operation buttons.
     */
    private GameController() {
        moveToController();
        shoreUpController();
        passToController();
        captureController();
        liftOffController();
        specialActionController();
        NextController();
        DiscardController();
        resetController();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    /**
     * Binds movement operation button listener.
     * If player's action count is less than 3 and current tile allows movement,
     * executes move action and consumes one action.
     * Otherwise prompts that movement is not allowed or maximum actions exceeded.
     */
    private void moveToController() {
        OperatePanel.opButtons.get(1).addActionListener(e -> {
            if (ForbiddenIslandGame.getActionCount() < 3) { // Check action count limit
                if (ElementEngine.getBoard().isCanMove()) { // Check if movement is allowed
                    Adventurer currentPlayer = ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()];
                    
                    // 如果是领航员在移动其他玩家
                    if ("Navigator".equals(currentPlayer.getName()) && ElementEngine.getSelectedPawn() != -1) {
                        Adventurer targetPlayer = ElementEngine.getAdventurers()[ElementEngine.getSelectedPawn()];
                        ElementEngine.moveTo();
                        LogUtil.console("Navigator moved " + targetPlayer.getName() + " to " + 
                                      Arrays.toString(Map.coordinatesMatcher.get(targetPlayer.getPos())));
                    } else {
                        ElementEngine.moveTo();
                        LogUtil.console("Move To " + Arrays.toString(Map.coordinatesMatcher.get(currentPlayer.getPos())));
                    }

                    RenderingEngine.getBoardRendering().update(); // Update interface
                    if (ForbiddenIslandGame.isNeed2save()) { // Check if round state needs saving
                        ForbiddenIslandGame.SavePlayersRound();
                    }
                } else {
                    LogUtil.console("Can't [Move To] This Tile");   // Prompt movement not allowed
                }
            } else {
                LogUtil.console("Exceeded Maximum Actions");    // Prompt maximum actions exceeded
            }
        });
    }

    /**
     * Binds shore up button event.
     * If player's action count is less than 3 and shore up is allowed,
     * executes shore up action. Engineer role gets an additional action opportunity.
     */
    private void shoreUpController() {
        OperatePanel.opButtons.get(2).addActionListener(e -> {
            if (ForbiddenIslandGame.getActionCount() < 3) {
                if (ElementEngine.getBoard().isCanShoreUp()) {  // Check if shore up is allowed
                    ElementEngine.shoreUp();    // Execute shore up operation
                    LogUtil.console("Shore Up " + Arrays.toString(Map.coordinatesMatcher.get(
                            ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getShoredPos())));
                    RenderingEngine.getBoardRendering().update();

                    ForbiddenIslandGame.doAction();
                    // Engineer's special ability: gets an additional action after shore up
                    if (ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()] instanceof Engineer) {
                        if (((Engineer) ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()]).getShoreUpCount() > 0) {
                            ((Engineer) ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()]).ShoreUp();
                            ForbiddenIslandGame.moreAction();
                        }
                    }
                } else {
                    LogUtil.console("Can't [Shore Up] This Tile");
                }
            }
            else {
                LogUtil.console("Exceeded Maximum Actions");
            }
        });
    }

    /**
     * Binds card passing button event.
     * If player's action count is less than 3 and target player is selected,
     * checks if current player and target player can pass cards (adjacent or Messenger role),
     * executes pass operation and consumes one action if conditions are met.
     */
    private void passToController() {
        OperatePanel.opButtons.get(3).addActionListener(e -> {
            if (ForbiddenIslandGame.getActionCount() < 3 && ElementEngine.getSelectedPawn() != -1) {
                if (ElementEngine.getBoard().getTile(ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getX(), ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getY())
                        .CanPass(ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()], ElementEngine.getAdventurers()[ElementEngine.getSelectedPawn()])
                        || ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getName().equals("Messenger")) {

                    // Check if cards can be passed, or if current player is Messenger (can pass at distance)
                    if (ElementEngine.passTo()) {
                        LogUtil.console(ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getName()
                                + " Passed A Card To " + ElementEngine.getAdventurers()[ElementEngine.getSelectedPawn()].getName());
                        RenderingEngine.getPlayerRendering().update();
                        RenderingEngine.getTreasureRendering().update();
                        ForbiddenIslandGame.doAction(); // Consume one action
                        ElementEngine.selectPawn(-1); // Cancel target player selection
                        ElementEngine.resetCardsInRound(); // Reset round card selection
                    }
                } else {
                    LogUtil.console("Can't Do [Pass To] Action");
                }
            } else {
                LogUtil.console("Exceeded Maximum Actions Or No Receiver Is Selected");
            }
        });
    }

    /**
     * Binds treasure capture button event.
     * If player's action count is less than 3 and has 4 cards of a treasure type,
     * and is on the corresponding treasure tile, executes treasure capture action,
     * removes corresponding cards from hand and marks treasure as captured.
     */
    private void captureController() {
        OperatePanel.opButtons.get(4).addActionListener(e -> {
            LogUtil.console("Trying To Capture The Treasure Figurine...");
            if (ForbiddenIslandGame.getActionCount() < 3) {
                Adventurer currentPlayer = ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()];
                ArrayList<Integer> handCards = new ArrayList<>(currentPlayer.getHandCards());

                int[] treasureCount = countTreasureCards(handCards);

                boolean captured = attemptCapture(currentPlayer, treasureCount);

                if (!captured) {
                    LogUtil.console("You Don't Have Enough Treasure Cards To Capture The Figurine");
                }
            } else {
                LogUtil.console("Exceeded Maximum Actions");
            }
        });
    }

    /**
     * Counts the number of four types of treasure cards in player's hand.
     * @param handCards Player's current hand cards list
     * @return Array of length 4, representing counts of four treasure card types
     */
    private int[] countTreasureCards(ArrayList<Integer> handCards) {
        int[] treasureCount = new int[4];
        for (int handCard : handCards) {
            if (handCard >= 0 && handCard <= 4) treasureCount[0]++;
            else if (handCard >= 5 && handCard <= 9) treasureCount[1]++;
            else if (handCard >= 10 && handCard <= 14) treasureCount[2]++;
            else if (handCard >= 15 && handCard <= 19) treasureCount[3]++;
        }
        return treasureCount;
    }

    /**
     * Attempts to capture treasure figurine.
     * 1. Checks if has 4 cards of same treasure type.
     * 2. Checks if current player is on corresponding treasure tile.
     * 3. Sets tile as captured, removes corresponding cards from player's hand, discards them.
     * 4. Updates rendering.
     * @param currentPlayer Current player object
     * @param treasureCount Array of treasure card type counts
     * @return Whether treasure figurine was successfully captured
     */
    private boolean attemptCapture(Adventurer currentPlayer, int[] treasureCount) {
        for (int i = 0; i < treasureCount.length; i++) {
            if (treasureCount[i] == 4) {
                Tile currentTile = ElementEngine.getBoard().getTile(currentPlayer.getX(), currentPlayer.getY());
                if (isCurrentTileMatchingTreasure(currentTile, i)) {
                    captureTreasureAtTile(currentPlayer, i);
                    updateCapturedTiles(i);
                    RenderingEngine.getPlayerRendering().update();
                    RenderingEngine.getBoardRendering().update();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if current tile matches specified treasure.
     * @param currentTile Current tile
     * @param treasureIndex Treasure number (0~3)
     * @return Whether it matches
     */
    private boolean isCurrentTileMatchingTreasure(Tile currentTile, int treasureIndex) {
        int tileId = currentTile.getTileId();
        return tileId == 2 * treasureIndex + 1 || tileId == 2 * treasureIndex + 2;
    }

    /**
     * Sets current treasure as captured, updates player state, and removes corresponding treasure cards from player's hand.
     * @param currentPlayer Current player
     * @param treasureIndex Treasure number (0~3)
     */
    private void captureTreasureAtTile(Adventurer currentPlayer, int treasureIndex) {
        Tile currentTile = ElementEngine.getBoard().getTile(currentPlayer.getX(), currentPlayer.getY());
        currentTile.setCaptured();

        // Mark treasure as captured by player
        for (TreasureFigurines figurine : TreasureFigurines.values()) {
            if (figurine.ordinal() == treasureIndex) {
                currentPlayer.addCapturedFigurine(figurine);

                // Remove treasure cards from player's hand and discard them
                Iterator<Integer> iterator = currentPlayer.getHandCards().iterator();
                while (iterator.hasNext()) {
                    Integer handCardNo = iterator.next();
                    if (handCardNo >= treasureIndex * 5 && handCardNo <= treasureIndex * 5 + 4) {
                        iterator.remove();
                        ElementEngine.getTreasureDeck().discard(handCardNo);
                    }
                }
                break;
            }
        }
    }

    /**
     * Marks all tiles corresponding to treasure as captured.
     * @param treasureIndex Treasure number (0~3)
     */
    private void updateCapturedTiles(int treasureIndex) {
        int tileIndex1 = ElementEngine.getTilesArray().indexOf(2 * treasureIndex + 1);
        int tileIndex2 = ElementEngine.getTilesArray().indexOf(2 * treasureIndex + 2);
        int[] coord1 = Map.coordinatesMatcher.get(tileIndex1);
        int[] coord2 = Map.coordinatesMatcher.get(tileIndex2);

        ElementEngine.getBoard().getTile(coord1[0], coord1[1]).setCaptured();
        ElementEngine.getBoard().getTile(coord2[0], coord2[1]).setCaptured();
    }

    /**
     * Binds helicopter lift off button event listener.
     * This method checks if player meets helicopter lift off conditions:
     * 1. Helicopter pad (Tile 14) exists and all players are on it.
     * 2. All players have at least one helicopter lift card (20, 21, 22).
     * 3. All players have collected all 4 treasure figurines.
     * If conditions are met, triggers game victory.
     */
    private void liftOffController() {
        // Get 6th button on operation panel (index 5), bind click event listener
        OperatePanel.opButtons.get(5).addActionListener(e -> {
            // Find index of Tile 14 in map tiles array
            int idx14 = ElementEngine.getTilesArray().indexOf(14);

            // Get Tile 14 object based on coordinate mapping
            Tile tile14 = ElementEngine.getBoard().getTile(
                    Map.coordinatesMatcher.get(idx14)[0],
                    Map.coordinatesMatcher.get(idx14)[1]);

            // Prepare two lists to collect all players' hand cards and captured treasure figurines
            ArrayList<Integer> handCards = new ArrayList<>();
            ArrayList<TreasureFigurines> figurines = new ArrayList<>();

            // Iterate through all players, add their hand cards and treasure figurines to respective lists
            for (Adventurer adventurer : ElementEngine.getAdventurers()) {
                handCards.addAll(adventurer.getHandCards());
                figurines.addAll(adventurer.getCapturedFigurines());
            }

            // Check if Tile 14 exists and all players are on it (player count equals game player count)
            if (tile14.isExist() && tile14.getPlayerOnBoard().size() == ForbiddenIslandGame.getNumOfPlayer()) {
                // Check if player hand contains any helicopter lift card (20, 21, or 22)
                // and if all treasure figurines count is 4 (all collected)
                if ((handCards.contains(20) || handCards.contains(21) || handCards.contains(22)) && figurines.size() == 4) {
                    // Conditions met, print success log and end game (victory)
                    LogUtil.console("Lift Off Success!");
                    ForbiddenIslandGame.finish(true);
                } else {
                    // Conditions not met, print failure log
                    LogUtil.console("Lift Off failed!");
                }
            }
        });
    }

    /**
     * Binds special action button event (e.g., using Diver's diving ability).
     * Checks if current player is Diver role, if yes, executes diving operation.
     */
    private void specialActionController() {
        OperatePanel.opButtons.get(6).addActionListener(e -> {
            if (canPerformSpecialAction()) {
                handleSpecialAction();
                checkAndRestoreFakeRound();
            }
            else if (shouldEnterFakeRound()) {
                enterFakeRound();
            }
            else if (shouldExitFakeRound()) {
                exitFakeRound();
            }
            // Update interface display
            RenderingEngine.getBoardRendering().update();
            RenderingEngine.getPlayerRendering().update();
        });
    }

    /**
     * Checks if conditions for performing special action are met:
     * - Has cards selected in current round
     * - Special action target tile coordinates are valid
     */
    private boolean canPerformSpecialAction() {
        return ElementEngine.getCardsInRound() != null
                && !ElementEngine.getCardsInRound().isEmpty()
                && ElementEngine.getSpecialActionTile()[0] != -1
                && ElementEngine.getSpecialActionTile()[1] != -1;
    }

    /**
     * Executes corresponding special action based on last selected card:
     * - Sandbag card: Drain flooded tile
     * - Helicopter card: Move player to specified tile
     */
    private void handleSpecialAction() {
        int lastSelect = ElementEngine.getCardsInRound().get(ElementEngine.getCardsInRound().size() - 1);

        if (isSandbagCard(lastSelect)) {
            useSandbagToShoreUp();
        } else if (isHelicopterCard(lastSelect) && !ElementEngine.getSelectedPawns().isEmpty()) {
            useHelicopterLift(lastSelect);
        }
    }

    /**
     * Checks if card is a sandbag card (23 or 24)
     */
    private boolean isSandbagCard(int cardId) {
        return cardId == 23 || cardId == 24;
    }

    /**
     * Uses sandbag card to drain selected tile
     */
    private void useSandbagToShoreUp() {
        int x = ElementEngine.getSpecialActionTile()[0];
        int y = ElementEngine.getSpecialActionTile()[1];
        Tile shoredTile = ElementEngine.getBoard().getTile(x, y);

        if (shoredTile.isExist() && shoredTile.getStatus() == TileStatus.Flooded) {
            shoredTile.shoreUp();  // Execute drain action
            // Remove sandbag card from current player's hand
            ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().remove((Integer) ElementEngine.getCardsInRound().get(ElementEngine.getCardsInRound().size() - 1));
            ElementEngine.getTreasureDeck().discard(ElementEngine.getCardsInRound().get(ElementEngine.getCardsInRound().size() - 1));
            LogUtil.console("Use A [Sandbag] To Shore Up A Tile");
            ElementEngine.resetCardsInRound();
            ElementEngine.resetSpecialActionTile();
        }
    }

    /**
     * Checks if card is a helicopter card (20, 21, 22)
     */
    private boolean isHelicopterCard(int cardId) {
        return cardId == 20 || cardId == 21 || cardId == 22;
    }

    /**
     * Uses helicopter card to move current player and selected players to target tile
     */
    private void useHelicopterLift(int cardId) {
        int roundNum = ForbiddenIslandGame.getRoundNum();
        int targetX = ElementEngine.getSpecialActionTile()[0];
        int targetY = ElementEngine.getSpecialActionTile()[1];

        // Remove current player from original tile
        Tile currentTile = ElementEngine.getBoard().getTile(ElementEngine.getAdventurers()[roundNum].getX(), ElementEngine.getAdventurers()[roundNum].getY());
        currentTile.moveOff(ElementEngine.getAdventurers()[roundNum]);

        // Move current player to target tile
        ElementEngine.getAdventurers()[roundNum].setPosition(targetX, targetY);
        ElementEngine.getBoard().getTile(targetX, targetY).moveOn(ElementEngine.getAdventurers()[roundNum].getId());

        // Move selected players to target tile
        for (int pawn : ElementEngine.getSelectedPawns()) {
            Tile tile = ElementEngine.getBoard().getTile(ElementEngine.getAdventurers()[pawn].getX(), ElementEngine.getAdventurers()[pawn].getY());
            tile.moveOff(ElementEngine.getAdventurers()[pawn]);
            ElementEngine.getAdventurers()[pawn].setPosition(targetX, targetY);
            ElementEngine.getBoard().getTile(targetX, targetY).moveOn(ElementEngine.getAdventurers()[pawn].getId());
        }

        // Remove helicopter card from current player's hand, discard card
        ElementEngine.getAdventurers()[roundNum].getHandCards().remove((Integer) cardId);
        ElementEngine.getTreasureDeck().discard(cardId);
        LogUtil.console("Use A [Helicopter Lift]");

        // Reset selections and cards
        ElementEngine.selectPawn(-1);
        ElementEngine.resetCardsInRound();
        ElementEngine.resetSpecialActionTile();
    }

    /**
     * If currently in fake round state, restores real player round state
     */
    private void checkAndRestoreFakeRound() {
        if (ForbiddenIslandGame.isInFakeRound()) {
            ForbiddenIslandGame.setInFakeRound(false);
            ForbiddenIslandGame.setRoundNum(ForbiddenIslandGame.getFakeRoundNum());
            ForbiddenIslandGame.setFakeRoundNum(-1);
            ForbiddenIslandGame.setActionCount(ForbiddenIslandGame.getFakeActionCount());
            ForbiddenIslandGame.setFakeActionCount(-1);
            LogUtil.console("Back To Player " + (ForbiddenIslandGame.getRoundNum() + 1) + "'s Turn (" + ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getName() + ")");
            LogUtil.console("Have Done " + ForbiddenIslandGame.getActionCount() + " Actions");
        }
    }

    /**
     * Checks if should enter fake round (selected other player and not in fake round)
     */
    private boolean shouldEnterFakeRound() {
        return ElementEngine.getSelectedPawn() != -1 && !ForbiddenIslandGame.isInFakeRound();
    }

    /**
     * Enters fake round state, switches to selected player's operation
     */
    private void enterFakeRound() {
        ForbiddenIslandGame.setInFakeRound(true);
        ForbiddenIslandGame.setFakeRoundNum(ForbiddenIslandGame.getRoundNum());
        ForbiddenIslandGame.setFakeActionCount(ForbiddenIslandGame.getActionCount());
        ForbiddenIslandGame.setActionCount(3);  // Reset fake round action count to 3
        ForbiddenIslandGame.setRoundNum(ElementEngine.getSelectedPawn());
        RenderingEngine.getPlayerRendering().update();
        LogUtil.console("Switch To Player " + (ForbiddenIslandGame.getRoundNum() + 1) + "'s Turn (" + ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getName() + ")");
    }

    /**
     * Checks if should exit fake round (currently selected player is fake round player and in fake round state)
     */
    private boolean shouldExitFakeRound() {
        return ElementEngine.getSelectedPawn() == ForbiddenIslandGame.getFakeRoundNum() && ForbiddenIslandGame.isInFakeRound();
    }

    /**
     * Exits fake round, restores real player round
     */
    private void exitFakeRound() {
        ForbiddenIslandGame.setInFakeRound(false);
        ForbiddenIslandGame.setRoundNum(ForbiddenIslandGame.getFakeRoundNum());
        ForbiddenIslandGame.setFakeRoundNum(-1);
        ForbiddenIslandGame.setActionCount(ForbiddenIslandGame.getFakeActionCount());
        ForbiddenIslandGame.setFakeActionCount(-1);
        RenderingEngine.getBoardRendering().update();
        RenderingEngine.getPlayerRendering().update();
        LogUtil.console("Back To Player " + (ForbiddenIslandGame.getRoundNum() + 1) + "'s Turn (" + ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getName() + ")");
        LogUtil.console("Have Done " + ForbiddenIslandGame.getActionCount() + " Actions");
    }

    /**
     * Sets "Next" button event listener, handles "Next" logic in game.
     * Includes phase switching, round ending, fake round switching, etc.
     */
    private void NextController() {
        // Add click event listener to 8th operation button (button index 7)
        OperatePanel.opButtons.get(7).addActionListener(e -> {
            // If not in fake round state
            if(!ForbiddenIslandGame.isInFakeRound()){
                // If phases 2 and 3 are not completed
                if (!ForbiddenIslandGame.isStage23Done()) {
                    LogUtil.console("[Next] Stage");   // Console output: Enter next stage
                    ForbiddenIslandGame.Stage23();      // Process phase 2 or 3
                }
                // Phases 2 and 3 completed, but need to save adventurer state first
                else if (ForbiddenIslandGame.isNeed2save()) {
                    LogUtil.console("Please save adventures first");    // Prompt player to save state first
                }

                // Phases 2 and 3 completed and no need to save, process round end
                else {
                    LogUtil.console("[Next] Round");   // Console output: Enter next round
                    ForbiddenIslandGame.RoundEnd();    // Process round end logic
                }
            }

            // Currently in fake round state, need to restore real round info
            else {
                // Restore real round number
                ForbiddenIslandGame.setInFakeRound(false);
                ForbiddenIslandGame.setRoundNum(ForbiddenIslandGame.getFakeRoundNum());
                ForbiddenIslandGame.setFakeRoundNum(-1);

                // Restore real action count
                ForbiddenIslandGame.setActionCount(ForbiddenIslandGame.getFakeActionCount());
                ForbiddenIslandGame.setFakeActionCount(-1);

                // Reset special action related states
                ElementEngine.resetSpecialActionTile();
                ElementEngine.resetCardsInRound();
                ElementEngine.selectPawn(-1);

                // Refresh game interface, update board and player info display
                RenderingEngine.getBoardRendering().update();
                RenderingEngine.getPlayerRendering().update();
            }
        });
    }

    /**
     * Sets "Reset" button event listener, used to clear all current player selections.
     * Includes canceling selected pawn, clearing round selected cards, and resetting special action target tile.
     */
    private void resetController() {
        OperatePanel.opButtons.get(9).addActionListener(e -> {
            LogUtil.console("[Reset] Your Selections");

            ElementEngine.selectPawn(-1);          // Cancel selected pawn (set to invalid value -1)
            ElementEngine.resetCardsInRound();           // Clear round selected cards list
            ElementEngine.resetSpecialActionTile();      // Reset special action target tile coordinates
        });
    }

    /**
     * Sets "Discard" button event listener, handles player card discard logic.
     */
    private void DiscardController() {
        OperatePanel.opButtons.get(8).addActionListener(e -> {
            if (!ElementEngine.getCardsInRound().isEmpty() && !ForbiddenIslandGame.isInFakeRound()) {
                handleDiscardInNormalRound();
            } else if (ForbiddenIslandGame.isInFakeRound()) {
                handleDiscardInFakeRound();
            } else {
                LogUtil.console("Please Select Cards To [Discard]");
            }
            RenderingEngine.getPlayerRendering().update();
            RenderingEngine.getTreasureRendering().update();
        });
    }

    /**
     * Handles card discard in normal round:
     *  - Merges player's hand cards and displayed treasure cards
     *  - Clears player's hand cards and display area
     *  - Discards selected cards based on selected card list, keeps unselected cards
     *  - Cards exceeding 5 are put in display area
     *  - Clears round selected cards
     */
    private void handleDiscardInNormalRound() {
        // Copy current player's hand cards and displayed treasure cards
        ArrayList<Integer> allCardsInRound = new ArrayList<>();
        allCardsInRound.addAll(ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards());
        allCardsInRound.addAll(ElementEngine.getDisplayedTreasureCard());

        // Clear player's hand cards and displayed treasure cards
        ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().clear();
        ElementEngine.getDisplayedTreasureCard().clear();

        // Check each card if selected for discard, otherwise return to hand
        for (Integer card : allCardsInRound) {
            if (ElementEngine.getCardsInRound().contains(card)) {
                ElementEngine.getTreasureDeck().discard(card);
            } else {
                ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().add(card);
            }
        }

        // If hand cards exceed 5, put excess in display area
        Iterator<Integer> iterator = ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            int handCard = iterator.next();
            if (count >= 5) {
                ElementEngine.getDisplayedTreasureCard().add(handCard);
                iterator.remove();
            }
            count++;
        }

        LogUtil.console("[Discard] Card(s)");
        ElementEngine.resetCardsInRound();
    }

    /**
     * Handles card discard in fake round:
     *  - Copies current player's hand cards
     *  - Clears player's hand cards
     *  - Discards selected cards based on selected card list, keeps unselected cards
     *  - Exits fake round, restores round state
     *  - Cancels selected pawn
     */
    private void handleDiscardInFakeRound() {
        ArrayList<Integer> allCardsInRound = new ArrayList<>(ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards());
        ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().clear();

        for (Integer card : allCardsInRound) {
            if (ElementEngine.getCardsInRound().contains(card)) {
                ElementEngine.getTreasureDeck().discard(card);
            } else {
                ElementEngine.getAdventurers()[ForbiddenIslandGame.getRoundNum()].getHandCards().add(card);
            }
        }

        LogUtil.console("[Discard] Card(s)");

        // Restore round state
        ForbiddenIslandGame.setInFakeRound(false);
        ForbiddenIslandGame.setActionCount(ForbiddenIslandGame.getFakeActionCount());
        ForbiddenIslandGame.setFakeActionCount(-1);
        ForbiddenIslandGame.setRoundNum(ForbiddenIslandGame.getFakeRoundNum());
        ForbiddenIslandGame.setFakeRoundNum(-1);

        ElementEngine.selectPawn(-1);
    }
}
