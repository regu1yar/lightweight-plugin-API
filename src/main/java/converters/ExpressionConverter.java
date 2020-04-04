package converters;

import exceptions.TypeError;
import expressions.Expression;

public interface ExpressionConverter {
    Expression convert(Expression expression) throws TypeError;
}
