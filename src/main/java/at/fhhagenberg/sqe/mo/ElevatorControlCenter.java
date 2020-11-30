package at.fhhagenberg.sqe.mo;

import com.google.common.collect.ImmutableList;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import sqelevator.IElevator;

public class ElevatorControlCenter {

  private static final Logger LOGGER = Logger.getLogger(ElevatorControlCenter.class.getName());

  private final IElevator elevatorApi;
  private Building building;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  public ElevatorControlCenter(IElevator elevatorApi) {
    this.elevatorApi = elevatorApi;
  }

  public ElevatorControlCenter() {
    elevatorApi = null;
  }

  public void initDemoBuilding() {
    Random random = new Random();
    int floorHeight = 5;
    ImmutableList<Floor> floors =
        ImmutableList.of(
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight),
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight),
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight),
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight),
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight),
            new Floor(random.nextBoolean(), random.nextBoolean(), floorHeight));
    ImmutableList<Elevator> elevators =
        ImmutableList.of(
            new Elevator(
                random.nextInt(3),
                random.nextInt(6),
                random.nextInt(9) + 2,
                random.nextInt(2) + 1,
                0,
                0,
                random.nextInt(11),
                random.nextInt(11) * 80,
                randomServicedFloors(floors),
                randomFloor(floors)),
            new Elevator(
                random.nextInt(3),
                random.nextInt(6),
                random.nextInt(9) + 2,
                random.nextInt(2) + 1,
                0,
                0,
                random.nextInt(11),
                random.nextInt(11) * 80,
                randomServicedFloors(floors),
                randomFloor(floors)),
            new Elevator(
                random.nextInt(3),
                random.nextInt(6),
                random.nextInt(9) + 2,
                random.nextInt(2) + 1,
                0,
                0,
                random.nextInt(11),
                random.nextInt(11) * 80,
                randomServicedFloors(floors),
                randomFloor(floors)));

    building = new Building(floors, elevators);
  }

  private Set<Integer> randomServicedFloors(ImmutableList<Floor> floors) {
    Set<Integer> servicedFloors = new HashSet<>();
    for (int floor = 0; floor < floors.size(); floor++) {
      servicedFloors.add(floor);
    }
    Random random = new Random();
    if (random.nextBoolean()) {
      servicedFloors.remove(random.nextInt(floors.size()));
    }
    return servicedFloors;
  }

  private int randomFloor(ImmutableList<Floor> floors) {
    return new Random().nextInt(floors.size());
  }

  public Building getBuilding() {
    return building;
  }

  public void setBuilding(Building building) {
    this.building = building;
  }

  public void startPolling() {
    scheduler.scheduleAtFixedRate(
        () -> {
          try {
            pollElevatorApi();
          } catch (RemoteException detail) {
            LOGGER.severe(detail.getMessage());
          } catch (DesynchronizationException detail) {
            LOGGER.warning(detail.getMessage());
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
      Set<Integer> servicedFloors = new HashSet<>();
      for (int floorId = 0; floorId < numberOfFloors; floorId++) {
        if (elevatorApi.getElevatorButton(elevatorId, floorId)) {
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
              servicedFloors,
              elevatorApi.getTarget(elevatorId)));
    }
    return elevatorsBuilder.build();
  }
}
