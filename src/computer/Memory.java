package computer;

import java.util.ArrayList;
import java.util.List;
import computer.processor.register.Register;

public class Memory {

  private Register mar;
  private Register mbr;

  List<Integer> codeSegment;

  public Memory() {
    this.codeSegment = new ArrayList<>();
  }

  public void setCodeSegment(List<Integer> instructions) {
    this.codeSegment = instructions;
  }

  public void associate(Register mar, Register mbr) {
    this.mar = mar;
    this.mbr = mbr;
  }

  public void load() {
    int address = mar.getValue();
    mbr.setValue(this.codeSegment.get(address));
  }

  public void store() {
    int address = mar.getValue();
    int value = mbr.getValue();
    this.codeSegment.set(address, value);
  }
}
