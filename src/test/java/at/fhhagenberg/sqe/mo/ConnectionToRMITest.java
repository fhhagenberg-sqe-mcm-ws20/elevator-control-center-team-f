package at.fhhagenberg.sqe.mo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
class ConnectionToRMITest {

  private static Registry registry;
  private static final String processName = "ElevatorSim";
  private static final int port = 8000;
  private static final String rmiConnectionString =
      String.format("rmi://127.0.0.1:%d/%s", port, processName);
  private static App app;

  @BeforeAll
  static void setUp() throws RemoteException, AlreadyBoundException {
    IElevator elevatorApiMock = new BuildingSimulation();
    registry = LocateRegistry.createRegistry(port);
    registry.bind(processName, elevatorApiMock);
    app = new App(elevatorApiMock, rmiConnectionString);
  }

  @Start
  void start(Stage stage) {
    app.start(stage);
  }

  @Test
  void testSuccessfulConnectionToRMI() {
    assertTrue(app.isConnected());
  }

  @Test
  void testFailedConnectionToRMI() throws RemoteException, NotBoundException {
    registry.unbind(processName);
    app.retryConnecting();
    assertFalse(app.isConnected());
  }
}
