package Visitor;

public class Program {
    public Statement[] statements;
    public Program(Statement[] statements){
        this.statements = statements;
    }

    public void accept(Visitor v){
        v.visitProgram(this);
    }
}
