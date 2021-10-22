package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.servicies.EmployeeService;
import za.jfx.utils.UtilFx;

import java.util.List;

@Component
@FxmlView("fxml/employees-view.fxml")
public class EmployeesViewFxController {

    private final EmployeeService employeeService;
    private final ConfigurableApplicationContext applicationContext;

    private PointOfPresence pointOfPresence;
    private Employee currentEmployee;
    private ObservableList<Employee> employees;
    private String[] searchText;

    @FXML
    private ListView<Employee> lvEmployees;
    @FXML
    private TextField searchField;

    public EmployeesViewFxController(EmployeeService employeeService, ConfigurableApplicationContext applicationContext) {
        this.employeeService = employeeService;
        this.applicationContext = applicationContext;
    }

    public void initialize() {
        lvEmployees.setCellFactory(UtilFx.setCellViewEmployee());
        employees = FXCollections.observableList(employeeService.findAll());
        lvEmployees.setItems(employees);
        lvEmployees.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() > 1 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                choiceEmployee();
            }
        });
        lvEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue() == null)
                return;
            currentEmployee = observable.getValue();
        });
        searchField.setOnAction(actionEvent -> {
            List<Employee> employees;
            if (searchField.getText().trim().isEmpty()) {
                employees = employeeService.findAll();
                searchField.setText(null);
                searchText=null;
            } else {
                searchText = searchField.getText().split("\\s+");
                employees = employeeService.findByFio(searchText);
            }
            this.employees = FXCollections.observableList(employees);
            lvEmployees.setItems(this.employees);
        });
    }

    private void choiceEmployee() {
        if (pointOfPresence == null) {
            return;
        } else if (currentEmployee == null) {
            return;
        }
        currentEmployee.setPointOfPresence(pointOfPresence);
        employeeService.update(currentEmployee);

        applicationContext.getBean(PointOfPresenceWithChildrenFxController.class).fillEmployees();
//        ((Stage)this.lvEmployees.getScene().getWindow()).close();
    }


    public void setPointOfPresence(PointOfPresence pointOfPresence) {
        this.pointOfPresence = pointOfPresence;
        System.out.println(pointOfPresence);
    }
}
