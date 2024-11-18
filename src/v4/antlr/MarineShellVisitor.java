// Generated from D:/ACours/Brest/Master_TILL-A/semestre9/Analyse et conception objet/satelliteEtBalise/src/v4/MarineShell.g4 by ANTLR 4.13.2
package v4.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MarineShellParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MarineShellVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MarineShellParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MarineShellParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(MarineShellParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(MarineShellParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#objectType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectType(MarineShellParser.ObjectTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(MarineShellParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#method}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethod(MarineShellParser.MethodContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(MarineShellParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(MarineShellParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(MarineShellParser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link MarineShellParser#color}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitColor(MarineShellParser.ColorContext ctx);
}