package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.Equipment;
import za.jfx.model.jfx.Workstation;
import za.jfx.servicies.EmployeeService;
import za.jfx.servicies.EquipmentDtoService;
import za.jfx.servicies.WorkstationService;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/employees.fxml")
public class EmployeesFxController {

    private final ConfigurableApplicationContext applicationContext;

    private final EmployeeService employeeService;
    private final WorkstationService workstationService;
    private final EquipmentDtoService equipmentDtoService;

    private final EmployeeFxController employeeFxController;
    private ObservableList<Employee> employees;
    private Employee currentEmployee;

    private String[] searchText;

    @FXML
    private TableView<Employee> tvEmployees;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Employee, String> middleName;
    @FXML
    private TableColumn<Employee, String> firstName;
    @FXML
    private TableColumn<Employee, String> lastName;

    @FXML
    private BorderPane pEmployees;

    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;


    public EmployeesFxController(
            ConfigurableApplicationContext applicationContext,
            EmployeeService employeeService,
            WorkstationService workstationService, EquipmentDtoService equipmentDtoService, EmployeeFxController employeeFxController
    ) {
        this.applicationContext = applicationContext;
        this.employeeService = employeeService;
        this.workstationService = workstationService;
        this.equipmentDtoService = equipmentDtoService;
        this.employeeFxController = employeeFxController;
    }

    public void initialize() {
        //Инициализация таблицы
        initTable();
        if (searchText != null) {
            searchField.setText(String.join("\\+", searchText));
        }
        searchField.setOnAction(this::handle);
        setIcons();
    }

    private void setIcons() {
        String pathToIcons = System.getProperty("user.dir") + "/ico/";
        setIcon(addButton, pathToIcons + "user-add.png");
        setIcon(editButton, pathToIcons + "user-edit.png");
        setIcon(deleteButton, pathToIcons + "user-delete.png");
    }

    private void setIcon(Button button, String pathIcon) {
        File file = new File(pathIcon);
        if (file.exists() && !file.isDirectory()) {
            button.setText(null);
            ImageView icoOk = new ImageView("file:" + pathIcon);
            icoOk.setFitHeight(20);
            icoOk.setFitWidth(20);
            button.graphicProperty().setValue(icoOk);
        }
    }

    private void initTable() {
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        middleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        fillEmployees();
        tvEmployees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue() == null) {
                currentEmployee = null;
                return;
            }
            currentEmployee = observable.getValue();
            viewEmployee();
            employeeFxController.setEmployee(currentEmployee);
        });
        tvEmployees.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() > 1 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                onEmployeeEdit();
            }
        });
        selectFirstItem();
        employeeFxController.setEmployee(currentEmployee);
    }

    public void fillEmployees(Long id) {
        employees = FXCollections.observableList(findAll(searchText));
        tvEmployees.setItems(employees);
        if (employees.size() > 0) {
            tvEmployees.getSelectionModel().select(0);
            currentEmployee = tvEmployees.getSelectionModel().getSelectedItem();
            if (id != null) {
                currentEmployee = employeeService.findById(id);
            }
            Employee employee1 = employees.stream().filter(employee -> employee.getId().equals(currentEmployee.getId())).findFirst().orElse(null);
            tvEmployees.getSelectionModel().select(employee1);
            tvEmployees.scrollTo(employee1);
            viewEmployee();
            employeeFxController.setEmployee(employee1);
        }
    }

    public void fillEmployees() {
        fillEmployees(null);
    }

    private List<Employee> findAll(String... fio) {
        if (fio != null && fio.length > 0) {
            return employeeService.findByFio(fio);
        } else
            return employeeService.findAll();
    }


    private void selectFirstItem() {
        if (employees.size() > 0) {
            tvEmployees.getSelectionModel().select(0);
        }
    }

    private void viewEmployee() {
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        Node employeeNode = fxWeaver.loadView(EmployeeFxController.class);
        pEmployees.setRight(employeeNode);
    }

    @FXML
    private void onEmployeeEdit() {
        EmployeeView(currentEmployee);
        employeeFxController.setEmployee(currentEmployee);
    }

    @FXML
    private void onEmployeeAdd() {
        EmployeeView(null);
    }

    @FXML
    private void onEmployeeDelete() {
        int selectedIndex = tvEmployees.getSelectionModel().getSelectedIndex();
        employeeService.delete(currentEmployee.getId());
        if (selectedIndex > 0) {
            tvEmployees.getSelectionModel().select(selectedIndex - 1);
        }
        fillEmployees(tvEmployees.getSelectionModel().getSelectedItem().getId());
    }

    private void EmployeeView(Employee employee) {
        Window mainWindow = pEmployees.getScene().getWindow();
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
        stage.show();
    }


    private void handle(ActionEvent actionEvent) {
        List<Employee> employees;
        if (searchField.getText().trim().isEmpty()) {
            employees = employeeService.findAll();
            searchField.setText(null);
            searchText = null;
        } else {
            searchText = searchField.getText().split("\\s+");
            employees = employeeService.findByFio(searchText);
        }
        this.employees = FXCollections.observableList(employees);
        tvEmployees.setItems(this.employees);
        selectFirstItem();
    }

    public void fillEquipment(ActionEvent actionEvent) {

        List<Employee> collect = employeeService.findAll().stream()
                .filter(employee -> employee.getWorkstation() != null && !employee.getWorkstation().trim().equals(""))
                .collect(Collectors.toList());
        List<Equipment> collect1 = collect.stream()
                .map(employee -> {
                    Equipment equipment = new Equipment();
                    equipment.setHostName(employee.getWorkstation());
                    equipment.setEmployee(employee);
                    equipment.setIpAddress(employee.getIp());
                    List<Workstation> byHostName = workstationService.findByHostName(employee.getWorkstation().trim());
                    if (byHostName != null && byHostName.size() > 0) {
                        Workstation workstation = byHostName.get(0);
                        equipment.setHostFullName(workstation.getHostFullName());
                        equipment.setDescription(workstation.getDescription());
                    }
                    equipment.setNetwork(true);
                    return equipment;
                })
                .collect(Collectors.toList());
        equipmentDtoService.addAllEq(collect1);

    }
}
