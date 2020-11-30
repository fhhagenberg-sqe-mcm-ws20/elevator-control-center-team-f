package at.fhhagenberg.sqe.mo.gui;

import at.fhhagenberg.sqe.mo.ElevatorControlCenter;
import at.fhhagenberg.sqe.mo.Floor;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class FloorsView extends BorderPane {

  private final ElevatorControlCenter elevatorControlCenter;

  private Button floorUpButton;
  private Button floorDownButton;

  public FloorsView(ElevatorControlCenter controller) throws RemoteException {
    super();
    this.elevatorControlCenter = controller;
    initView();
  }

  private void initView() throws RemoteException {
    // Floor label
    Label floorLabel = new Label("Floor: ");

    // Floors
    List<String> floors = new ArrayList<>();
    int numberOfFloors = elevatorControlCenter.getBuilding().getFloors().size();
    for (int floorId = 0; floorId < numberOfFloors; floorId++) {
      floors.add(String.format("Floor %s", floorId));
    }

    // Floors combo box
    ComboBox<String> floorsComboBox = new ComboBox<>(FXCollections.observableArrayList(floors));
    floorsComboBox.setValue(floors.get(0));
    floorsComboBox.setOnAction(
        event -> {
          try {
            updateFloorButtons(floorsComboBox.getValue());
          } catch (RemoteException remoteException) {
            remoteException.printStackTrace();
          }
        });

    // Floors buttons
    floorUpButton = new Button("⬆️");
    floorDownButton = new Button("⬇️");

    HBox hBox = new HBox(floorLabel, floorsComboBox, floorUpButton, floorDownButton);
    this.setCenter(hBox);
  }

  private void updateFloorButtons(String floorIdStr) throws RemoteException {
    int floorId = Integer.parseInt(floorIdStr.split(" ")[1]);
    Floor floor = elevatorControlCenter.getBuilding().getFloors().get(floorId);
    if (floor.isButtonUp()) {
      floorUpButton.setStyle("-fx-text-fill: red");
    } else {
      //      floorUpButton.getStyleClass().clear();
    }

    if (floor.isButtonDown()) {
      floorDownButton.setStyle("-fx-text-fill: red");
    } else {
      //      floorUpButton.getStyleClass().clear();
    }
  }
}
