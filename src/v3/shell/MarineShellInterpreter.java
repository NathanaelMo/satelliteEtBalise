package v3.shell;

import java.util.HashMap;
import java.util.Map;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class MarineShellInterpreter extends MarineShellBaseVisitor<Object> {
    private final CommandClasses.SimulationContext context;

    public MarineShellInterpreter(CommandClasses.SimulationContext context) {
        this.context = context;
    }

    public void interpret(String input) {
        MarineShellLexer lexer = new MarineShellLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MarineShellParser parser = new MarineShellParser(tokens);
        ParseTree tree = parser.program();
        visit(tree);
    }

    @Override
    public Object visitDeclaration(MarineShellParser.DeclarationContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        Object value = visit(ctx.expression());
        context.setVariable(varName, value);
        return null;
    }

    @Override
    public Object visitExpression(MarineShellParser.ExpressionContext ctx) {
        if (ctx.objectType() != null) {
            String type = ctx.objectType().getText();
            Map<String, Object> params = new HashMap<>();

            if (ctx.paramList() != null) {
                ctx.paramList().param().forEach(param -> {
                    String paramName = param.getChild(0).getText();
                    Object paramValue = param.getChild(2).getText();
                    params.put(paramName, paramValue);
                });
            }

            Command cmd;
            if (type.equals("Satellite")) {
                cmd = new CreateSatelliteCommand(ctx.IDENTIFIER().getText(), params);
            } else {
                cmd = new CreateBeaconCommand(ctx.IDENTIFIER().getText(), params);
            }
            cmd.execute(context);
            return null;
        }
        return context.getVariable(ctx.IDENTIFIER().getText());
    }

    @Override
    public Object visitCommand(MarineShellParser.CommandContext ctx) {
        String targetName = ctx.IDENTIFIER().getText();
        String methodName = ctx.method().getChild(0).getText();

        Command cmd;
        switch (methodName) {
            case "start":
                cmd = new StartCommand(targetName);
                break;
            case "stop":
                // Implement stop command
                return null;
            case "setSpeed":
                // Implement setSpeed command
                return null;
            case "setPattern":
                // Implement setPattern command
                return null;
            case "setColor":
                // Implement setColor command
                return null;
            default:
                throw new RuntimeException("Unknown command: " + methodName);
        }

        cmd.execute(context);
        return null;
    }
}