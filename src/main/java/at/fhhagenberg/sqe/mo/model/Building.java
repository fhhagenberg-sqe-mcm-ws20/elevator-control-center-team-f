package at.fhhagenberg.sqe.mo.model;

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

  public List<Elevator> getElevators() {
    return elevators;
  }

  public void update(List<Floor> floors, List<Elevator> elevators) {
    this.floors = floors;
    this.elevators = elevators;
  }
}
