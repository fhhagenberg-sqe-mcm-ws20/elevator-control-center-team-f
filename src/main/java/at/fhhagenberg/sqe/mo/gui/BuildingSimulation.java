package at.fhhagenberg.sqe.mo.gui;

import java.rmi.RemoteException;
import java.util.Random;
import sqelevator.IElevator;

public class BuildingSimulation implements IElevator {

  private static final int NUMBER_OF_ELEVATORS = 3;
  private static final int NUMBER_OF_FLOORS = 6;

  private static final Random random = new Random();

  public BuildingSimulation() {
    // create floors
    // create elevators
  }

  @Override
  public int getCommittedDirection(int elevatorNumber) throws RemoteException {
    return random.nextInt(3);
  }

  @Override
  public int getElevatorAccel(int elevatorNumber) throws RemoteException {
    return random.nextInt(6);
  }

  @Override
  public boolean getElevatorButton(int elevatorNumber, int floor) throws RemoteException {
    return random.nextBoolean();
  }

  @Override
  public int getElevatorDoorStatus(int elevatorNumber) throws RemoteException {
    return random.nextInt(2) + 1;
  }

  @Override
  public int getElevatorFloor(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public int getElevatorNum() throws RemoteException {
    return 0;
  }

  @Override
  public int getElevatorPosition(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public int getElevatorSpeed(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public int getElevatorWeight(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public int getElevatorCapacity(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public boolean getFloorButtonDown(int floor) throws RemoteException {
    return false;
  }

  @Override
  public boolean getFloorButtonUp(int floor) throws RemoteException {
    return false;
  }

  @Override
  public int getFloorHeight() throws RemoteException {
    return 0;
  }

  @Override
  public int getFloorNum() throws RemoteException {
    return 0;
  }

  @Override
  public boolean getServicesFloors(int elevatorNumber, int floor) throws RemoteException {
    return false;
  }

  @Override
  public int getTarget(int elevatorNumber) throws RemoteException {
    return 0;
  }

  @Override
  public void setCommittedDirection(int elevatorNumber, int direction) throws RemoteException {}

  @Override
  public void setServicesFloors(int elevatorNumber, int floor, boolean service)
      throws RemoteException {}

  @Override
  public void setTarget(int elevatorNumber, int target) throws RemoteException {}

  @Override
  public long getClockTick() throws RemoteException {
    return 0;
  }
}
