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
public class Return extends AbstractNode implements Statement {

  private final Expression expr;

  public Return(Expression expr) {
    this.expr = expr;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix);
    if (expr == null) {
      builder.append("return;\n");
    } else {
      builder.append("return ");
      expr.toCminus(builder, prefix);
      builder.append(";\n");
    }
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {

    MIPSResult exprMips = expr.toMIPS(code, data, symbolTable, regAllocator);
    String reg = exprMips.getRegister();
    SymbolInfo returnSymbol = symbolTable.find("return");
    int offset = returnSymbol.getOffset();
    // place offset in reg
    code.append("# store the return value on the stack\n");
    code.append(String.format("sw %s %d($sp)\n", reg, offset));
    code.append("jr $ra\n");
    regAllocator.clear(reg);
    return MIPSResult.createVoidResult();
  }
}
