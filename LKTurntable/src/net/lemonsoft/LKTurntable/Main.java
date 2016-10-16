package net.lemonsoft.LKTurntable;

import javafx.application.Application;
import javafx.stage.Stage;
import net.lemonsoft.LKTurntable.viewController.MainViewController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new MainViewController().createMainStage().show();// 创建并显示主界面
    }


    public static void main(String[] args) {
        launch(args);
    }
}
