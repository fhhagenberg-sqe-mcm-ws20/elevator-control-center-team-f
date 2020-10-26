package at.fhhagenberg.sqe.mo;

import com.google.common.collect.ImmutableSet;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Simulation {

  private IElevator elevatorApi;
  private Building building;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public Building getBuilding() {
    return building;
  }

  public Simulation(IElevator elevatorApi) {
    this.elevatorApi = elevatorApi;
    schedulePolling();
  }

  private void schedulePolling() {
    scheduler.scheduleAtFixedRate(
            () -> {
              try {
                pollElevatorApi();
              } catch (Exception e) {
                e.printStackTrace();
              }
            },
            5,
            5,
            TimeUnit.SECONDS);
  }

  private void pollElevatorApi() throws Exception {
    long startClockTick = elevatorApi.getClockTick();
    int numberOfFloors = elevatorApi.getFloorNum();
    Set<Floor> floors = fetchFloors(numberOfFloors);
    Set<Elevator> elevators = fetchElevators(numberOfFloors);
    long endClockTick = elevatorApi.getClockTick();
    if (startClockTick == endClockTick) {
      if (building == null) {
        building = new Building(floors, elevators);
      } else {
        building.setFloors(floors);
        building.setElevators(elevators);
      }
    } else {
      throw new Exception("Discarding results due to desynchronization of clock tick...");
    }
  }

  private Set<Floor> fetchFloors(int numberOfFloors) throws RemoteException {
    ImmutableSet.Builder<Floor> floorsBuilder = ImmutableSet.builder();
    int floorHeight = elevatorApi.getFloorHeight();
    for (int floorId = 0; floorId < numberOfFloors; floorId++) {
      floorsBuilder.add(
              new Floor(
                      floorId,
                      elevatorApi.getFloorButtonDown(floorId),
                      elevatorApi.getFloorButtonUp(floorId),
                      floorHeight));
    }
    return floorsBuilder.build();
  }

  private Set<Elevator> fetchElevators(int numberOfFloors) throws RemoteException {
    ImmutableSet.Builder<Elevator> elevatorsBuilder = ImmutableSet.builder();
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
                      elevatorId,
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
