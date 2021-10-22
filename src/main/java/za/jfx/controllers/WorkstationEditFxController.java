package za.jfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.model.jfx.Workstation;
import za.jfx.servicies.WorkstationService;

@Component
@FxmlView("fxml/workstation-edit.fxml")
public class WorkstationEditFxController {

    private final ConfigurableApplicationContext applicationContext;
    private final WorkstationService workstationService;

    private PointOfPresence pointOfPresence;
    private Workstation workstation;
    private Class<?> classOwner;

    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    @FXML
    private TextField fieldHostName;
    @FXML
    private TextField fieldHostFullName;
    @FXML
    private TextField fieldIpAddress;
    @FXML
    private TextArea areaDescription;

    public WorkstationEditFxController(ConfigurableApplicationContext applicationContext, WorkstationService workstationService) {
        this.applicationContext = applicationContext;
        this.workstationService = workstationService;
    }

    public void initialize() {

    }

    public void setPointOfPresence(PointOfPresence pointOfPresence) {
        this.pointOfPresence = pointOfPresence;
    }

    public void setWorkstation(Workstation workstation) {
        this.workstation = workstation;
        fillFields();
    }

    private void fillFields() {
        fieldHostName.setText(workstation.getHostName());
        fieldHostFullName.setText(workstation.getHostFullName());
        fieldIpAddress.setText(workstation.getIpAddress());
        areaDescription.setText(workstation.getDescription());
    }

    private Workstation getFillWorkstation() {
        Workstation workstationLocal;
        if (workstation == null)
            workstationLocal = new Workstation();
        else
            workstationLocal = workstation;
        workstationLocal.setHostName(fieldHostName.getText());
        workstationLocal.setHostFullName(fieldHostFullName.getText());
        workstationLocal.setIpAddress(fieldIpAddress.getText());
        workstationLocal.setDescription(areaDescription.getText());
        if (pointOfPresence!=null)
            workstationLocal.setPointOfPresence(pointOfPresence);
        return workstationLocal;
    }

    @FXML
    private void onSaveWorkStation() {
        if(workstation!=null){
            workstationService.add(getFillWorkstation());
        }else {
            workstationService.update(getFillWorkstation());
        }
        if(classOwner!=null){
            if (classOwner.equals(PointOfPresenceWithChildrenFxController.class)){
                applicationContext.getBean(PointOfPresenceWithChildrenFxController.class).fillWorksations();
            }
            if (classOwner.equals(EquipmentsByEmployeeFxController.class)){

            }
        }
        onClose();
    }

    public void setClassOwner(Class<?> classOwner) {
        this.classOwner = classOwner;
    }

    @FXML
    private void onClose(){
        ((Stage)this.btSave.getScene().getWindow()).close();
    }

}
