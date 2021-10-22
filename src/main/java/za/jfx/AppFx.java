package za.jfx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import za.jfx.controllers.AuthFxController;
import za.jfx.controllers.MainFxController;
import za.jfx.controllers.UsersFromAdFxController;

public class AppFx extends Application{

    private ConfigurableApplicationContext applicationContext;

    public AppFx() {
    }

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(AppSpringBoot.class)
                .run(args);
    }


    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(MainFxController.class);
//        Node node = fxWeaver.loadView(EmployeesFxController.class);
//        Node node = fxWeaver.loadView(StartPageFxController.class);
        Node node = fxWeaver.loadView(AuthFxController.class);
//        Node node = fxWeaver.loadView(UsersFromAdFxController.class);
        ((BorderPane)root.getChildrenUnmodifiable().get(0)).setCenter(node);

        Scene scene = new Scene(root, 880, 660);
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:"+System.getProperty("user.dir") + "/ico/ico.jpg"));
        stage.show();
    }
}
