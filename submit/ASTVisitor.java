package submit;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.CminusBaseVisitor;
import parser.CminusParser;
import submit.ast.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ASTVisitor extends CminusBaseVisitor<Node> {
    private final Logger LOGGER;
    private SymbolTable symbolTable;

    public ASTVisitor(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    private VarType getVarType(CminusParser.TypeSpecifierContext ctx) {
        final String t = ctx.getText();
        return (t.equals("int")) ? VarType.INT : (t.equals("bool")) ? VarType.BOOL : VarType.CHAR;
    }

    // TODO: 4/18/23 this is just a getter should it be something more?
    public SymbolTable getSymbolTable(){
        return this.symbolTable;
    }

    @Override public Node visitProgram(CminusParser.ProgramContext ctx) {
        symbolTable = new SymbolTable();
        List<Declaration> decls = new ArrayList<>();
        for (CminusParser.DeclarationContext d : ctx.declaration()) {
            decls.add((Declaration) visitDeclaration(d));
        }
        return new Program(decls);
    }

    @Override public Node visitVarDeclaration(CminusParser.VarDeclarationContext ctx) {
        VarType type = getVarType(ctx.typeSpecifier());
        List<String> ids = new ArrayList<>();
        List<Integer> arraySizes = new ArrayList<>();
        for (CminusParser.VarDeclIdContext v : ctx.varDeclId()) {
            String id = v.ID().getText();
            ids.add(id);
            symbolTable.addSymbol(id, new SymbolInfo(id, type, false));
            if (v.NUMCONST() != null) {
                arraySizes.add(Integer.parseInt(v.NUMCONST().getText()));
            } else {
                arraySizes.add(-1);
            }
        }
        final boolean isStatic = false;
        return new VarDeclaration(type, ids, arraySizes, isStatic);
    }

    @Override public Node visitFunDeclaration(CminusParser.FunDeclarationContext ctx) {
        VarType returnType = null;
        if (ctx.typeSpecifier() != null) {
            returnType = getVarType(ctx.typeSpecifier());
        }
        String id = ctx.ID().getText();
        List<Param> params = new ArrayList<>();
        for (CminusParser.ParamContext p : ctx.param()) {
            params.add((Param) visitParam(p));
        }
        Statement statement = (Statement) visitStatement(ctx.statement());
        symbolTable.addSymbol(id, new SymbolInfo(id, returnType, true));
        return new FunDeclaration(returnType, id, params, statement);
    }

    @Override public Node visitParam(CminusParser.ParamContext ctx) {
        VarType type = getVarType(ctx.typeSpecifier());
        String id = ctx.paramId().ID().getText();
        symbolTable.addSymbol(id, new SymbolInfo(id, type, false));
        return new Param(type, id, ctx.paramId().children.size() > 1);
    }

    @Override public Node visitCompoundStmt(CminusParser.CompoundStmtContext ctx) {
        symbolTable = symbolTable.createChild();
        List<Statement> statements = new ArrayList<>();
        for (CminusParser.VarDeclarationContext d : ctx.varDeclaration()) {
            statements.add((VarDeclaration) visitVarDeclaration(d));
        }
        for (CminusParser.StatementContext d : ctx.statement()) {
            statements.add((Statement) visitStatement(d));
        }
        symbolTable = symbolTable.getParent();
        return new CompoundStatement(statements);
    }

    @Override public Node visitExpressionStmt(CminusParser.ExpressionStmtContext ctx) {
        if (ctx.expression() == null) {
            return Statement.empty();
        }
        return new ExpressionStatement((Expression) visitExpression(ctx.expression()));
    }

    @Override public Node visitIfStmt(CminusParser.IfStmtContext ctx) {
        Expression expression = (Expression) visitSimpleExpression(ctx.simpleExpression());
        Statement trueStatement = (Statement) visitStatement(ctx.statement(0));
        Statement falseStatement = null;
        if (ctx.statement().size() > 1) {
            falseStatement = (Statement) visitStatement(ctx.statement(1));
        }
        return new If(expression, trueStatement, falseStatement);
    }

    @Override public Node visitWhileStmt(CminusParser.WhileStmtContext ctx) {
        Expression expression = (Expression) visitSimpleExpression(ctx.simpleExpression());
        Statement statement = (Statement) visitStatement(ctx.statement());
        return new While(expression, statement);
    }

    @Override public Node visitReturnStmt(CminusParser.ReturnStmtContext ctx) {
        if (ctx.expression() != null) {
            return new Return((Expression) visitExpression(ctx.expression()));
        }
        return new Return(null);
    }

    @Override public Node visitBreakStmt(CminusParser.BreakStmtContext ctx) {
        return new Break();
    }

    @Override public Node visitExpression(CminusParser.ExpressionContext ctx) {
        final Node ret;
        CminusParser.MutableContext mutable = ctx.mutable();
        CminusParser.ExpressionContext expression = ctx.expression();
        if (mutable != null) {
            // Assignment
            ParseTree operator = ctx.getChild(1);
            Mutable lhs = (Mutable) visitMutable(mutable);//new Mutable(mutable.ID().getText(), (Expression) visitExpression(mutable.expression()));
            Expression rhs = null;
            if (expression != null) {
                rhs = (Expression) visitExpression(expression);
            }
            ret = new Assignment(lhs, operator.getText(), rhs);
        } else {
            ret = visitSimpleExpression(ctx.simpleExpression());
        }
        return ret;
    }

    @Override public Node visitOrExpression(CminusParser.OrExpressionContext ctx) {
        List<Node> ands = new ArrayList<>();
        for (CminusParser.AndExpressionContext and : ctx.andExpression()) {
            ands.add(visitAndExpression(and));
        }
        if (ands.size() == 1) {
            return ands.get(0);
        }
        BinaryOperator op = new BinaryOperator((Expression)ands.get(0), "||", (Expression)ands.get(1));
        for (int i = 2; i < ands.size(); ++i) {
            op = new BinaryOperator(op, "||", (Expression) ands.get(i));
        }
        return op;
    }

    @Override public Node visitAndExpression(CminusParser.AndExpressionContext ctx) {
        List<Node> uns = new ArrayList<>();
        for (CminusParser.UnaryRelExpressionContext un : ctx.unaryRelExpression()) {
            uns.add(visitUnaryRelExpression(un));
        }
        if (uns.size() == 1) {
            return uns.get(0);
        }
        BinaryOperator op = new BinaryOperator((Expression)uns.get(0), "&&", (Expression)uns.get(1));
        for (int i = 2; i < uns.size(); ++i) {
            op = new BinaryOperator(op, "&&", (Expression) uns.get(i));
        }
        return op;
    }

    @Override public Node visitUnaryRelExpression(CminusParser.UnaryRelExpressionContext ctx) {
        Expression e = (Expression)visitRelExpression(ctx.relExpression());
        for (TerminalNode n : ctx.BANG()) {
            e = new UnaryOperator("!", e);
        }
        return e;
    }

    @Override public Node visitRelExpression(CminusParser.RelExpressionContext ctx) {
        List<Node> uns = new ArrayList<>();
        for (CminusParser.SumExpressionContext un : ctx.sumExpression()) {
            uns.add(visitSumExpression(un));
        }
        if (uns.size() == 1) {
            return uns.get(0);
        }
        BinaryOperator op = new BinaryOperator((Expression)uns.get(0), ctx.relop(0).getText(), (Expression)uns.get(1));
        for (int i = 2; i < uns.size(); ++i) {
            op = new BinaryOperator(op, ctx.relop(i-1).getText(), (Expression) uns.get(i));
        }
        return op;
    }

    @Override public Node visitSumExpression(CminusParser.SumExpressionContext ctx) {
        List<Node> es = new ArrayList<>();
        for (CminusParser.TermExpressionContext e : ctx.termExpression()) {
            es.add(visitTermExpression(e));
        }
        if (es.size() == 1) {
            return es.get(0);
        }
        BinaryOperator op = new BinaryOperator((Expression)es.get(0), ctx.sumop(0).getText(), (Expression)es.get(1));
        for (int i = 2; i < es.size(); ++i) {
            op = new BinaryOperator(op, ctx.sumop(i-1).getText(), (Expression) es.get(i));
        }
        return op;
    }

    @Override public Node visitTermExpression(CminusParser.TermExpressionContext ctx) {
        List<Node> es = new ArrayList<>();
        for (CminusParser.UnaryExpressionContext e : ctx.unaryExpression()) {
            es.add(visitUnaryExpression(e));
        }
        if (es.size() == 1) {
            return es.get(0);
        }
        BinaryOperator op = new BinaryOperator((Expression)es.get(0), ctx.mulop(0).getText(), (Expression)es.get(1));
        for (int i = 2; i < es.size(); ++i) {
            op = new BinaryOperator(op, ctx.mulop(i-1).getText(), (Expression) es.get(i));
        }
        return op;
    }

    @Override public Node visitUnaryExpression(CminusParser.UnaryExpressionContext ctx) {
        Node ret = visitFactor(ctx.factor());
        for (int i  = ctx.unaryop().size()-1; i >= 0; i--) {
            ret = new UnaryOperator(ctx.unaryop(i).getText(), (Expression)ret);
        }
        return ret;
    }

    @Override public Node visitMutable(CminusParser.MutableContext ctx) {
        Expression e = null;
        if (ctx.expression() != null) {
            e = (Expression) visitExpression(ctx.expression());
        }
        String id = ctx.ID().getText();
        if (symbolTable.find(id) == null) {
            LOGGER.warning("Undefined symbol on line " + ctx.getStart().getLine() + ": " + id);
        }
        return new Mutable(id, e);
    }

    @Override public Node visitImmutable(CminusParser.ImmutableContext ctx) {
        if (ctx.expression() != null) {
            return new ParenExpression((Expression) visitExpression(ctx.expression()));
        }
        return visitChildren(ctx);
    }

    @Override public Node visitCall(CminusParser.CallContext ctx) {
        final String id = ctx.ID().getText();
        final List<Expression> args = new ArrayList<>();
        for (CminusParser.ExpressionContext e : ctx.expression()) {
            args.add((Expression) visitExpression(e));
        }
        if (symbolTable.find(id) == null) {
            LOGGER.warning("Undefined symbol on line " + ctx.getStart().getLine() + ": " + id);
        }
        return new Call(id, args);
    }

    @Override public Node visitConstant(CminusParser.ConstantContext ctx) {
        final Node node;
        if (ctx.NUMCONST() != null) {
            node = new NumConstant(Integer.parseInt(ctx.NUMCONST().getText()));
        } else if (ctx.CHARCONST() != null) {
            node = new CharConstant(ctx.CHARCONST().getText().charAt(0));
        } else if (ctx.STRINGCONST() != null) {
            node = new StringConstant(ctx.STRINGCONST().getText());
        } else {
            node = new BoolConstant(ctx.getText().equals("true"));
        }
        return node;
    }
}
