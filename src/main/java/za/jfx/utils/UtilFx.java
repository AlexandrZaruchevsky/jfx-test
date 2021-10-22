package za.jfx.utils;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import za.jfx.dto.EmployeeDto;
import za.jfx.dto.EquipmentDto;
import za.jfx.model.jfx.Employee;

import java.io.File;

public class UtilFx {

    public static ImageView setIconForButton(Button button, String pathIcon) {
        File file = new File(pathIcon);
        if (file.exists() && !file.isDirectory()) {
            button.setText(null);
            ImageView imageView = new ImageView("file:" + pathIcon);
            imageView.setFitHeight(28);
            imageView.setFitWidth(28);
            return imageView;
        }
        return null;
    }

    public static Callback<ListView<Employee>, ListCell<Employee>> setCellViewEmployee() {
        return (param) -> new ListCell<>() {
            @Override
            protected void updateItem(Employee employee, boolean empty) {
                super.updateItem(employee, empty);
                if (empty || employee == null || employee.getFullFio() == null) {
                    setText(null);
                } else {
                    setText(employee.getFullFio());
                }
            }
        };
    }

    public static Callback<ListView<EmployeeDto>, ListCell<EmployeeDto>> setCellViewEmployeeDto() {
        return (param) -> new ListCell<>() {
            @Override
            protected void updateItem(EmployeeDto employeeDto, boolean empty) {
                super.updateItem(employeeDto, empty);
                if (empty || employeeDto == null || employeeDto.getFullFio() == null) {
                    setText(null);
                } else {
                    setText(employeeDto.getFullFio());
                }
            }
        };
    }

    public static String getResourceAddress(EquipmentDto equipmentDto) {
        if (equipmentDto != null) {
            if (equipmentDto.getHostName() == null || equipmentDto.getHostName().trim().equals("")){
                if(equipmentDto.getIpAddress()==null || equipmentDto.getIpAddress().trim().equals("")){
                    return null;
                }else {
                    return equipmentDto.getIpAddress().trim();
                }
            }else {
                return equipmentDto.getHostName().trim();
            }
        }
        return null;
    }



}
