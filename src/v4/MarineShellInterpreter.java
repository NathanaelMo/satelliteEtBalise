package v4;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import v4.antlr.*;

import java.util.HashMap;
import java.util.Map;

public class MarineShellInterpreter extends MarineShellBaseVisitor<Object> {
    private final CommandClasses.SimulationContext context;

    public MarineShellInterpreter(CommandClasses.SimulationContext context) {
        this.context = context;
    }

    public void interpret(String input) {
        try {
            MarineShellLexer lexer = new MarineShellLexer(CharStreams.fromString(input));
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MarineShellParser parser = new MarineShellParser(tokens);
            ParseTree tree = parser.program();
            visit(tree);
        } catch (Exception e) {
            System.err.println("Error interpreting input: " + e.getMessage());
        }
    }

    @Override
    public Object visitDeclaration(MarineShellParser.DeclarationContext ctx) {
        try {
            String varName = ctx.IDENTIFIER().getText();
            visit(ctx.expression()); // L'expression créera et stockera l'objet dans le contexte
            return null;
        } catch (Exception e) {
            throw new MarineShellException("Error in declaration: " + e.getMessage());
        }
    }

    @Override
    public Object visitExpression(MarineShellParser.ExpressionContext ctx) {
        try {
            if (ctx.objectType() != null) {
                // Création d'un nouvel objet
                String type = ctx.objectType().getText();
                String varName = ctx.IDENTIFIER().getText();
                Map<String, Object> params = new HashMap<>();

                // Traitement des paramètres
                if (ctx.paramList() != null) {
                    for (MarineShellParser.ParamContext param : ctx.paramList().param()) {
                        String paramName = param.getChild(0).getText();
                        String paramValueStr = param.getChild(2).getText();
                        Object paramValue = parseParamValue(paramValueStr);
                        params.put(paramName, paramValue);
                    }
                }

                // Création de la commande appropriée
                CommandClasses.Command cmd;
                if (type.equals("Satellite")) {
                    cmd = new CommandClasses.CreateSatelliteCommand(varName, params);
                } else {
                    cmd = new CommandClasses.CreateBeaconCommand(varName, params);
                }

                cmd.execute(context);
                return null;
            } else {
                // Récupération d'une variable existante
                return context.getVariable(ctx.IDENTIFIER().getText());
            }
        } catch (Exception e) {
            throw new MarineShellException("Error in expression: " + e.getMessage());
        }
    }

    @Override
    public Object visitCommand(MarineShellParser.CommandContext ctx) {
        try {
            String targetName = ctx.IDENTIFIER().getText();
            MarineShellParser.MethodContext methodCtx = ctx.method();
            String methodName = methodCtx.getChild(0).getText();

            CommandClasses.Command cmd;

            switch (methodName) {
                case "start":
                    cmd = new CommandClasses.StartCommand(targetName);
                    break;

                case "stop":
                    cmd = new CommandClasses.StopCommand(targetName);
                    break;

                case "setSpeed":
                    int speed = Integer.parseInt(methodCtx.NUMBER().getText());
                    cmd = new CommandClasses.SetSpeedCommand(targetName, speed);
                    break;

                case "setPattern":
                    String pattern = methodCtx.pattern().getText();
                    cmd = new CommandClasses.SetPatternCommand(targetName, pattern);
                    break;

                case "setColor":
                    String color = methodCtx.color().getText();
                    cmd = new CommandClasses.SetColorCommand(targetName, color);
                    break;

                default:
                    throw new MarineShellException("Unknown command: " + methodName);
            }

            cmd.execute(context);
            return null;
        } catch (Exception e) {
            throw new MarineShellException("Error executing command: " + e.getMessage());
        }
    }

    private Object parseParamValue(String value) {
        if (value.startsWith("#")) {
            return value; // Garde le # pour les patterns et couleurs
        } else {
            try {
                // Essaie de convertir en entier d'abord
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                try {
                    // Si ce n'est pas un entier, essaie de convertir en double
                    return Double.parseDouble(value);
                } catch (NumberFormatException e2) {
                    // Si ce n'est ni un entier ni un double, retourne la chaîne
                    return value;
                }
            }
        }
    }
}