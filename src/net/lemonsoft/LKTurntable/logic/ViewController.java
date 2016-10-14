package net.lemonsoft.LKTurntable.logic;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主UI视图控制器
 * Created by lemonsoft on 16-10-14.
 */
public class ViewController implements Initializable {

    @FXML
    private WebView rootWebView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("===>" + getClass().getResource("../view/core.html").toString());
        rootWebView.getEngine().load(getClass().getResource("../view/core.html").toString());
    }

}
