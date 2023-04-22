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
public class UnaryOperator extends AbstractNode implements Expression {

  private final UnaryOperatorType type;
  private final Expression expression;

  public UnaryOperator(String type, Expression expression) {
    this.type = UnaryOperatorType.fromString(type);
    this.expression = expression;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(type);
    expression.toCminus(builder, prefix);
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    MIPSResult exprMips = expression.toMIPS(code, data, symbolTable, regAllocator);
    String reg = exprMips.getRegister();
    if (reg == null) {
      reg = regAllocator.getT();
    }
    // todo: deal with not expr not return reg
    // TODO: 4/21/23 do unary operation and load result to reg
    if (type == UnaryOperatorType.NEG) {
      code.append(String.format("sub %s $zero %s\n", reg, reg));
    } else {
      System.err.println("todo: impl unary operator " + type.toString());
    }
    return MIPSResult.createRegisterResult(reg, exprMips.getType());
  }
}
