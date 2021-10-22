package za.jfx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.model.jfx.Employee;
import za.jfx.servicies.EmployeeService;
import za.jfx.utils.Net;
import za.jfx.utils.Uvnc;

import java.io.IOException;

@Component
@FxmlView("fxml/employee.fxml")
@Slf4j
public class EmployeeFxController {

    private final ConfigurableApplicationContext applicationContext;
    private final EmployeeService employeeService;


    private Long id = -1L;

    private Employee employee;

    @FXML
    private Button btPing;
    @FXML
    private Button btUvnc;
    @FXML
    private TextField fioField;
    @FXML
    private TextField positionField;
    @FXML
    public TextField phoneKspdField;
    @FXML
    public TextField phoneField;
    @FXML
    private TextField ipField;


    private BorderPane pOut;

    public EmployeeFxController(ConfigurableApplicationContext applicationContext, EmployeeService employeeService) {
        this.applicationContext = applicationContext;
        this.employeeService = employeeService;
    }

    public void initialize() {

    }

    private Parent getParent() {
        return btPing.getScene().getRoot();
    }

    @FXML
    private void onPing() {
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
        String resourse = getResourceAddress(employee);
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
                btPing.setDisable(true);
                thread.start();
                thread.join();
                pOut.getTop().setDisable(false);
                btPing.setDisable(false);
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

    public Long getId() {
        return id;
    }

    public void setIdq(Long id) {
        this.id = id;
        employee = getEmployee(id);
        fillFields();
    }

    public void setEmployee(Employee employee){
        this.employee = employee;
        fillFields();
    }

    private void fillFields() {
        if (employee == null)
            return;
        fioField.setText(employee.getFullFio());
        positionField.setText(employee.getPosition());
        phoneField.setText(employee.getPhone());
        phoneKspdField.setText(employee.getPhoneKspd());
        ipField.setText(employee.getResource());
    }

    private Employee getEmployee(Long id) {
//        return employeeServiceJdbcTemplate.findById(id);
        return employeeService.findById(id);
    }

    public void onUvnc(ActionEvent actionEvent) throws IOException {
        String resourse = getResourceAddress(employee);
        if (resourse != null) {
//            Uvnc uvnc = new Uvnc("vncviewer1201.exe");
            Uvnc uvnc = new Uvnc("C:\\programs\\vncviewer1201.exe");
            uvnc.executeWithParam(resourse, System.getProperty("user.name"), "alexandr");
        }
    }

    public void onRdp(ActionEvent actionEvent) throws IOException {
        String resourse = getResourceAddress(employee);
        if (resourse != null) {
            Net.rdpConnect(resourse);
        }

    }

    public void onMgmt(ActionEvent actionEvent) throws IOException {
        String resourse = getResourceAddress(employee);
        if (resourse != null) {
            Net.compmgmtConnect(resourse);
        }
    }

    public void OnExplorer(ActionEvent actionEvent) throws IOException {
        String resource = getResourceAddress(employee);
        if (resource != null) {
            Net.explorerWithShare(resource);
        }
    }

    private String getResourceAddress(Employee employee) {
        if (employee != null) {
            if (employee.getWorkstation() == null || employee.getWorkstation().trim().equals("")) {
                if (employee.getIp() == null || employee.getIp().trim().equals("")) {
                    return null;
                } else {
                    return employee.getIp().trim();
                }
            } else {
                return employee.getWorkstation().trim();
            }
        }
        return null;
    }

}
