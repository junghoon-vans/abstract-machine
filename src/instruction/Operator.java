package instruction;

public enum Operator {
  HALT,
  LDA,
  STA,
  ADD,
  AND,
  JMP,
  BZ,
  NOT,
  ;

  public static Operator valueOf(int value) {
    return Operator.values()[value];
  }
}
