package computer.processor;

import computer.Memory;
import computer.processor.register.IR;
import computer.processor.register.Register;

/**
 * Central Processing Unit
 */
public class CPU {

  private final IR ir;
  private final Register mar;
  private final Register mbr;
  private final Register cs;
  private final Register pc;
  private final Register ac;

  private Memory memory;
  private State state;

  /**
   * Generate a CPU with IR and Registers
   * @param ir
   * @param registers
   */
  public CPU(IR ir, Register... registers) {
    this.ir = ir;
    this.mar = registers[0];
    this.mbr = registers[1];
    this.cs = registers[2];
    this.pc = registers[3];
    this.ac = registers[4];
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
      default:
        break;
    }

    System.out.println();
  }
}
