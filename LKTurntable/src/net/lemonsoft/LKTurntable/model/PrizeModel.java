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
    /**
     * 是否真实中奖，如果该奖品为谢谢参与等没有真中奖的时候，该属性应该为false
     */
    private boolean isRealPrize;

    public PrizeModel(String name, double chance, int count, boolean isRealPrize) {
        this.name = name;
        this.setChance(chance);
        this.setCount(count);
        this.isRealPrize = isRealPrize;
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

    public boolean isRealPrize() {
        return isRealPrize;
    }

    public void setRealPrize(boolean realPrize) {
        isRealPrize = realPrize;
    }

    /**
     * 减少奖品库存量，若要减少的库存量比当前剩余数量大的话，那么只能把剩下的所有库存全部清空
     * 此方法为测试阶段使用，实际上线使用的话通常都是从数据库中取奖品数量，而不是掉此方法来动态减少
     *
     * @param count 要减少的库存量
     * @return 减少之后的库存量
     */
    public synchronized int sub(int count) {
        if (this.isRealPrize)// 非真实商品不参加减少库存的操作
            this.count -= count > this.count ? this.count : count;// 保证库存不会被减成负数
        return this.count;
    }
}
