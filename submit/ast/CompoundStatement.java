/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolInfo;
import submit.SymbolTable;

import java.util.List;
import java.util.Set;

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

  public void addSymbol(String id, SymbolInfo symbolInfo) {
    if (localSymbolTable != null) {
      localSymbolTable.addSymbol(id, symbolInfo);
    }
  }

  public void addParams(List<Param> params) {
    // get current keys in local table
    Set<String> keys = localSymbolTable.getTable().keySet();
    int numberOfParams = params.size();
    // loop through keys in table and decrement offsets by 4 * number of params
    for (String k : keys) {
      SymbolInfo symbol = localSymbolTable.getTable().get(k);
      if (!symbol.isFunction()) {
        symbol.setOffset(symbol.getOffset() - 4 * numberOfParams);
      }
    }
    // add param and set offset to be first in table maybe using addSymbol overload
    for (int i = 0; i < params.size(); i++) {
      Param p = params.get(i);
      SymbolInfo newParam = new SymbolInfo(p.getId(), p.getType(), false);
      localSymbolTable.addSymbol(p.getId(), newParam);
      newParam.setOffset(-4 * (i + 1));
    }
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
    // todo: reset the stack pointer?
    for (Statement s : statements) {
      s.toMIPS(code, data, localSymbolTable, regAllocator);
    }
    code.append("# exiting scope, restoring sp \n");
    code.append(String.format("addi $sp $sp %d\n", symbolTable.getARSize()));
    return MIPSResult.createVoidResult();
  }
}
