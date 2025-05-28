package com.forbidden.island.view;

import com.forbidden.island.ForbiddenIslandGame;
import com.forbidden.island.model.adventurer.Adventurer;
import com.forbidden.island.enums.TileStatus;
import com.forbidden.island.utils.LogUtil;

import java.util.ArrayList;

/**
 * Tile 类表示游戏地图上的一个单元格，
 * 它存储了关于该格子（图块）的一切信息，包括图像路径、状态、是否存在、是否被夺宝、以及在该格子上的冒险者。
 */
public class Tile {
    // 图块 ID（唯一标识该 Tile），默认值为 -1
    private int tileId = -1;
    // 图块的当前状态（正常、淹没或沉没）
    private TileStatus status;
    // 图像路径，用于界面展示图块（完整路径 = imgFolder + imgFile）
    private String img;
    // 图像文件夹路径，默认为 "/Tiles/" 或 "/SubmersedTiles/"
    private String imgFolder;
    // 图像文件名，格式如 "1.png"，可根据 tileId 自动生成
    private String imgFile;
    // 标识该图块是否仍存在于地图中（沉没后将设为 false）
    private boolean isExist;
    // 当前站在该图块上的冒险者 ID 列表（按 Integer 存储）
    private ArrayList<Integer> adventurersOnBoard;
    // 是否已经在该图块上夺得了宝物
    private boolean isCaptured = false;

    private static final int FOOLS_LANDING_TILE_ID = 14;


    /**
     * 创建一个初始状态的图块，图块上已经有一个冒险者。
     *
     * @param tileId 图块编号
     * @param playerID 初始位于该图块的玩家 ID
     * @param isExist 图块是否存在
     */
    public Tile(int tileId, int playerID, boolean isExist) {
        this.adventurersOnBoard = new ArrayList<>();
        this.tileId = tileId;
        this.status = TileStatus.Normal;
        this.imgFolder = "/Tiles/";
        this.imgFile = tileId + ".png";
        this.img = imgFolder + imgFile;
        this.adventurersOnBoard.add(playerID);
        this.isExist = isExist;
    }

    /**
     * 创建一个初始状态的图块，图块上没有冒险者。
     *
     * @param tileId 图块编号
     * @param isExist 图块是否存在
     */
    public Tile(int tileId, boolean isExist) {
        this.adventurersOnBoard = new ArrayList<>();
        this.tileId = tileId;
        this.status = TileStatus.Normal;
        this.imgFolder = "/Tiles/";
        this.imgFile = tileId + ".png";
        this.img = imgFolder + imgFile;
        this.isExist = isExist;
    }

    /**
     * 创建一个空的占位图块（用于边界填充或不可访问区域）。
     *
     * @param isExist 图块是否存在
     */
    public Tile(boolean isExist) {
        this.isExist = isExist;
    }

    /**
     * 将一个玩家移动到该图块上（将其 ID 添加到列表中）。
     *
     * @param playerID 玩家 ID
     */
    public void moveOn(int playerID) {
        this.adventurersOnBoard.add(playerID);
    }

    /**
     * 将一个玩家从该图块移除（移动离开）。
     *
     * @param adventurer 要移除的冒险者对象
     */
    public void moveOff(Adventurer adventurer) {
        adventurersOnBoard.remove((Integer) adventurer.getId());
    }

    /**
     * 判断两个冒险者是否同时在该图块上。
     * 用于判断是否能进行物品交换等操作。
     *
     * @param sender 发起者
     * @param receiver 接收者
     * @return 是否都在当前图块上
     */
    public boolean CanPass(Adventurer sender, Adventurer receiver) {
        return adventurersOnBoard.contains(sender.getId()) && adventurersOnBoard.contains(receiver.getId());
    }

    /**
     * 尝试将该图块从淹没状态复原（挽救图块）。
     * 只有在状态为 Flooded（淹没）时才允许操作。
     */
    public void shoreUp() {
        if (status == TileStatus.Flooded) {
            status = TileStatus.Normal;
            this.imgFolder = "/Tiles/";
            this.img = imgFolder + imgFile;
        } else {
            System.out.println("ERROR! Tile is not flooded");
        }
    }

    /**
     * 将该图块下沉（改变状态并更新图像）。
     *
     * 正常 → 淹没：更新图像为“淹没版”
     * 淹没 → 沉没：移除图像和存在标记，若为重要地点（如Fool's Landing），结束游戏
     *
     * @return true 如果图块被彻底沉没，false 如果只是淹没
     */
    public boolean sinkTile() {
        if (status == TileStatus.Normal) {
            status = TileStatus.Flooded;
            imgFolder = "/SubmersedTiles/";
            img = imgFolder + imgFile;
            return false;
        } else if (status == TileStatus.Flooded) {
            status = TileStatus.Sunk;
            img = null;
            isExist = false;
            if (tileId == FOOLS_LANDING_TILE_ID) { // tileId 14 被设定为“Fool's Landing”，游戏关键位置
                ForbiddenIslandGame.finish(false);  // 结束游戏（失败）
                LogUtil.console("[!] Fool's Landing Is Flooded!");
            }
            return true;
        } else {
            LogUtil.console("ERROR! This tile has sunk");
            return true;
        }
    }

    /**
     * 设置该图块为已夺宝状态，并更新图像文件。
     * 通常用于玩家成功夺得图块上的宝物后调用。
     */
    public void setCaptured() {
        isCaptured = true;
        this.imgFile = tileId + 24 + ".png";    // 特殊图像表示“已夺宝”
        this.img = imgFolder + imgFile;
    }

    /**
     * 判断该图块是否还未被夺宝。
     *
     * @return true 如果还未被夺宝
     */
    public boolean isUnCaptured() {
        return !isCaptured;
    }

    public int getTileId() {
        return tileId;
    }

    public TileStatus getStatus() {
        return status;
    }

    public String getImg() {
        return img;
    }

    public boolean isExist() {
        return isExist;
    }

    public ArrayList<Integer> getPlayerOnBoard() {
        return adventurersOnBoard;
    }
}
