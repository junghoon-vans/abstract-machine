package computer;

import java.util.ArrayList;
import java.util.List;
import computer.processor.register.Register;

public class Memory {

  private static final int CODE_SEGMENT = 0;
  private static final int DATA_SEGMENT = 1024;
  private static final int HEAP_SEGMENT = 2048;
  private static final int STACK_SEGMENT = 3072;
  private static final int MAX_MEMORY_ADDRESS = 4096;

  private Register mar;
  private Register mbr;

  List<Integer> codeSegment;
  List<Integer> dataSegment;
  List<Integer> heapSegment;
  List<Integer> stackSegment;

  public Memory() {
    this.codeSegment = new ArrayList<>();
    this.dataSegment = new ArrayList<>();
    this.heapSegment = new ArrayList<>();
    this.stackSegment = new ArrayList<>();
  }

  public void setCodeSegment(List<Integer> instructions) {
    this.codeSegment = instructions;
  }

  public void associate(Register mar, Register mbr) {
    this.mar = mar;
    this.mbr = mbr;
  }

  public void load() {
    int address = mar.getValue();

    if (address > MAX_MEMORY_ADDRESS || address < CODE_SEGMENT) {
      throw new RuntimeException("Memory address out of bounds");
    }

    if (address >= STACK_SEGMENT) {
      mbr.setValue(this.stackSegment.get(address - STACK_SEGMENT));
      return;
    }

    if (address >= HEAP_SEGMENT) {
      mbr.setValue(this.heapSegment.get(address - HEAP_SEGMENT));
      return;
    }

    if (address >= DATA_SEGMENT) {
      mbr.setValue(this.dataSegment.get(address - DATA_SEGMENT));
      return;
    }

    mbr.setValue(this.codeSegment.get(address));
  }

  public void store() {
    int address = mar.getValue();
    int value = mbr.getValue();

    if (address > MAX_MEMORY_ADDRESS || address < CODE_SEGMENT) {
      throw new RuntimeException("Memory address out of bounds");
    }

    if (address >= STACK_SEGMENT) {
      this.stackSegment.set(address - STACK_SEGMENT, value);
      return;
    }

    if (address >= HEAP_SEGMENT) {
      this.heapSegment.set(address - HEAP_SEGMENT, value);
      return;
    }

    if (address >= DATA_SEGMENT) {
      this.dataSegment.set(address - DATA_SEGMENT, value);
      return;
    }

    this.codeSegment.set(address, value);
  }
}
