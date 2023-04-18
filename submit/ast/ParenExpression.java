/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

/**
 *
 * @author edwajohn
 */
public class ParenExpression implements Expression {

  private final Expression expression;

  public ParenExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append("(");
    expression.toCminus(builder, prefix);
    builder.append(")");
  }

}
