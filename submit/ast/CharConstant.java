/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

/**
 *
 * @author edwajohn
 */
public class CharConstant implements Expression {

  private final char value;

  public CharConstant(char value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append("'").append(value).append("'");
  }

}
