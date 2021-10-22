package za.jfx.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Data
@Component
public class Uvnc {

    private String path;
    private String ip;
    private String user;
    private String password;

    public final static String PATH = "vncviewer1201.exe";

    public Uvnc(String path) {
        this.path = path;
    }

    public Uvnc() {
    }

    public void executeWithParam(String ip, String user, String password) {
        if (ip == null || ip.trim().equals("")) return;
        try {
            new ProcessBuilder(this.path, "-connect", ip, "-user", user, "-password", password).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeWithParam() throws IOException {
        new ProcessBuilder(
                this.path != null ? this.path : "vncviewer1201.exe",
                "-connect",
                this.ip != null ? this.ip : "127.0.0.1",
                "-user",
                this.user != null ? this.user : "adm",
                "-password",
                this.password != null ? this.password : "password"
        ).start();
    }
}

