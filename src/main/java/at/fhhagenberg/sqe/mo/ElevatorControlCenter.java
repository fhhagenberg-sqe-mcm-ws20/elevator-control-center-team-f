package at.fhhagenberg.sqe.mo;

import com.google.common.collect.ImmutableList;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import sqelevator.IElevator;

public class ElevatorControlCenter {

  private final IElevator elevatorApi;
  private Building building;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public Building getBuilding() {
    return building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  public ElevatorControlCenter(IElevator elevatorApi) {
    this.elevatorApi = elevatorApi;
  }

  public void startPolling() {
    scheduler.scheduleAtFixedRate(
        () -> {
          try {
            pollElevatorApi();
          } catch (RemoteException detail) {
            detail.printStackTrace();
          } catch (DesynchronizationException detail) {
            System.out.println(detail.getMessage());
          }
        },
        5,
        5,
        TimeUnit.SECONDS);
  }

  public void stopPolling() {
    scheduler.shutdown();
  }

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
        building.setFloors(floors);
        building.setElevators(elevators);
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
      Map<Integer, Boolean> servicedFloors = new HashMap<>();
      for (int floorId = 0; floorId < numberOfFloors; floorId++) {
        buttons.put(floorId, elevatorApi.getElevatorButton(elevatorId, floorId));
        servicedFloors.put(floorId, elevatorApi.getServicesFloors(elevatorId, floorId));
      }
      elevatorsBuilder.add(
          new Elevator(
              elevatorApi.getCommittedDirection(elevatorId),
              elevatorApi.getElevatorAccel(elevatorId),
              buttons,
              elevatorApi.getElevatorCapacity(elevatorId),
              elevatorApi.getElevatorDoorStatus(elevatorId),
              elevatorApi.getElevatorFloor(elevatorId),
              elevatorApi.getElevatorPosition(elevatorId),
              elevatorApi.getElevatorSpeed(elevatorId),
              elevatorApi.getElevatorWeight(elevatorId),
              servicedFloors,
              elevatorApi.getTarget(elevatorId)));
    }
    return elevatorsBuilder.build();
  }
}
