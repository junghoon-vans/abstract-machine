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

  int[] codeSegment;
  int[] dataSegment;
  int[] heapSegment;
  int[] stackSegment;

  public Memory() {
    this.codeSegment = new int[1024];
    this.dataSegment = new int[1024];
    this.heapSegment = new int[1024];
    this.stackSegment = new int[1024];
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
    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      this.mbr.setValue(this.stackSegment[address - STACK_SEGMENT]);
    } else if (address >= HEAP_SEGMENT) {
      this.mbr.setValue(this.heapSegment[address - HEAP_SEGMENT]);
    } else if (address >= DATA_SEGMENT) {
      this.mbr.setValue(this.dataSegment[address - DATA_SEGMENT]);
    } else if (address >= CODE_SEGMENT){
      this.mbr.setValue(this.codeSegment[address]);
    }
  }

  public void store() {
    int address = mar.getValue();
    int value = mbr.getValue();

    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      this.stackSegment[address - STACK_SEGMENT] = value;
    } else if (address >= HEAP_SEGMENT) {
      this.heapSegment[address - HEAP_SEGMENT] = value;
    } else if (address >= DATA_SEGMENT) {
      this.dataSegment[address - DATA_SEGMENT] = value;
    } else if (address >= CODE_SEGMENT){
      this.codeSegment[address] = value;
    }
  }
}
