package at.fhhagenberg.sqe.mo;

public class Floor {

  private int id;
  private boolean buttonDown;
  private boolean buttonUp;
  private int height;

  public Floor(int id, boolean buttonDown, boolean buttonUp, int height) {
    this.id = id;
    this.buttonDown = buttonDown;
    this.buttonUp = buttonUp;
    this.height = height;
  }

  public int getId() {
    return id;
  }

  public boolean isButtonDown() {
    return buttonDown;
  }

  public void setButtonDown(boolean buttonDown) {
    this.buttonDown = buttonDown;
  }

  public boolean isButtonUp() {
    return buttonUp;
  }

  public void setButtonUp(boolean buttonUp) {
    this.buttonUp = buttonUp;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }
}
