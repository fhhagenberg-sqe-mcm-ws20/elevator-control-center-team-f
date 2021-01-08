package at.fhhagenberg.sqe.mo.view;

import at.fhhagenberg.sqe.mo.IBuildingObserver;
import at.fhhagenberg.sqe.mo.viewcontroller.ElevatorsViewController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ElevatorsView extends BorderPane implements IBuildingObserver {

  private final ElevatorsViewController controller;

  private HBox hBox;

  public ElevatorsView(ElevatorsViewController controller) {
    super();
    this.controller = controller;
    initView();
  }

  private void initView() {
    int numberOfElevators = controller.getNumberOfElevators();
    hBox = new HBox(numberOfElevators);
    hBox.setSpacing(64);
    hBox.setAlignment(Pos.CENTER);
    for (int elevatorId = 0; elevatorId < numberOfElevators; elevatorId++) {
      initElevator(elevatorId);
    }
    this.setCenter(hBox);
  }

  private void initElevator(int elevatorId) {
    // Elevator info labels
    Label targetFloorLabel = new Label(controller.getElevatorTarget(elevatorId));
    Label speedLabel = new Label(controller.getElevatorSpeed(elevatorId));
    Label doorStatusLabel = new Label(controller.getElevatorDoorStatus(elevatorId));
    Label currentPayloadLabel = new Label(controller.getElevatorWeight(elevatorId));
    Label capacityLabel = new Label(controller.getElevatorCapacity(elevatorId));

    // Elevator current floor and direction
    Label currentFloorLabel = new Label(controller.getElevatorFloor(elevatorId));
    Label currentDirectionLabel = new Label(controller.getElevatorCommittedDirection(elevatorId));
    HBox currentFloorAndDirectionHBox = new HBox(currentFloorLabel, currentDirectionLabel);
    currentFloorAndDirectionHBox.setAlignment(Pos.CENTER);

    // Elevator buttons
    GridPane elevatorButtonsGridPane = new GridPane();
    int numberOfButtons = controller.getNumberOfElevatorButtons(elevatorId);
    int row = (numberOfButtons + 1) / 2;
    int column = 2;
    for (int y = 0; y < row; y++) {
      for (int x = 0; x < column; x++) {
        int floor = (row * column - 2 * y - 2) + x;
        Button elevatorButton = new Button(Integer.toString(floor));
        elevatorButton.setPrefWidth(25);
        if (controller.doesElevatorServiceFloor(elevatorId, floor)) {
          if (controller.doesElevatorTargetFloor(elevatorId, floor)) {
            elevatorButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
          }
        } else {
          elevatorButton.setStyle("-fx-background-color: black; -fx-text-fill: white");
        }
        elevatorButton.setOnAction(
            actionEvent ->
                controller.elevatorButtonPressed(elevatorId, elevatorButton, targetFloorLabel));
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

  @Override
  public void update() {
    initView();
  }
}
