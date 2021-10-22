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
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import za.jfx.factories.NetworkFactory;
import za.jfx.model.jfx.Network;
import za.jfx.model.jfx.PointOfPresence;
import za.jfx.servicies.NetworkService;
import za.jfx.servicies.PointOfPresenceService;
import za.jfx.utils.Net;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@FxmlView("fxml/point-of-presence.fxml")
@Slf4j
public class PointOfPresenceFxController {

    private final ConfigurableApplicationContext applicationContext;

    private final PointOfPresenceService pointOfPresenceService;
    private final NetworkService networkService;

    private ObservableList<PointOfPresence> pointOfPresences;
    private ObservableList<String> subnetNames;
    private ObservableList<Network> subnet;
    private PointOfPresence currentPointOfPresence;

    @FXML
    private ListView<PointOfPresence> listPointOfPresence;
    @FXML
    private ListView<String> listSubNet;
    @FXML
    private TableView<Network> tvSubNet;
    @FXML
    private TableColumn<Network, String> ipAddress;
    @FXML
    private TableColumn<Network, String> hostName;
    @FXML
    private TableColumn<Network, String> hostFullName;
    @FXML
    private TableColumn<Network, LocalDateTime> datePoll;
    @FXML
    private Button btNetScan;
    @FXML
    private TextField txtSubNet;


    public PointOfPresenceFxController(ConfigurableApplicationContext applicationContext, PointOfPresenceService pointOfPresenceService, NetworkService networkService) {
        this.applicationContext = applicationContext;
        this.pointOfPresenceService = pointOfPresenceService;
        this.networkService = networkService;
    }

    public void initialize() {
        pointOfPresences = FXCollections.observableList(
                pointOfPresenceService.findAll().stream()
                        .sorted(Comparator.comparing(PointOfPresence::getName))
                        .collect(Collectors.toList()));
        listPointOfPresence.setItems(pointOfPresences);
        listPointOfPresence.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(PointOfPresence pointOfPresence, boolean empty) {
                super.updateItem(pointOfPresence, empty);
                if (empty || pointOfPresence == null || pointOfPresence.getName() == null) {
                    setText(null);
                } else {
                    setText(pointOfPresence.getName());
                }
            }
        });
        initSubnetTable();
        listPointOfPresence.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (observable.getValue() == null)
                return;
            currentPointOfPresence = observable.getValue();
            subnetNames = getSubnetNames(currentPointOfPresence);
            listSubNet.setItems(subnetNames);
            if (subnetNames.size() > 0) {
                subnetNameSelectFirst();
            } else {
                tvSubNet.setItems(null);
            }
        });
        listPointOfPresence.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount()==2 && mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                onViewEmployees();
            }
        });

        listSubNet.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (observableValue.getValue() == null || observableValue.getValue().trim().equals("")) return;
            tvSubNet.setItems(getSubnet(currentPointOfPresence, observableValue.getValue()));
            subnetSelectFirst();
        });

        selectFirstItem();
    }

    private void subnetSelectFirst() {
        if (tvSubNet.getItems().size() > 0) {
            tvSubNet.getSelectionModel().select(0);
            tvSubNet.refresh();
        }
    }

    private void initSubnetTable() {
        ipAddress.setCellValueFactory(new PropertyValueFactory<>("ipAdress"));
        hostName.setCellValueFactory(new PropertyValueFactory<>("hostName"));
        hostFullName.setCellValueFactory(new PropertyValueFactory<>("hostFullName"));
        datePoll.setCellValueFactory(new PropertyValueFactory<>("datePoll"));
    }

    private void subnetNameSelectFirst() {
        if (listSubNet.getItems().size() > 0) {
            listSubNet.getSelectionModel().select(0);
            listSubNet.refresh();
        }
    }

    private void selectFirstItem() {
        if (pointOfPresences.size() > 0) {
            listPointOfPresence.getSelectionModel().select(0);
            listPointOfPresence.refresh();
        }
    }

    private ObservableList<String> getSubnetNames(PointOfPresence pointOfPresence) {
        if (pointOfPresence == null) return null;
        return FXCollections.observableList(
                networkService.findByPointOfPresence(pointOfPresence).stream()
                        .map(Network::getSubnet)
                        .distinct()
                        .collect(Collectors.toList())
        );
    }

    private ObservableList<Network> getSubnet(PointOfPresence pointOfPresence, String subnetName) {
        if (pointOfPresence == null || subnetName == null) return null;
        return FXCollections.observableList(networkService.findBySubnet(pointOfPresence, subnetName));
    }

    @FXML
    private void onScanNetwork(){
        String subnet = txtSubNet.getText();
        if (subnet == null || "".equals(subnet))
            return;
        PointOfPresence pointOfPresence = currentPointOfPresence;
        List<Network> networks = new ArrayList<>();
        log.info("Start network scan");
        Thread thread = new Thread(() -> {
            try {
                networks.addAll(Net.scanNetwork(subnet, 1000, 0, 256).stream()
                        .map(NetworkFactory::fromInetAddress)
                        .collect(Collectors.toList()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        new Thread(() -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("End network scan");
            networkService.addAll(
                    networks.stream().peek(network -> {
                        network.setSubnet(subnet);
                        network.setPointOfPresence(pointOfPresence);
                    }).collect(Collectors.toList())
            );
        }).start();
    }

    @FXML
    private void onDeleteBySubnet() {
        String subnet = txtSubNet.getText();
        if (subnet == null || "".equals(subnet))
            return;
        networkService.deleteBySubnet(networkService.findBySubnet(currentPointOfPresence, subnet));
    }

    private void viewEmployees(){
        FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        AnchorPane node = fxWeaver.loadView(PointOfPresenceWithChildrenFxController.class);
        Stage stage = new Stage();
        stage.setScene(new Scene(node));
        stage.initOwner(listPointOfPresence.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    @FXML
    private void onViewEmployees(){
        viewEmployees();
        applicationContext.getBean(PointOfPresenceWithChildrenFxController.class).setCurrentPointOfPresence(currentPointOfPresence);
    }


}
