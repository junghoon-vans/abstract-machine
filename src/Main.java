import computer.Computer;
import computer.Memory;
import computer.processor.CPU;
import computer.processor.register.AC;
import computer.processor.register.IR;
import computer.processor.register.Register;
import computer.processor.unit.ALU;

public class Main {

  public static void main(String[] args) {
    IR ir = new IR();
    AC ac = new AC();
    ALU alu = new ALU(ac);

    Register mar = new Register();
    Register mbr = new Register();
    Register cs = new Register();
    Register pc = new Register();

    CPU cpu = new CPU(ir, ac, alu, mar, mbr, cs, pc);

    Memory memory = new Memory();
    memory.associate(mar, mbr);
    cpu.associate(memory);

    Computer computer = new Computer(cpu, memory);
    computer.run();
  }
}
