package com.forbidden.island.view;

import com.forbidden.island.controller.ForbiddenIslandGame;
import com.forbidden.island.model.adventurer.*;
import com.forbidden.island.model.cards.FloodDeck;
import com.forbidden.island.model.cards.TreasureDeck;
import com.forbidden.island.model.enums.TileStatus;
import com.forbidden.island.view.handler.RenderingEngine;
import com.forbidden.island.utils.LogUtil;
import com.forbidden.island.utils.Map;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ElementEngine class is responsible for managing the game's data layer and implementing core game operations.
 * It maintains game elements including card decks, adventurers, game map, and current game state selections.
 * Main functionalities include game initialization, player action management (movement, shore up, card passing),
 * and interaction with game tiles.
 */
public class ElementEngine {
    /**
     * Coordinates for special action target tile, initialized to invalid values {-1, -1}
     */
    private static final int[] SpecialActionTile = {-1, -1};

    /**
     * Game board managing all tile states
     */
    private static TileBoard board;

    /**
     * Treasure card deck
     */
    private static TreasureDeck treasureDeck;

    /**
     * Flood card deck
     */
    private static FloodDeck floodDeck;

    /**
     * Water meter indicating current water level in the game
     */
    private static WaterMeter waterMeter;

    /**
     * Array of all adventurers, length equals number of players
     */
    private static Adventurer[] adventurers;

    /**
     * List of tile numbers used in current game map
     */
    private static ArrayList<Integer> tiles;

    /**
     * List of treasure cards displayed in the interface
     */
    private static ArrayList<Integer> displayedTreasureCard;

    /**
     * Stack of cards selected in current round for temporary storage of cards related to player actions
     */
    private static ArrayList<Integer> cardsInRound;

    /**
     * Currently selected player pawn number, -1 indicates no selection
     */
    private static int selectedPawn = -1;

    /**
     * List of multiple player pawn numbers selected in current round
     */
    private static ArrayList<Integer> selectedPawns;

    private static int navigatorMovesLeft = 2;  // 记录领航员当前行动还能移动几次其他玩家

    /**
     * Initializes game state including card decks, water meter, adventurer roles, and map tiles.
     * Randomly determines player roles and initializes each adventurer's hand cards.
     *
     * @param numOfPlayers Number of players
     * @param waterLevel Initial water level
     */
    public static void init(int numOfPlayers, int waterLevel) {
        Map.setLocation();  // Initialize map locations (tile coordinates, specific positions, etc.)

        // Initialize flood deck for managing flood-related events
        floodDeck = new FloodDeck();

        // Initialize treasure deck for managing treasure-related events
        treasureDeck = new TreasureDeck();

        // Initialize water meter with initial water level
        waterMeter = new WaterMeter(waterLevel);

        // Initialize adventurer array with length equal to number of players
        adventurers = new Adventurer[numOfPlayers];

        // Initialize displayed treasure card list (for interface display)
        displayedTreasureCard = new ArrayList<>();

        // Initialize current round card list
        cardsInRound = new ArrayList<>();

        // Initialize selected character list (for round selection)
        selectedPawns = new ArrayList<>();

        // Initialize character list and randomly select numOfPlayers roles
        ArrayList<Integer> playerList = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            playerList.add(i);
        }

        // Shuffle role numbers to ensure random role assignment
        Collections.shuffle(playerList);

        // Select first numOfPlayers role numbers from shuffled list
        ArrayList<Integer> players = new ArrayList<>(playerList.subList(0, numOfPlayers));

        // Create corresponding role instances for each player based on role numbers
        for (int i = 0; i < players.size(); i++) {
            switch (players.get(i)) {
                case 0:
                    adventurers[i] = new Diver(i);
                    break;
                case 1:
                    adventurers[i] = new Engineer(i);
                    break;
                case 2:
                    adventurers[i] = new Explorer(i);
                    break;
                case 3:
                    adventurers[i] = new Messenger(i);
                    break;
                case 4:
                    adventurers[i] = new Navigator(i);
                    break;
                case 5:
                    adventurers[i] = new Pilot(i);
                    break;
                default:
                    // Handle unexpected cases, print warning or throw exception
                    throw new IllegalArgumentException("Invalid role number: " + players.get(i));
            }
        }

        // Initialize map tile number list, numbers 1-24 represent different tiles
        tiles = new ArrayList<>();
        for (int i = 1; i <= 24; i++) {
            tiles.add(i);
        }

        // Shuffle map tile number list to ensure random map distribution each game
        Collections.shuffle(tiles);

