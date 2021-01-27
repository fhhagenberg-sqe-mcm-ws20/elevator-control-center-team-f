package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.view.BuildingView;
import at.fhhagenberg.sqe.mo.viewcontroller.BuildingViewController;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import sqelevator.IElevator;

/** JavaFX App */
public class App extends Application {

  private final ElevatorControlCenter ecc;

  private boolean testing = false;

  public App() throws RemoteException, NotBoundException, MalformedURLException {
    super();
    IElevator api = (IElevator) Naming.lookup("rmi://127.0.0.1/ElevatorSim");
    ecc = new ElevatorControlCenter(api);
  }

  public App(ElevatorControlCenter ecc, boolean testing) {
    super();
    this.ecc = ecc;
    this.testing = testing;
  }

  @Override
  public void start(Stage stage) {
    // Create building view controller
    BuildingViewController buildingViewController =
        new BuildingViewController(new BuildingView(), ecc.getBuilding());

    // Bind ecc with view controller
    ecc.setBuildingViewController(buildingViewController);

    // Setup the stage
    stage.setTitle("Elevator Control Center");
    stage.setScene(buildingViewController.view.getScene());
    stage.setOnCloseRequest(event -> System.exit(0));
    stage.sizeToScene();
    stage.show();

    if (!testing) {
      // Create an executor service for periodic polling
      ScheduledExecutorService executor =
          Executors.newScheduledThreadPool(
              1,
              runnable -> {
                Thread t = new Thread(runnable);
                t.setDaemon(true);
                return t;
              });

      // Define the polling loop thread
      Runnable pollingLoop =
          () ->
              Platform.runLater(
                  () -> {
                    try {
                      ecc.pollElevatorApi();
                    } catch (DesynchronizationException | RemoteException exception) {
                      if (exception instanceof RemoteException) {
                        //  TODO show it to user
                      } else {
                        // just do nothing for the desynchronization
                      }
                    }
                  });

      executor.scheduleAtFixedRate(pollingLoop, 0, 200, TimeUnit.MILLISECONDS);
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
