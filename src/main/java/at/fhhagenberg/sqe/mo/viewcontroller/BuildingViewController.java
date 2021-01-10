package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.DesynchronizationException;
import at.fhhagenberg.sqe.mo.model.Building;
import at.fhhagenberg.sqe.mo.view.BuildingView;
import at.fhhagenberg.sqe.mo.view.IBuildingViewDelegate;
import java.rmi.RemoteException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BuildingViewController implements IBuildingViewController, IBuildingViewDelegate {

  private IBuildingViewControllerDelegate delegate;

  public final BuildingView view;

  private Building building;

  private boolean autoModeEnabled = true;

  public BuildingViewController(BuildingView view, Building building) {
    this.view = view;
    this.view.setDelegate(this);
    this.building = building;
  }

  @Override
  public void setDelegate(IBuildingViewControllerDelegate delegate) {
    this.delegate = delegate;
  }

  @Override
  public void update(Building building) {
    this.building = building;
    if (view != null) {
      view.update();
    }
  }

  @Override
  public void didModeChange(String mode) {
    autoModeEnabled = "Auto".equals(mode);
    if (view != null) {
      view.update();
    }
  }

  @Override
  public int getNumberOfElevators() {
    return building.getElevators().size();
  }

  @Override
  public List<String> getElevatorServicedFloors(int elevatorId) {
    return building.getElevators().get(elevatorId).getServicedFloors().stream()
        .sequential()
        .map(floorId -> String.format("Floor %s", floorId))
        .collect(Collectors.toList());
  }

  @Override
  public void didTargetChange(int elevatorId, String targetStr) {
    int currentFloor = building.getElevators().get(elevatorId).getFloor();
    int target = Integer.parseInt(targetStr.split(" ")[1]);
    // up=0, down=1 and uncommitted=2
    int committedDirection;
    if (target > currentFloor) {
      committedDirection = 0;
    } else if (target < currentFloor) {
      committedDirection = 1;
    } else {
      committedDirection = 2;
    }

    delegate.didSetTarget(elevatorId, target);
    delegate.didSetCommittedDirection(elevatorId, committedDirection);
    poll();
  }

  @Override
  public boolean isAutoModeEnabled() {
    return autoModeEnabled;
  }

  @Override
  public String getElevatorTarget(int elevatorId) {
    return String.format("Target floor: %s", building.getElevators().get(elevatorId).getTarget());
  }

  @Override
  public String getElevatorSpeed(int elevatorId) {
    return String.format("Speed: %s ft/sec", building.getElevators().get(elevatorId).getSpeed());
  }

  @Override
  public String getElevatorDoorStatus(int elevatorId) {
    // open=1, closed=2
    switch (building.getElevators().get(elevatorId).getDoorStatus()) {
      case 1:
        return "Door status: Open";
      case 2:
        return "Door status: Closed";
      default:
        return "-";
    }
  }

  @Override
  public String getElevatorWeight(int elevatorId) {
    return String.format(
        "Current payload: %s lbs", building.getElevators().get(elevatorId).getWeight());
  }

  @Override
  public String getElevatorCapacity(int elevatorId) {
    return String.format("Capacity: %s ppl", building.getElevators().get(elevatorId).getCapacity());
  }

  @Override
  public String getElevatorFloor(int elevatorId) {
    return Integer.toString(building.getElevators().get(elevatorId).getFloor());
  }

  @Override
  public String getElevatorCommittedDirection(int elevatorId) {
    // up=0, down=1 and uncommitted=2
    switch (building.getElevators().get(elevatorId).getCommittedDirection()) {
      case 0:
        return "⬆️️";
      case 1:
        return "⬇️";
      case 2:
        return "↔️";
      default:
        return "-";
    }
  }

  @Override
  public int getNumberOfElevatorButtons(int elevatorId) {
    return building.getElevators().get(elevatorId).getButtons().size();
  }

  @Override
  public String getElevatorButtonStyle(int elevatorId, int floor) {
    if (doesElevatorServiceFloor(elevatorId, floor)) {
      if (doesElevatorTargetFloor(elevatorId, floor)) {
        return "-fx-border-color: lightgrey; -fx-background-color: red; -fx-text-fill: white";
      } else {
        return "-fx-border-color: lightgrey; -fx-text-fill: black";
      }
    } else {
      return "-fx-border-color: lightgrey; -fx-background-color: black; -fx-text-fill: white";
    }
  }

  @Override
  public List<String> getFloors() {
    return IntStream.range(0, building.getFloors().size())
        .mapToObj(floorId -> String.format("Floor %s", floorId))
        .collect(Collectors.toList());
  }

  @Override
  public void didFloorChange() {
    if (view != null) {
      view.update();
    }
  }

  @Override
  public String getFloorButtonUp(int floorId) {
    if (building.getFloors().get(floorId).isButtonUp()) {
      return "-fx-border-color: lightgrey; -fx-text-fill: red";
    } else {
      return "-fx-border-color: lightgrey; -fx-text-fill: black";
    }
  }

  @Override
  public String getFloorButtonDown(int floorId) {
    if (building.getFloors().get(floorId).isButtonDown()) {
      return "-fx-border-color: lightgrey; -fx-text-fill: red";
    } else {
      return "-fx-border-color: lightgrey; -fx-text-fill: black";
    }
  }

  private boolean doesElevatorServiceFloor(int elevatorId, int floor) {
    return building.getElevators().get(elevatorId).getServicedFloors().contains(floor);
  }

  private boolean doesElevatorTargetFloor(int elevatorId, int floor) {
    return building.getElevators().get(elevatorId).getTarget() == floor;
  }

  public void startService() {
    poll();
    //    // init an executor service for periodic polling
    //    ScheduledExecutorService executor =
    //        Executors.newScheduledThreadPool(
    //            1,
    //            runnable -> {
    //              Thread t = new Thread(runnable);
    //              t.setDaemon(true);
    //              return t;
    //            });
    //    Runnable loop = () -> Platform.runLater(this::poll);
    //    executor.scheduleAtFixedRate(loop, 0, 2, TimeUnit.SECONDS);
  }

  private void poll() {
    if (delegate != null) {
      try {
        delegate.pollElevatorApi();
      } catch (DesynchronizationException | RemoteException e) {
        e.printStackTrace();
      }
    }
  }
}
