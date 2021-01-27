package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.model.Elevator;
import at.fhhagenberg.sqe.mo.model.Floor;
import com.google.common.collect.ImmutableList;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sqelevator.IElevator;

public class BuildingSimulation implements IElevator {

  private static final int NUMBER_OF_ELEVATORS = 2;
  private static final int NUMBER_OF_FLOORS = 4;
  private static final int FLOOR_HEIGHT = 5;

  private final List<Floor> floors;
  private final List<Elevator> elevators;

  public BuildingSimulation() {
    floors =
        ImmutableList.of(
            new Floor(false, false, FLOOR_HEIGHT),
            new Floor(false, true, FLOOR_HEIGHT),
            new Floor(true, false, FLOOR_HEIGHT),
            new Floor(true, true, FLOOR_HEIGHT));

    elevators =
        ImmutableList.of(
            new Elevator(0, 1, 8, 2, 0, 0, 5, 100, getButtons(floors, 1), getServicedFloors(), 1),
            new Elevator(1, 1, 8, 2, 1, 1, 5, 100, getButtons(floors, 0), getServicedFloors(), 0),
            new Elevator(2, 0, 8, 1, 2, 2, 5, 100, getButtons(floors, 2), getServicedFloors(), 2));
  }

  private Map<Integer, Boolean> getButtons(List<Floor> floors, int target) {
    Map<Integer, Boolean> buttons = new HashMap<>();
    for (int floor = 0; floor < floors.size(); floor++) {
      buttons.put(floor, floor == target);
    }
    return buttons;
  }

  private Set<Integer> getServicedFloors() {
    Set<Integer> servicedFloors = new HashSet<>();
    servicedFloors.add(0);
    servicedFloors.add(1);
    servicedFloors.add(2);
    return servicedFloors;
  }

  @Override
  public int getCommittedDirection(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getCommittedDirection();
  }

  @Override
  public int getElevatorAccel(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getAcceleration();
  }

  @Override
  public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
    return elevators.get(elevatorNumber).getButtons().get(floor);
  }

  @Override
  public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getDoorStatus();
  }

  @Override
  public int getElevatorFloor(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getFloor();
  }

  @Override
  public int getElevatorNum() throws RemoteException {
    return NUMBER_OF_ELEVATORS;
  }

  @Override
  public int getElevatorPosition(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getPosition();
  }

  @Override
  public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getSpeed();
  }

  @Override
  public int getElevatorWeight(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getWeight();
  }

  @Override
  public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getCapacity();
  }

  @Override
  public boolean getFloorButtonDown(int floor) throws RemoteException {
    return floors.get(floor).isButtonDown();
  }

  @Override
  public boolean getFloorButtonUp(int floor) throws RemoteException {
    return floors.get(floor).isButtonUp();
  }

  @Override
  public int getFloorHeight() throws RemoteException {
    return FLOOR_HEIGHT;
  }

  @Override
  public int getFloorNum() throws RemoteException {
    return NUMBER_OF_FLOORS;
  }

  @Override
  public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
    return elevators.get(elevatorNumber).getServicedFloors().contains(floor);
  }

  @Override
  public int getTarget(int elevatorNumber) throws RemoteException {
    return elevators.get(elevatorNumber).getTarget();
  }

  @Override
  public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {
    elevators.get(elevatorNumber).setCommittedDirection(direction);
  }

  @Override
  public void setServicesFloors(int elevatorNumber, int floor, boolean service)
      throws RemoteException {
    elevators.get(elevatorNumber).setServicesFloors(floor, service);
  }

  @Override
  public void setTarget(int elevatorNumber, int target) throws RemoteException {
    elevators.get(elevatorNumber).setTarget(target);
  }

  @Override
  public long getClockTick() throws RemoteException {
    return 0;
  }
}
