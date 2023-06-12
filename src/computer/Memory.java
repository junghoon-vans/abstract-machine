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

  private int[] codeSegment;
  private int[] dataSegment;
  private int[] heapSegment;
  private List<Integer> stackSegment;

  public Memory() {
    this.codeSegment = new int[1024];
    this.dataSegment = new int[1024];
    this.heapSegment = new int[1024];
    this.stackSegment = new ArrayList<>();
  }

  public void setCodeSegment(List<Integer> instructions) {
    for (int i = 0; i < instructions.size(); i++) {
      this.codeSegment[i] = instructions.get(i);
    }
  }

  public void associate(Register mar, Register mbr) {
    this.mar = mar;
    this.mbr = mbr;
  }

  public void load() {
    int address = mar.getValue();
    int value = 0;

    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      if (stackSegment.size() != 0) {
        value = this.stackSegment.get((address - STACK_SEGMENT)/4);
        System.out.println("stack[" + (address - STACK_SEGMENT)/4 + "]: " + value);
      }
    } else if (address >= HEAP_SEGMENT) {
      value = this.heapSegment[(address - HEAP_SEGMENT)/4];
      System.out.println("heap[" + (address - HEAP_SEGMENT)/4 + "]: " + value);
    } else if (address >= DATA_SEGMENT) {
      value = this.dataSegment[(address - DATA_SEGMENT)/4];
      System.out.println("data[" + (address - DATA_SEGMENT)/4 + "]: " + value);
    } else if (address >= CODE_SEGMENT){
      value = this.codeSegment[address];
      System.out.println("code[" + (address) + "]: "
              + String.format("%16s", Integer.toBinaryString(value))
              .replace(' ', '0')
      );
    }

    this.mbr.setValue(value);
  }

  public void store() {
    int address = mar.getValue();
    int value = mbr.getValue();

    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      this.stackSegment.set((address - STACK_SEGMENT)/4, value);
    } else if (address >= HEAP_SEGMENT) {
      this.heapSegment[(address - HEAP_SEGMENT)/4] = value;
    } else if (address >= DATA_SEGMENT) {
      this.dataSegment[(address - DATA_SEGMENT)/4] = value;
    } else if (address >= CODE_SEGMENT){
      this.codeSegment[address] = value;
    }
  }

  public void push(int value) {
    int size = value/4;
    for (int i = 0; i < size; i++) {
      this.stackSegment.add(0, 0);
    }
  }

  public void pop(int value) {
    int size = value/4;
    for (int i = 0; i < size; i++) {
      this.stackSegment.remove(0);
    }
  }
}
