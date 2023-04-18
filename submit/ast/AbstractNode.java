package submit.ast;

import submit.MIPSResult;
import submit.RegisterAllocator;
import submit.SymbolTable;

public abstract class AbstractNode {
    MIPSResult toMIPS(StringBuilder code, StringBuilder data,
                      SymbolTable symbolTable, RegisterAllocator regAllocator) {
        return MIPSResult.createVoidResult();
    }
}
