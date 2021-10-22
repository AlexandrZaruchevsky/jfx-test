package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.dto.EmployeeDto;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;
import za.jfx.servicies.EmployeeService;
import za.jfx.servicies.WorkstationService;
import za.jfx.utils.UtilFx;

import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/point-of-presence-with-children.fxml")
public class PointOfPresenceWithChildrenFxController {


    private final EmployeeService employeeService;
    private final WorkstationService workstationService;
    private final ConfigurableApplicationContext applicationContext;


//    private ObservableList<Employee> employees;
    private ObservableList<EmployeeDto> employeeDtos;
    private ObservableList<Workstation> workstations;
    private EmployeeDto currentEmployeeDto;
//    private Employee currentEmployee;
    private Workstation currentWorkstation;

    private PointOfPresence currentPointOfPresence;

    @FXML
    private TabPane tpTabs;
    @FXML
    private Tab tabEmployees;
    @FXML
    private Tab tabWorkstations;
    @FXML
    private Tab tabSharedResource;
    @FXML
    private ListView<EmployeeDto> lvEmployees;
//    private ListView<Employee> lvEmployees;
    @FXML
    private Button btAddButton;
    @FXML
    private Button btDeleteButton;
    @FXML
    private Button btAddWorkstation;
    @FXML
    private Button btEditWorkstation;
    @FXML
    private Button btDeleteWorkStation;
    @FXML
    private Button btSelectWorkStation;
    @FXML
    private TextField name;
    @FXML
    private TextField address;
    @FXML
    private TableView<Workstation> tvWorkstations;
    @FXML
    private TableColumn<Workstation, String> ipAddress;
    @FXML
    private TableColumn<Workstation, String> hostName;
    @FXML
    private TableColumn<Workstation, String> hostFullName;

    public PointOfPresenceWithChildrenFxController(
            EmployeeService employeeService,
            WorkstationService workstationService,
            ConfigurableApplicationContext applicationContext
    ) {
        this.employeeService = employeeService;
        this.workstationService = workstationService;
        this.applicationContext = applicationContext;
    }

