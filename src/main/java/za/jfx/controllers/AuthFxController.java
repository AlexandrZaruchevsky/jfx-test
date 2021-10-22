package za.jfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.utils.Authentication;

@Component
@FxmlView("fxml/auth.fxml")
public class AuthFxController {

    private final Authentication authentication;
    private final ConfigurableApplicationContext applicationContext;

    public TextField usernameFiled;
    public PasswordField passwordField;

    public AuthFxController(Authentication authentication, ConfigurableApplicationContext applicationContext) {
        this.authentication = authentication;
        this.applicationContext = applicationContext;
    }

    public void initialize(){
        usernameFiled.setText(System.getProperty("user.name"));
    }

    @FXML
    private void authAction() {
        if (usernameFiled.getText() == null || usernameFiled.getText().trim().equals("") || passwordField.getText() == null || passwordField.getText().trim().equals(""))
            return;
        authentication.setUsername(usernameFiled.getText().trim());
        authentication.setPassword(passwordField.getText().trim());
//        System.out.println(authentication);
        BorderPane borderPane = (BorderPane) usernameFiled.getScene().getRoot().getChildrenUnmodifiable().get(0);

        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Node node = fxWeaver.loadView(StartPageFxController.class);
        borderPane.setCenter(node);


    }


}
