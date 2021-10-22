package za.jfx.dto.impl;

import lombok.Data;
import za.jfx.dto.EmployeeDto;
import za.jfx.dto.ListViewZa;

@Data
public class UserEmployeeDto implements ListViewZa {

    private UserFromAd userFromAd;
    private EmployeeDto employeeDto;

    public UserEmployeeDto(UserFromAd userFromAd, EmployeeDto employeeDto) {
        this.userFromAd = userFromAd;
        this.employeeDto = employeeDto;
    }

    @Override
    public String getRowText() {
        return (userFromAd != null ? userFromAd.getRowText() : "None") + " <-> " +
                (employeeDto != null ? employeeDto.getRowText() : "None");
    }

}
