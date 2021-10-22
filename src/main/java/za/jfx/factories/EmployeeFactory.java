package za.jfx.factories;

import za.jfx.dto.EmployeeDto;
import za.jfx.model.jfx.Employee;

public class EmployeeFactory {

    public static EmployeeDto fromEmployee(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getSnils(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getMiddleName(),
                employee.getDepartment(),
                employee.getPosition(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getPhoneKspd(),
                employee.getUsername()
        );
    }

    public static Employee createEmployeeFromDtoNew(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setSnils(employeeDto.getSnils());
        employee.setLastName(employeeDto.getLastName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setMiddleName(employeeDto.getMiddleName());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhone(employeeDto.getPhone());
        employee.setPhoneKspd((employeeDto.getPhoneKspd()));
        employee.setUsername(employee.getUsername());
        return employee;
    }

    public static Employee createEmployeeFromDto(EmployeeDto employeeDto) {
        Employee employee = createEmployeeFromDtoNew(employeeDto);
        employee.setId(employeeDto.getId());
        return employee;
    }

    public static EmployeeDto copyEmployeeDto(EmployeeDto employeeDto) {
        return new EmployeeDto(
                employeeDto.getId(),
                employeeDto.getSnils(),
                employeeDto.getLastName(),
                employeeDto.getFirstName(),
                employeeDto.getMiddleName(),
                employeeDto.getDepartment(),
                employeeDto.getPosition(),
                employeeDto.getEmail(),
                employeeDto.getPhone(),
                employeeDto.getPhoneKspd(),
                employeeDto.getUsername()
        );
    }

    public static void updateEmployeeDto(EmployeeDto employeeDtoNew, EmployeeDto employeeDtoOld) {
        employeeDtoOld.setId(employeeDtoNew.getId());
        employeeDtoOld.setSnils(employeeDtoNew.getSnils());
        employeeDtoOld.setLastName(employeeDtoNew.getLastName());
        employeeDtoOld.setFirstName(employeeDtoNew.getFirstName());
        employeeDtoOld.setMiddleName(employeeDtoNew.getMiddleName());
        employeeDtoOld.setPosition(employeeDtoNew.getPosition());
        employeeDtoOld.setEmail(employeeDtoNew.getEmail());
        employeeDtoOld.setPhone(employeeDtoNew.getPhone());
        employeeDtoOld.setPhoneKspd(employeeDtoNew.getPhoneKspd());
        employeeDtoOld.setUsername(employeeDtoNew.getUsername());
    }

}
