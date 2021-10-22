package za.jfx.controllers.components;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import lombok.Getter;
import za.jfx.dto.ListViewZa;

public class ListViewFxZa<T extends ListViewZa> extends ListView<T> {

    @Getter
    private T currentItem;


    public ListViewFxZa() {
        super();
        this.init();
    }

    public void init() {
        setCellFactory(tListView -> new ListCell<>() {
                    @Override
                    protected void updateItem(T t, boolean empty) {
                        super.updateItem(t, empty);
                        if (empty || t == null || t.getRowText() == null) {
                            setText(null);
                        } else {
                            setText(t.getRowText());
                        }
                    }
                }
        );

        this.getSelectionModel().selectedItemProperty().addListener((observableValue, employeeDto, t1) -> {
            if (observableValue.getValue() == null) {
                currentItem = null;
            } else {
                currentItem = observableValue.getValue();
            }
        });


    }

}
