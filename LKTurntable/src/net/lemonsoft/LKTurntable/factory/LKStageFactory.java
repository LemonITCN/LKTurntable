package net.lemonsoft.LKTurntable.factory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.lemonsoft.LKTurntable.category.LKStage;

import java.io.IOException;
import java.net.URL;

/**
 * Stage工厂类
 * Created by lemonsoft on 16-10-16.
 */
public class LKStageFactory {

    // 单例对象
    private static LKStageFactory sharedClient;

    /**
     * 单例方法
     *
     * @return Stage工厂类的实例对象
     */
    public static LKStageFactory sharedInstance() {
        if (sharedClient == null)
            sharedClient = new LKStageFactory();
        return sharedClient;
    }

    /**
     * 创建stage
     *
     * @param fxmlURL    fxml的URL
     * @param cssURL     css对应的URL
     * @param stageTitle 创建的stage的标题
     * @return 新创建的stage实例对象
     * @throws IOException
     */
    public LKStage createLKStage(URL fxmlURL, URL cssURL, String stageTitle) throws IOException {
        LKStage stage = new LKStage();
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(cssURL.toExternalForm());
        stage.setTitle(stageTitle);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setViewController(fxmlLoader.getController());
        return stage;
    }

}
