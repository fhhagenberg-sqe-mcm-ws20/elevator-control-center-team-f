package at.fhhagenberg.sqe.mo;

import java.util.Map;

public class Elevator {

  private int id;
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
      int id,
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

    this.id = id;
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

  public int getId() {
    return id;
  }

  public int getCommittedDirection() {
    return committedDirection;
  }

  public void setCommittedDirection(int committedDirection) {
    this.committedDirection = committedDirection;
  }

  public int getAcceleration() {
    return acceleration;
  }

  public void setAcceleration(int acceleration) {
    this.acceleration = acceleration;
  }

  public Map<Integer, Boolean> getButtons() {
    return buttons;
  }

  public void setButtons(Map<Integer, Boolean> buttons) {
    this.buttons = buttons;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public int getDoorStatus() {
    return doorStatus;
  }

  public void setDoorStatus(int doorStatus) {
    this.doorStatus = doorStatus;
  }

  public int getFloor() {
    return floor;
  }

  public void setFloor(int floor) {
    this.floor = floor;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public Map<Integer, Boolean> getServicedFloors() {
    return servicedFloors;
  }

  public void setServicedFloors(Map<Integer, Boolean> servicedFloors) {
    this.servicedFloors = servicedFloors;
  }

  public int getTarget() {
    return target;
  }

  public void setTarget(int target) {
    this.target = target;
  }
}
