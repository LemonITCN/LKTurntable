package net.lemonsoft.LKTurntable.category;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * 带有Controller属性的Stage
 * Created by lemonsoft on 16-10-16.
 */
public class LKStage extends Stage {

    private Initializable viewController;

    public LKStage() {
    }

    public LKStage(Initializable viewController) {
        this.viewController = viewController;
    }

    public void setViewController(Initializable viewController) {
        this.viewController = viewController;
    }

    public Initializable getViewController() {
        return viewController;
    }
}
