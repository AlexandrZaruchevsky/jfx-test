package za.jfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import net.rgielen.fxweaver.core.FxmlView;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import za.jfx.controllers.components.ListViewFxZa;
import za.jfx.dto.EmployeeDto;
import za.jfx.dto.impl.UserEmployeeDto;
import za.jfx.dto.impl.UserFromAd;
import za.jfx.dto.impl.UserFromAdV1;
import za.jfx.factories.EmployeeFactory;
import za.jfx.model.jfx.Employee;
import za.jfx.model.jfx.Equipment;
import za.jfx.servicies.EmployeeService;
import za.jfx.servicies.EquipmentService;
import za.jfx.utils.Net;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/users-from-ad.fxml")
public class UsersFromAdFxController {

    private final EmployeeService employeeService;
    private final EquipmentService equipmentService;

    public TextField searchField;


    private ObservableList<EmployeeDto> employeeDtos;
    private ObservableList<UserFromAd> userFromAds;
    private ObservableList<UserFromAdV1> userFromAdV1s;
    private ObservableList<UserEmployeeDto> userEmployeeDtos;

    @FXML
    private ListViewFxZa<UserFromAdV1> userFromAdListView;
    //    private ListViewFxZa<UserFromAd> userFromAdListView;
    @FXML
    private ListViewFxZa<EmployeeDto> employeeDtoListView;
    @FXML
    private ListViewFxZa<UserEmployeeDto> userEmployeeListView;
    @FXML
    private Button btAddUserEmployee;


    public UsersFromAdFxController(EmployeeService employeeService, EquipmentService equipmentService) {
        this.employeeService = employeeService;
        this.equipmentService = equipmentService;
    }

    public void initialize() {
        employeeDtos = FXCollections.observableList(
                employeeService.findAll().stream()
                        .map(EmployeeFactory::fromEmployee)
                        .collect(Collectors.toList()));
        employeeDtoListView.setItems(employeeDtos);
        userEmployeeDtos = FXCollections.observableArrayList();
        userEmployeeListView.setItems(userEmployeeDtos);
//        userFromAds = FXCollections.observableList(readUsersFromAd());
        userFromAdV1s = FXCollections.observableList(readUsersFromAdV1());
//        userFromAdListView.setItems(userFromAds);
        userFromAdListView.setItems(userFromAdV1s);
        employeeDtoListView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && mouseEvent.getButton() == MouseButton.PRIMARY) {
                addUserEmployee();
            }
        });

