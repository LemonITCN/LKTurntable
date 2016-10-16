package net.lemonsoft.LKTurntable.viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import net.lemonsoft.LKTurntable.category.LKStage;
import net.lemonsoft.LKTurntable.factory.LKStageFactory;
import net.lemonsoft.LKTurntable.logic.PrizeManager;
import net.lemonsoft.LKTurntable.model.PrizeModel;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 添加/修改奖品信息的视图控制器
 * Created by lemonsoft on 16-10-16.
 */
public class PrizeViewController implements Initializable {

    @FXML
    public TextField prizeNameTextField;
    @FXML
    public TextField prizeChanceTextField;
    @FXML
    public TextField prizeCountTextField;
    @FXML
    public ToggleSwitch realPrizeSwitch;

    /**
     * 主界面的视图控制器
     */
    private MainViewController mainViewController;

    public int currentSetPrizeIndex = -1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initInfo(){
        if (currentSetPrizeIndex >= 0) {// 当前是修改模式，需要还原上次填写的值
            PrizeModel prizeModel = PrizeManager.sharedInstance().getPrizeByIndex(currentSetPrizeIndex);
            if (prizeModel == null)// 没有这个奖品
                currentSetPrizeIndex = -1;
            else {
                prizeNameTextField.setText(prizeModel.getName());
                prizeChanceTextField.setText(prizeModel.getChance() + "");
                prizeCountTextField.setText(prizeModel.getCount() + "");
                realPrizeSwitch.setSelected(prizeModel.isRealPrize());
            }
        }
    }

    /**
     * 创建奖品stage
     *
     * @return 奖品添加/修改的界面stage
     */
    public LKStage createPrizeStage() throws IOException {
        return LKStageFactory.sharedInstance()
                .createLKStage(getClass().getResource("../view/ui-prize.fxml"),
                        getClass().getResource("../view/ui-prize.css"), "LKTurntable - Prize");
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * 提交 - GUI调用
     */
    public void commit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("添加奖品");
        try {
            PrizeModel prizeModel = new PrizeModel(prizeNameTextField.getText(),
                    Double.valueOf(prizeChanceTextField.getText()),
                    Double.valueOf(prizeCountTextField.getText()).intValue(),
                    realPrizeSwitch.isSelected());
            if (currentSetPrizeIndex >= 0)// 当前是修改
                PrizeManager.sharedInstance().setPrizeByIndex(currentSetPrizeIndex, prizeModel);
            else// 当前是添加
                PrizeManager.sharedInstance().addPrize(prizeModel);
            mainViewController.refreshPrizesList();
            alert.setContentText(currentSetPrizeIndex >= 0 ? "奖品信息修改成功" : "添加奖品成功！");
            if (currentSetPrizeIndex < 0) {// 添加模式下重置表单
                prizeNameTextField.setText("");// 添加成功，重置表单
                prizeChanceTextField.setText("");
                prizeCountTextField.setText("");
            }
        } catch (Exception e) {// 发生错误，报异常，添加失败
            e.printStackTrace();
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("提交失败，请您检查添加的奖品的参数是否正确");
        } finally {
            alert.showAndWait();
        }
    }

}
