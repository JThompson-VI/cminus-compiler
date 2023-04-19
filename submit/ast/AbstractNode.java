package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

public abstract class AbstractNode {
    public MIPSResult toMIPS(StringBuilder code, StringBuilder data,
                      SymbolTable symbolTable, RegisterAllocator regAllocator) {
        System.out.println("toMips " + this.getClass());

        return MIPSResult.createVoidResult();
    }
}
