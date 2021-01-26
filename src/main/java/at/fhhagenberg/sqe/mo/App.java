package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.view.BuildingView;
import at.fhhagenberg.sqe.mo.viewcontroller.BuildingViewController;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.stage.Stage;
import sqelevator.IElevator;

/** JavaFX App */
public class App extends Application {

  private final ElevatorControlCenter ecc;

  public App() throws RemoteException, NotBoundException, MalformedURLException {
    super();
    IElevator api = (IElevator) Naming.lookup("rmi://127.0.0.1/ElevatorSim");
    ecc = new ElevatorControlCenter(api);
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
