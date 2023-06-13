package computer.processor;

import computer.memory.Memory;
import computer.bus.Bus;
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

  private final Bus dataBus;
  private final Bus addressBus;

  private State state;

  /**
   * Generate a CPU with IR and Registers
   * @param buses
   */
  public CPU(Bus... buses) {
    this.ir = new IR();
    this.ac = new AC();
    this.mar = new Register();
    this.mbr = new Register();
    this.cs = new Register();
    this.pc = new Register();

    this.alu = new ALU(ac);
    this.dataBus = buses[0];
    this.addressBus = buses[1];

    this.dataBus.connect(this.mbr);
    this.addressBus.connect(this.mar);

    this.cu = new CU(ir, ac, alu, dataBus, addressBus, cs, pc);
  }

  /**
   * Associate a memory to the CPU
   * @param memory
   */
  public void associate(Memory memory) {
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