    public void initialize() {
        setIconForButtons();
        lvEmployees.setCellFactory(UtilFx.setCellViewEmployeeDto());
        lvEmployees.getSelectionModel().selectedItemProperty().addListener((observableValue, employee, t1) -> {
            if (observableValue.getValue() != null)
                currentEmployeeDto = observableValue.getValue();
        });
        initTableViewWorkstations();
        tpTabs.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            if (observableValue.getValue().equals(tabWorkstations)) fillWorksations();
            if (observableValue.getValue().equals(tabEmployees)) fillEmployees();
        });
    }

    private void initTableViewWorkstations() {
        ipAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        hostName.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        hostFullName.setCellValueFactory(new PropertyValueFactory<>("hostFullName"));
        tvWorkstations.getSelectionModel().selectedItemProperty().addListener((observableValue, workstation, t1) -> {
            if (observableValue.getValue() != null) {
                currentWorkstation = observableValue.getValue();
            }
        });
        tvWorkstations.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY) && currentWorkstation != null) {
                onViewEditWorkstation();
            }
        });
    }

    private void setIconForButtons() {
        btAddButton.graphicProperty().setValue(UtilFx.setIconForButton(btAddButton, System.getProperty("user.dir") + "/ico/user-add.png"));
        btDeleteButton.graphicProperty().setValue(UtilFx.setIconForButton(btDeleteButton, System.getProperty("user.dir") + "/ico/user-delete.png"));
        btAddWorkstation.graphicProperty().setValue(UtilFx.setIconForButton(btAddWorkstation, System.getProperty("user.dir") + "/ico/workstation-add.png"));
        btEditWorkstation.graphicProperty().setValue(UtilFx.setIconForButton(btEditWorkstation, System.getProperty("user.dir") + "/ico/workstation-edit.png"));
        btDeleteWorkStation.graphicProperty().setValue(UtilFx.setIconForButton(btDeleteWorkStation, System.getProperty("user.dir") + "/ico/workstation-remove.png"));
        btSelectWorkStation.graphicProperty().setValue(UtilFx.setIconForButton(btSelectWorkStation, System.getProperty("user.dir") + "/ico/workstation-select.png"));
    }

    public void setCurrentPointOfPresence(PointOfPresence currentPointOfPresence) {
        this.currentPointOfPresence = currentPointOfPresence;
        fillFields();
        fillEmployees();
    }

    public void fillEmployees() {
        List<Employee> employees = currentPointOfPresence != null ?
        employeeService.findByPointOfPresence(currentPointOfPresence)
                        : employeeService.findAll();
        employeeDtos = FXCollections.observableList(
                employees.stream().map(EmployeeFactory::fromEmployee).collect(Collectors.toList())
        );
        lvEmployees.setItems(employeeDtos);
    }

    public void fillWorksations() {
        workstations = FXCollections.observableList(
                currentPointOfPresence != null ?
                        workstationService.findByPointOfPresence(currentPointOfPresence)
                        : workstationService.findAll());
        tvWorkstations.setItems(workstations);
    }

    private void fillFields() {
        if (currentPointOfPresence == null) return;
        name.setText(currentPointOfPresence.getName());
        address.setText(currentPointOfPresence.getAddress());
    }

    private void viewEmployees() {
        openView(EmployeesViewFxController.class);
    }

    @FXML
    private void onViewEmployees() {
        viewEmployees();
        applicationContext.getBean(EmployeesViewFxController.class).setPointOfPresence(currentPointOfPresence);
    }

    private void deleteEmployee() {
        if (currentEmployeeDto == null || employeeDtos == null || employeeDtos.size() < 1) return;
        Employee employeeFromDb = employeeService.findById(currentEmployeeDto.getId());
        employeeFromDb.setPointOfPresence(null);
        employeeService.update(employeeFromDb);
        int selectedIndex = lvEmployees.getSelectionModel().getSelectedIndex();
        employeeDtos.remove(currentEmployeeDto);
        if (employeeDtos.size() > 0)
            lvEmployees.getSelectionModel().select(selectedIndex);
    }

    @FXML
    private void onDeleteEmployee() {
        deleteEmployee();
    }

    private void viewWorkstations() {
        openView(WorkstationViewsFxController.class);
    }

    @FXML
    private void onViewWorkstations() {
        viewWorkstations();
        applicationContext.getBean(WorkstationViewsFxController.class).setPointOfPresence(currentPointOfPresence);
    }

    private void delWorkstation() {
        if (currentWorkstation == null || workstations == null || workstations.size() < 1) return;
        workstationService.delete(currentWorkstation.getId());
        int selectedIndex = tvWorkstations.getSelectionModel().getSelectedIndex();
        workstations.remove(currentWorkstation);
        if (workstations.size() > 0)
            tvWorkstations.getSelectionModel().select(selectedIndex);
    }

    @FXML
    private void onDeleteWorkstation() {
        delWorkstation();
    }

    @FXML
    private void onViewAddWorkstation() {
        openView(WorkstationEditFxController.class);
        WorkstationEditFxController workstationEditFxController = applicationContext.getBean(WorkstationEditFxController.class);
        workstationEditFxController.setPointOfPresence(currentPointOfPresence);
        workstationEditFxController.setClassOwner(this.getClass());
    }

    @FXML
    private void onViewEditWorkstation() {
        if (currentWorkstation == null) return;
        openView(WorkstationEditFxController.class);
        WorkstationEditFxController workstationEditFxController = applicationContext.getBean(WorkstationEditFxController.class);
        workstationEditFxController.setPointOfPresence(currentPointOfPresence);
        workstationEditFxController.setWorkstation(currentWorkstation);
        workstationEditFxController.setClassOwner(this.getClass());
    }

    private void openView(Class<?> clazz) {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        AnchorPane node = fxWeaver.loadView(clazz);
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.initOwner(this.btAddWorkstation.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

}
