package Visitor;

public class Assignment extends Statement{
    public Expr rightHandSide;
    public Variable leftHandSide;
    public Assignment(Variable leftHandSide,Expr rightHandSide) {
        this.rightHandSide = rightHandSide;
        this.leftHandSide = leftHandSide;
    }

    @Override
    public void accept(Visitor v) {
        v.visitAssignment(this);
    }
}
