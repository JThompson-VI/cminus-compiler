/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

import java.util.List;

/**
 *
 * @author edwajohn
 */
public class CompoundStatement extends AbstractNode implements Statement {

  private final List<Statement> statements;
  private final SymbolTable localSymbolTable;

  public CompoundStatement(List<Statement> statements) {
    this.statements = statements;
    this.localSymbolTable = new SymbolTable();
  }

  public CompoundStatement(List<Statement> statements, SymbolTable symbolTable) {
    this.statements = statements;
    this.localSymbolTable = symbolTable;
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("{\n");
    for (Statement s : statements) {
      s.toCminus(builder, prefix + "  ");
    }
    builder.append(prefix).append("}\n");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    code.append("# Entering a new scope\n");
    if (localSymbolTable == null) {
      return MIPSResult.createVoidResult();
    }
    code.append("# Symbols on the symbol table\n");
    for (String key: localSymbolTable.getTable().keySet()) {
      code.append(String.format("# %s\n", key));
    }
    code.append("# update the stack pointer\n");
    code.append(String.format("addi $sp $sp -%d\n", symbolTable.getARSize()));
    for (Statement s : statements) {
      s.toMIPS(code, data, localSymbolTable, regAllocator);
    }
    code.append("# exiting scope, restoring sp \n");
    code.append(String.format("addi $sp $sp %d\n", symbolTable.getARSize()));
    return MIPSResult.createVoidResult();
  }
}
