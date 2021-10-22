package za.jfx.dto.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.jfx.dto.ListViewZa;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFromAdV1 implements ListViewZa {

    private String name;
    private String samAccountName;
    private String mail;
    private String department;
    private String title;
    private String info;
    private boolean isEnabled;

    @Override
    public String getRowText() {
        return (name != null && !name.trim().equals("") ? name.trim() : "Name") + " <-> " +
                (samAccountName != null && !samAccountName.trim().equals("") ? samAccountName.trim() : "SamAccountName") + " <-> " +
                (info != null && !info.trim().equals("") ? info.trim() : "Info");
    }

    public String getLastName() {
        String[] split = name.split("\\s+");
        return split.length == 3 ? split[0] : "";
    }

    public String getFirstName() {
        String[] split = name.split("\\s+");
        return split.length == 3 ? split[1] : "";
    }

    public String getMiddleName() {
        String[] split = name.split("\\s+");
        return split.length == 3 ? split[2] : "";
    }

}
