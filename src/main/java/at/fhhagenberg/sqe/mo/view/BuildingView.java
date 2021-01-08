package at.fhhagenberg.sqe.mo.view;

import at.fhhagenberg.sqe.mo.IBuildingObserver;
import at.fhhagenberg.sqe.mo.viewcontroller.BuildingViewController;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BuildingView extends VBox implements IBuildingObserver {

  private final BuildingViewController controller;

  public BuildingView(
      BuildingViewController controller, ElevatorsView elevatorsView, FloorsView floorsView) {
    super();
    this.controller = controller;
    this.getChildren().addAll(initModeSelectionElements(), elevatorsView, floorsView);
    this.setPadding(new Insets(16));
    this.setSpacing(64);
  }

  private HBox initModeSelectionElements() {
    Label modeLabel = new Label("Mode: ");
    RadioButton manualModeRadioButton = new RadioButton("Manual");
    RadioButton autoModeRadioButton = new RadioButton("Auto");
    ToggleGroup modeToggleGroup = new ToggleGroup();
    manualModeRadioButton.setSelected(true);
    manualModeRadioButton.setToggleGroup(modeToggleGroup);
    autoModeRadioButton.setToggleGroup(modeToggleGroup);
    modeToggleGroup
        .selectedToggleProperty()
        .addListener(
            (observableValue, toggle, t1) -> {
              RadioButton radioButton = (RadioButton) toggle.getToggleGroup().getSelectedToggle();
              controller.modeChanged(radioButton.getText());
            });

    VBox modeSelectionVBox = new VBox(manualModeRadioButton, autoModeRadioButton);
    modeSelectionVBox.setSpacing(8);
    return new HBox(modeLabel, modeSelectionVBox);
  }

  @Override
  public void update() {
    // update mode selection by reading the value from building
  }
}
