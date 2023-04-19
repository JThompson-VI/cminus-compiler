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
public class StringConstant extends AbstractNode implements Expression {

  private final String value;

  public StringConstant(String value) {
    this.value = value;
  }

  public void toCminus(StringBuilder builder, final String prefix) {
    builder.append("\"").append(value).append("\"");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data, SymbolTable symbolTable, RegisterAllocator regAllocator) {
    /*
    * create data label
    * [new label from symboltabl]: \t.asciiz "this.value"
    * */
    String dataLabel = symbolTable.generateDataLabel();
    data.append(dataLabel).append(":\t").append(".asciiz ").append(value).append("\n");

    return MIPSResult.createAddressResult(dataLabel, VarType.CHAR);
  }
}
