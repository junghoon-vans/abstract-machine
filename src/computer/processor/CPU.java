package computer.processor;

import computer.Memory;
import computer.processor.register.AC;
import computer.processor.register.IR;
import computer.processor.register.Register;
import computer.processor.unit.ALU;
import computer.processor.unit.CU;

/**
 * Central Processing Unit
 */
public class CPU {

  private final IR ir;
  private final AC ac;

  private final ALU alu;
  private final CU cu;

  private final Register mar;
  private final Register mbr;
  private final Register cs;
  private final Register pc;

  private Memory memory;
  private State state;

  /**
   * Generate a CPU with IR and Registers
   * @param ir
   * @param registers
   */
  public CPU(IR ir, AC ac, ALU alu, CU cu, Register... registers) {
    this.ir = ir;
    this.ac = ac;
    this.alu = alu;
    this.cu = cu;

    this.mar = registers[0];
    this.mbr = registers[1];
    this.cs = registers[2];
    this.pc = registers[3];
  }

  public void associate(Memory memory) {
    this.memory = memory;
    this.cu.associate(memory);
  }

  public void start() {
    this.state = State.RUNNING;
    this.run();
  }

  public void run() {
    while(this.state == State.RUNNING) {
      try {
        cu.fetch();
        cu.decode();
        cu.execute();
      } catch (Exception e) {
        this.state = State.STOPPED;
        System.out.println(e.getMessage());
      }
    }
  }
}