        // Create board object, passing selected player numbers and shuffled tile list
        board = new TileBoard(players, tiles);

        // Deal initial hand cards to each adventurer using non-water-rise cards from treasure deck
        for (Adventurer adventurer : adventurers) {
            adventurer.setHandCards(treasureDeck.getNoRiseCards());
        }
    }

    /**
     * Selects a treasure card to add to current round selection stack.
     * Can select from hand cards or display area.
     *
     * @param isFromHands Whether selection is from hand cards
     * @param index Index of selected card in hand or display area
     */
    public static void selectTreasureCard(boolean isFromHands, int index) {
        int cardInUse;
        if (isFromHands) {
            cardInUse = adventurers[ForbiddenIslandGame.getRoundNum()].getHandCards().get(index);
        } else {
            cardInUse = displayedTreasureCard.get(index);
        }
        if (cardsInRound.size() < 5) {
            cardsInRound.add(cardInUse);
        }
    }

    /**
     * Selects a player pawn. If -1 is passed, resets round selections for pawns and cards.
     *
     * @param index Index of selected pawn
     */
    public static void selectPawn(int index) {
        selectedPawn = index;
        selectedPawns.add(selectedPawn);
        if (index == -1) {
            cardsInRound.clear();
            selectedPawns.clear();
        }
    }

    /**
     * Determines if current player can perform movement or shore up operations based on given coordinates,
     * and updates board state accordingly.
     *
     * @param coords Target tile coordinates [x, y]
     */
    public static void nextTile(int[] coords) {
        setSpecialActionTile(coords);

        Adventurer current = adventurers[ForbiddenIslandGame.getRoundNum()];
        int currX = current.getX();
        int currY = current.getY();
        int targetX = coords[0];
        int targetY = coords[1];

        // Check relative position of target tile to current position
        boolean isNearY = isNearVertically(currX, currY, targetX, targetY);
        boolean isNearX = isNearHorizontally(currX, currY, targetX, targetY);
        boolean isOnTile = isOnSameTile(currX, currY, targetX, targetY);
        boolean isNearDiagonally = isNearDiagonally(currX, currY, targetX, targetY);

        // Check if normal movement conditions are met, including special role abilities
        if (canNormalMove(current, targetX, targetY, isNearX, isNearY, isNearDiagonally, isOnTile)) {
            handleNormalMove(current, targetX, targetY, isNearX, isNearY);
        }
        // Check if current tile can be shored up (flooded state)
        else if (canShoreUpCurrentTile(isOnTile, targetX, targetY)) {
            handleShoreUpCurrentTile(current, targetX, targetY);
        }
        // Check conditions for Diver's special diving movement
        else if (canDiverSpecialMove(current, targetX, targetY)) {
            handleDiverSpecialMove(current, targetX, targetY);
        }
        // Check conditions for Navigator's special movement ability
        else if (canNavigatorSpecialMove(current, targetX, targetY)) {
            handleNavigatorSpecialMove(current, targetX, targetY);
        }
        // If no movement or shore up conditions are met, reset related flags
        else {
            board.setCanMove(false);
            board.setCanShoreUp(false);
        }
    }

    /**
     * Records current special action tile
     * @param coords Coordinate array [x, y]
     */
    private static void setSpecialActionTile(int[] coords) {
        SpecialActionTile[0] = coords[0];
        SpecialActionTile[1] = coords[1];
    }

    /**
     * Checks if target tile is vertically adjacent to current tile (same x, y differs by 1)
     */
    private static boolean isNearVertically(int currX, int currY, int targetX, int targetY) {
        return currX == targetX && Math.abs(currY - targetY) == 1;
    }

    /**
     * Checks if target tile is horizontally adjacent to current tile (same y, x differs by 1)
     */
    private static boolean isNearHorizontally(int currX, int currY, int targetX, int targetY) {
        return currY == targetY && Math.abs(currX - targetX) == 1;
    }

    /**
     * Checks if target tile is exactly the same as current position
     */
    private static boolean isOnSameTile(int currX, int currY, int targetX, int targetY) {
        return currX == targetX && currY == targetY;
    }

    /**
     * Checks if target tile is diagonally adjacent to current tile (x differs by 1 and y differs by 1)
     */
    private static boolean isNearDiagonally(int currX, int currY, int targetX, int targetY) {
        return Math.abs(currX - targetX) == 1 && Math.abs(currY - targetY) == 1;
    }

    /**
     * Checks if player can perform normal movement:
     * - Regular adjacent tiles (up/down/left/right) and tile exists
     * - Explorer role can move diagonally and tile exists
     * - Pilot role can move to any non-current tile
     */
    private static boolean canNormalMove(Adventurer current, int x, int y, boolean isNearX, boolean isNearY, boolean isNearDiagonally, boolean isOnTile) {
        Tile targetTile = board.getTile(x, y);
        boolean exists = targetTile.isExist();
        String role = current.getName();

        return ((isNearX || isNearY) && exists)
                || (isNearDiagonally && exists && "Explorer".equals(role))
                || ("Pilot".equals(role) && !isOnTile);
    }

    /**
     * Handles normal movement logic:
     * - Sets new position for character
     * - If target tile is flooded, checks if can shore up (except Pilot needs to be adjacent)
     * - Sets shore up flag
     */
    private static void handleNormalMove(Adventurer current, int x, int y, boolean isNearX, boolean isNearY) {
        current.setMoveTarget(x, y);
        board.setCanMove(true);
        Tile tile = board.getTile(x, y);

        if (tile.getStatus() == TileStatus.Flooded) {
            if (!"Pilot".equals(current.getName()) || ((isNearX || isNearY) && tile.isExist())) {
                current.setShoreUp(x, y);
                board.setCanShoreUp(true);
            } else {
                board.setCanShoreUp(false);
            }
        } else {
            board.setCanShoreUp(false);
        }
    }

    /**
     * Checks if current tile can be shored up (tile exists and is in flooded state)
     */
    private static boolean canShoreUpCurrentTile(boolean isOnTile, int x, int y) {
        Tile tile = board.getTile(x, y);
        return isOnTile && tile.isExist() && tile.getStatus() == TileStatus.Flooded;
    }

    /**
     * Handles shore up action for current tile
     */
    private static void handleShoreUpCurrentTile(Adventurer current, int x, int y) {
        board.setCanMove(false);
        current.setShoreUp(x, y);
        board.setCanShoreUp(true);
    }

    /**
     * Checks if Diver can perform special diving movement:
     * - Character is Diver
     * - Target tile exists
     * - Either in rescue mode OR can reach target through flooded/missing tiles
     */
    private static boolean canDiverSpecialMove(Adventurer current, int x, int y) {
        if (!"Diver".equals(current.getName())) return false;
        
        Tile targetTile = board.getTile(x, y);
        if (!targetTile.isExist()) return false;
        
        // 在救援模式下的特殊处理
        if (ForbiddenIslandGame.isInFakeRound() && ForbiddenIslandGame.isNeed2save()) {
            return true;
        }
        
        // 检查是否可以通过淹没/缺失的板块到达目标
        return canReachThroughFloodedTiles(current.getX(), current.getY(), x, y);
    }

    /**
     * 检查潜水员是否可以通过淹没/缺失的板块到达目标位置
     * 使用广度优先搜索(BFS)找到最短路径
     */
    private static boolean canReachThroughFloodedTiles(int startX, int startY, int targetX, int targetY) {
        // 如果目标就是相邻格子，直接返回true
        if (isNearVertically(startX, startY, targetX, targetY) || 
            isNearHorizontally(startX, startY, targetX, targetY)) {
            return true;
        }
        
        // 使用BFS查找路径
        ArrayList<int[]> queue = new ArrayList<>();
        boolean[][] visited = new boolean[6][6];
        queue.add(new int[]{startX, startY, 0}); // 第三个参数是步数
        visited[startX][startY] = true;
        
        while (!queue.isEmpty()) {
            int[] current = queue.remove(0);
            int currX = current[0];
            int currY = current[1];
            int steps = current[2];
            
            // 检查四个方向
            int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}}; // 上下左右
            for (int[] dir : directions) {
                int nextX = currX + dir[0];
                int nextY = currY + dir[1];
                
                // 检查边界
                if (nextX < 0 || nextX >= 6 || nextY < 0 || nextY >= 6) continue;
                if (visited[nextX][nextY]) continue;
                
                // 获取下一个格子
                Tile nextTile = board.getTile(nextX, nextY);
                
                // 如果是目标位置
                if (nextX == targetX && nextY == targetY) {
                    return true;
                }
                
                // 如果是淹没或缺失的板块，可以继续搜索
                if (!nextTile.isExist() || nextTile.getStatus() == TileStatus.Flooded) {
                    queue.add(new int[]{nextX, nextY, steps + 1});
                    visited[nextX][nextY] = true;
                }
            }
        }
        
        return false;
    }

    /**
     * Handles Diver's special diving movement:
     * For normal diving movement, just set the target position
     * For rescue mode, use the original closest tile logic
     */
    private static void handleDiverSpecialMove(Adventurer current, int targetX, int targetY) {
        if (ForbiddenIslandGame.isInFakeRound() && ForbiddenIslandGame.isNeed2save()) {
            // 原有的救援模式逻辑
            ArrayList<int[]> closestCoords = new ArrayList<>();
            double minDistance = Double.MAX_VALUE;
            
            int currX = current.getX();
            int currY = current.getY();
            
            for (int i = 0; i < board.getTileMap().length; i++) {
                for (int j = 0; j < board.getTileMap()[i].length; j++) {
                    Tile tile = board.getTile(i, j);
                    if (!tile.isExist() || (i == currX && j == currY)) continue;
                    
                    double dist = Math.sqrt(Math.pow(i - currX, 2) + Math.pow(j - currY, 2));
                    if (dist < minDistance) {
                        closestCoords.clear();
                        minDistance = dist;
                        closestCoords.add(new int[]{i, j});
                    } else if (dist == minDistance) {
                        closestCoords.add(new int[]{i, j});
                    }
                }
            }
            
            board.setCanMove(false);
            for (int[] coord : closestCoords) {
                if (coord[0] == targetX && coord[1] == targetY) {
                    current.setMoveTarget(targetX, targetY);
                    board.setCanMove(true);
                    break;
                }
            }
        } else {
            // 正常潜水移动逻辑
            current.setMoveTarget(targetX, targetY);
            board.setCanMove(true);
        }
        board.setCanShoreUp(false);
    }

    /**
     * 检查是否是 Navigator 的特殊移动能力
     */
    private static boolean canNavigatorSpecialMove(Adventurer current, int targetX, int targetY) {
        if (!"Navigator".equals(current.getName()) || selectedPawn == -1 || navigatorMovesLeft <= 0) return false;
        
        Adventurer targetPlayer = adventurers[selectedPawn];
        Tile targetTile = board.getTile(targetX, targetY);
        
        // 检查目标地形是否存在
        if (!targetTile.isExist()) return false;
        
        // 检查目标位置是否与目标玩家相邻
        boolean isNearTarget = isNearVertically(targetPlayer.getX(), targetPlayer.getY(), targetX, targetY) || 
                              isNearHorizontally(targetPlayer.getX(), targetPlayer.getY(), targetX, targetY);
        
        return isNearTarget;
    }

    /**
     * 处理 Navigator 的特殊移动
     */
    private static void handleNavigatorSpecialMove(Adventurer current, int targetX, int targetY) {
        Adventurer targetPlayer = adventurers[selectedPawn];
        
        // 设置目标玩家的移动目标
        targetPlayer.setMoveTarget(targetX, targetY);
        board.setCanMove(true);
        
        // 检查目标地形是否淹没
        Tile targetTile = board.getTile(targetX, targetY);
        if (targetTile.getStatus() == TileStatus.Flooded) {
            targetPlayer.setShoreUp(targetX, targetY);
            board.setCanShoreUp(true);
        } else {
            board.setCanShoreUp(false);
        }
        
        // 减少剩余移动次数
        navigatorMovesLeft--;
    }

    /**
     * Executes player movement action, removing player from original tile and placing on target tile.
     */
    public static void moveTo() {
        int roundNum = ForbiddenIslandGame.getRoundNum();
        Adventurer currentPlayer = adventurers[roundNum];
        
        // 如果是 Navigator 在移动其他玩家
        if ("Navigator".equals(currentPlayer.getName()) && selectedPawn != -1) {
            Adventurer targetPlayer = adventurers[selectedPawn];
            // 从原位置移除目标玩家
            board.getTile(targetPlayer.getX(), targetPlayer.getY()).moveOff(targetPlayer);
            targetPlayer.Move();
            // 在新位置添加目标玩家
            board.getTile(targetPlayer.getX(), targetPlayer.getY()).moveOn(targetPlayer.getId());
            
            // 如果已经移动了两次或没有剩余移动次数，消耗一个行动点并重置状态
            if (navigatorMovesLeft <= 0) {
                ForbiddenIslandGame.doAction();
                navigatorMovesLeft = 2;  // 重置移动次数
                selectedPawn = -1;  // 重置选择的玩家
            }
        } else {
            // 正常移动逻辑
            board.getTile(currentPlayer.getX(), currentPlayer.getY()).moveOff(currentPlayer);
            currentPlayer.Move();
            board.getTile(currentPlayer.getX(), currentPlayer.getY()).moveOn(currentPlayer.getId());
            ForbiddenIslandGame.doAction();
        }
    }

    /**
     * Executes shore up action, restoring target tile state (removing flooded state).
     */
    public static void shoreUp() {
        board.getTile(adventurers[ForbiddenIslandGame.getRoundNum()].getShoreUpX(), adventurers[ForbiddenIslandGame.getRoundNum()].getShoreUpY()).shoreUp();
    }

    /**
     * Implements functionality to pass a card to another player.
     * If receiver's hand is full (5 cards), enters fake round to let receiver discard first.
     *
     * @return Whether card passing was successful
     */
    public static boolean passTo() {
        if (!isValidPassToRequest()) {
            LogUtil.console("Please Select A Card And A player To Pass A Card To");
            return false;
        }

        if (isReceiverHandFull(selectedPawn)) {
            handleReceiverHandFull(selectedPawn);
            return false;
        }

        return doPassCard(selectedPawn, cardsInRound.get(0));
    }

    /**
     * Checks if pass request is valid:
     * - Has target player been selected
     * - Are there cards selected in current round
     */
    private static boolean isValidPassToRequest() {
        return selectedPawn != -1 && !cardsInRound.isEmpty();
    }

    /**
     * Checks if receiver's hand is full (5 cards)
     */
    private static boolean isReceiverHandFull(int receiverId) {
        return adventurers[receiverId].getHandCards().size() == 5;
    }

    /**
     * Handles case where receiver's hand is full, enters fake round to require card discard
     */
    private static void handleReceiverHandFull(int receiverId) {
        LogUtil.console("Player Has 5 Hand.\n Please Discard Card(s) Before Receiving a Card From You");
        cardsInRound.clear();

        // Enter fake round state, pause current round
        ForbiddenIslandGame.setInFakeRound(true);
        ForbiddenIslandGame.setFakeRoundNum(ForbiddenIslandGame.getRoundNum());
        ForbiddenIslandGame.setFakeActionCount(ForbiddenIslandGame.getActionCount());

        // Switch round to receiver, give 3 actions to discard cards
        ForbiddenIslandGame.setRoundNum(receiverId);
        ForbiddenIslandGame.setActionCount(3);

        LogUtil.console("Please Select Card(s) " + adventurers[receiverId].getName() +
                " Would Like To Discard And Redo [Pass To]");
        RenderingEngine.getPlayerRendering().update();
    }

    /**
     * Executes actual card passing operation:
     * - Adds card to receiver
     * - Removes card from current player's hand
     *
     * @param receiverId Receiver player id
     * @param cardId Card id to pass
     * @return Whether passing was successful
     */
    private static boolean doPassCard(int receiverId, int cardId) {
        // Add card to receiver
        ArrayList<Integer> cardList = new ArrayList<>();
        cardList.add(cardId);
        adventurers[receiverId].setHandCards(cardList);

        // Remove card from current player's hand
        adventurers[ForbiddenIslandGame.getRoundNum()].getHandCards().remove(Integer.valueOf(cardId));

        return true;
    }

    // Getters for game elements
    public static TileBoard getBoard() {
        return board;
    }

    public static ArrayList<Integer> getTilesArray() {
        return tiles;
    }

    public static FloodDeck getFloodDeck() {
        return floodDeck;
    }

    public static TreasureDeck getTreasureDeck() {
        return treasureDeck;
    }

    public static WaterMeter getWaterMeter() {
        return waterMeter;
    }

    public static String getWaterMeterImg() {
        return waterMeter.getImg();
    }

    public static Adventurer[] getAdventurers() {
        return adventurers;
    }

    public static int getFloodCardCount() {
        return waterMeter.getFloodCardCount();
    }

    public static ArrayList<Integer> getDisplayedTreasureCard() {
        return displayedTreasureCard;
    }

    public static ArrayList<Integer> getCardsInRound() {
        return cardsInRound;
    }

    public static void resetCardsInRound() {
        cardsInRound.clear();
    }

    public static int[] getSpecialActionTile() {
        return SpecialActionTile;
    }

    public static void resetSpecialActionTile() {
        SpecialActionTile[0] = -1;
        SpecialActionTile[1] = -1;
    }

    public static int getSelectedPawn() {
        return selectedPawn;
    }

    public static ArrayList<Integer> getSelectedPawns() {
        return selectedPawns;
    }
}
