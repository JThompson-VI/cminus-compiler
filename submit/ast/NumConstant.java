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
public class NumConstant extends AbstractNode implements Expression, Node {

  private final int value;

  public NumConstant(int value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append(Integer.toString(value));
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    String reg = regAllocator.getT();;
    if (reg != null) {
      code.append("li ")
              .append(reg).append(" ")
              .append(value).append("\n");
      return MIPSResult.createRegisterResult(reg, VarType.INT);
    } else {
      // todo: special case if we run out of regs
      System.out.println("ran out of regs in numConst");
    }
    return MIPSResult.createRegisterResult(reg, VarType.INT);
  }
}
