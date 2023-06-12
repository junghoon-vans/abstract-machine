package computer.processor.register;

public class AC extends Register {

  private boolean zeroFlag;

  public AC() {
    super();
    this.zeroFlag = false;
  }

  @Override
  public void setValue(int value) {
    super.setValue(value);
    if (value == 0) {
      this.zeroFlag = true;
    } else {
      this.zeroFlag = false;
    }
  }

  public boolean isZero() {
    return this.zeroFlag;
  }
}
