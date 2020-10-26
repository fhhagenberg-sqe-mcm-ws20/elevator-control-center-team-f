package at.fhhagenberg.sqe.mo;

import java.util.Set;

public class Building {

  private Set<Floor> floors;
  private Set<Elevator> elevators;

  public Building(Set<Floor> floors, Set<Elevator> elevators) {
    this.floors = floors;
    this.elevators = elevators;
  }

  public Set<Floor> getFloors() {
    return floors;
  }

  public void setFloors(Set<Floor> floors) {
    this.floors = floors;
  }

  public Set<Elevator> getElevators() {
    return elevators;
  }

  public void setElevators(Set<Elevator> elevators) {
    this.elevators = elevators;
  }
}
