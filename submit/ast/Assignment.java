/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
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
    SymbolInfo symbolInfo = symbolTable.find(mutable.getId());
    int targetOffset = symbolInfo.getOffset();
    // get offset of symbol
    // add sp to offset to address of symbol
    String reg = regAllocator.getT();
    if (reg == null) {
      System.err.println("failed to get reg in assignment");
    }
    code.append(String.format("# get %s offset from the stack pointer.\n", mutable.getId() ));
    code.append(String.format("li %s %d\n", reg, targetOffset));

    code.append("# load offset + sp to get the address of ").append(mutable.getId()).append("\n");
    code.append(String.format("add %s $sp %s\n", reg, reg));

    code.append("# compute rhs for assignment\n");
    MIPSResult expressionMips = rhs.toMIPS(code, data, symbolTable, regAllocator); // returns reg with result of rhs

    // use symbol table to get a offset from sp
    code.append("# complete assignment by storing rhs in address\n");
    code.append(String.format("sw %s 0(%s)\n\n", expressionMips.getRegister(), reg));

    regAllocator.clear(expressionMips.getRegister());
    regAllocator.clear(reg);

    return MIPSResult.createVoidResult();
//    return MIPSResult.createRegisterResult(reg, expressionMips.getType());
  }
}
