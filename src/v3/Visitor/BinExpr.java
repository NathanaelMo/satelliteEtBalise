package Visitor;

public abstract class BinExpr extends Expr {
    Expr opg;
    Expr opd;

    public Expr getLeft(){
        return opg;
    }
    public Expr getRight(){
        return opd;
    }

    public abstract void accept(Visitor v);
}
