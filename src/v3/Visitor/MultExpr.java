package Visitor;

public class MultExpr extends BinExpr{
    public MultExpr(Expr opg, Expr opd){
        this.opg=opg;
        this.opd=opd;
    }
    @Override
    public void accept(Visitor v) {
        v.visitMultExpr(this);
    }
}
