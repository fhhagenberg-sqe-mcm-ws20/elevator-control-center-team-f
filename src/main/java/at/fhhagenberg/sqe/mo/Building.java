package at.fhhagenberg.sqe.mo;

import java.util.List;

public class Building {

  private List<Floor> floors;
  private List<Elevator> elevators;

  public Building(List<Floor> floors, List<Elevator> elevators) {
    this.floors = floors;
    this.elevators = elevators;
  }

  public List<Floor> getFloors() {
    return floors;
  }

  public void setFloors(List<Floor> floors) {
    this.floors = floors;
  }

  public List<Elevator> getElevators() {
    return elevators;
  }

  public void setElevators(List<Elevator> elevators) {
    this.elevators = elevators;
  }
}
