/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

/**
 *
 * @author edwajohn
 */
public class BoolConstant implements Expression {

  private final boolean value;

  public BoolConstant(boolean value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    if (value) {
      builder.append("true");
    } else {
      builder.append("false");
    }
  }

}
