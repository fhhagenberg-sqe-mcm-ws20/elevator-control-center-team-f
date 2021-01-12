package at.fhhagenberg.sqe.mo.view;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildingView implements IBuildingView {

  private IBuildingViewDelegate delegate;

  private final Scene scene;

  private VBox layout;

  private ToggleGroup modeToggleGroup;

  private RadioButton manualModeRadioButton;

  private RadioButton autoModeRadioButton;

  private BorderPane elevatorsPane;

  private HBox floorsPane;

  private ComboBox<String> floorsComboBox;

  private Label floorButtonUp;

  private Label floorButtonDown;

  private int selectedFloorIndex = 0;

  private boolean isUpdating = false;

  public BuildingView() {
    initView();
    scene = new Scene(layout, 800, 600);
  }

  public Scene getScene() {
    return scene;
  }

  @Override
  public void setDelegate(IBuildingViewDelegate delegate) {
    this.delegate = delegate;
  }

  @Override
  public void update() {
    if (delegate != null && !isUpdating) {
      isUpdating = true;

      // update mode selection
      if (delegate.isAutoModeEnabled()) {
        autoModeRadioButton.setSelected(true);
      } else {
        manualModeRadioButton.setSelected(true);
      }
      modeToggleGroup
          .selectedToggleProperty()
          .addListener(
              (observableValue, toggle, t1) -> {
                RadioButton radioButton = (RadioButton) t1.getToggleGroup().getSelectedToggle();
                delegate.didModeChange(radioButton.getText());
              });

      // update elevators pane
      int numberOfElevators = delegate.getNumberOfElevators();
      HBox elevatorsHBox = new HBox(numberOfElevators);
      elevatorsHBox.setSpacing(64);
      elevatorsHBox.setAlignment(Pos.CENTER);
      for (int elevatorId = 0; elevatorId < numberOfElevators; elevatorId++) {
        elevatorsHBox.getChildren().add(initElevator(elevatorId));
      }
      elevatorsPane.setCenter(elevatorsHBox);

      // update floor pane
      floorsComboBox.setItems(FXCollections.observableList(delegate.getFloors()));
      floorsComboBox.setValue(floorsComboBox.getItems().get(selectedFloorIndex));
      floorsComboBox.setOnAction(
          event -> {
            selectedFloorIndex = floorsComboBox.getSelectionModel().getSelectedIndex();
            delegate.didFloorChange();
          });
      floorButtonUp.setStyle(delegate.getFloorButtonUp(selectedFloorIndex));
      floorButtonDown.setStyle(delegate.getFloorButtonDown(selectedFloorIndex));
      floorsPane.getChildren().clear();
      floorsPane.getChildren().addAll(floorsComboBox, floorButtonUp, floorButtonDown);

      isUpdating = false;
    }
  }

  private void initView() {
    layout = new VBox();
    layout.getChildren().clear();
    layout.getChildren().addAll(initModeSelectionElements(), initElevatorsPane(), initFloorsPane());
    layout.setPadding(new Insets(16));
    layout.setSpacing(64);
  }

  private HBox initModeSelectionElements() {
    Label modeLabel = new Label("Mode: ");
    manualModeRadioButton = new RadioButton("Manual");
    manualModeRadioButton.setId("manualModeRadioButton");
    autoModeRadioButton = new RadioButton("Auto");
    modeToggleGroup = new ToggleGroup();
    manualModeRadioButton.setToggleGroup(modeToggleGroup);
    autoModeRadioButton.setToggleGroup(modeToggleGroup);

    VBox modeSelectionVBox = new VBox(manualModeRadioButton, autoModeRadioButton);
    modeSelectionVBox.setSpacing(8);
    return new HBox(modeLabel, modeSelectionVBox);
  }

  private BorderPane initElevatorsPane() {
    elevatorsPane = new BorderPane();
    return elevatorsPane;
  }

  private VBox initElevator(int elevatorId) {
    // Elevator target selection for manual mode
    Label selectTargetFloorLabel = new Label("Select target:");
    ComboBox<String> targetComboBox =
        new ComboBox<>(
            FXCollections.observableArrayList(delegate.getElevatorServicedFloors(elevatorId)));
    targetComboBox.setOnAction(
        event ->
            delegate.didTargetChange(
                elevatorId, targetComboBox.getSelectionModel().getSelectedItem()));
    targetComboBox.setId("targetComboBox");

    // Check the mode logic
    targetComboBox.setDisable(delegate.isAutoModeEnabled());

    HBox selectTargetHBox = new HBox(selectTargetFloorLabel, targetComboBox);
    selectTargetHBox.setSpacing(2);
    selectTargetHBox.setAlignment(Pos.CENTER);

    // Elevator info labels
    Label targetFloorLabel = new Label(delegate.getElevatorTarget(elevatorId));
    targetFloorLabel.setId("targetFloorLabel");
    Label speedLabel = new Label(delegate.getElevatorSpeed(elevatorId));
    Label doorStatusLabel = new Label(delegate.getElevatorDoorStatus(elevatorId));
    Label currentPayloadLabel = new Label(delegate.getElevatorWeight(elevatorId));
    Label capacityLabel = new Label(delegate.getElevatorCapacity(elevatorId));

    // Elevator current floor and direction
    Label currentFloorLabel = new Label(delegate.getElevatorFloor(elevatorId));
    Label currentDirectionLabel = new Label(delegate.getElevatorCommittedDirection(elevatorId));
    currentDirectionLabel.setId("currentDirectionLabel-" + elevatorId);
    HBox currentFloorAndDirectionHBox = new HBox(currentFloorLabel, currentDirectionLabel);
    currentFloorAndDirectionHBox.setAlignment(Pos.CENTER);

    // Elevator buttons
    GridPane elevatorButtonsGridPane = new GridPane();
    int numberOfButtons = delegate.getNumberOfElevatorButtons(elevatorId);
    int row = (numberOfButtons + 1) / 2;
    int column = 2;
    for (int y = 0; y < row; y++) {
      for (int x = 0; x < column; x++) {
        int floor = (row * column - 2 * y - 2) + x;
        Label elevatorButtonLabel = new Label(Integer.toString(floor));
        elevatorButtonLabel.setMinSize(25, 25);
        elevatorButtonLabel.setStyle(delegate.getElevatorButtonStyle(elevatorId, floor));
        elevatorButtonLabel.setAlignment(Pos.CENTER);
        elevatorButtonLabel.setId("e"+elevatorId+"-floor"+floor);
        elevatorButtonsGridPane.add(elevatorButtonLabel, x, y);
      }
    }
    elevatorButtonsGridPane.setAlignment(Pos.CENTER);

    VBox elevatorInfoVBox =
        new VBox(
            selectTargetHBox,
            targetFloorLabel,
            speedLabel,
            doorStatusLabel,
            currentPayloadLabel,
            capacityLabel,
            currentFloorAndDirectionHBox,
            elevatorButtonsGridPane);
    elevatorInfoVBox.setSpacing(8);
    elevatorInfoVBox.setStyle("-fx-border-color: lightgrey; -fx-padding: 16");
    return elevatorInfoVBox;
  }

  private HBox initFloorsPane() {
    // Floors combo box
    floorsComboBox = new ComboBox<>();

    // Floor buttons
    floorButtonUp = new Label("⬆️");
    floorButtonUp.setMinSize(25, 25);
    floorButtonUp.setAlignment(Pos.CENTER);
    floorButtonDown = new Label("⬇️");
    floorButtonDown.setMinSize(25, 25);
    floorButtonDown.setAlignment(Pos.CENTER);

    floorsPane = new HBox(floorsComboBox, floorButtonUp, floorButtonDown);
    floorsPane.setSpacing(5);
    floorsPane.setAlignment(Pos.CENTER);
    return floorsPane;
  }
}
