package computer.processor.unit;

import computer.memory.DMA;
import computer.memory.Memory;
import computer.bus.Bus;
import computer.processor.register.AC;
import computer.processor.register.IR;
import computer.processor.register.Register;

public class CU {

  private Memory memory;
  private IR ir;
  private AC ac;
  private ALU alu;

  private Bus dataBus;
  private Bus addressBus;
  private Register cs;
  private Register pc;


  public CU(IR ir, AC ac, ALU alu, Bus dataBus, Bus addressBus, Register... registers) {
    this.ir = ir;
    this.ac = ac;
    this.alu = alu;

    this.dataBus = dataBus;
    this.addressBus = addressBus;

    this.cs = registers[0];
    this.pc = registers[1];
  }

  public void associate(Memory memory) {
    this.memory = memory;
  }

  public void fetch() {
    System.out.println("[fetch]");
    addressBus.write(cs.getValue() + pc.getValue());
    memory.load();
    ir.setValue(dataBus.read());
  }

  public void decode() {
    System.out.println("[decode]");
    System.out.println("operator: " + ir.getOperator());
    System.out.println("operand: " + ir.getOperand());
  }

  public void execute() {
    System.out.println("[execute]");
    pc.setValue(pc.getValue() + 1);
    switch(ir.getOperator()) {
      case HALT:
        halt();
        break;
      case LOADA:
        loada();
        break;
      case LOADC:
        loadc();
        break;
      case STORE:
        store();
        break;
      case ADDA:
        adda();
        break;
      case ADDC:
        addc();
        break;
      case SUB:
        sub();
        break;
      case DIV:
        div();
        break;
      case JUMP:
        jump();
        break;
      case BZ:
        bz();
        break;
      case PUSH:
        push();
        break;
      case POP:
        pop();
        break;
      case OUT:
        out();
        break;
      default:
        throw new RuntimeException("invalid operator");
    }
    System.out.println();
  }

  public void halt() {
    throw new RuntimeException("System exit");
  }

  private void loada() {

    // Scan @input
    if (ir.getOperand() == 2047) {
      int inputValue = DMA.scan("input: ");
      ac.setValue(inputValue);
      return;
    }

    addressBus.write(ir.getOperand());
    memory.load();
    ac.setValue(dataBus.read());
  }

  private void loadc() {
    ac.setValue(ir.getOperand());
  }

  private void store() {
    addressBus.write(ir.getOperand());
    dataBus.write(ac.getValue());
    memory.store();
  }

  private void adda() {
    addressBus.write(ir.getOperand());
    memory.load();
    alu.add(dataBus.read());
  }

  private void addc() {
    alu.add(ir.getOperand());
  }

  private void sub() {
    addressBus.write(ir.getOperand());
    memory.load();
    alu.sub(dataBus.read());
  }

  private void div() {
    alu.div(ir.getOperand());
  }

  private void jump() {
    if (ir.getOperand() < 1024) {
      pc.setValue(ir.getOperand());
      return;
    }

    addressBus.write(ir.getOperand());
    memory.load();
    pc.setValue(dataBus.read());
  }

  private void bz() {
    if (ac.isZero()) {
      pc.setValue(ir.getOperand());
    }
  }

  private void push() {
    memory.push(ir.getOperand());
  }

  private void pop() {
    memory.pop(ir.getOperand());
  }

  private void out() {
    DMA.print("output: " + ac.getValue());
  }
}
