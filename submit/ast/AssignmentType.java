/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

/**
 *
 * @author edwajohn
 */
public enum AssignmentType {

  EQUALS("="), PLUS("+="), MINUS("-="), TIMES("*="), DIVIDE("/="), INC("++"), DEC("--");

  private final String value;

  private AssignmentType(String value) {
    this.value = value;
  }

  public static AssignmentType fromString(String s) {
    for (AssignmentType at : AssignmentType.values()) {
      if (at.value.equals(s)) {
        return at;
      }
    }
    throw new RuntimeException("Illegal string in AssignType.fromString()");
  }

  @Override
  public String toString() {
    return value;
  }

}
