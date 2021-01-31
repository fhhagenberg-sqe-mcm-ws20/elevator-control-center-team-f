package at.fhhagenberg.sqe.mo.model;

import java.util.Map;
import java.util.Set;

public class Elevator {

  private final int acceleration;
  private final int capacity;
  private final int doorStatus;
  private final int floor;
  private final int position;
  private final int speed;
  private final int weight;
  private final Map<Integer, Boolean> buttons;
  private final Set<Integer> servicedFloors;
  private int committedDirection;
  private int target;

  public Elevator(
      int committedDirection,
      int acceleration,
      int capacity,
      int doorStatus,
      int floor,
      int position,
      int speed,
      int weight,
      Map<Integer, Boolean> buttons,
      Set<Integer> servicedFloors,
      int target) {

    this.committedDirection = committedDirection;
    this.acceleration = acceleration;
    this.capacity = capacity;
    this.doorStatus = doorStatus;
    this.floor = floor;
    this.position = position;
    this.speed = speed;
    this.weight = weight;
    this.buttons = buttons;
    this.servicedFloors = servicedFloors;
    this.target = target;
  }

  public int getCommittedDirection() {
    return committedDirection;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public int getCapacity() {
    return capacity;
  }

  public int getDoorStatus() {
    return doorStatus;
  }

  public int getFloor() {
    return floor;
  }

  public int getPosition() {
    return position;
  }

  public int getSpeed() {
    return speed;
  }

  public int getWeight() {
    return weight;
  }

  public Map<Integer, Boolean> getButtons() {
    return buttons;
  }

  public Set<Integer> getServicedFloors() {
    return servicedFloors;
  }

  public int getTarget() {
    return target;
  }

  public void setCommittedDirection(int committedDirection) {
    this.committedDirection = committedDirection;
  }

  public void setServicesFloors(int floor, boolean service) {
    if (service) {
      servicedFloors.add(floor);
    } else {
      servicedFloors.remove(floor);
    }
  }

  public void setTarget(int target) {
    this.target = target;
    buttons.put(target, true);
  }
}
