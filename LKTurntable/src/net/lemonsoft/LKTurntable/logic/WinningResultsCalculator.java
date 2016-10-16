package net.lemonsoft.LKTurntable.logic;

import net.lemonsoft.LKTurntable.model.PrizeModel;

import java.util.List;

/**
 * 中奖结果计算器
 * Created by lemonsoft on 16-10-16.
 */
public class WinningResultsCalculator {

    public static int calculate(List<PrizeModel> prizeModels) {
        double allProbability = 0;// 总概率（中奖率+不中奖率）
        double winningProbability = 0;// 中奖率（IsRealPrize为true且有库存的奖品的中奖率之和）
        int winningPrizeCount = 0;// 能中奖的奖品数量（IsRealPrize为true且有库存的奖品的总数量）
        for (PrizeModel model : prizeModels) {
            allProbability += model.getChance();
            // 忽略没有库存的和不是真正奖品的
            winningProbability +=
                    (model.getCount() > 0 && model.isRealPrize()) ? model.getChance() : 0;
            // 计算真实可以获奖的奖品数量
            winningPrizeCount += (model.getCount() > 0 && model.isRealPrize()) ? 1 : 0;
        }
        Double[] rProbabilities = new Double[prizeModels.size()];
        Double[] rProbabilitiesLine = new Double[prizeModels.size()];
        double rProbabilitiesLinePointer = 0.00;
        for (int i = 0; i < prizeModels.size(); i++) {
            PrizeModel model = prizeModels.get(i);
            rProbabilities[i] = (model.getCount() > 0 && model.isRealPrize()) ?
                    prizeModels.get(i).getChance() / allProbability :// 真实奖品 有库存
                    (model.isRealPrize() ? 0 :// 真实奖品 没有库存
                            // 不是真实奖品（谢谢参与）
                            (model.getChance() +
                                    (allProbability - winningProbability) / (prizeModels.size() - winningPrizeCount))
                                    / allProbability);
            rProbabilities[i] = model.getChance() / allProbability;
            rProbabilitiesLine[i] = rProbabilitiesLinePointer + rProbabilities[i];
            rProbabilitiesLinePointer = rProbabilitiesLine[i];
        }
        double rWinningProbability = winningProbability / allProbability;
        System.out.println("中奖的概率是：" + rWinningProbability);
        System.out.println("中奖率列表为：");
        for (Double p : rProbabilities) {
            System.out.println("   " + p + "   ");
        }
        System.out.println("中奖率LINE为：");
        for (Double p : rProbabilitiesLine) {
            System.out.println("   " + p + "   ");
        }
        double winNumber = Math.random();
        System.out.println("中奖随机数为：" + winNumber);
        for (int i = 0; i < rProbabilitiesLine.length; i++) {
            if (winNumber < rProbabilitiesLine[i])
                return i;
        }
        return rProbabilitiesLine.length - 1;
    }

    /**
     * 计算抽奖转盘最终要旋转成的角度
     *
     * @param winIndex   抽中的索引
     * @param prizeCount 总中奖奖品的数量
     * @return 最终应旋转的角度
     */
    public static double calculateTurntableAimAngle(int winIndex, int prizeCount) {
        if (winIndex > (prizeCount - 1))// 中奖的索引大于最大奖品索引，出错
            return -1;
        else
            // 计算指定奖品对应的转盘角度，并且根据是随机数让其产生随机角度，使其停止的度数更自然，通过×0.85防止最终停止在边线上造成误解
            return 360 - ((360.00 / prizeCount) * (winIndex + (Math.random() - 0.5) * 0.85));
    }

}
