package instruction;

public enum Operator {
  HALT,
  LOADA,
  LOADC,
  STORE,
  ADDA,
  ADDC,
  SUB,
  DIV,
  JUMP,
  BZ,
  PUSH,
  POP,
  OUT,
  ;

  public static Operator valueOf(int value) {
    return Operator.values()[value];
  }
}
