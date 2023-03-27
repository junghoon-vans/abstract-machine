package computer.processor;

import computer.Memory;

/**
 * Central Processing Unit
 */
public class CPU {

  private Memory memory;
  private State state;
  private IR ir;
  public Register mar, mbr;
  public Register cs, pc, ac;

  public CPU() {
    ir = new IR();
    mar = new Register();
    mbr = new Register();
    cs = new Register();
    pc = new Register();
    ac = new Register();
  }

  public void associate(Memory memory) {
    this.memory = memory;
  }

  public void start() {
    this.state = State.RUNNING;
    this.run();
  }
  public void halt() {
    this.state = State.STOPPED;
  }

  public void add() {
    mar.setValue(this.ir.getOperand());
    memory.load();
    this.ac.setValue(this.ac.getValue() + mbr.getValue());
  }

  public void run() {
    while(this.state == State.RUNNING) {
      this.fetch();
      this.decode();
      this.execute();
      // check interrupt
    }
  }

  private void fetch() {
    System.out.println("[fetch]");
    mar.setValue(cs.getValue() + pc.getValue());
    memory.load();

    System.out.println(
        "memory[" + mar.getValue() + "]: "
            + String.format("%16s", Integer.toBinaryString(mbr.getValue()))
            .replace(' ', '0')
    );

    ir.setValue(mbr.getValue());
  }

  private void decode() {
    System.out.println("[decode]");
    System.out.println("mode: " + ir.isMode());
    System.out.println("operator: " + ir.getOperator());
    System.out.println("operand: " + ir.getOperand());
  }


  private void execute() {
    System.out.println("[execute]");
    pc.setValue(pc.getValue() + 1);

    switch(ir.getOperator()) {
      case HALT:
        this.halt();
        break;
      case ADD:
        this.add();
        break;
      default:
        break;
    }

    System.out.println();
  }
}
