package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.gui.ElevatorsView;
import at.fhhagenberg.sqe.mo.gui.FloorsView;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) throws RemoteException {
    BorderPane root = new BorderPane();
    root.setCenter(initMainElements());

    stage.setTitle("Elevator Control Center");
    stage.setScene(new Scene(root, 800, 500));
    stage.setOnCloseRequest(event -> System.exit(0));
    stage.show();
  }

  private VBox initMainElements() throws RemoteException {
    ElevatorControlCenter ecc = new ElevatorControlCenter();
    ecc.initDemoBuilding();
    ElevatorsView elevatorsView = new ElevatorsView(ecc);
    FloorsView floorsView = new FloorsView(ecc);

    VBox vBox = new VBox(initModeSelectionElements(), elevatorsView, floorsView);
    vBox.setPadding(new Insets(16));
    vBox.setSpacing(64);
    return vBox;
  }

  private HBox initModeSelectionElements() {
    Label modeLabel = new Label("Mode: ");
    RadioButton manualModeRadioButton = new RadioButton("Manual");
    RadioButton autoModeRadioButton = new RadioButton("Auto");
    ToggleGroup modeToggleGroup = new ToggleGroup();
    manualModeRadioButton.setSelected(true);
    manualModeRadioButton.setToggleGroup(modeToggleGroup);
    autoModeRadioButton.setToggleGroup(modeToggleGroup);

    VBox modeSelectionVBox = new VBox(manualModeRadioButton, autoModeRadioButton);
    modeSelectionVBox.setSpacing(8);
    return new HBox(modeLabel, modeSelectionVBox);
  }

  public static void main(String[] args) {
    launch();
  }
}
