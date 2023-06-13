package computer.bus;

import computer.processor.register.Register;

public class DataBus implements Bus {

  private Register mbr;

  @Override
  public void connect(Register mbr) {
    this.mbr = mbr;
  }

  @Override
  public void write(int value) {
    this.mbr.setValue(value);
  }

  @Override
  public int read() {
    return this.mbr.getValue();
  }
}
