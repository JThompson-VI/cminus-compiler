package submit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Code formatter project
 * CS 4481
 */
/**
 *
 */
public class SymbolTable {

  private final HashMap<String, SymbolInfo> table;
  private SymbolTable parent;
  private final List<SymbolTable> children;
  private int activationRecordSize;
  private static int dataLabelNumber = 0;

  public SymbolTable() {
    table = new HashMap<>();
    parent = null;
    children = new ArrayList<>();
    this.addSymbol("println", new SymbolInfo("println"));
  }
  public int getARSize() { return this.activationRecordSize; }

  public HashMap<String, SymbolInfo> getTable() {
    return table;
  }

  public void addSymbol(String id, SymbolInfo symbol) {
    if (!symbol.isFunction()){
      activationRecordSize = activationRecordSize + 4;
      symbol.setOffset(activationRecordSize * -1);
    }
    table.put(id, symbol);
  }

  public String generateDataLabel() {
    String label = String.format("datalabel%d", dataLabelNumber);
    dataLabelNumber++;
    return label;
  }

  /**
   * Returns null if no symbol with that id is in this symbol table or an
   * ancestor table.
   *
   * @param id
   * @return
   */
  public SymbolInfo find(String id) { // TODO: 4/21/23 deal with offset if symbol in parent table
    if (table.containsKey(id)) {
      return table.get(id);
    }
    if (parent != null) {
      return parent.find(id);
    }
    return null;
  }

  /**
   * Returns the new child.
   *
   * @return
   */
  public SymbolTable createChild() {
    SymbolTable child = new SymbolTable();
    children.add(child);
    child.parent = this;
    return child;
  }

  public SymbolTable getParent() {
    return parent;
  }

}
