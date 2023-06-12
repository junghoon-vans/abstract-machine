package computer.processor.unit;

import computer.processor.register.AC;

public class ALU {

  private AC ac;

  public ALU(AC ac) {
    this.ac = ac;
  }

  public void add(int b) {
    int a = ac.getValue();
    ac.setValue(a + b);
  }

  public void sub(int b) {
    int a = ac.getValue();
    ac.setValue(a - b);
  }

  public void div(int b) {
    int a = ac.getValue();
    ac.setValue(a / b);
  }
}
