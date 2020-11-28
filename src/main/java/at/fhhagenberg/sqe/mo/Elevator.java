package at.fhhagenberg.sqe.mo;

import java.util.Map;

public class Elevator {

  private int committedDirection;
  private int acceleration;
  private Map<Integer, Boolean> buttons;
  private int capacity;
  private int doorStatus;
  private int floor;
  private int position;
  private int speed;
  private int weight;
  private Map<Integer, Boolean> servicedFloors;
  private int target;

  public Elevator(
      int committedDirection,
      int acceleration,
      Map<Integer, Boolean> buttons,
      int capacity,
      int doorStatus,
      int floor,
      int position,
      int speed,
      int weight,
      Map<Integer, Boolean> servicedFloors,
      int target) {

    this.committedDirection = committedDirection;
    this.acceleration = acceleration;
    this.buttons = buttons;
    this.capacity = capacity;
    this.doorStatus = doorStatus;
    this.floor = floor;
    this.position = position;
    this.speed = speed;
    this.weight = weight;
    this.servicedFloors = servicedFloors;
    this.target = target;
  }

  public int getCommittedDirection() {
    return committedDirection;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public Map<Integer, Boolean> getButtons() {
    return buttons;
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

  public Map<Integer, Boolean> getServicedFloors() {
    return servicedFloors;
  }

  public int getTarget() {
    return target;
  }
}
