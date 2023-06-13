package computer.bus;

import computer.processor.register.Register;

public interface Bus {
  public void connect(Register register);
  public void write(int value);
  public int read();
}
