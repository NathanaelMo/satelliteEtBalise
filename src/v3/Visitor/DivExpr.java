package Visitor;

public class DivExpr extends BinExpr {
    public DivExpr(Expr opg, Expr opd) {
        this.opg=opg;
        this.opd=opd;
    }

    @Override
    public void accept(Visitor v) {
        v.visitDivExpr(this);
    }
}
