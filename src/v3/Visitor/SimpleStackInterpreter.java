package v3.Visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public class SimpleStackInterpreter implements Visitor {
    Stack<Expr> stk;
    Map<Variable, IntExpr> values;
    Map<String, Variable> variables;

    public SimpleStackInterpreter() {
        // pile d’execution
        this.stk = new Stack<Expr>();
        // 1 contexte global pour les variables
        this.values = new HashMap<>();
        this.variables = new HashMap<>();
    }

    @Override
    public void visitIntExpr(IntExpr intExpr) {
        this.stk.push(intExpr);
    }

    @Override
    public void visitMultExpr(MultExpr multExpr) {
        multExpr.getLeft().accept(this) ;
        multExpr.getRight().accept(this) ;
        // recuperation des arguments du + sur la pile
        IntExpr r = (IntExpr) this.stk.pop();
        IntExpr l = (IntExpr) this.stk.pop();
        // empilement du resultat
        this.stk.push(new IntExpr(l.getRawValue() * r.getRawValue())) ;
    }

    @Override
    public void visitDivExpr(DivExpr divExpr) {
        divExpr.getLeft().accept(this) ;
        divExpr.getRight().accept(this) ;
        // recuperation des arguments du + sur la pile
        IntExpr r = (IntExpr) this.stk.pop();
        IntExpr l = (IntExpr) this.stk.pop();
        // empilement du resultat
        this.stk.push(new IntExpr(l.getRawValue() / r.getRawValue())) ;
    }

    @Override
    public void visitPlusExpr(PlusExpr plusExpr) {
        plusExpr.getLeft().accept(this) ;
        plusExpr.getRight().accept(this) ;
        // recuperation des arguments du + sur la pile
        IntExpr r = (IntExpr) this.stk.pop();
        IntExpr l = (IntExpr) this.stk.pop();
        // empilement du resultat
        this.stk.push(new IntExpr(l.getRawValue() + r.getRawValue())) ;
    }

    @Override
    public void visitSubExpr(SubExpr subExpr) {
        subExpr.getLeft().accept(this) ;
        subExpr.getRight().accept(this) ;
        // recuperation des arguments du + sur la pile
        IntExpr r = (IntExpr) this.stk.pop();
        IntExpr l = (IntExpr) this.stk.pop();
        // empilement du resultat
        this.stk.push(new IntExpr(l.getRawValue() - r.getRawValue())) ;
    }


    @Override
    public void visitVariableUse(VariableUse variableUse) {
        Variable v = variableUse.getVariable();
        IntExpr expr = this.values.get(v);
        this.stk.push(expr);
    }

    @Override
    public void visitVariable(Variable variable) {
        this.variables.put(variable.getName(), variable);
    }

    @Override
    public void visitAssignment(Assignment assignment) {
        assignment.rightHandSide.accept(this) ;
        // on récupère l'expr assigné au nom de la variable
        IntExpr res = (IntExpr) this.stk.pop();
        this.values.put(assignment.leftHandSide, res); }

    @Override
    public void visitPrintln(Println println) {
        for ( Expr expr : println.arguments){
            expr.accept(this);
            IntExpr res = (IntExpr) this.result();
            if (res!=null) {
                System.out.println(res.getRawValue());
            }
        }
    }

    @Override
    public void visitProgram(Program program) {
        for ( Statement stat : program.statements){
            stat.accept(this);
        }
    }

    public Variable getVariableNamed(String aName) {
        Variable v = this.variables.get(aName);
        if (v == null) {
            v = new Variable(aName);
            this.variables.put(aName, v);

        }
        return v;
    }

    public Object result() {
        if(!this.stk.isEmpty()){
            return this.stk.peek();
        }else {
            return null;
        }
    }

    public IntExpr getVariableValueFromName(String v) {
      return this.values.get(this.variables.get(v));
    }
}
