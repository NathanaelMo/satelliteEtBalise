// Generated from D:/ACours/Brest/Master_TILL-A/semestre9/Analyse et conception objet/satelliteEtBalise/src/v4/MarineShell.g4 by ANTLR 4.13.2
package v4.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MarineShellParser}.
 */
public interface MarineShellListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MarineShellParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MarineShellParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MarineShellParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MarineShellParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(MarineShellParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(MarineShellParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(MarineShellParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(MarineShellParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#objectType}.
	 * @param ctx the parse tree
	 */
	void enterObjectType(MarineShellParser.ObjectTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#objectType}.
	 * @param ctx the parse tree
	 */
	void exitObjectType(MarineShellParser.ObjectTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(MarineShellParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(MarineShellParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#method}.
	 * @param ctx the parse tree
	 */
	void enterMethod(MarineShellParser.MethodContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#method}.
	 * @param ctx the parse tree
	 */
	void exitMethod(MarineShellParser.MethodContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(MarineShellParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(MarineShellParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(MarineShellParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(MarineShellParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#pattern}.
	 * @param ctx the parse tree
	 */
	void enterPattern(MarineShellParser.PatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#pattern}.
	 * @param ctx the parse tree
	 */
	void exitPattern(MarineShellParser.PatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link MarineShellParser#color}.
	 * @param ctx the parse tree
	 */
	void enterColor(MarineShellParser.ColorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MarineShellParser#color}.
	 * @param ctx the parse tree
	 */
	void exitColor(MarineShellParser.ColorContext ctx);
}