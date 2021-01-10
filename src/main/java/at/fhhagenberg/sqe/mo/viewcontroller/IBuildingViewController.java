package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.model.Building;

public interface IBuildingViewController {

  void setDelegate(IBuildingViewControllerDelegate delegate);

  void update(Building building);
}
