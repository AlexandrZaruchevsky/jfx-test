package za.jfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.Employee;
import za.jfx.servicies.EmployeeService;

@Component
@FxmlView("fxml/employee-edit.fxml")
public class EmployeeEditFxController {

    private final EmployeeService employeeService;

    private Employee currentEmployee;
    private Long id;
    private Class<?> ownerClass;
    private final EmployeesFxController employeesFxController;
    private final ConfigurableApplicationContext applicationContext;

//    @FXML
//    private TextField snils;
    @FXML
    private TextField lastName;
    @FXML
    private TextField firstName;
    @FXML
    private TextField middleName;
    @FXML
    private TextField department;
    @FXML
    private TextField position;
    @FXML
    private TextField phone;
    @FXML
    private TextField phoneKspd;
    @FXML
    private  TextField email;

    public EmployeeEditFxController(EmployeeService employeeService, EmployeesFxController employeesFxController, ConfigurableApplicationContext applicationContext) {
        this.employeeService = employeeService;
        this.employeesFxController = employeesFxController;
        this.applicationContext = applicationContext;
    }

    public void setId(Long id) {
        this.id = id;
        this.currentEmployee = this.id != null ? employeeService.findById(id) : null;
        fillFields();
    }

    private void fillFields() {
        if(this.currentEmployee==null)
            return;
        lastName.setText(currentEmployee.getLastName());
        firstName.setText(currentEmployee.getFirstName());
        middleName.setText(currentEmployee.getMiddleName());
        department.setText(currentEmployee.getDepartment());
        position.setText(currentEmployee.getPosition());
        phone.setText(currentEmployee.getPhone());
        phoneKspd.setText(currentEmployee.getPhoneKspd());
        email.setText(currentEmployee.getEmail());
    }

    @FXML
    private void onSave() {
        boolean flag = false;
        if (currentEmployee == null) {
            currentEmployee = new Employee();
        } else
            flag = true;
        currentEmployee.setLastName(lastName.getText() != null ? lastName.getText().trim() : null);
        currentEmployee.setFirstName(firstName.getText() != null ? firstName.getText().trim() : null);
        currentEmployee.setMiddleName(middleName.getText() != null ? middleName.getText().trim() : null);
        currentEmployee.setDepartment(department.getText() != null ? department.getText().trim() : null);
        currentEmployee.setPosition(position.getText() != null ? position.getText().trim() : null);
        currentEmployee.setPhone(phone.getText() != null ? phone.getText().trim() : null);
        currentEmployee.setPhoneKspd(phoneKspd.getText() != null ? phoneKspd.getText().trim() : null);
        currentEmployee.setEmail(email.getText() != null ? email.getText().trim() : null);
        if (flag) {
            currentEmployee = employeeService.update(currentEmployee);
        } else {
            currentEmployee = employeeService.add(currentEmployee);
        }
        if(ownerClass!=null && ownerClass.equals(StartPageFxController.class)){
            StartPageFxController startPageFxController = applicationContext.getBean(StartPageFxController.class);
            startPageFxController.setCurrentEmployeeDto(EmployeeFactory.fromEmployee(currentEmployee), !flag);
        }else {
            employeesFxController.fillEmployees(currentEmployee.getId());
        }
        onCloseStage();
    }

    @FXML
    private void onCloseStage() {
        Stage stage = (Stage) lastName.getScene().getWindow();
        stage.close();
    }

    public void setOwnerClass(Class<?> ownerClass) {
        this.ownerClass = ownerClass;
    }
}
