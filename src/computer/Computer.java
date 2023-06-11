package computer;

import computer.processor.CPU;
import kernel.Loader;

public class Computer {

  private final CPU cpu;
  private final Memory memory;
  private final Loader loader;

  public Computer() {
    cpu = new CPU();
    memory = new Memory();
    memory.associate(cpu.mar, cpu.mbr);
    cpu.associate(memory);
    loader = new Loader(memory);
  }

  public void run() {
    loader.load("./resources/program.exe");
    cpu.start();
  }
}
