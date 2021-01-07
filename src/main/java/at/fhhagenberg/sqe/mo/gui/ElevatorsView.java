package at.fhhagenberg.sqe.mo.gui;

import at.fhhagenberg.sqe.mo.Elevator;
import at.fhhagenberg.sqe.mo.ElevatorControlCenter;
import java.rmi.RemoteException;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ElevatorsView extends BorderPane {

  private final ElevatorControlCenter elevatorControlCenter;

  private HBox hBox;

  public ElevatorsView(ElevatorControlCenter controller) throws RemoteException {
    super();
    this.elevatorControlCenter = controller;
    initView();
  }

  private void initView() throws RemoteException {
    int numberOfElevators = elevatorControlCenter.getBuilding().getElevators().size();
    hBox = new HBox(numberOfElevators);
    hBox.setSpacing(64);
    hBox.setAlignment(Pos.CENTER);
    for (int elevatorId = 0; elevatorId < numberOfElevators; elevatorId++) {
      initElevator(elevatorControlCenter.getBuilding().getElevators().get(elevatorId));
    }
    this.setCenter(hBox);
  }

  private void initElevator(Elevator elevator) throws RemoteException {
    // Elevator info labels
    Label targetFloorLabel = new Label(String.format("Target floor: %s", elevator.getTarget()));
    Label speedLabel = new Label(String.format("Speed: %s ft/sec", elevator.getSpeed()));
    Label doorStatusLabel = new Label(getCurrentDoorStatus(elevator));
    Label currentPayloadLabel =
        new Label(String.format("Current payload: %s lbs", elevator.getWeight()));
    Label capacityLabel = new Label(String.format("Capacity: %s ppl", elevator.getCapacity()));

    // Elevator current floor and direction
    Label currentFloorLabel = new Label(Integer.toString(elevator.getFloor()));
    Label currentDirectionLabel = new Label(getCurrentDirection(elevator));
    HBox currentFloorAndDirectionHBox = new HBox(currentFloorLabel, currentDirectionLabel);
    currentFloorAndDirectionHBox.setAlignment(Pos.CENTER);

    // Elevator buttons
    GridPane elevatorButtonsGridPane = new GridPane();
    int numberOfFloors = elevatorControlCenter.getBuilding().getFloors().size();
    int row = (numberOfFloors + 1) / 2;
    int column = 2;
    for (int y = 0; y < row; y++) {
      for (int x = 0; x < column; x++) {
        int floor = (row * column - 2 * y - 2) + x;
        Button elevatorButton = new Button(Integer.toString(floor));
        elevatorButton.setPrefWidth(25);
        if (elevator.getServicedFloors().contains(floor)) {
          if (elevator.getTarget() == floor) {
            elevatorButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
          }
        } else {
          elevatorButton.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        elevatorButton.setOnAction(
            actionEvent -> {
              String floorIdStr = elevatorButton.getText();
              if (elevator.getServicedFloors().contains(Integer.valueOf(floorIdStr))) {
                targetFloorLabel.setText(String.format("Target Floor: %s", floorIdStr));
                elevatorButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
              }
            });
        elevatorButtonsGridPane.add(elevatorButton, x, y);
      }
    }
    elevatorButtonsGridPane.setAlignment(Pos.CENTER);

    VBox elevatorInfoVBox =
        new VBox(
            targetFloorLabel,
            speedLabel,
            doorStatusLabel,
            currentPayloadLabel,
            capacityLabel,
            currentFloorAndDirectionHBox,
            elevatorButtonsGridPane);
    elevatorInfoVBox.setSpacing(8);
    elevatorInfoVBox.setStyle("-fx-border-color: lightgrey; -fx-padding: 16");
    hBox.getChildren().add(elevatorInfoVBox);
  }

  private String getCurrentDoorStatus(Elevator elevator) {
    // open=1, closed=2
    switch (elevator.getDoorStatus()) {
      case 1:
        return "Door status: Open";
      case 2:
        return "Door status: Closed";
      default:
        return "-";
    }
  }

  private String getCurrentDirection(Elevator elevator) throws RemoteException {
    // up=0, down=1 and uncommitted=2
    switch (elevator.getCommittedDirection()) {
      case 0:
        return "⬆️️";
      case 1:
        return "⬇️";
      case 2:
        return "↔️";
      default:
        return "-";
    }
  }
}
