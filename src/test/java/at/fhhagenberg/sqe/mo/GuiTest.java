package at.fhhagenberg.sqe.mo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;
import static org.testfx.util.NodeQueryUtils.isVisible;

import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;
import javafx.scene.control.ComboBox;
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
    App app = new App(ecc);
    app.start(stage);
  }

  @Test
  void testSettingTargetInManualMode(FxRobot robot) throws RemoteException {
    verifyThat("#manualModeRadioButton", isVisible());
    robot.clickOn("#manualModeRadioButton");
    verifyThat("#targetComboBox", isVisible());

    ComboBox<String> targetComboBox = robot.lookup("#targetComboBox").query();
    String targetFloorFirst = targetComboBox.getItems().get(1).split("-")[1];

    robot.clickOn(targetComboBox);
    robot.clickOn("Floor-" + targetFloorFirst);

    // verify that api is called
    assertEquals(targetFloorFirst, String.valueOf(elevatorApiMock.getTarget(0)));

    // verify that target floor label changed
    verifyThat("#targetFloorLabel", hasText("Target floor: " + targetFloorFirst));
  }

  @Test
  void testCommittedDirectionChange(FxRobot robot) throws RemoteException {
    // get the first serviced floor
    Integer firstServicedFloor =
        ecc.getBuilding().getElevators().get(0).getServicedFloors().iterator().next();

    // make sure that regarding button is not black style
    String elevatorFloorId = String.format("#e0-floor%d", firstServicedFloor);
    Label elevatorFloorLabel = robot.lookup(elevatorFloorId).query();
    verifyThat(elevatorFloorId, isVisible());
    assertFalse(elevatorFloorLabel.getStyle().contains("-fx-background-color: black"));

    // call api to set services floor to that floor false
    elevatorApiMock.setServicesFloors(0, firstServicedFloor, false);

    // wait 5 seconds to make it update
    robot.sleep(5, TimeUnit.SECONDS);

    // then assert that regarding floor button changed to black style
    assertTrue(
        robot.lookup(elevatorFloorId).query().getStyle().contains("-fx-background-color: black"));
  }
}
