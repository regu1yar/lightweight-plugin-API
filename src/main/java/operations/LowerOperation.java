package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import expressions.LogicalExpression;

public class LowerOperation implements Operation {
    @Override
    public Expression applyTo(Expression leftOperand, Expression rightOperand) throws TypeError {
        checkExpression(leftOperand);
        checkExpression(rightOperand);
        return new LogicalExpression("(" + leftOperand.getRepresentation() + " < " + rightOperand.getRepresentation() + ")");
    }

    private void checkExpression(Expression expression) throws TypeError {
        if (!(expression instanceof ArithmeticalExpression)) {
            throw new TypeError("Expected an arithmetical expression, got: " + expression.getRepresentation());
        }
    }
}
