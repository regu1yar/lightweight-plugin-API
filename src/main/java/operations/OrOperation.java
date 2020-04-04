package operations;

import exceptions.TypeError;
import expressions.Expression;
import expressions.LogicalExpression;

public class OrOperation implements Operation {
    @Override
    public Expression applyTo(Expression leftOperand, Expression rightOperand) throws TypeError {
        checkExpression(leftOperand);
        checkExpression(rightOperand);
        return new LogicalExpression("(" + leftOperand.getRepresentation() + " | " + rightOperand.getRepresentation() + ")");
    }

    private void checkExpression(Expression expression) throws TypeError {
        if (!(expression instanceof LogicalExpression)) {
            throw new TypeError("Expected an logical expression, got: " + expression.getRepresentation());
        }
    }
}
