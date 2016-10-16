package net.lemonsoft.LKTurntable.logic;

import net.lemonsoft.LKTurntable.model.PrizeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖品管理类
 * Created by lemonsoft on 16-10-14.
 */
public class PrizeManager {

    private final List<PrizeModel> prizeList;// 奖品列表

    // 单例对象
    private static PrizeManager sharedClient;

    /**
     * 隐藏构造方法
     */
    private PrizeManager() {
        this.prizeList = new ArrayList<>();
    }

    /**
     * 单例方法
     *
     * @return 奖品管理器的实例对象
     */
    public synchronized static PrizeManager sharedInstance() {
        if (sharedClient == null)
            sharedClient = new PrizeManager();
        return sharedClient;
    }

    /**
     * 添加奖品
     *
     * @param prizeModel 要添加的奖品实体对象
     */
    public void addPrize(PrizeModel prizeModel) {
        this.prizeList.add(prizeModel);
    }

    /**
     * 删除指定索引的奖品
     *
     * @param index 要删除的奖品的位置索引
     */
    public void removePrizeByIndex(int index) {
        this.prizeList.remove(index);
    }

    /**
     * 清空奖品列表
     */
    public void removeAllPrizes() {
        this.prizeList.clear();
    }

    /**
     * 设置指定位置的奖品信息
     *
     * @param index      索引位置
     * @param prizeModel 要设置的奖品实体对象
     */
    public void setPrizeByIndex(int index, PrizeModel prizeModel) {
        this.prizeList.set(index >= prizeList.size() ? prizeList.size() : index, prizeModel);
    }

    /**
     * 获取奖品列表
     *
     * @return 奖品列表
     */
    public List<PrizeModel> getPrizeList() {
        return prizeList;
    }

    /**
     * 查询当前奖品的数量
     *
     * @return 奖品的数量数值
     */
    public int countPrizes() {
        return this.prizeList.size();
    }
}
