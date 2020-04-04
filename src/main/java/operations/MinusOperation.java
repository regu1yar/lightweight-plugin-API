package operations;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;

public class MinusOperation implements Operation {
    @Override
    public Expression applyTo(Expression leftOperand, Expression rightOperand) throws TypeError {
        checkExpression(leftOperand);
        checkExpression(rightOperand);
        return new ArithmeticalExpression("(" + leftOperand.getRepresentation() + " - " + rightOperand.getRepresentation() + ")");
    }

    private void checkExpression(Expression expression) throws TypeError {
        if (!(expression instanceof ArithmeticalExpression)) {
            throw new TypeError("Expected an arithmetical expression, got: " + expression.getRepresentation());
        }
    }
}
