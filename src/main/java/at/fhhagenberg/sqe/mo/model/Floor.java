package at.fhhagenberg.sqe.mo.model;

public class Floor {

  private boolean buttonDown;
  private boolean buttonUp;
  private final int height;

  public Floor(boolean buttonDown, boolean buttonUp, int height) {
    this.buttonDown = buttonDown;
    this.buttonUp = buttonUp;
    this.height = height;
  }

  public boolean isButtonDown() {
    return buttonDown;
  }

  public boolean isButtonUp() {
    return buttonUp;
  }

  public int getHeight() {
    return height;
  }

  public void setDownButton(boolean buttonDown) {
    this.buttonDown = buttonDown;
  }

  public void setUpButton(boolean buttonUp) {
    this.buttonUp = buttonUp;
  }
}
