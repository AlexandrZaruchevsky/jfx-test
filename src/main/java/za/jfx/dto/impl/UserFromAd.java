package za.jfx.dto.impl;

import lombok.Data;
import za.jfx.dto.ListViewZa;

@Data
public class UserFromAd implements ListViewZa {

    private String username;
    private String workstation;
    private String osType;

    public UserFromAd(String username, String workstation, String osType) {
        this.username = username;
        this.workstation = workstation;
        this.osType = osType;
    }

    @Override
    public String getRowText() {
        return (username != null && !username.trim().equals("") ? username.trim() : "") + " [ " +
                (workstation != null && !workstation.trim().equals("") ? workstation.trim() : "") + " ] " +
                (osType != null && !osType.trim().equals("") ? osType.trim() : "");
    }

}
