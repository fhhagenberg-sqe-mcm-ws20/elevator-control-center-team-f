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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import sqelevator.IElevator;

/** JavaFX App */
public class App extends Application {

  private String rmiConnectionString = "rmi://127.0.0.1/ElevatorSim";
  private ElevatorControlCenter ecc;
  private boolean testing = false;
  private boolean connected = false;

  public App() throws InterruptedException, RemoteException {
    super();
    connectToApi();
  }

  public App(IElevator apiMock, String rmiConnectionString) {
    super();
    this.rmiConnectionString = rmiConnectionString;
    ecc = new ElevatorControlCenter(apiMock);
    connected = true;
  }

  public App(ElevatorControlCenter ecc) {
    super();
    this.ecc = ecc;
    this.testing = true;
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
                    } catch (RemoteException remoteException) {
                      Alert alert = new Alert(AlertType.ERROR);
                      alert.setContentText(remoteException.getLocalizedMessage());
                      alert.show();
                      retryConnecting();
                    } catch (DesynchronizationException desynchronizationException) {
                      // silence
                    }
                  });

      executor.scheduleAtFixedRate(pollingLoop, 0, 200, TimeUnit.MILLISECONDS);
    }
  }

  public boolean isConnected() {
    return connected;
  }

  public void retryConnecting() {
    try {
      connectToApi();
    } catch (InterruptedException | RemoteException e) {
      e.printStackTrace();
    }
  }

  private void connectToApi() throws InterruptedException, RemoteException {
    connected = false;
    int trial = 0;
    while (!connected && trial != 3) {
      trial++;
      try {
        IElevator api = (IElevator) Naming.lookup(rmiConnectionString);
        ecc = new ElevatorControlCenter(api);
        connected = true;
      } catch (RemoteException | NotBoundException | MalformedURLException exception) {
        Thread.sleep(1000);
      }
    }
    if (!connected) {
      throw new RemoteException();
    }
  }

  public static void main(String[] args) {
    launch();
  }
}
