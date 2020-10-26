package at.fhhagenberg.sqe.mo;

import org.junit.jupiter.api.Test;
import sqelevator.IElevator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimulationTest {

  @Test
  void testNumberOfFloors() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);

    Simulation simulation = new Simulation(elevatorApi);

    assertEquals(3, simulation.getBuilding().getFloors().size());
  }

  @Test
  void testNumberOfElevators() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);

    Simulation simulation = new Simulation(elevatorApi);

    assertEquals(3, simulation.getBuilding().getElevators().size());
  }

  @Test
  void testDirectionOfElevator() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);
    when(elevatorApi.getCommittedDirection(1)).thenReturn(5);

    Simulation simulation = new Simulation(elevatorApi);

    assertEquals(
        5,
        simulation.getBuilding().getElevators().stream()
            .filter(elevator -> elevator.getId() == 1)
            .map(Elevator::getCommittedDirection)
            .findFirst()
            .orElse(-1));
  }

  @Test
  void testElevatorDoorStatus() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getElevatorNum()).thenReturn(3);
    when(elevatorApi.getElevatorDoorStatus(2)).thenReturn(2);

    Simulation simulation = new Simulation(elevatorApi);

    assertEquals(
        2,
        simulation.getBuilding().getElevators().stream()
            .filter(elevator -> elevator.getId() == 2)
            .map(Elevator::getDoorStatus)
            .findFirst()
            .orElse(-1));
  }

  @Test
  void testButtonUp() throws Exception {
    IElevator elevatorApi = mock(IElevator.class);
    when(elevatorApi.getFloorNum()).thenReturn(3);
    when(elevatorApi.getFloorButtonUp(2)).thenReturn(true);

    Simulation simulation = new Simulation(elevatorApi);

    assertTrue(
        simulation.getBuilding().getFloors().stream()
            .filter(floor -> floor.getId() == 2)
            .map(Floor::isButtonUp)
            .findFirst()
            .orElse(false));
  }
}
