package kernel;

import computer.memory.Memory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loader
 * executable 파일을 메모리에 로드한다.
 */
public class Loader {

  private final Memory memory;

  public Loader(Memory memory) {
    this.memory = memory;
  }

  public void load(String filepath) {
    List<Integer> instructions = new ArrayList<>();
    File file = new File(filepath);
    try {
      Scanner scanner = new Scanner(file);
      while (scanner.hasNext()) {
        String instruction = scanner.next();
        instructions.add(Integer.parseInt(instruction, 2));
      }
      memory.setCodeSegment(instructions);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
