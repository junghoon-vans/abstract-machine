package computer;

import computer.bus.Bus;
import java.util.ArrayList;
import java.util.List;
import computer.processor.register.Register;

public class Memory {

  private static final int CODE_SEGMENT = 0;
  private static final int DATA_SEGMENT = 1024;
  private static final int HEAP_SEGMENT = 2048;
  private static final int STACK_SEGMENT = 3072;
  private static final int MAX_MEMORY_ADDRESS = 4096;

  private final Bus dataBus;
  private final Bus addressBus;

  private int[] codeSegment;
  private int[] dataSegment;
  private int[] heapSegment;
  private List<Integer> stackSegment;

  public Memory(Bus... buses) {
    this.codeSegment = new int[1024];
    this.dataSegment = new int[1024];
    this.heapSegment = new int[1024];
    this.stackSegment = new ArrayList<>();

    this.dataBus = buses[0];
    this.addressBus = buses[1];
  }

  public void setCodeSegment(List<Integer> instructions) {
    for (int i = 0; i < instructions.size(); i++) {
      this.codeSegment[i] = instructions.get(i);
    }
  }

  public void load() {
    int address = addressBus.read();

    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      if (stackSegment.size() == 0) {
        this.dataBus.write(0);
        return;
      }

      int index = (address - STACK_SEGMENT)/4;
      int value = this.stackSegment.get(index);

      System.out.println("stack[" + index + "]: " + value);
      this.dataBus.write(value);
      return;
    }

    if (address >= HEAP_SEGMENT) {
      int index = (address - HEAP_SEGMENT)/4;
      int value = this.heapSegment[index];

      System.out.println("heap[" + index + "]: " + value);
      this.dataBus.write(value);
      return;
    }

    if (address >= DATA_SEGMENT) {
      int index = (address - DATA_SEGMENT)/4;
      int value = this.dataSegment[index];

      System.out.println("data[" + index + "]: " + value);
      this.dataBus.write(value);
      return;
    }

    if (address >= CODE_SEGMENT){
      int value = this.codeSegment[address];
      System.out.println("code[" + (address) + "]: "
              + String.format("%16s", Integer.toBinaryString(value))
              .replace(' ', '0')
      );
      this.dataBus.write(value);
    }
  }

  public void store() {
    int address = addressBus.read();
    int value = dataBus.read();

    if (address >= STACK_SEGMENT && address < MAX_MEMORY_ADDRESS) {
      this.stackSegment.set((address - STACK_SEGMENT) / 4, value);
      return;
    }

    if (address >= HEAP_SEGMENT) {
      this.heapSegment[(address - HEAP_SEGMENT)/4] = value;
      return;
    }

    if (address >= DATA_SEGMENT) {
      this.dataSegment[(address - DATA_SEGMENT)/4] = value;
      return;
    }

    if (address >= CODE_SEGMENT){
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
