package com.forbidden.island.view;

import com.forbidden.island.ForbiddenIslandGame;
import com.forbidden.island.ui.handler.RenderingEngine;
import com.forbidden.island.utils.LogUtil;
import com.forbidden.island.utils.Map;

import java.util.ArrayList;

/**
 * TileBoard 类表示游戏地图的数据结构，由 Tile（方块）组成
 */
public class TileBoard {
    // 6x6 游戏地图（方块）数组
    private final Tile[][] tileMap;
    // 所有方块的 ID 列表（顺序与地图布置一致）
    private final ArrayList<Integer> tiles;
    // 是否允许当前玩家移动
    private boolean canMove;
    // 是否允许当前玩家加固（shore up）方块
    private boolean canShoreUp;

    /**
     * 构造函数，初始化游戏地图
     * @param players 玩家 ID 列表
     * @param tiles 方块 ID 列表（已洗牌的）
     */
    public TileBoard(ArrayList<Integer> players, ArrayList<Integer> tiles) {
        this.tiles = tiles;
        tileMap = new Tile[6][6]; // 初始化 6x6 地图网格
        int tileIdx = 0; // 当前正在处理的 tile 索引

        // 遍历地图格子
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int flatIndex = i * 6 + j;

                // 如果是空白格子（不放置任何方块）
                if (Map.blankLayout.contains(flatIndex)) {
                    tileMap[i][j] = new Tile(false); // 创建空白 Tile
                } else {
                    int tileId = this.tiles.get(tileIdx);

                    // 如果此方块上有玩家初始放置
                    if (players.contains(tileId - 9)) {
                        int playerID = tileId - 9;
                        tileMap[i][j] = new Tile(tileId, playerID, true); // 初始化带玩家的方块
                        // 设置玩家的初始位置
                        ElementEngine.getAdventurers()[players.indexOf(playerID)].setPosition(i, j);
                    } else {
                        tileMap[i][j] = new Tile(tileId, true); // 正常方块
                    }
                    tileIdx++; // 移动到下一个 tile
                }
            }
        }
    }

    /**
     * 下沉一组方块
     * @param sinkTiles 要下沉的方块 ID 列表
     */
    public void sinkTiles(ArrayList<Integer> sinkTiles) {
        for (int sinkTile : sinkTiles) {
            // 获取该 tile 的坐标
            int[] coords = Map.coordinatesMatcher.get(this.tiles.indexOf(sinkTile));

            // 如果该 tile 被完全移除（下沉两次）
            if (tileMap[coords[0]][coords[1]].sinkTile()) {
                // 从 flood deck 中移除该卡片
                ElementEngine.getFloodDeck().removeFloodCard(sinkTile);

                // 如果此 tile 上仍有玩家
                if (!tileMap[coords[0]][coords[1]].getPlayerOnBoard().isEmpty()) {
                    for (int player : tileMap[coords[0]][coords[1]].getPlayerOnBoard()) {
                        LogUtil.console(Map.adventurerMatcher.get(player) + " Has Fallen Into Sea");
                    }

                    // 标记需要营救
                    ForbiddenIslandGame.setNeed2save(true);
                    ForbiddenIslandGame.setPlayerIDinWater(tileMap[coords[0]][coords[1]].getPlayerOnBoard());

                    // 清除 tile 上的所有玩家
                    tileMap[coords[0]][coords[1]].getPlayerOnBoard().clear();
                }
            }
        }

        // 如果有玩家掉入水中，触发假回合以进行营救逻辑
        if (ForbiddenIslandGame.isNeed2save()) {
            ForbiddenIslandGame.setInFakeRound(true); // 开启假回合
            ForbiddenIslandGame.setFakeRoundNum(ForbiddenIslandGame.getRoundNum()); // 保存当前回合数
            ForbiddenIslandGame.SavePlayersRound(); // 保存玩家状态
        }

        // 重新渲染游戏地图
        RenderingEngine.getBoardRendering().update();
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public void setCanShoreUp(boolean canShoreUp) {
        this.canShoreUp = canShoreUp;
    }

    public boolean isShrinesFlooded() {
        int[] isShrinesFlooded = {1, 1, 1, 1};
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                switch (tileMap[i][j].getTileId()) {
                    case 1:
                    case 2:
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[0]--;
                        }
                        break;
                    case 3:
                    case 4:
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[1]--;
                        }
                        break;
                    case 5:
                    case 6:
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[2]--;
                        }
                        break;
                    case 7:
                    case 8:
                        if (!tileMap[i][j].isExist() && tileMap[i][j].isUnCaptured()) {
                            isShrinesFlooded[3]--;
                        }
                        break;
                }
            }
        }
        for (int isFlooded : isShrinesFlooded) {
            if (isFlooded == -1) {
                return true;
            }
        }
        return false;
    }

    public boolean isCanShoreUp() {
        return canShoreUp;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public Tile getTile(int x, int y) {
        return tileMap[x][y];
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }
}
