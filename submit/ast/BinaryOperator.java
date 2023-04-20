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
public class BinaryOperator extends AbstractNode implements Expression {

  private final Expression lhs, rhs;
  private final BinaryOperatorType type;

  public BinaryOperator(Expression lhs, BinaryOperatorType type, Expression rhs) {
    this.lhs = lhs;
    this.type = type;
    this.rhs = rhs;
  }

  public BinaryOperator(Expression lhs, String type, Expression rhs) {
    this.lhs = lhs;
    this.type = BinaryOperatorType.fromString(type);
    this.rhs = rhs;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    lhs.toCminus(builder, prefix);
    builder.append(" ").append(type).append(" ");
    rhs.toCminus(builder, prefix);
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    // TODO: 4/19/23 currently only dealing with +, -, *, /
    MIPSResult lhsMips = lhs.toMIPS(code, data, symbolTable, regAllocator);
    MIPSResult rhsMips = rhs.toMIPS(code, data, symbolTable, regAllocator);
    if (type == BinaryOperatorType.PLUS || type == BinaryOperatorType.MINUS) {
      code.append(String.format("%s %s %s %s\n",
              type == BinaryOperatorType.MINUS ? "sub" : "add",
              lhsMips.getRegister(),
              lhsMips.getRegister(),
              rhsMips.getRegister()));
      regAllocator.clear(rhsMips.getRegister());
      return MIPSResult.createRegisterResult(lhsMips.getRegister(), VarType.INT);
    } else if (type == BinaryOperatorType.DIVIDE || type == BinaryOperatorType.TIMES) {
      code.append(String.format("%s %s %s\n",
                      type == BinaryOperatorType.DIVIDE ? "div" : "mult",
                      lhsMips.getRegister(),
                      rhsMips.getRegister()))
              .append(String.format("mflo %s\n", lhsMips.getRegister()));
      regAllocator.clear(rhsMips.getRegister());
      return MIPSResult.createRegisterResult(lhsMips.getRegister(), VarType.INT);
    }
    System.err.println("Need to implement more binary operators");
    return super.toMIPS(code, data, symbolTable, regAllocator);
  }
}
