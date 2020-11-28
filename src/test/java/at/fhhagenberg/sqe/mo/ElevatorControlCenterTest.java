package at.fhhagenberg.sqe.mo;

import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ElevatorControlCenterTest {

  @Test
  void testNumberOfFloors() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(3, elevatorControlCenter.getBuilding().getFloors().size());
  }

  @Test
  void testNumberOfElevators() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);

    assertEquals(3, elevatorControlCenter.getBuilding().getElevators().size());
  }

  @Test
  void testDirectionOfElevator() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);
    when(elevatorApi.getCommittedDirection(1)).thenReturn(5);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);

    assertEquals(
            5, elevatorControlCenter.getBuilding().getElevators().get(1).getCommittedDirection());
  }

  @Test
  void testElevatorDoorStatus() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);
    when(elevatorApi.getElevatorDoorStatus(2)).thenReturn(2);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);

    assertEquals(2, elevatorControlCenter.getBuilding().getElevators().get(2).getDoorStatus());
  }

  @Test
  void testButtonUp() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);
    when(elevatorApi.getFloorButtonUp(2)).thenReturn(true);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);

    assertTrue(elevatorControlCenter.getBuilding().getFloors().get(2).isButtonUp());
  }
}
