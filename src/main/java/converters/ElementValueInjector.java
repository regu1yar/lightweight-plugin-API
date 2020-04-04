package converters;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;

public class ElementValueInjector implements ExpressionConverter {
    private final Expression elementValue;

    public ElementValueInjector(Expression elementValue) throws TypeError {
        if (!(elementValue instanceof ArithmeticalExpression)) {
            throw new TypeError("Expected arithmetical expression for value injection, got: " + elementValue.getRepresentation());
        }

        this.elementValue = elementValue;
    }

    @Override
    public Expression convert(Expression expression) throws TypeError {
        String oldRepresentation = expression.getRepresentation();
        String newRepresentation = oldRepresentation.replaceAll("element", elementValue.getRepresentation());
        if (expression instanceof ArithmeticalExpression) {
            return new ArithmeticalExpression(newRepresentation);
        } else if (expression instanceof LogicalExpression) {
            return new LogicalExpression(newRepresentation);
        } else {
            throw new TypeError("Unexpected type of expression: " + expression.getRepresentation());
        }
    }
}
