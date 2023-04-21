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
public class Mutable extends AbstractNode implements Expression, Node {

  private final String id;
  private final Expression index;

  public Mutable(String id, Expression index) {
    this.id = id;
    this.index = index;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(id);
    if (index != null) {
      builder.append("[");
      index.toCminus(builder, prefix);
      builder.append("]");
    }
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    SymbolInfo symbolInfo = symbolTable.find(id);
    int targetOffset = symbolInfo.getOffset();
    // binary operator expects this the return a reg with the value of this
    // assignment expects a reg with the sp + offset of this entry in the symbol table
    String reg = regAllocator.getT();
    if (reg == null) {
      System.err.println("failed to get reg in Mutable");
    }
    code.append(String.format("# get %s offset from the stack pointer.\n", id ));
    code.append(String.format("li %s %d\n", reg, targetOffset));

    code.append("# load offset + sp to get the address of ").append(id).append("\n");
    code.append(String.format("add %s $sp %s\n", reg, reg));

    return MIPSResult.createAddressResult(reg, symbolInfo.getType()); // TODO: 4/21/23 do I return type of symbol or type of contents of reg
  }
}
