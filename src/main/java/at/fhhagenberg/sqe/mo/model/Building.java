package at.fhhagenberg.sqe.mo.model;

import at.fhhagenberg.sqe.mo.IBuildingObserver;
import java.util.ArrayList;
import java.util.List;

public class Building {

  private List<Floor> floors;
  private List<Elevator> elevators;

  private final List<IBuildingObserver> observers = new ArrayList<>();

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

  public void addObserver(IBuildingObserver observer) {
    observers.add(observer);
  }

  public void update(List<Floor> floors, List<Elevator> elevators) {
    this.floors = floors;
    this.elevators = elevators;
    update();
  }

  public void update() {
    observers.forEach(
        observer -> {
          if (observer != null) {
            observer.update();
          }
        });
  }
}
