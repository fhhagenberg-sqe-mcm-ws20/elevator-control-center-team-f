package at.fhhagenberg.sqe.mo;

import at.fhhagenberg.sqe.mo.model.Building;
import at.fhhagenberg.sqe.mo.model.Elevator;
import at.fhhagenberg.sqe.mo.model.Floor;
import at.fhhagenberg.sqe.mo.viewcontroller.IBuildingViewController;
import at.fhhagenberg.sqe.mo.viewcontroller.IBuildingViewControllerDelegate;
import com.google.common.collect.ImmutableList;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sqelevator.IElevator;

public class ElevatorControlCenter implements IBuildingViewControllerDelegate {

  private static final Logger logger = LogManager.getLogger(ElevatorControlCenter.class);

  private IElevator elevatorApi = null;

  private Building building;

  private IBuildingViewController buildingViewController;

  private ElevatorControlCenter() {
    // no-op empty constructor
  }

  public ElevatorControlCenter(IElevator elevatorApi) {
    this.elevatorApi = elevatorApi;
  }

  public void setBuildingViewController(IBuildingViewController buildingViewController) {
    this.buildingViewController = buildingViewController;
    this.buildingViewController.setDelegate(this);
  }

  public Building getBuilding() {
    return building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  @Override
  public void pollElevatorApi() throws DesynchronizationException, RemoteException {
    long startClockTick = elevatorApi.getClockTick();
    int numberOfFloors = elevatorApi.getFloorNum();
    List<Floor> floors = fetchFloors(numberOfFloors);
    List<Elevator> elevators = fetchElevators(numberOfFloors);
    long endClockTick = elevatorApi.getClockTick();
    if (startClockTick == endClockTick) {
      if (building == null) {
        building = new Building(floors, elevators);
      } else {
        building.update(floors, elevators);
      }
      if (buildingViewController != null) {
        buildingViewController.update(building);
      }
    } else {
      throw new DesynchronizationException(
          "Discarding results due to desynchronization of clock tick...");
    }
  }

  private List<Floor> fetchFloors(int numberOfFloors) throws RemoteException {
    ImmutableList.Builder<Floor> floorsBuilder = ImmutableList.builder();
    int floorHeight = elevatorApi.getFloorHeight();
    for (int floorId = 0; floorId < numberOfFloors; floorId++) {
      floorsBuilder.add(
          new Floor(
              elevatorApi.getFloorButtonDown(floorId),
              elevatorApi.getFloorButtonUp(floorId),
              floorHeight));
    }
    return floorsBuilder.build();
  }

  private List<Elevator> fetchElevators(int numberOfFloors) throws RemoteException {
    ImmutableList.Builder<Elevator> elevatorsBuilder = ImmutableList.builder();
    int numberOfElevators = elevatorApi.getElevatorNum();
    for (int elevatorId = 0; elevatorId < numberOfElevators; elevatorId++) {
      Map<Integer, Boolean> buttons = new HashMap<>();
      Set<Integer> servicedFloors = new HashSet<>();
      for (int floorId = 0; floorId < numberOfFloors; floorId++) {
        if (elevatorApi.getElevatorButton(elevatorId, floorId)) {
          buttons.put(floorId, true);
        } else {
          buttons.put(floorId, false);
        }
        if (elevatorApi.getServicesFloors(elevatorId, floorId)) {
          servicedFloors.add(floorId);
        }
      }

      elevatorsBuilder.add(
          new Elevator(
              elevatorApi.getCommittedDirection(elevatorId),
              elevatorApi.getElevatorAccel(elevatorId),
              elevatorApi.getElevatorCapacity(elevatorId),
              elevatorApi.getElevatorDoorStatus(elevatorId),
              elevatorApi.getElevatorFloor(elevatorId),
              elevatorApi.getElevatorPosition(elevatorId),
              elevatorApi.getElevatorSpeed(elevatorId),
              elevatorApi.getElevatorWeight(elevatorId),
              buttons,
              servicedFloors,
              elevatorApi.getTarget(elevatorId)));
    }
    return elevatorsBuilder.build();
  }

  @Override
  public void didSetCommittedDirection(int elevatorNumber, int direction) {
    try {
      elevatorApi.setCommittedDirection(elevatorNumber, direction);
    } catch (RemoteException remoteException) {
      logger.error("RemoteException: ", remoteException);
    }
  }

  @Override
  public void didSetTarget(int elevatorId, int target) {
    try {
      elevatorApi.setTarget(elevatorId, target);
    } catch (RemoteException remoteException) {
      logger.error("RemoteException: ", remoteException);
    }
  }
}
