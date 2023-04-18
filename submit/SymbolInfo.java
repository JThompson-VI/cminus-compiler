/*
 * Code formatter project
 * CS 4481
 */
package submit;

import submit.ast.VarType;

/**
 *
 * @author edwajohn
 */
public class SymbolInfo {

  private final String id;
  // In the case of a function, type is the return type
  private final VarType type;
  private int offset = 0;
  private final boolean function;

  public SymbolInfo(String id, VarType type, boolean function) {
    this.id = id;
    this.type = type;
    this.function = function;
  }

  public int getOffset() {
    return offset;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  @Override
  public String toString() {
    return "<" + id + ", " + type + '>';
  }

}
