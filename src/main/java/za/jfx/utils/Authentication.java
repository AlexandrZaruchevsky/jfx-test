package za.jfx.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Authentication {

    private String username;
    private String password;

}
