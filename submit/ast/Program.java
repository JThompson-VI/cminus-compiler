/*
 * Code formatter project
 * CS 4481
 */
package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edwajohn
 */
public class Program extends AbstractNode implements Node {

  private ArrayList<Declaration> declarations;

  public Program(List<Declaration> declarations) {
    this.declarations = new ArrayList<>(declarations);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    toCminus(builder, "");
    return builder.toString();
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    for (Declaration declaration : declarations) {
      declaration.toCminus(builder, "");
    }
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    for (Declaration declaration: declarations) {
      declaration.toMIPS(code, data, symbolTable, regAllocator);
    }
    code.append("li $v0 10\nsyscall");
    return MIPSResult.createVoidResult();
  }
}
