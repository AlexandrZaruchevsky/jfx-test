package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.*;
import za.jfx.servicies.EquipmentService;
import za.jfx.servicies.NetworkService;
import za.jfx.servicies.WorkstationService;

@Component
@FxmlView("fxml/workstation-views.fxml")
public class WorkstationViewsFxController {

    private final ConfigurableApplicationContext applicationContext;
    private final NetworkService networkService;
    private final WorkstationService workstationService;
    private final EquipmentService equipmentService;
    private Class<?> ownerClass;
    private Employee currentEmployee;


    @FXML
    private TableColumn<Network, String> ipAddress;
    @FXML
    private TableColumn<Network, String> hostName;
    @FXML
    private TableColumn<Network, String> hostFullName;
    @FXML
    private TableView<Network> tvWorkstation;
    @FXML
    private TextField searchField;

    private Network currentNetwork;
    private ObservableList<Network> networks;
    private PointOfPresence pointOfPresence;

    private String searchText;

    public WorkstationViewsFxController(
            ConfigurableApplicationContext applicationContext,
            NetworkService networkService,
            WorkstationService workstationService,
            EquipmentService equipmentService
    ) {
        this.applicationContext = applicationContext;
        this.networkService = networkService;
        this.workstationService = workstationService;
        this.equipmentService = equipmentService;
    }

    public void initialize() {
        initTableViewWorkstation();
        tvWorkstation.setItems(getNetworks(searchText));
        searchField.setOnAction(actionEvent -> tvWorkstation.setItems(getNetworks(searchField.getText())));
    }

    private void initTableViewWorkstation() {
        ipAddress.setCellValueFactory(new PropertyValueFactory<>("ipAdress"));
        hostName.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        hostFullName.setCellValueFactory(new PropertyValueFactory<>("hostFullName"));
        tvWorkstation.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (ownerClass != null && ownerClass.equals(EquipmentsByEmployeeFxController.class)) {
                    addWorkstationToEquipment();
                    applicationContext.getBean(EquipmentsByEmployeeFxController.class)
                            .fillEquipmentDtos(EmployeeFactory.fromEmployee(currentEmployee));
                } else {
                    addWorkstationToPointPresence();
                    applicationContext.getBean(PointOfPresenceWithChildrenFxController.class).fillWorksations();
                }
            }
        });
        tvWorkstation.getSelectionModel().selectedItemProperty().addListener((observableValue, network, t1) -> {
            if (observableValue != null) {
                currentNetwork = observableValue.getValue();
            }
        });
    }

    private void addWorkstationToPointPresence() {
        if (pointOfPresence == null || currentNetwork == null) return;
        Workstation workstation = new Workstation();
        workstation.setIpAddress(currentNetwork.getIpAdress());
        workstation.setHostName(currentNetwork.getHostName() != null ? currentNetwork.getHostName().split("\\.")[0] : null);
        workstation.setHostFullName(currentNetwork.getHostFullName());
        workstation.setPointOfPresence(pointOfPresence);
        workstationService.add(workstation);
    }

    private ObservableList<Network> getNetworks(String filter) {
        return FXCollections.observableList(
                filter != null && !filter.trim().equals("") ?
                        networkService.findByIpAddress(filter.trim())
                        : networkService.findAll()
        );
    }

    public void setPointOfPresence(PointOfPresence pointOfPresence) {
        this.pointOfPresence = pointOfPresence;
    }


    public void setOwnerClass(Class<?> ownerClass) {
        this.ownerClass = ownerClass;
    }

    private void addWorkstationToEquipment() {
        if (currentEmployee == null || currentNetwork == null) return;
        Equipment equipment = new Equipment();
        equipment.setHostName(currentNetwork.getHostName() != null ? currentNetwork.getHostName().split("\\.")[0] : null);
        equipment.setHostFullName(currentNetwork.getHostFullName());
        equipment.setIpAddress(currentNetwork.getIpAdress());
        equipment.setEmployee(currentEmployee);
        equipment.setNetwork(true);
        equipmentService.add(equipment);
    }

    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }
}
