package model.adventurer;


/**
 * 工程师角色类，继承自Adventurer。
 * 拥有特殊属性 shoreUpCount，表示可执行的加固操作次数。
 * 角色ID设为1。
 */
public class Engineer extends Adventurer {

    private int shoreUpCount = 1;

    public Engineer(int order) {
        super(order, "Engineer");
        this.id = 1;
    }


    /**
     * 获取当前剩余可执行的加固次数。
     * @return 剩余加固次数
     */
    public int getShoreUpCount() {
        return shoreUpCount;
    }

    /**
     * 执行一次加固操作，消耗一次加固次数。
     */
    public void ShoreUp() {
        this.shoreUpCount -= 1;
    }

    /**
     * 重置加固次数，通常用于新回合开始时恢复。
     */
    public void resetShoreUpCount() {
        this.shoreUpCount = 1;
    }
}
