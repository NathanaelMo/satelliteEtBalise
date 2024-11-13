package v3.Visitor;

public class Println extends ProcCall{
    public Println(Expr[] arguments) {
        super(arguments);
    }

    @Override
    public void accept(Visitor v) {
        v.visitPrintln(this);
    }
}
