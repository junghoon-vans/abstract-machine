package computer;

import computer.processor.CPU;

public class Computer {

  private final CPU cpu;
  private final Memory memory;

  public Computer() {
    cpu = new CPU();
    memory = new Memory();
    memory.associate(cpu.mar, cpu.mbr);
    cpu.associate(memory);
  }

  public void run() {
    cpu.start();
  }
}
