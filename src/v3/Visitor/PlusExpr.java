package v3.Visitor;

public class PlusExpr extends BinExpr{
    public PlusExpr(Expr opg, Expr opd){
        this.opg=opg;
        this.opd=opd;
    }

    @Override
    public void accept(Visitor v) {
        v.visitPlusExpr(this);
    }
}
