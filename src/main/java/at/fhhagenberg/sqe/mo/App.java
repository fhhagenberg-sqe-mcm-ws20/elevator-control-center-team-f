package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.viewcontroller.BuildingViewController;
import at.fhhagenberg.sqe.mo.viewcontroller.ElevatorsViewController;
import at.fhhagenberg.sqe.mo.viewcontroller.FloorsViewController;
import at.fhhagenberg.sqe.mo.view.BuildingView;
import at.fhhagenberg.sqe.mo.view.ElevatorsView;
import at.fhhagenberg.sqe.mo.view.FloorsView;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/** JavaFX App */
public class App extends Application {

  @Override
  public void start(Stage stage) throws DesynchronizationException, RemoteException {
    BorderPane root = new BorderPane();
    root.setCenter(initMainElements());

    stage.setTitle("Elevator Control Center");
    stage.setScene(new Scene(root, 800, 500));
    stage.setOnCloseRequest(event -> System.exit(0));
    stage.show();
  }

  private VBox initMainElements() throws DesynchronizationException, RemoteException {
    BuildingSimulation elevatorApiSimulation = new BuildingSimulation();
    ElevatorControlCenter ecc = new ElevatorControlCenter(elevatorApiSimulation);
    ecc.pollElevatorApi();

    BuildingViewController buildingViewController = new BuildingViewController(ecc.getBuilding());

    ElevatorsViewController elevatorsViewController =
        new ElevatorsViewController(
            buildingViewController, buildingViewController.getBuilding().getElevators());
    ElevatorsView elevatorsView = new ElevatorsView(elevatorsViewController);
    ecc.getBuilding().addObserver(elevatorsView);

    FloorsViewController floorsViewController =
        new FloorsViewController(
            buildingViewController, buildingViewController.getBuilding().getFloors());
    FloorsView floorsView = new FloorsView(floorsViewController);
    ecc.getBuilding().addObserver(floorsView);

    BuildingView buildingView = new BuildingView(buildingViewController, elevatorsView, floorsView);
    ecc.getBuilding().addObserver(buildingView);
    return buildingView;
  }

  public static void main(String[] args) {
    launch();
  }
}
