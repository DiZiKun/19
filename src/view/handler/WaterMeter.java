package view.handler;

import com.forbidden.island.ForbiddenIslandGame;
import com.forbidden.island.utils.LogUtil;

/**
 * WaterMeter 类表示禁岛游戏中的“水位计”。
 * 它记录当前水位等级，并根据等级决定每轮要抽取的洪水卡数量。
 */
public class WaterMeter {
    // 当前水位等级（范围通常是 1-10）
    private int waterLevel;

    // 当前每轮需要抽取的洪水卡数量
    private int floodCardCount;

    // 当前水位等级对应的图像路径
    private String img;

    /**
     * 构造方法：初始化水位计。
     *
     * @param waterLevel 初始水位等级（由玩家选择难度决定）
     */
    public WaterMeter(int waterLevel) {
        this.waterLevel = waterLevel;
        // 设置水位图像路径（用于 UI 显示）
        this.img = "/WaterMeter/" + waterLevel + ".png";

        // 根据水位等级设置每轮抽取的洪水卡数量
        setFloodCardCount();
    }

    /**
     * 执行水位上升操作（当抽到“Water Rise!”卡牌时调用）。
     * 增加水位等级，并更新图像与洪水卡数量。
     * 若水位升至骷髅等级（10），游戏失败。
     */
    public void WaterRise() {
        // 增加水位
        waterLevel += 1;

        // 更新图像路径
        img = "/WaterMeter/" + waterLevel + ".png";

        // 更新当前每轮洪水卡数量
        setFloodCardCount();

        // 若水位达到最大值（骷髅图标），结束游戏
        if (waterLevel == 10) {
            LogUtil.console("[!] Water Level Reaches The Skull And Crossbones");

            // 触发游戏失败（false 表示失败）
            ForbiddenIslandGame.finish(false);
        }
    }

    /**
     * 根据当前水位等级设置每轮应抽取的洪水卡数量。
     */
    private void setFloodCardCount() {
        switch (waterLevel) {
            case 1:
            case 2:
                floodCardCount = 2; // 最低等级：每轮抽 2 张
                break;
            case 3:
            case 4:
            case 5:
                floodCardCount = 3; // 中等水位：抽 3 张
                break;
            case 6:
            case 7:
                floodCardCount = 4; // 高水位：抽 4 张
                break;
            case 8:
            case 9:
                floodCardCount = 5; // 极高水位：抽 5 张
                break;

            // 水位等级 10（骷髅）不再抽卡，因为游戏结束
        }
    }

    public int getFloodCardCount() {
        return floodCardCount;
    }

    public String getImg() {
        return img;
    }
}
