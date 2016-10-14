package net.lemonsoft.LKTurntable.model;

/**
 * 模型类 - 奖品
 * Created by lemonsoft on 16-10-14.
 */
public class PrizeModel {
    /**
     * 奖品名称
     */
    private String name;
    /**
     * 奖品的中奖率，0-1之间的浮点型
     */
    private double chance;
    /**
     * 奖品的剩余库存数量
     */
    private int count;

    public PrizeModel(String name, double chance, int count) {
        this.name = name;
        this.setChance(chance);
        this.setCount(count);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance > 1 ? 1 : (chance < 0 ? 0 : chance);// 保证中奖率在0-1之间
    }

    public double getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count > 0 ? count : 0;// 保证库存数量为大于等于0
    }

    /**
     * 减少奖品库存量，若要减少的库存量比当前剩余数量大的话，那么只能把剩下的所有库存全部清空
     *
     * @param count 要减少的库存量
     * @return 减少之后的库存量
     */
    public synchronized int sub(int count) {
        this.count -= count > this.count ? this.count : count;// 保证库存不会被减成负数
        return this.count;
    }
}
