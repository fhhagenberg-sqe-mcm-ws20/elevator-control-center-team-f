package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.view.BuildingView;
import at.fhhagenberg.sqe.mo.viewcontroller.BuildingViewController;
import javafx.application.Application;
import javafx.stage.Stage;
import sqelevator.IElevator;

/** JavaFX App */
public class App extends Application {

  private final ElevatorControlCenter ecc;

  public App() {
    super();
    IElevator elevatorApi = new BuildingSimulation();
    ecc = new ElevatorControlCenter(elevatorApi);
  }

  public App(ElevatorControlCenter ecc) {
    super();
    this.ecc = ecc;
  }

  @Override
  public void start(Stage stage) {
    // Create building view controller
    BuildingViewController buildingViewController =
        new BuildingViewController(new BuildingView(), ecc.getBuilding());

    // Bind ecc with view controller
    ecc.setBuildingViewController(buildingViewController);
    buildingViewController.startService();

    stage.setTitle("Elevator Control Center");
    stage.setScene(buildingViewController.view.getScene());
    stage.setOnCloseRequest(event -> System.exit(0));
    stage.sizeToScene();
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
