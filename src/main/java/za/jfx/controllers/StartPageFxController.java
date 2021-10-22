package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.dto.EmployeeDto;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.servicies.EmployeeService;
import za.jfx.utils.UtilFx;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/start-page.fxml")
public class StartPageFxController {

    private final ConfigurableApplicationContext applicationContext;
    private final EmployeeService employeeService;



    private ObservableList<EmployeeDto> employeeDtos;
    private Employee currentEmployee;
    private EmployeeDto currentEmployeeDto;
    private PointOfPresence currentPointOfPresence;
    private String[] searchText;

    @FXML
    private TextField fioField;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField positionField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField phoneKspdField;
    @FXML
    private TextField emailField;
    @FXML
    private TitledPane equipmentPane;
    @FXML
    private TitledPane pointOfPresencePane;
    @FXML
    private AnchorPane pointOfPresenceAnchorPane;
    @FXML
    private TextField searchTextField;
    @FXML
    private ListView<EmployeeDto> employeesListView;
    @FXML
    private Button btAddEmployee;
    @FXML
    private Button btEditEmployee;
    @FXML
    private Button btDeleteEmployee;

    public StartPageFxController(ConfigurableApplicationContext applicationContext, EmployeeService employeeService) {
        this.applicationContext = applicationContext;
        this.employeeService = employeeService;
    }

    public void initialize() {
        equipmentPane.setExpanded(true);
        if (searchText != null && searchText.length > 0) {
            searchTextField.setText(String.join(" ", searchText));
        }
        setIconForButtons();
        initListViewEmployees();
        initPointOfPresencePane();
        searchTextField.setOnAction(actionEvent -> {
            searchText = !searchTextField.getText().trim().equals("") ? searchTextField.getText().split("\\s+") : null;
            fillEmployeeDtos();
        });
    }

    private void initPointOfPresencePane() {
        if (currentPointOfPresence != null) {
            pointOfPresencePane.setVisible(true);
//            pointOfPresencePane.setExpanded(true);
            return;
        }
        pointOfPresencePane.setVisible(false);
//        pointOfPresencePane.setExpanded(false);
    }

    private void initListViewEmployees() {
        employeesListView.setCellFactory(UtilFx.setCellViewEmployeeDto());
        employeesListView.getSelectionModel().selectedItemProperty().addListener((observableValue, employeeDto, t1) -> {
            if (observableValue.getValue() == null) return;

            currentEmployeeDto = observableValue.getValue();
            fillFieldsEmployee();
            currentEmployee = employeeService.findById(currentEmployeeDto.getId());
            currentPointOfPresence = currentEmployee.getPointOfPresence();
//            System.out.println(currentPointOfPresence);
            loadPointOfPresenceWithChildren();
            loadEquipmentsByEmployee();
        });
        employeesListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount()==2 && mouseEvent.getButton()== MouseButton.PRIMARY){
                onEmployeeEdit();
            }
        });
        fillEmployeeDtos();
    }

    private void fillFieldsEmployee() {
        fioField.setText(currentEmployeeDto.getFullFio());
        departmentField.setText(currentEmployeeDto.getDepartment());
        positionField.setText(currentEmployeeDto.getPosition());
        phoneField.setText(currentEmployeeDto.getPhone());
        phoneKspdField.setText(currentEmployeeDto.getPhoneKspd());
        emailField.setText(currentEmployeeDto.getEmail());
    }

    private void fillEmployeeDtos() {
        List<Employee> employees = searchText == null ?
                employeeService.findAll()
                : employeeService.findByFio(searchText);
        employeeDtos = FXCollections.observableList(
                employees.stream()
                        .map(EmployeeFactory::fromEmployee)
                        .collect(Collectors.toList())
        );
        employeesListView.setItems(employeeDtos);
        if (employeeDtos != null && employees.size() > 0) {
            employeesListView.getSelectionModel().select(0);
            employeesListView.scrollTo(0);
        }
    }

    private void setIconForButtons() {
        btAddEmployee.graphicProperty().setValue(UtilFx.setIconForButton(btAddEmployee, System.getProperty("user.dir") + "/ico/user-add.png"));
        btEditEmployee.graphicProperty().setValue(UtilFx.setIconForButton(btEditEmployee, System.getProperty("user.dir") + "/ico/user-edit.png"));
        btDeleteEmployee.graphicProperty().setValue(UtilFx.setIconForButton(btDeleteEmployee, System.getProperty("user.dir") + "/ico/user-delete.png"));
    }

    private void loadPointOfPresenceWithChildren() {
        if (currentPointOfPresence == null) {
            pointOfPresencePane.setContent(null);
            pointOfPresencePane.setVisible(false);
            return;
        }
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        AnchorPane node = fxWeaver.loadView(PointOfPresenceWithChildrenFxController.class);
        applicationContext.getBean(PointOfPresenceWithChildrenFxController.class).setCurrentPointOfPresence(currentPointOfPresence);
        pointOfPresencePane.setContent(node);
        pointOfPresencePane.setVisible(true);
//        pointOfPresencePane.setExpanded(true);
    }

    private void employeeView(Employee employee) {
        Window mainWindow = this.btAddEmployee.getScene().getWindow();
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        AnchorPane nodeEmployeeEdit = fxWeaver.loadView(EmployeeEditFxController.class);
        EmployeeEditFxController employeeEditFxController = fxWeaver.getBean(EmployeeEditFxController.class);
        Stage stage = new Stage();
        stage.setScene(new Scene(nodeEmployeeEdit));
        stage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "/ico/user-edit.png"));
        stage.initOwner(mainWindow);
        stage.initModality(Modality.WINDOW_MODAL);
        if (employee == null) {
            employeeEditFxController.setId(null);
        } else {
            employeeEditFxController.setId(employee.getId());
        }
        employeeEditFxController.setOwnerClass(this.getClass());
        stage.show();
    }

    @FXML
    private void onEmployeeAdd() {
        employeeView(null);
    }

    @FXML
    private void onEmployeeEdit() {
        employeeView(currentEmployee);
    }

    @FXML
    private void onEmployeeDelete() {
        int selectedIndex = employeesListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) return;
        EmployeeDto selectedItem = employeesListView.getSelectionModel().getSelectedItem();
        employeeService.delete(selectedItem.getId());
        employeeDtos.remove(selectedItem);
        if (employeeDtos.size() == 1) {
            employeesListView.getSelectionModel().select(0);
        }
        if (employeeDtos.size() > 1)
            employeesListView.getSelectionModel().select(selectedIndex);

    }

    public void setCurrentEmployeeDto(EmployeeDto currentEmployeeDto, boolean isNew) {
        this.currentEmployeeDto = currentEmployeeDto;
        fillFieldsEmployee();
        if (isNew) {
            fillEmployeeDtos();
            employeesListView.scrollTo(currentEmployeeDto);
        }
        employeeDtos.filtered(employeeDto -> employeeDto.getId().equals(currentEmployeeDto.getId()))
                .forEach(employeeDto -> EmployeeFactory.updateEmployeeDto(currentEmployeeDto, employeeDto));
        employeesListView.refresh();
    }

    private void loadEquipmentsByEmployee() {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        AnchorPane node = fxWeaver.loadView(EquipmentsByEmployeeFxController.class);
        equipmentPane.setContent(node);
        equipmentPane.setExpanded(true);
        applicationContext.getBean(EquipmentsByEmployeeFxController.class).setEmployeeDto(currentEmployeeDto);
    }

}
