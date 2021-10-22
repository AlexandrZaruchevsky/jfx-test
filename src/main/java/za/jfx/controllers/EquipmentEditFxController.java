package za.jfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.Equipment;
import za.jfx.servicies.EquipmentService;

@Component
@FxmlView("fxml/equipment-edit.fxml")
public class EquipmentEditFxController {

    private final ConfigurableApplicationContext applicationContext;
    private final EquipmentService equipmentService;

    private Employee currentEmployee;
    private Equipment currentEquipment;

    @FXML
    private TextField hostNameField;
    @FXML
    private TextField hostFullNameField;
    @FXML
    private TextField ipAddressField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField manufacturerField;
    @FXML
    private TextField countryOfManufacturerField;
    @FXML
    private TextField inventoryNumberField;
    @FXML
    private TextField serialNumberField;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;


    public EquipmentEditFxController(
            ConfigurableApplicationContext applicationContext,
            EquipmentService equipmentService
    ) {
        this.applicationContext = applicationContext;
        this.equipmentService = equipmentService;
    }

    public void initialize() {
    }

    private void fillFields() {
        if (currentEquipment == null)
            return;
        hostNameField.setText(currentEquipment.getHostName());
        hostFullNameField.setText(currentEquipment.getHostFullName());
        ipAddressField.setText(currentEquipment.getIpAddress());
        nameField.setText(currentEquipment.getName());
        fullNameField.setText(currentEquipment.getFullName());
        manufacturerField.setText(currentEquipment.getManufacturer());
        countryOfManufacturerField.setText(currentEquipment.getCountryOfManufacture());
        inventoryNumberField.setText(currentEquipment.getInventoryNumber());
        serialNumberField.setText(currentEquipment.getSerialNumber());
    }


    public void setCurrentEmployee(Employee currentEmployee) {
        this.currentEmployee = currentEmployee;
    }

    public void setCurrentEquipment(Equipment currentEquipment) {
        this.currentEquipment = currentEquipment;
        fillFields();
    }

    public void onSaveEquipment(ActionEvent actionEvent) {
        if (currentEquipment == null) {
            currentEquipment = new Equipment();
        }
        if (currentEmployee != null) {
            currentEquipment.setEmployee(currentEmployee);
        }
        fillEquipmentFromField();
        equipmentService.add(currentEquipment);
        if (currentEmployee != null) {
            applicationContext.getBean(EquipmentsByEmployeeFxController.class).fillEquipmentDtos(EmployeeFactory.fromEmployee(currentEmployee));
        }
        onClose(null);
    }

    private void fillEquipmentFromField() {
        currentEquipment.setHostName(hostNameField.getText());
        currentEquipment.setHostFullName(hostFullNameField.getText());
        currentEquipment.setIpAddress(ipAddressField.getText());
        currentEquipment.setName(nameField.getText());
        currentEquipment.setFullName(fullNameField.getText());
        currentEquipment.setManufacturer(manufacturerField.getText());
        currentEquipment.setCountryOfManufacture(countryOfManufacturerField.getText());
        currentEquipment.setInventoryNumber(inventoryNumberField.getText());
        currentEquipment.setSerialNumber(serialNumberField.getText());
        currentEquipment.setNetwork(true);
    }

    public void onClose(ActionEvent actionEvent) {
        ((Stage) this.btSave.getScene().getWindow()).close();
    }
}
