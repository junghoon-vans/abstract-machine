package computer.processor.register;

import instruction.Mode;
import instruction.Operator;

/**
 * Instruction Register
 */
public class IR extends Register {

  private Mode mode;
  private Operator operator;
  private int operand;

  /**
   * 명령어를 설정한다.
   * @param value
   *
   * 명령어는 16비트로 이루어져 있으며, 각 비트는 다음과 같은 의미를 가진다.
   * 0: addressing mode
   * 1~3: operator
   * 4~15: operand
   */
  @Override
  public void setValue(int value) {
    super.setValue(value);

    this.setMode(Mode.valueOf(value >> 15 & 0x1));
    this.setOperator(Operator.valueOf(value >> 12 & 0x7));
    this.setOperand(value & 0xFFF);
  }

  public Mode isMode() {
    return mode;
  }

  public void setMode(Mode mode) {
    this.mode = mode;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public int getOperand() {
    return operand;
  }

  public void setOperand(int operand) {
    this.operand = operand;
  }
}
