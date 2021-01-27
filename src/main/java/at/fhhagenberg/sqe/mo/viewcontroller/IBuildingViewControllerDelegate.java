package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.DesynchronizationException;
import java.rmi.RemoteException;

public interface IBuildingViewControllerDelegate {

  void didSetCommittedDirection(int elevatorNumber, int direction);

  void didSetTarget(int elevatorId, int target);

  void pollElevatorApi() throws DesynchronizationException, RemoteException;
}
