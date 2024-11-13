package Visitor;

public class ProcCall extends Statement{
    public Expr[] arguments;
    public ProcCall(Expr[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public void accept(Visitor v) {

    }
}
