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
    if (id.equals("println")){
      // todo: this is broken for prints of math expressions
      code.append("# println\n");
      for (Expression arg: args) {
        mipsResults.add(arg.toMIPS(code, data, symbolTable, regAllocator));
      }
      MIPSResult arg = mipsResults.get(0);
      String moveOp = String.format("move $a0 %s\n", arg.getRegister());
      String laOp = String.format("la $a0 %s\n", arg.getAddress());
      code.append(arg.getAddress() == null ? moveOp : laOp);
      code.append(String.format("li $v0 %d\n", mipsResults.get(0).getType() == VarType.CHAR ? 4 : 1));
      code.append("syscall\n");
      code.append("la $a0 newline\nli $v0 4\nsyscall\n\n");
      if (arg.getRegister() != null){
        regAllocator.clear(arg.getRegister());
      }
      return MIPSResult.createVoidResult();
    }
    // get the type of the arg to determine which print syscall to use
    return MIPSResult.createVoidResult(); // TODO: 4/18/23 come back here
  }
}
