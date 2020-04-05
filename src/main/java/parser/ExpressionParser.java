package parser;

import exceptions.SyntaxError;
import exceptions.TypeError;
import expressions.Expression;

public interface ExpressionParser {
    Expression parse(String representation) throws SyntaxError, TypeError;
}
