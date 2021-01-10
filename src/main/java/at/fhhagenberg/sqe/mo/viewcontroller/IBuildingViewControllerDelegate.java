package at.fhhagenberg.sqe.mo.viewcontroller;

import at.fhhagenberg.sqe.mo.DesynchronizationException;
import java.rmi.RemoteException;

public interface IBuildingViewControllerDelegate {

  void didSetCommittedDirection(int elevatorNumber, int direction);

  void didSetServicesFloors(int elevatorNumber, int floor, boolean service);

  void didSetTarget(int elevatorId, int target);

  void pollElevatorApi() throws DesynchronizationException, RemoteException;
}
