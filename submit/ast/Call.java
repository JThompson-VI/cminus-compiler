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
public class Call extends AbstractNode implements Expression {

  private final String id;
  private final List<Expression> args;

  public Call(String id, List<Expression> args) {
    this.id = id;
    this.args = new ArrayList<>(args);
  }

  @Override
  public void toCminus(StringBuilder builder, String prefix) {
    builder.append(id).append("(");
    for (Expression arg : args) {
      arg.toCminus(builder, prefix);
      builder.append(", ");
    }
    if (!args.isEmpty()) {
      builder.setLength(builder.length() - 2);
    }
    builder.append(")");
  }

  @Override
  public MIPSResult toMIPS(StringBuilder code, StringBuilder data,
                    SymbolTable symbolTable, RegisterAllocator regAllocator){
    List<MIPSResult> mipsResults = new ArrayList<>();
    for (Expression arg: args) {
      mipsResults.add(arg.toMIPS(code, data, symbolTable, regAllocator));
    }
    // special case for println
    if (id.equals("println")){
      data.append("newline:\t").append(".asciiz \"\\n\"\n");
      code.append(String.format("la $a0 %s\n", mipsResults.get(0).getAddress()));
      code.append(String.format("li $v0 %d\n", mipsResults.get(0).getType() == VarType.CHAR ? 4 : 1));
      code.append("syscall\n");
      code.append("la $a0 newline\nli $v0 4\nsyscall\n");
    }
    // get the type of the arg to determine which print syscall to use
    return MIPSResult.createVoidResult(); // TODO: 4/18/23 come back here
  }
}
