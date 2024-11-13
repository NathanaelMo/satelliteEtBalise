package Visitor;

public class VariableUse extends UnaryExpr{
    public  Variable variable;
    public VariableUse(Variable variable) {
        this.variable = variable;
    }

    public Variable getVariable() {
        return this.variable;
    }

    @Override
    public void accept(Visitor v) {
        v.visitVariableUse(this);
    }
}
