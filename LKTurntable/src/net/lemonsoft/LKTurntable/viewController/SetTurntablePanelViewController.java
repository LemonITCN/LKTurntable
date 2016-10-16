package net.lemonsoft.LKTurntable.viewController;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import net.lemonsoft.LKTurntable.category.LKStage;
import net.lemonsoft.LKTurntable.factory.LKStageFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 设置抽奖转盘的界面的视图控制器
 * Created by lemonsoft on 16-10-16.
 */
public class SetTurntablePanelViewController implements Initializable {

    @FXML
    public TextField turntablePanelImageUrlField;// 图片URL输入框

    /**
     * 主界面的视图控制器
     */
    private MainViewController mainViewController;

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 获取设置转盘图片的Stage
     *
     * @return 设置转盘图片的Stage对象
     * @throws IOException
     */
    public LKStage createSetTurntablePanelStage() throws IOException {
        return LKStageFactory.sharedInstance()
                .createLKStage(getClass().getResource("../view/ui-setTurntablePanel.fxml"),
                        getClass().getResource("../view/ui-prize.css"), "LKTurntable - Prize");
    }

    /**
     * 提交设置抽奖转盘的图片 - GUI调用
     */
    public void commit() {
        mainViewController.setTurntablePanelImage(turntablePanelImageUrlField.getText());
    }

}
