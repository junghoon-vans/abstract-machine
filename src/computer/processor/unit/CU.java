package computer.processor.unit;

import computer.Memory;
import computer.processor.register.AC;
import computer.processor.register.IR;
import computer.processor.register.Register;
import java.util.Scanner;

public class CU {

  private Memory memory;
  private IR ir;
  private AC ac;
  private ALU alu;

  private Register mar;
  private Register mbr;
  private Register cs;
  private Register pc;


  public CU(IR ir, AC ac, ALU alu, Register... registers) {
    this.ir = ir;
    this.ac = ac;
    this.alu = alu;

    this.mar = registers[0];
    this.mbr = registers[1];
    this.cs = registers[2];
    this.pc = registers[3];
  }

  public void associate(Memory memory) {
    this.memory = memory;
  }

  public void fetch() {
    System.out.println("[fetch]");
    mar.setValue(cs.getValue() + pc.getValue());
    memory.load();
    ir.setValue(mbr.getValue());
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
      System.out.print("input: ");
      Scanner scanner = new Scanner(System.in);
      ac.setValue(scanner.nextInt());
      scanner.close();
      return;
    }

    mar.setValue(ir.getOperand());
    memory.load();
    ac.setValue(mbr.getValue());
  }

  private void loadc() {
    ac.setValue(ir.getOperand());
  }

  private void store() {
    mar.setValue(ir.getOperand());
    mbr.setValue(ac.getValue());
    memory.store();
  }

  private void adda() {
    mar.setValue(ir.getOperand());
    memory.load();
    alu.add(mbr.getValue());
  }

  private void addc() {
    alu.add(ir.getOperand());
  }

  private void sub() {
    mar.setValue(ir.getOperand());
    memory.load();
    alu.sub(mbr.getValue());
  }

  private void div() {
    alu.div(ir.getOperand());
  }

  private void jump() {
    if (ir.getOperand() < 1024) {
      pc.setValue(ir.getOperand());
      return;
    }

    mar.setValue(ir.getOperand());
    memory.load();
    pc.setValue(mbr.getValue());
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
    System.out.printf("output: %d\n", ac.getValue());
  }
}
