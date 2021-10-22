package za.jfx;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppSpringBoot {

    public static void main(String[] args) {
        Application.launch(AppFx.class, args);
    }

}
