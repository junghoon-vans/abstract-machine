package computer;

import java.util.ArrayList;
import java.util.List;
import computer.processor.register.Register;

public class Memory {

  private Register mar;
  private Register mbr;

  List<Integer> memory;

  public Memory() {
    this.memory = new ArrayList<>();
  }

  public void setMemory(List<Integer> memory) {
    this.memory = memory;
  }

  public void associate(Register mar, Register mbr) {
    this.mar = mar;
    this.mbr = mbr;
  }

  public void load() {
    int address = mar.getValue();
    mbr.setValue(this.memory.get(address));
  }

  public void store() {
    int address = mar.getValue();
    int value = mbr.getValue();
    this.memory.set(address, value);
  }
}
