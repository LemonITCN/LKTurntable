package net.lemonsoft.LKTurntable.viewController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.web.WebView;
import net.lemonsoft.LKTurntable.category.LKStage;
import net.lemonsoft.LKTurntable.factory.LKStageFactory;
import net.lemonsoft.LKTurntable.logic.PrizeManager;
import net.lemonsoft.LKTurntable.logic.WinningResultsCalculator;
import net.lemonsoft.LKTurntable.model.PrizeModel;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主UI视图控制器
 * Created by lemonsoft on 16-10-14.
 */
public class MainViewController implements Initializable {

    @FXML
    public WebView rootWebView;// 左侧抽奖转盘webView
    @FXML
    public TableView<PrizeModel> prizesTableView;// 奖品列表tableView

    @FXML
    public Button deletePrizeButton;// 删除奖品按钮
    @FXML
    public Button setPrizeButton;// 设置奖品信息按钮

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootWebView.getEngine().load(getClass().getResource("../view/core.html").toString());
        prizesTableView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                boolean disable = newValue.intValue() < 0;
                deletePrizeButton.setDisable(disable);
                setPrizeButton.setDisable(disable);
            }
        });

        // 初始化设置
        rootWebView.getEngine().getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
            @Override
            public void changed(ObservableValue<? extends Worker.State> observable,
                                Worker.State oldValue, Worker.State newValue) {
                if (newValue == Worker.State.SUCCEEDED) {
                    setTurntablePanelImage("../resource/turntable-panel.png");// 设置默认表盘
                    ((JSObject) rootWebView.getEngine().executeScript("window")).setMember("lkTurntable", new TurntableProcessor());
                }
            }
        });
        // 创建抽奖奖品数据
        PrizeManager.sharedInstance().addPrize(new PrizeModel("智能手环", 0.05, 20, true));
        PrizeManager.sharedInstance().addPrize(new PrizeModel("谢谢参与", 0.2, 999999, false));
        PrizeManager.sharedInstance().addPrize(new PrizeModel("VR眼镜", 0.05, 1, true));
        PrizeManager.sharedInstance().addPrize(new PrizeModel("iPhone 7", 0.00001, 1, true));
        PrizeManager.sharedInstance().addPrize(new PrizeModel("谢谢参与", 0.2, 1, false));
        PrizeManager.sharedInstance().addPrize(new PrizeModel("现金红包2元", 0.5, 1, true));
        refreshPrizesList();// 刷新显示
    }

    /**
     * 创建主界面
     *
     * @return 创建出的主界面stage实体对象
     * @throws IOException
     */
    public LKStage createMainStage() throws IOException {
        return LKStageFactory.sharedInstance().
                createLKStage(getClass().getResource("../view/ui-main.fxml"),
                        getClass().getResource("../view/ui-main.css"), "LKTurntable");
    }

    /**
     * 添加奖品的stage - gui调用
     */
    public void showCreatePrizeStage() {
        try {
            LKStage stage = new PrizeViewController().createPrizeStage();
            ((PrizeViewController) stage.getViewController()).setMainViewController(this);
            ((PrizeViewController) stage.getViewController()).currentSetPrizeIndex = -1;
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示设置奖品信息的stage - GUI调用
     */
    public void showSetPrizeStage() {
        try {
            LKStage stage = new PrizeViewController().createPrizeStage();
            ((PrizeViewController) stage.getViewController()).setMainViewController(this);
            ((PrizeViewController) stage.getViewController())
                    .currentSetPrizeIndex = prizesTableView.getSelectionModel().getFocusedIndex();
            ((PrizeViewController) stage.getViewController()).initInfo();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除奖品信息 - GUI调用
     */
    public void deletePrize() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("您确认要删除这个奖品吗？");
        alert.setTitle("删除奖品");
        if (alert.showAndWait().get() == ButtonType.OK) {// 确认删除
            PrizeManager.sharedInstance().removePrizeByIndex(prizesTableView.getSelectionModel().getFocusedIndex());
            refreshPrizesList();// 刷新商品列表
        }
    }

    /**
     * 显示设置抽奖转盘的stage - GUI调用
     */
    public void showSetTurntablePanelImageStage() {
        try {
            LKStage stage = new SetTurntablePanelViewController().createSetTurntablePanelStage();
            ((SetTurntablePanelViewController) stage.getViewController()).setMainViewController(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置抽奖转盘的图片
     */
    public void setTurntablePanelImage(String imageURL) {
        String script = String.format("LKTurntable.sharedInstance().setPanelImage('%s')", imageURL);
        rootWebView.getEngine().executeScript(script);
    }

    /**
     * 重新抽奖,重置抽奖转盘 - GUI调用
     */
    public void reset() {
        rootWebView.getEngine().reload();
    }

    /**
     * 刷新奖品列表
     */
    public void refreshPrizesList() {
        prizesTableView.getItems().clear();
        prizesTableView.getItems().addAll(PrizeManager.sharedInstance().getPrizeList());
    }

    /**
     * 抽奖处理机制
     */
    public class TurntableProcessor {

        /**
         * 抽奖，并返回结果
         *
         * @return 抽奖结果字符串，格式：奖品索引_奖品名称_转盘旋转角度
         */
        public String prizeDraw() {
            int index = WinningResultsCalculator.calculate(PrizeManager.sharedInstance().getPrizeList());
            String result = String.format("%d_%s_%f", index, PrizeManager.sharedInstance().getPrizeByIndex(index).getName(),
                    WinningResultsCalculator.calculateTurntableAimAngle(index, PrizeManager.sharedInstance().countPrizes()));
            System.out.println("抽奖被调用：" + result);
            return result;
        }

    }

}
