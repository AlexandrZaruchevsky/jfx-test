package za.jfx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDto implements ListViewZa{

    private Long id;
    private String snils;
    private String lastName;
    private String firstName;
    private String middleName;
    private String department;
    private String position;
    private String email;
    private String phone;
    private String phoneKspd;
    private String username;

    public EmployeeDto(Long id, String snils, String lastName, String firstName, String middleName, String position, String email, String phone, String phoneKspd) {
        this.id = id;
        this.snils = snils;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.phoneKspd = phoneKspd;
    }

    public EmployeeDto(Long id, String snils, String lastName, String firstName, String middleName, String position, String email, String phone, String phoneKspd, String username) {
        this.id = id;
        this.snils = snils;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.phoneKspd = phoneKspd;
        this.username = username;
    }

    public EmployeeDto(Long id, String snils, String lastName, String firstName, String middleName, String department, String position, String email, String phone, String phoneKspd, String username) {
        this.id = id;
        this.snils = snils;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.department = department;
        this.position = position;
        this.email = email;
        this.phone = phone;
        this.phoneKspd = phoneKspd;
        this.username = username;
    }

    public String getFullFio() {
        return (lastName != null && lastName.trim().length() > 0 ? lastName + " " : "") +
                (firstName != null && firstName.trim().length() > 0 ? firstName + " " : "") +
                (middleName != null && middleName.trim().length() > 0 ? middleName : "");
    }

    @Override
    public String getRowText() {
        return (lastName != null && lastName.trim().length() > 0 ? lastName + " " : "") +
                (firstName != null && firstName.trim().length() > 0 ? firstName + " " : "") +
                (middleName != null && middleName.trim().length() > 0 ? middleName : "");
    }
}
