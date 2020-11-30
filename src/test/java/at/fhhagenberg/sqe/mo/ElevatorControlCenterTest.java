package at.fhhagenberg.sqe.mo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.rmi.RemoteException;
import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

public class ElevatorControlCenterTest {

  @Test
  void testElevatorDirection() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getCommittedDirection(1)).thenReturn(5);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(
        5, elevatorControlCenter.getBuilding().getElevators().get(1).getCommittedDirection());
  }

  @Test
  void testElevatorAcceleration() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorAccel(1)).thenReturn(10);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(10, elevatorControlCenter.getBuilding().getElevators().get(1).getAcceleration());
  }

  @Test
  void testElevatorCapacity() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorCapacity(1)).thenReturn(6);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(6, elevatorControlCenter.getBuilding().getElevators().get(1).getCapacity());
  }

  @Test
  void testElevatorDoorStatus() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorDoorStatus(1)).thenReturn(2);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(2, elevatorControlCenter.getBuilding().getElevators().get(1).getDoorStatus());
  }

  @Test
  void testElevatorFloor() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorFloor(1)).thenReturn(5);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(5, elevatorControlCenter.getBuilding().getElevators().get(1).getFloor());
  }

  @Test
  void testElevatorPosition() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorPosition(1)).thenReturn(1);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(1, elevatorControlCenter.getBuilding().getElevators().get(1).getPosition());
  }

  @Test
  void testElevatorSpeed() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorSpeed(1)).thenReturn(5);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(5, elevatorControlCenter.getBuilding().getElevators().get(1).getSpeed());
  }

  @Test
  void testElevatorWeight() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getElevatorWeight(1)).thenReturn(100);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(100, elevatorControlCenter.getBuilding().getElevators().get(1).getWeight());
  }

  @Test
  void testElevatorTarget() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(2);
    when(elevatorApi.getTarget(1)).thenReturn(2);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(2, elevatorControlCenter.getBuilding().getElevators().get(1).getTarget());
  }

  @Test
  void testFloorButtonDown() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);
    when(elevatorApi.getFloorButtonDown(2)).thenReturn(true);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertTrue(elevatorControlCenter.getBuilding().getFloors().get(2).isButtonDown());
  }

  @Test
  void testFloorButtonUp() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);
    when(elevatorApi.getFloorButtonUp(2)).thenReturn(true);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertTrue(elevatorControlCenter.getBuilding().getFloors().get(2).isButtonUp());
  }

  @Test
  void testFloorButtonHeight() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);
    when(elevatorApi.getFloorHeight()).thenReturn(5);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    elevatorControlCenter
        .getBuilding()
        .getFloors()
        .forEach(floor -> assertEquals(5, floor.getHeight()));
  }

  @Test
  void testNumberOfFloors() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(3, elevatorControlCenter.getBuilding().getFloors().size());
  }

  @Test
  void testNumberOfElevators() throws RemoteException, DesynchronizationException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.pollElevatorApi();

    assertEquals(3, elevatorControlCenter.getBuilding().getElevators().size());
  }

  @Test
  void testPollingApiUpdatesIfBuildingAlreadyExists()
      throws RemoteException, DesynchronizationException {
    ImmutableList<Floor> floors =
        ImmutableList.of(
            new Floor(false, false, 0), new Floor(true, false, 1), new Floor(false, true, 2));
    ImmutableList<Elevator> elevators =
        ImmutableList.of(
            new Elevator(0, 0, 100, 0, 0, 0, 0, 0, ImmutableSet.of(), 0),
            new Elevator(0, 0, 100, 0, 0, 0, 0, 0, ImmutableSet.of(), 0),
            new Elevator(0, 0, 100, 0, 0, 0, 0, 0, ImmutableSet.of(), 0));

    Building building = new Building(floors, elevators);

    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(2);
    when(elevatorApi.getElevatorNum()).thenReturn(1);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    elevatorControlCenter.setBuilding(building);
    elevatorControlCenter.pollElevatorApi();

    assertSame(building, elevatorControlCenter.getBuilding());
    assertEquals(2, elevatorControlCenter.getBuilding().getFloors().size());
    assertEquals(1, elevatorControlCenter.getBuilding().getElevators().size());
  }

  @Test
  void testPollingApiThrowsDesynchronizationExceptionWhenStarAndEndClockTickIsDifferent()
      throws RemoteException {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getClockTick()).thenReturn(1L, 2L);

    ElevatorControlCenter elevatorControlCenter = new ElevatorControlCenter(elevatorApi);
    assertThrows(DesynchronizationException.class, elevatorControlCenter::pollElevatorApi);
  }
}
