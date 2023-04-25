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
    String lhsReg = lhsMips.getRegister();
    MIPSResult rhsMips = rhs.toMIPS(code, data, symbolTable, regAllocator);
    String rhsReg = rhsMips.getRegister();

    if (type == BinaryOperatorType.PLUS || type == BinaryOperatorType.MINUS) {
      code.append(String.format("%s %s %s %s\n",
              type == BinaryOperatorType.MINUS ? "sub" : "add",
              lhsReg,
              lhsReg,
              rhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.INT);
    } else if (type == BinaryOperatorType.DIVIDE || type == BinaryOperatorType.TIMES) {
      code.append(String.format("%s %s %s\n",
                      type == BinaryOperatorType.DIVIDE ? "div" : "mult",
                      lhsReg,
                      rhsReg))
              .append(String.format("mflo %s\n", lhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.INT);
    } else if (type == BinaryOperatorType.LT) {
      code.append(String.format("slt %s %s %s\n", lhsReg, lhsReg, rhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.BOOL);
    } else if (type == BinaryOperatorType.GT) {
      code.append(String.format("slt %s %s %s\n", lhsReg, rhsReg, lhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.BOOL);
    } else if (type == BinaryOperatorType.LE) {
      code.append(String.format("slt %s %s %s\n", lhsReg, rhsReg, lhsReg));
      code.append(String.format("subi %s %s 1\n", lhsReg, lhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.BOOL);
    } else if (type == BinaryOperatorType.GE) {
      code.append(String.format("slt %s %s %s\n", lhsReg, lhsReg, rhsReg));
      code.append(String.format("subi %s %s 1\n", lhsReg, lhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.BOOL);
    } else if (type == BinaryOperatorType.EQ) {
      code.append(String.format("xor %s %s %s\n", lhsReg, lhsReg, rhsReg));
      code.append(String.format("slti %s %s 1\n", lhsReg, lhsReg));
      regAllocator.clear(rhsReg);
      return MIPSResult.createRegisterResult(lhsReg, VarType.BOOL);
    }
    System.out.println("Need to implement more binary operators: " + type.toString());
    return super.toMIPS(code, data, symbolTable, regAllocator);
  }
}
