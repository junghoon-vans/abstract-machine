package computer;

import computer.memory.Memory;
import computer.processor.CPU;
import kernel.Loader;

public class Computer {

  private final CPU cpu;
  private final Memory memory;
  private final Loader loader;

  /**
   * Generate a computer with CPU and Memory
   * @param cpu
   * @param memory
   */
  public Computer(CPU cpu, Memory memory) {
    this.cpu = cpu;
    this.memory = memory;

    this.loader = new Loader(memory);
  }

  public void run() {
    loader.load("./resources/program.exe");
    cpu.start();
  }
}
