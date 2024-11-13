package v3.Visitor;
public class IntExpr extends UnaryExpr {
    public Integer rawValue;

    public IntExpr(Integer val){
        this.rawValue = val;

    }

    public void accept(Visitor v) {
        v.visitIntExpr(this);
    }

    public Integer getRawValue() {
        return rawValue;
    }
}

