package za.jfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("fxml/output-log.fxml")
public class OutputLogFxController {

    @FXML
    private Button btClose;

    @FXML
    private void onClose(){
        ((BorderPane)btClose.getScene().getRoot().getChildrenUnmodifiable().get(0)).setBottom(null);
    }

}
