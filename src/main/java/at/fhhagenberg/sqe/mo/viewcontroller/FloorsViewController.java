package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.model.Floor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.scene.control.Button;

interface FloorsViewControllerDelegate {
  void didUpdateBuilding();
}

public class FloorsViewController {

  private final FloorsViewControllerDelegate delegate;

  private final List<Floor> floors;

  public FloorsViewController(FloorsViewControllerDelegate delegate, List<Floor> floors) {
    this.delegate = delegate;
    this.floors = floors;
  }

  public List<String> getFloors() {
    return IntStream.range(0, floors.size())
        .mapToObj(floorId -> String.format("Floor %s", floorId))
        .collect(Collectors.toList());
  }

  public void floorChanged(int floorId, Button floorUpButton, Button floorDownButton) {
    if (floors.get(floorId).isButtonUp()) {
      floorUpButton.setStyle("-fx-text-fill: red");
    } else {
      floorUpButton.setStyle("-fx-text-fill: black");
    }
    if (floors.get(floorId).isButtonDown()) {
      floorDownButton.setStyle("-fx-text-fill: red");
    } else {
      floorDownButton.setStyle("-fx-text-fill: black");
    }
  }

  public void floorUpButtonPressed(int floorId) {
    floors.get(floorId).setUpButton(true);
    notifyDelegate();
  }

  public void floorDownButtonPressed(int floorId) {
    floors.get(floorId).setDownButton(true);
    notifyDelegate();
  }

  private void notifyDelegate() {
    if (delegate != null) {
      delegate.didUpdateBuilding();
    }
  }
}
