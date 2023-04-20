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
public class ParenExpression extends AbstractNode implements Expression {

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

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    MIPSResult exprMips = expression.toMIPS(code, data, symbolTable, regAllocator);
    // TODO: 4/19/23 potential bug I am assuming all paren expressions return register result
    return MIPSResult.createRegisterResult(exprMips.getRegister(), exprMips.getType());
  }
}
