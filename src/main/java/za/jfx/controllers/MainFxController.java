package za.jfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/main-stage.fxml")
public class MainFxController {

    private final ConfigurableApplicationContext applicationContext;

    @FXML
    public AnchorPane pMain;

    public MainFxController(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void initialize(){
    }

    @FXML
    private void loadPointOfPresence(){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Node node = fxWeaver.loadView(PointOfPresenceFxController.class);
        BorderPane pane = (BorderPane) pMain.getChildrenUnmodifiable().get(0);
        pane.setCenter(node);
    }

    @FXML
    private void loadEmployees(){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Node node = fxWeaver.loadView(EmployeesFxController.class);
        BorderPane pane = (BorderPane) pMain.getChildrenUnmodifiable().get(0);
        pane.setCenter(node);
    }

    @FXML
    private void loadStartPage(){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Node node = fxWeaver.loadView(StartPageFxController.class);
        BorderPane pane = (BorderPane) pMain.getChildrenUnmodifiable().get(0);
        pane.setCenter(node);
    }


}
