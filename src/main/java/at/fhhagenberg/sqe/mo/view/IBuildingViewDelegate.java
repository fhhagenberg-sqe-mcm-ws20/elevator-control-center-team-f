package at.fhhagenberg.sqe.mo.view;

import java.util.List;

public interface IBuildingViewDelegate {

  void didModeChange(String mode);

  int getNumberOfElevators();

  List<String> getElevatorServicedFloors(int elevatorId);

  void didTargetChange(int elevatorId, String target);

  boolean isAutoModeEnabled();

  String getElevatorTarget(int elevatorId);

  String getElevatorSpeed(int elevatorId);

  String getElevatorDoorStatus(int elevatorId);

  String getElevatorWeight(int elevatorId);

  String getElevatorCapacity(int elevatorId);

  String getElevatorFloor(int elevatorId);

  String getElevatorCommittedDirection(int elevatorId);

  int getNumberOfElevatorButtons(int elevatorId);

  String getElevatorButtonStyle(int elevatorId, int floor);

  List<String> getFloors();

  void didFloorChange();

  String getFloorButtonUp(int floorId);

  String getFloorButtonDown(int floorId);
}
