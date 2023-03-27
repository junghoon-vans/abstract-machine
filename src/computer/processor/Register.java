package computer.processor;

/**
 * Register
 */
public class Register {

  int value;

  Register() {
    this.value = 0;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
