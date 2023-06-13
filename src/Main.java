import computer.Computer;
import computer.memory.Memory;
import computer.bus.Bus;
import computer.bus.DataBus;
import computer.processor.CPU;

public class Main {

  public static void main(String[] args) {
    Bus dataBus = new DataBus();
    Bus addressBus = new DataBus();

    CPU cpu = new CPU(dataBus, addressBus);
    Memory memory = new Memory(dataBus, addressBus);

    cpu.associate(memory);

    Computer computer = new Computer(cpu, memory);
    computer.run();
  }
}
