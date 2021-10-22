package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.dto.EmployeeDto;
import za.jfx.dto.EquipmentDto;
import za.jfx.factories.EmployeeFactory;
import za.jfx.factories.EquipmentFactory;
import za.jfx.servicies.EquipmentDtoService;
import za.jfx.utils.Authentication;
import za.jfx.utils.Net;
import za.jfx.utils.UtilFx;
import za.jfx.utils.Uvnc;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/equipments-by-employee.fxml")
public class EquipmentsByEmployeeFxController {

    private final ConfigurableApplicationContext applicationContext;

    private final EquipmentDtoService equipmentDtoService;

    private final Authentication authentication;



    private EquipmentDto equipmentDto;
    private EmployeeDto employeeDto;
    private ObservableList<EquipmentDto> equipmentDtos;

    @FXML
    private Button btAddWorkstation;
    @FXML
    private Button btEditWorkstation;
    @FXML
    private Button btDeleteWorkStation;
    @FXML
    private Button btSelectWorkStation;
    @FXML
    private Button btPing;
    @FXML
    private Button btUvnc;
    @FXML
    private Button btRdp;
    @FXML
    private Button btShare;
    @FXML
    private Button btMgmt;
    @FXML
    private TableColumn<EquipmentDto, String> columnHostName;
    @FXML
    private TableColumn<EquipmentDto, String> columnHostFullName;
    @FXML
    private TableColumn<EquipmentDto, String> columnIpAddress;
    @FXML
    private TableView<EquipmentDto> tvEquipments;


    private BorderPane pOut;


    public EquipmentsByEmployeeFxController(ConfigurableApplicationContext applicationContext, EquipmentDtoService equipmentDtoService, Authentication authentication) {
        this.applicationContext = applicationContext;
        this.equipmentDtoService = equipmentDtoService;
        this.authentication = authentication;
    }

    public void initialize() {
        employeeDto = null;
        equipmentDto = null;
        initTableEquipments();
        setIconForButtons();
    }

    private void setIconForButtons() {
        btAddWorkstation.graphicProperty().setValue(UtilFx.setIconForButton(btAddWorkstation, System.getProperty("user.dir") + "/ico/workstation-add.png"));
        btEditWorkstation.graphicProperty().setValue(UtilFx.setIconForButton(btEditWorkstation, System.getProperty("user.dir") + "/ico/workstation-edit.png"));
        btDeleteWorkStation.graphicProperty().setValue(UtilFx.setIconForButton(btDeleteWorkStation, System.getProperty("user.dir") + "/ico/workstation-remove.png"));
        btSelectWorkStation.graphicProperty().setValue(UtilFx.setIconForButton(btSelectWorkStation, System.getProperty("user.dir") + "/ico/workstation-select.png"));
        btPing.graphicProperty().setValue(UtilFx.setIconForButton(btPing, System.getProperty("user.dir") + "/ico/ping.png"));
        btUvnc.graphicProperty().setValue(UtilFx.setIconForButton(btUvnc, System.getProperty("user.dir") + "/ico/uvnc.png"));
        btRdp.graphicProperty().setValue(UtilFx.setIconForButton(btRdp, System.getProperty("user.dir") + "/ico/rdp.png"));
        btMgmt.graphicProperty().setValue(UtilFx.setIconForButton(btMgmt, System.getProperty("user.dir") + "/ico/mgmt.png"));
        btShare.graphicProperty().setValue(UtilFx.setIconForButton(btShare, System.getProperty("user.dir") + "/ico/share.png"));
    }


