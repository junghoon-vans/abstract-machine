package computer.processor;

import computer.Memory;
import computer.processor.register.IR;
import computer.processor.register.Register;
import java.util.Scanner;

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

  public void run() {
    while(this.state == State.RUNNING) {
      try {
        this.fetch();
        this.decode();
        this.execute();
      } catch (Exception e) {
        this.state = State.STOPPED;
        System.out.println("Error: " + e.getMessage());
      }
    }
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
    this.state = State.STOPPED;
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
    ac.setValue(ac.getValue() + mbr.getValue());
  }

  private void addc() {
    ac.setValue(ac.getValue() + ir.getOperand());
  }

  private void sub() {
    mar.setValue(ir.getOperand());
    memory.load();
    ac.setValue(ac.getValue() - mbr.getValue());
  }

  private void div() {
    ac.setValue(ac.getValue() / ir.getOperand());
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
    if (ac.getValue() == 0) {
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
