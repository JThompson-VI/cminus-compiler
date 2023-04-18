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
public class Break extends AbstractNode implements Statement {

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(prefix).append("break;\n");
  }

}
