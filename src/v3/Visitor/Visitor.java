package Visitor;

public interface Visitor {

    public void visitIntExpr(IntExpr intExpr);

    public void visitMultExpr(MultExpr multExpr);

    public void visitPlusExpr(PlusExpr plusExpr);

    public void visitVariableUse(VariableUse variableUse);

    public void visitVariable(Variable variable);

    public void visitAssignment(Assignment assignment);

    public void visitPrintln(Println println);

    public void visitProgram(Program program);

	public void visitDivExpr(DivExpr divExpr);

	public void visitSubExpr(SubExpr subExpr);
}
