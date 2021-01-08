package at.fhhagenberg.sqe.mo.view;

import at.fhhagenberg.sqe.mo.IBuildingObserver;
import at.fhhagenberg.sqe.mo.viewcontroller.FloorsViewController;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FloorsView extends BorderPane implements IBuildingObserver {

  private final FloorsViewController controller;

  private ComboBox<String> floorsComboBox;
  private Button floorUpButton;
  private Button floorDownButton;

  private int selectedFloorIndex = 0;

  public FloorsView(FloorsViewController controller) {
    super();
    this.controller = controller;
    initView();
  }

  private void initView() {
    // Floors
    List<String> floors = controller.getFloors();

    // Floors combo box
    floorsComboBox = new ComboBox<>(FXCollections.observableArrayList(floors));
    floorsComboBox.setOnAction(
        event -> {
          selectedFloorIndex = floorsComboBox.getSelectionModel().getSelectedIndex();
          controller.floorChanged(selectedFloorIndex, floorUpButton, floorDownButton);
        });

    // Floor up button
    floorUpButton = new Button("⬆️");
    floorUpButton.setOnAction(event -> controller.floorUpButtonPressed(selectedFloorIndex));

    // Floor down button
    floorDownButton = new Button("⬇️");
    floorDownButton.setOnAction(event -> controller.floorDownButtonPressed(selectedFloorIndex));

    // Initial selection
    floorsComboBox.setValue(floors.get(selectedFloorIndex));
    controller.floorChanged(selectedFloorIndex, floorUpButton, floorDownButton);

    HBox hBox = new HBox(floorsComboBox, floorUpButton, floorDownButton);
    this.setCenter(hBox);
  }

  @Override
  public void update() {
    initView();
  }
}
