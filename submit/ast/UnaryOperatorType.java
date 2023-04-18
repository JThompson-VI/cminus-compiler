/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

/**
 *
 * @author edwajohn
 */
public enum UnaryOperatorType {

  NOT("!"), NEG("-"), DEREF("*"), QUESTION("?");

  private final String value;

  private UnaryOperatorType(String value) {
    this.value = value;
  }

  public static UnaryOperatorType fromString(String s) {
    for (UnaryOperatorType at : UnaryOperatorType.values()) {
      if (at.value.equals(s)) {
        return at;
      }
    }
    throw new RuntimeException("Illegal string in UnaryOperatorType.fromString()");
  }

  @Override
  public String toString() {
    return value;
  }

}