    private ContextMenu getContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem ping = new MenuItem();
        ping.setText("Ping...");
        ping.setOnAction(this::onPing);
        contextMenu.getItems().add(ping);
        MenuItem uvnc = new MenuItem();
        uvnc.setText("Удаленный сеанс UVNC");
        uvnc.setOnAction(this::onUvnc);
        contextMenu.getItems().add(uvnc);
        MenuItem rdp = new MenuItem();
        rdp.setText("Удаленный сеанс RDP");
        rdp.setOnAction(this::onRdp);
        contextMenu.getItems().add(rdp);
        MenuItem mgmt = new MenuItem();
        mgmt.setText("Управление компьютером");
        mgmt.setOnAction(this::onMgmt);
        contextMenu.getItems().add(mgmt);
        MenuItem openShare = new MenuItem();
        openShare.setText("Открыть c$");
        openShare.setOnAction(this::onExplorerShare);
        contextMenu.getItems().add(openShare);
        return contextMenu;
    }

    @FXML
    private void onExplorerShare(ActionEvent actionEvent) {
        if (equipmentDto == null) return;
        String resource = UtilFx.getResourceAddress(equipmentDto);
        Net.explorerWithShare(resource);
    }

    @FXML
    private void onMgmt(ActionEvent actionEvent) {
        if (equipmentDto == null) return;
        String resource = UtilFx.getResourceAddress(equipmentDto);
        Net.compmgmtConnect(resource);
    }

    @FXML
    private void onRdp(ActionEvent actionEvent) {
        if (equipmentDto == null) return;
        String resource = UtilFx.getResourceAddress(equipmentDto);
        Net.rdpConnect(resource);
    }

    @FXML
    private void onUvnc(ActionEvent actionEvent) {
        if (equipmentDto == null) return;
        String resource = UtilFx.getResourceAddress(equipmentDto);
        Uvnc uvnc = new Uvnc(System.getProperty("user.dir") + "\\vncviewer1201.exe");
        uvnc.executeWithParam(resource, authentication.getUsername(), authentication.getPassword());
    }

    private void initTableEquipments() {
        columnHostName.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        columnHostFullName.setCellValueFactory(new PropertyValueFactory<>("hostFullName"));
        columnIpAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        tvEquipments.getSelectionModel().selectedItemProperty().addListener((observableValue, equipmentDto1, t1) -> {
            if (observableValue.getValue() != null) {
                equipmentDto = observableValue.getValue();
            }
        });
        tvEquipments.setContextMenu(getContextMenu());
    }

    public void setEmployeeDto(EmployeeDto employeeDto) {
        this.employeeDto = employeeDto;
        fillEquipmentDtos(employeeDto);
    }

    public void fillEquipmentDtos(EmployeeDto employeeDto) {
        List<EquipmentDto> equipmentDtoDtosFromDb;
        if (employeeDto == null) {
            equipmentDtoDtosFromDb = equipmentDtoService.findAll();
        } else {
            equipmentDtoDtosFromDb = equipmentDtoService.findAllByEmployee(employeeDto);
        }
        equipmentDtos = FXCollections.observableList(
                equipmentDtoDtosFromDb.stream()
                        .filter(EquipmentDto::isNetwork)
                        .collect(Collectors.toList())
        );
        tvEquipments.setItems(equipmentDtos);
    }

    public void onViewAddWorkstation(ActionEvent actionEvent) {
        openView(EquipmentEditFxController.class);
        EquipmentEditFxController equipmentEditFxController = applicationContext.getBean(EquipmentEditFxController.class);
        equipmentEditFxController.setCurrentEquipment(null);
        if (employeeDto != null)
            equipmentEditFxController.setCurrentEmployee(EmployeeFactory.createEmployeeFromDto(employeeDto));
    }

    public void onViewEditWorkstation(ActionEvent actionEvent) {
        if (equipmentDto != null) {
            openView(EquipmentEditFxController.class);
            EquipmentEditFxController equipmentEditFxController = applicationContext.getBean(EquipmentEditFxController.class);
            equipmentEditFxController.setCurrentEmployee(EmployeeFactory.createEmployeeFromDto(employeeDto));
            equipmentEditFxController.setCurrentEquipment(EquipmentFactory.createEquipmentFromDto(equipmentDto));
        }
    }

    @FXML
    private void onDeleteWorkstation(ActionEvent actionEvent) {
        if (equipmentDto == null || equipmentDtos.size() == 0) return;
        equipmentDtoService.delete(equipmentDto.getId());
        equipmentDtos.remove(equipmentDto);
        fillEquipmentDtos(employeeDto);
    }

    @FXML
    private void onViewWorkstations(ActionEvent actionEvent) {
        openView(WorkstationViewsFxController.class);
        WorkstationViewsFxController workstationViewsFxController = applicationContext.getBean(WorkstationViewsFxController.class);
        workstationViewsFxController.setOwnerClass(this.getClass());
        workstationViewsFxController.setCurrentEmployee(EmployeeFactory.createEmployeeFromDto(employeeDto));
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



    /*
    * Ping
    *
    * */

    private Parent getParent() {
        return this.btAddWorkstation.getScene().getRoot();
    }

    @FXML
    private void onPing(ActionEvent actionEvent) {
        if (equipmentDto == null) return;
        Parent parent = getParent();
        BorderPane borderPane = (BorderPane) parent.getChildrenUnmodifiable().get(0);
        Node outputLogNode;
        if (borderPane.getBottom() == null) {
            FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
            outputLogNode = fxWeaver.loadView(OutputLogFxController.class);
            borderPane.setBottom(outputLogNode);
        } else {
            outputLogNode = borderPane.getBottom();
        }
        pOut = (BorderPane) ((AnchorPane) outputLogNode).getChildrenUnmodifiable().get(0);
        String resourse = UtilFx.getResourceAddress(equipmentDto);
        if (resourse != null) {
            sendPingPool(resourse, 3000);
        }

    }

    private TextArea getOutput() {
        Parent bottom = (Parent) ((BorderPane) getParent().getChildrenUnmodifiable().get(0)).getBottom();
        return (TextArea) ((BorderPane) bottom.getChildrenUnmodifiable().get(0)).getCenter();

    }

    private void sendPingPool(String ip, int timeout) {
        sendPingPool(ip, timeout, (short) 5);
    }

    private void sendPingPool(String ip, int timeout, short maxPing) {
        Thread thread = getThreadOutput(ip, timeout, maxPing);
        new Thread(() -> {
            try {
                pOut.getTop().setDisable(true);
//                btPing.setDisable(true);
                thread.start();
                thread.join();
                pOut.getTop().setDisable(false);
//                btPing.setDisable(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private Thread getThreadOutput(String ip, int timeout, short maxPing) {
        return new Thread(() -> {
            for (int i = maxPing; i > 0; i--) {
                try {
                    getOutput().setText(String.format("%s:\t %s %s", "[Ping...]", Net.sendPing(ip, timeout), getOutput().getText().trim()));
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*
    * End Ping
    *
    * */



}
