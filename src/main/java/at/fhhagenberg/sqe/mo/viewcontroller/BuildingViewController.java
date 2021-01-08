package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.model.Building;

public class BuildingViewController
    implements ElevatorsViewControllerDelegate, FloorsViewControllerDelegate {

  private final Building building;

  public BuildingViewController(Building building) {
    this.building = building;
  }

  public Building getBuilding() {
    return building;
  }

  public void modeChanged(String modeText) {
    System.out.println(modeText);
  }

  @Override
  public void didUpdateBuilding() {
    building.update();
  }
}
