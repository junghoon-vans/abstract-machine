package kernel;

import computer.Memory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
    try(BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.contains("// ")) {
          System.out.println(line);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
