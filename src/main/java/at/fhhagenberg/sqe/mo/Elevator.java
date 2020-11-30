package at.fhhagenberg.sqe.mo;

import java.util.Set;

public class Elevator {

  private final int committedDirection;
  private final int acceleration;
  private final int capacity;
  private final int doorStatus;
  private final int floor;
  private final int position;
  private final int speed;
  private final int weight;
  private final Set<Integer> servicedFloors;
  private final int target;

  public Elevator(
      int committedDirection,
      int acceleration,
      int capacity,
      int doorStatus,
      int floor,
      int position,
      int speed,
      int weight,
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

  public Set<Integer> getServicedFloors() {
    return servicedFloors;
  }

  public int getTarget() {
    return target;
  }
}
