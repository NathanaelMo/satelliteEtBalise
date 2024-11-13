package Visitor;

public class SubExpr extends BinExpr{
    public SubExpr(Expr opg, Expr opd){
        this.opg=opg;
        this.opd=opd;
    }

    @Override
    public void accept(Visitor v) {
        v.visitSubExpr(this);
    }
}
