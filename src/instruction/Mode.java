package instruction;

public enum Mode {
  DIRECT(),
  INDIRECT(),
  ;

  public static Mode valueOf(int value) {
    return Mode.values()[value];
  }
}
