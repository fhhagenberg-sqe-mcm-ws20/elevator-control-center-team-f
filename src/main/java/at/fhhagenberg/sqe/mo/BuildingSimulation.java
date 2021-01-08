package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.model.Elevator;
import at.fhhagenberg.sqe.mo.model.Floor;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import sqelevator.IElevator;

public class BuildingSimulation implements IElevator {

  private static final int NUMBER_OF_ELEVATORS = 3;
  private static final int NUMBER_OF_FLOORS = 6;
  private static final int FLOOR_HEIGHT = 5;

  private final List<Floor> floors;
  private final List<Elevator> elevators;

  private static final Random random = new Random();

  public BuildingSimulation() {
    floors = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_FLOORS; i++) {
      floors.add(new Floor(random.nextBoolean(), random.nextBoolean(), FLOOR_HEIGHT));
    }

    elevators = new ArrayList<>();
    for (int i = 0; i < NUMBER_OF_ELEVATORS; i++) {
      int target = randomFloor(floors);
      elevators.add(
          new Elevator(
              random.nextInt(3),
              random.nextInt(6),
              random.nextInt(9) + 2,
              random.nextInt(2) + 1,
              0,
              0,
              random.nextInt(11),
              random.nextInt(11) * 80,
              getButtons(floors, target),
              randomServicedFloors(floors, target),
              target));
    }
  }

  private Map<Integer, Boolean> getButtons(List<Floor> floors, int target) {
    Map<Integer, Boolean> buttons = new HashMap<>();
    for (int floor = 0; floor < floors.size(); floor++) {
      buttons.put(floor, floor == target);
    }
    return buttons;
  }

  private Map<Integer, Boolean> randomServicedFloors(List<Floor> floors, int target) {
    Map<Integer, Boolean> servicedFloors = new HashMap<>();
    for (int floor = 0; floor < floors.size(); floor++) {
      servicedFloors.put(floor, true);
    }
    int floor = random.nextInt(floors.size());
    if (floor != target && random.nextBoolean()) {
      servicedFloors.remove(floor);
    }
    return servicedFloors;
  }

  private int randomFloor(List<Floor> floors) {
    return random.nextInt(floors.size());
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
    Boolean doesService = elevators.get(elevatorNumber).getServicedFloors().get(floor);
    return doesService != null && doesService;
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
