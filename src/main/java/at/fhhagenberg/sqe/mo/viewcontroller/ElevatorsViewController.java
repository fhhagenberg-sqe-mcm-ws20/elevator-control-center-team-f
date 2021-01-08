package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.model.Elevator;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

interface ElevatorsViewControllerDelegate {
  void didUpdateBuilding();
}

public class ElevatorsViewController {

  private final ElevatorsViewControllerDelegate delegate;

  private final List<Elevator> elevators;

  public ElevatorsViewController(
      ElevatorsViewControllerDelegate delegate, List<Elevator> elevators) {
    this.delegate = delegate;
    this.elevators = elevators;
  }

  public int getNumberOfElevators() {
    return elevators.size();
  }

  public String getElevatorTarget(int elevatorId) {
    return String.format("Target floor: %s", elevators.get(elevatorId).getTarget());
  }

  public String getElevatorSpeed(int elevatorId) {
    return String.format("Speed: %s ft/sec", elevators.get(elevatorId).getSpeed());
  }

  public String getElevatorDoorStatus(int elevatorId) {
    // open=1, closed=2
    switch (elevators.get(elevatorId).getDoorStatus()) {
      case 1:
        return "Door status: Open";
      case 2:
        return "Door status: Closed";
      default:
        return "-";
    }
  }

  public String getElevatorWeight(int elevatorId) {
    return String.format("Current payload: %s lbs", elevators.get(elevatorId).getWeight());
  }

  public String getElevatorCapacity(int elevatorId) {
    return String.format("Capacity: %s ppl", elevators.get(elevatorId).getCapacity());
  }

  public String getElevatorFloor(int elevatorId) {
    return Integer.toString(elevators.get(elevatorId).getFloor());
  }

  public String getElevatorCommittedDirection(int elevatorId) {
    // up=0, down=1 and uncommitted=2
    switch (elevators.get(elevatorId).getCommittedDirection()) {
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

  public int getNumberOfElevatorButtons(int elevatorId) {
    return elevators.get(elevatorId).getButtons().size();
  }

  public boolean doesElevatorServiceFloor(int elevatorId, int floor) {
    Boolean doesService = elevators.get(elevatorId).getServicedFloors().get(floor);
    return doesService != null && doesService;
  }

  public boolean doesElevatorTargetFloor(int elevatorId, int floor) {
    return elevators.get(elevatorId).getTarget() == floor;
  }

  public void elevatorButtonPressed(int elevatorId, Button elevatorButton, Label targetFloorLabel) {
    String floorIdStr = elevatorButton.getText();
    int floorId = Integer.parseInt(floorIdStr);
    if (elevators.get(elevatorId).getServicedFloors().get(floorId)) {
      targetFloorLabel.setText(String.format("Target Floor: %s", floorIdStr));
      elevatorButton.setStyle("-fx-background-color: red; -fx-text-fill: white");
      elevators.get(elevatorId).setTarget(floorId);
      notifyDelegate();
    }
  }

  private void notifyDelegate() {
    if (delegate != null) {
      delegate.didUpdateBuilding();
    }
  }
}