//        readUsersFromAdV1();

    }

    private List<UserFromAd> readUsersFromAd() {
        List<UserFromAd> collect = new ArrayList<>();
//        try {
//            List<String> list = Files.readAllLines(Path.of("C:\\develop\\users91.csv"));
//            collect = list.stream()
//                    .map(s -> {
//                        String[] split = s.split(",");
//                        return new UserFromAd(split[0].trim(), split[1].trim(), split[2].trim());
//                    })
//                    .collect(Collectors.toList());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return collect;

    }

    private List<UserFromAdV1> readUsersFromAdV1() {
        List<UserFromAdV1> list = new ArrayList<>();
        try {
            List<String> listFromFile = Files.readAllLines(Path.of("c:\\develop\\000-000.csv")).stream()
                    .map(s -> s.replaceAll("\"", "")).collect(Collectors.toList());
//            listFromFile.forEach(System.out::println);
//            System.out.println(listFromFile.size());
            list = listFromFile.stream()
                    .map(s -> {
                        String[] split = s.split(";");
                        return new UserFromAdV1(
                                split[0].trim(),
                                split[1].trim(),
                                split[2].trim(),
                                split[3].trim(),
                                split[4].trim(),
                                split[5].trim(),
                                split[6].trim().toLowerCase().equals("true")
                        );
                    })
                    .filter(UserFromAdV1::isEnabled)
                    .sorted(Comparator.comparing(UserFromAdV1::getName))
                    .collect(Collectors.toList());
//            System.out.println(list.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    @FXML
    private void addUserEmployee() {
        if ((userFromAdListView.getCurrentItem() != null) && (employeeDtoListView.getCurrentItem() != null)) {
//            userEmployeeDtos.add(new UserEmployeeDto(userFromAdListView.getCurrentItem(), employeeDtoListView.getCurrentItem()));
        }
    }

    @FXML
    private void deleteUserEmployee() {
        if (userEmployeeListView.getCurrentItem() != null) {
            userEmployeeDtos.remove(userEmployeeListView.getCurrentItem());
        }
    }

    @FXML
    private void searchEmployeeDto(ActionEvent actionEvent) {
        if (searchField.getText() == null || searchField.getText().trim().equals("")) {
            employeeDtoListView.setItems(employeeDtos);
            return;
        }
        employeeDtoListView.setItems(
                employeeDtos.filtered(employeeDto -> employeeDto.getLastName().toLowerCase().contains(searchField.getText().trim().toLowerCase()))
        );
    }

    @FXML
    private void updateEmployeeEquipment() {
//        UserEmployeeDto userEmployeeDto = userEmployeeListView.getCurrentItem();
//        if (userEmployeeDto == null) return;
        if (userEmployeeDtos.size() > 0) {
            userEmployeeDtos.forEach(userEmployeeDto1 -> {
                Employee employeeFromDb = employeeService.findById(userEmployeeDto1.getEmployeeDto().getId());
                employeeFromDb.setUsername(userEmployeeDto1.getUserFromAd().getUsername());
                employeeService.update(employeeFromDb);
                Equipment equipment = new Equipment();
                equipment.setHostName(userEmployeeDto1.getUserFromAd().getWorkstation());
                equipment.setHostFullName(userEmployeeDto1.getUserFromAd().getWorkstation());
                equipment.setEmployee(employeeFromDb);
                equipment.setNetwork(true);
                equipmentService.add(equipment);
            });
            userEmployeeDtos = FXCollections.observableArrayList();
            userEmployeeListView.setItems(userEmployeeDtos);
        }
    }

    @FXML
    private void updateEmployee(ActionEvent actionEvent) throws IOException {

        List<String> list = Files.readAllLines(Path.of("C:\\develop\\usersPS.csv")).stream().map(s -> s.replaceAll("\"", "")).collect(Collectors.toList());
        List<Employee> employees = list.stream()
                .map(s -> {
                    String[] split = s.split(";");
                    String[] fio = split[0].split("\\s+");
                    Employee employee = new Employee();
                    employee.setLastName(fio[0]);
                    employee.setFirstName(fio[1]);
                    employee.setMiddleName(fio[2]);
                    employee.setUsername(split[1]);
                    return employee;
                })
                .collect(Collectors.toList());
        employees
                .forEach(employee -> {
                    List<Employee> employeesFromDb = employeeService.findByFio(employee.getLastName(), employee.getFirstName(), employee.getMiddleName());
                    if (employeesFromDb.size() == 1) {
                        Employee employeeFromDb = employeesFromDb.get(0);
                        employeeFromDb.setUsername(employee.getUsername());
                        employeeService.update(employeeFromDb);
                    } else if (employeesFromDb.size() == 0) {
                        employeeService.add(employee);
                    } else {
                        employeesFromDb.forEach(System.out::println);
                    }
                });

    }

    @FXML
    private void updateEmployeeFromUserAdV1() {
        new Thread(() -> {
            List<Employee> employees = getUserFromAdV1List(readUsersFromAdV1()).stream()
                    .filter(employee -> !employee.getUsername().startsWith("039revi"))
                    .collect(Collectors.toList());
            employees
                    .forEach(employee -> {
                        List<Employee> employeesFromDb = employeeService.findByFio(employee.getLastName(), employee.getFirstName(), employee.getMiddleName());
                        if (employeesFromDb.size() == 1) {
                            Employee employeeFromDb = employeesFromDb.get(0);
                            employeeFromDb.setUsername(employee.getUsername());
                            employeeFromDb.setDepartment(employee.getDepartment());
                            employeeFromDb.setPosition(employee.getPosition());
                            employeeFromDb.setEmail(employee.getEmail());
                            employeeFromDb = employeeService.update(employeeFromDb);
                            String workstaionName = employee.getWorkstation() != null && !employee.getWorkstation().trim().equals("") ? employee.getWorkstation().trim().substring(16) : null;
                            if (workstaionName != null) {
                                Equipment equipment = new Equipment();
                                equipment.setHostName(workstaionName);
                                equipment.setNetwork(true);
                                try {
                                    InetAddress inetAddress = Net.sendPingV1(workstaionName, 100);
                                    if (inetAddress != null) {
                                        equipment.setHostFullName(inetAddress.getCanonicalHostName());
                                        equipment.setIpAddress(inetAddress.getHostAddress());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                equipment.setEmployee(employeeFromDb);
                                equipmentService.add(equipment);
                            }
                            System.out.println(workstaionName);
                        } else if (employeesFromDb.size() == 0) {
                            employeeService.add(employee);
                        } else {
//                            employeesFromDb.forEach(System.out::println);
                        }
                    });
        }).start();
    }

    private List<Employee> getUserFromAdV1List(List<UserFromAdV1> userFromAdV1s) {
        if (userFromAdV1s.size() == 0) return new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(UserFromAdV1.class, Employee.class).addMappings(mapper -> {
            mapper.map(UserFromAdV1::getTitle, Employee::setPosition);
            mapper.map(UserFromAdV1::getLastName, Employee::setLastName);
            mapper.map(UserFromAdV1::getFirstName, Employee::setFirstName);
            mapper.map(UserFromAdV1::getMiddleName, Employee::setMiddleName);
            mapper.map(UserFromAdV1::getMail, Employee::setEmail);
            mapper.map(UserFromAdV1::getSamAccountName, Employee::setUsername);
            mapper.map(UserFromAdV1::getInfo, Employee::setWorkstation);
        });
        return userFromAdV1s.stream()
                .map(userFromAdV1 -> modelMapper.map(userFromAdV1, Employee.class))
                .collect(Collectors.toList());
    }


}
