package ui.utils;

import com.forbidden.island.adventurer.Adventurer;
import com.forbidden.island.adventurer.Engineer;
import com.forbidden.island.ui.ElementEngine;
import com.forbidden.island.ui.handler.RenderingEngine;
import com.forbidden.island.utils.LogUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class ForbiddenIslandGame {
    // 当前轮数（从0开始计）
    private static int roundNum = 0;
    // 虚拟轮次编号（用于救援阶段）
    private static int fakeRoundNum = -1;
    // 当前玩家已经执行的动作数量（每轮最多3个动作）
    private static int actionCount = 0;
    // 虚拟动作数（用于救援时限制最多2步移动）
    private static int fakeActionCount = 0;
    // 当前游戏玩家总数
    private static int numOfPlayer;
    // 表示本轮是否已经进入“抽取宝藏卡片”和“沉没版块”阶段
    private static boolean stage23Done = false;
    // 是否处于“救援模式”，即有玩家落水需救援
    private static boolean need2save = false;
    // 是否在虚拟回合（处理落水玩家）
    private static boolean inFakeRound = false;
    // 当前落水玩家的 ID 列表
    private static ArrayList<Integer> playerIDinWater;

    /**
     * 初始化游戏，设置玩家数量和水位等级。
     */
    public static void init(int numOfPlayers, int waterLevel) {
        numOfPlayer = numOfPlayers;
        playerIDinWater = new ArrayList<>();

        // 初始化游戏元素引擎（牌堆、角色、地图等）
        ElementEngine.init(numOfPlayers, waterLevel);
        LogUtil.console("Initialise Players...");

        // 初始化渲染引擎（负责图形界面刷新）
        RenderingEngine.init();

        // 初始沉没一些版块
        LogUtil.console("Island starts to sink...");
        RenderingEngine.getFloodRendering().update();
        ElementEngine.getBoard().sinkTiles(ElementEngine.getFloodDeck().getCards());
        ElementEngine.getFloodDeck().discard(); // 将抽到的卡放入弃牌堆
        ElementEngine.getFloodDeck().set2Norm(); // 牌堆恢复为普通模式

        // 游戏开始提示
        LogUtil.console("[ Game Start ! ]");
        LogUtil.console("[ Player " + (roundNum + 1) + " ]\n(" + ElementEngine.getAdventurers()[roundNum].getName() + "'s Round)");
        LogUtil.console("Please Take Up To 3 Actions");
    }

    /**
     * 阶段2和3：抽取2张宝藏卡片，并抽取洪水卡沉没版块。
     */
    public static void Stage23() {
        // 抽取2张宝藏卡
        ElementEngine.getDisplayedTreasureCard().addAll(ElementEngine.getTreasureDeck().getCards());
        actionCount = 3;    // 表示阶段已完成，动作数为3

        RenderingEngine.getTreasureRendering().update();

        // 检查是否抽到“水位上升”卡，如果有，则触发相应处理
        Iterator<Integer> iterator = ElementEngine.getDisplayedTreasureCard().iterator();
        while (iterator.hasNext()) {
            Integer treasureID = iterator.next();
            if (treasureID == 25 || treasureID == 26 || treasureID == 27) { // 特殊卡：水位上升
                ElementEngine.getWaterMeter().WaterRise();  // 增加水位
                ElementEngine.getFloodDeck().putBack2Top(); // 将洪水弃牌堆放回顶端
                ElementEngine.getTreasureDeck().discard(treasureID);     // 弃掉这张卡
                iterator.remove();  // 移除该卡片，避免重复处理
            }
        }

        // 更新界面渲染
//        RenderingEngine.getTreasureRendering().update();
        RenderingEngine.getPlayerRendering().update();
        RenderingEngine.getWaterMeterRendering().update();
        RenderingEngine.getBoardRendering().update();

        // 抽洪水卡沉没相应版块
        RenderingEngine.getFloodRendering().update();
        ElementEngine.getBoard().sinkTiles(ElementEngine.getFloodDeck().getCards());

        ElementEngine.getFloodDeck().discard(); // 将沉没卡加入弃牌堆

        // 标记阶段完成
        stage23Done = true;
    }

    /**
     * 当前玩家轮次结束后的处理逻辑
     */
    public static void RoundEnd() {
        // 若卡片数量超限（手牌+展示卡超过5张），需先弃牌
        if (ElementEngine.getAdventurers()[roundNum].getHandCards().size() + ElementEngine.getDisplayedTreasureCard().size() > 5) {
            LogUtil.console("You Have More Than 5 Cards, Please Discard First!");
            ElementEngine.resetCardsInRound();  // 重置回合相关状态
            return;
        } else {

            // 合并手牌并清空展示区
            ElementEngine.getAdventurers()[roundNum].getHandCards().addAll(ElementEngine.getDisplayedTreasureCard());
            ElementEngine.getDisplayedTreasureCard().clear();
            ElementEngine.selectPawn(-1);   // 取消选中的角色
            ElementEngine.resetCardsInRound();  // 重置回合数据
            RenderingEngine.getTreasureRendering().update();
            RenderingEngine.getPlayerRendering().update();
        }

        // 判断游戏是否失败：所有神殿都沉没
        if (ElementEngine.getBoard().isShrinesFlooded()) {
            LogUtil.console("[!] Shrines And Treasures Are Sunk");
            finish(false);    // 游戏失败
            return;
        }

        // 重置状态，进入下一位玩家回合
        ElementEngine.selectPawn(-1);
        if (ElementEngine.getAdventurers()[roundNum] instanceof Engineer) {
            ((Engineer) ElementEngine.getAdventurers()[roundNum]).resetShoreUpCount();  // 重置工程师的特殊能力使用次数
        }
        actionCount = 0;
        roundNum++;
        roundNum = roundNum % numOfPlayer;  // 回合顺序轮换
        stage23Done = false;

        LogUtil.console("[ Player " + (roundNum + 1) + " ]\n(" + ElementEngine.getAdventurers()[roundNum].getName() + "'s Round)");
        RenderingEngine.getPlayerRendering().update();  // 更新玩家面板
    }

    /**
     * 若有玩家落水，将进入“救援回合”来允许他们移动到相邻格子。
     */
    public static void SavePlayersRound() {
        // 所有落水者已救援，恢复正常回合
        if (playerIDinWater.isEmpty()) {
            roundNum = fakeRoundNum;
            fakeRoundNum = -1;
            actionCount = 3;
            need2save = false;
            inFakeRound = false;
            RenderingEngine.getControllersRendering().update();
            return;
        }

        // 处理下一个落水玩家
        for (Adventurer adventurer : ElementEngine.getAdventurers()) {
            if (playerIDinWater.contains(adventurer.getId())) {
                roundNum = adventurer.getOrder();
                actionCount = 2; // 落水玩家仅能移动2步
                RenderingEngine.getControllersRendering().update();
                playerIDinWater.remove((Integer) adventurer.getId());

                int x = adventurer.getX();
                int y = adventurer.getY();

                // 非特殊角色必须有至少一个可移动的邻接格
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
     * 判断玩家当前位置是否存在可以游泳的邻接格子。
     * 考虑边界情况与“探索者”角色的斜向移动能力。
     */
    private static boolean checkCanSwim(int x, int y, String name) {
        boolean isExplorer = name.equals("Explorer");

        // 普通四方向检查
        boolean up = x > 0 && ElementEngine.getBoard().getTile(x - 1, y).isExist();
        boolean down = x < 5 && ElementEngine.getBoard().getTile(x + 1, y).isExist();
        boolean left = y > 0 && ElementEngine.getBoard().getTile(x, y - 1).isExist();
        boolean right = y < 5 && ElementEngine.getBoard().getTile(x, y + 1).isExist();

        // 探索者额外斜向检查
        boolean upLeft = isExplorer && x > 0 && y > 0 && ElementEngine.getBoard().getTile(x - 1, y - 1).isExist();
        boolean upRight = isExplorer && x > 0 && y < 5 && ElementEngine.getBoard().getTile(x - 1, y + 1).isExist();
        boolean downLeft = isExplorer && x < 5 && y > 0 && ElementEngine.getBoard().getTile(x + 1, y - 1).isExist();
        boolean downRight = isExplorer && x < 5 && y < 5 && ElementEngine.getBoard().getTile(x + 1, y + 1).isExist();

        return up || down || left || right || upLeft || upRight || downLeft || downRight;
    }

    /**
     * 游戏结束逻辑（true 为胜利，false 为失败）
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

    // take an action
    public static void doAction() {
        actionCount += 1;
    }

    // take one more action
    public static void moreAction() {
        actionCount -= 1;
    }

    // getters and setters
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
