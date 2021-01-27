package at.fhhagenberg.sqe.mo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.rmi.RemoteException;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sqelevator.IElevator;

@ExtendWith(ApplicationExtension.class)
public class GuiTest {

  private IElevator elevatorApiMock;
  private ElevatorControlCenter ecc;

  @Start
  public void start(Stage stage) {
    elevatorApiMock = new BuildingSimulation();
    ecc = new ElevatorControlCenter(elevatorApiMock);
    App app = new App(ecc, true);
    app.start(stage);
  }

  @Test
  void testSettingTargetOnGuiChangesValueOnApi(FxRobot robot) throws RemoteException {
    verifyPolling(robot);
    robot.clickOn("#manualModeRadioButton");
    robot.clickOn("#e0-floor2");

    assertEquals(2, elevatorApiMock.getTarget(0));
  }

  @Test
  void testServicesFloorsChangeOnApiIsVisibleOnGuiAfterSuccessfulPoll(FxRobot robot)
      throws RemoteException {
    verifyPolling(robot);
    String elevatorFloorId = "#e0-floor0";
    Label elevatorFloorLabel = robot.lookup(elevatorFloorId).query();
    assertFalse(elevatorFloorLabel.getStyle().contains("-fx-background-color: black"));

    elevatorApiMock.setServicesFloors(0, 0, false);

    verifyPolling(robot);
    assertTrue(
        robot.lookup(elevatorFloorId).query().getStyle().contains("-fx-background-color: black"));
  }

  private void verifyPolling(FxRobot robot) {
    robot.interact(
        () -> {
          try {
            ecc.pollElevatorApi();
          } catch (DesynchronizationException | RemoteException e) {
            e.printStackTrace();
          }
        });
  }
}
