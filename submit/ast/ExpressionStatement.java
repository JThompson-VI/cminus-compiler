/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

/**
 *
 * @author edwajohn
 */
public class ExpressionStatement extends AbstractNode implements Statement {

  private final Expression expression;

  public ExpressionStatement(Expression expression) {
    this.expression = expression;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix);
    expression.toCminus(builder, prefix);
    builder.append(";\n");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    expression.toMIPS(code, data, symbolTable, regAllocator);
    return MIPSResult.createVoidResult();
  }
}
