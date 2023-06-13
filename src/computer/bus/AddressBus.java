package computer.bus;

import computer.processor.register.Register;

public class AddressBus implements Bus {

  private Register mar;

  @Override
  public void connect(Register mar) {
    this.mar = mar;
  }

  @Override
  public void write(int value) {
    this.mar.setValue(value);
  }

  @Override
  public int read() {
    return this.mar.getValue();
  }
}
