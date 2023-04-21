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
public class Assignment extends AbstractNode implements Expression, Node {

  private final Mutable mutable;
  private final AssignmentType type;
  private final Expression rhs;

  public Assignment(Mutable mutable, String assign, Expression rhs) {
    this.mutable = mutable;
    this.type = AssignmentType.fromString(assign);
    this.rhs = rhs;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    mutable.toCminus(builder, prefix);
    if (rhs != null) {
      builder.append(" ").append(type.toString()).append(" ");
      rhs.toCminus(builder, prefix);
    } else {
      builder.append(type.toString());

    }
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    MIPSResult idMips = mutable.toMIPS(code, data, symbolTable, regAllocator); // return reg with address to id
    code.append("# compute rhs for assignment\n");
    MIPSResult expressionMips = rhs.toMIPS(code, data, symbolTable, regAllocator); // returns reg with result of rhs

    code.append("# complete assignment by storing rhs in address\n");
    code.append(String.format("sw %s 0(%s)\n\n", expressionMips.getRegister(), idMips.getAddress()));

    regAllocator.clear(expressionMips.getRegister());
    regAllocator.clear(idMips.getAddress());

    return MIPSResult.createVoidResult();
//    return MIPSResult.createRegisterResult(reg, expressionMips.getType());
  }
}
