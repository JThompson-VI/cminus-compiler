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
    if (id.equals("println")){
      return toMipsPrintln(code, data, symbolTable, regAllocator);
    }
    code.append(String.format("# calling function %s\n", id));
    code.append("# store ra\n");
    String raReg = regAllocator.getT();
    if (raReg == null) {
      System.err.println("no regs available for ra in call");
    }
    code.append(String.format("move %s $ra\n", raReg));

    code.append("# store t registers\n");
    int regOffset = regAllocator.saveT(code, symbolTable.getARSize());

    code.append("# Evaluate args and place on the stack\n");
    int offset = -4;
    for (Expression arg : args) {
      MIPSResult argMips = arg.toMIPS(code, data, symbolTable, regAllocator);
      String argReg = argMips.getRegister();

      code.append(String.format("sw %s %d($sp)\n", argReg, offset - regOffset -symbolTable.getARSize()));
      regAllocator.clear(argReg);
      offset = offset - 4;
    }
    code.append("# update stack pointer\n");
    code.append(String.format("addi $sp $sp %d\n", -regOffset - symbolTable.getARSize()));
    code.append("# call the function\n");
    code.append(String.format("jal %s\n", id));

    code.append("# restore stack pointer\n");
    code.append(String.format("addi $sp $sp %d\n", regOffset + symbolTable.getARSize()));

    code.append("# restore t regs\n");
    regAllocator.restoreT(code, symbolTable.getARSize());

    regAllocator.clear(raReg);
    code.append("# restore ra\n");
    code.append(String.format("move $ra %s\n", raReg));

    if (symbolTable.find(id).getType() != null) {
      code.append("# get return value off the stack\n");
      // offset of return is regoffset + arsize + # of params * 4
      int offsetOfReturn = -regOffset - symbolTable.getARSize() + args.size() * -4 - 4;
      String returnReg = regAllocator.getT();
      code.append(String.format("lw %s %d($sp)\n", returnReg, offsetOfReturn));
      regAllocator.clear(returnReg);
      return MIPSResult.createRegisterResult(returnReg, symbolTable.find(id).getType());
    }
    return MIPSResult.createVoidResult();
  }

  private MIPSResult toMipsPrintln(StringBuilder code, StringBuilder data,
                                 SymbolTable symbolTable, RegisterAllocator regAllocator) {
    // get the type of the arg to determine which print syscall to use
    List<MIPSResult> mipsResults = new ArrayList<>();
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
}
