package operations;

import exceptions.TypeError;
import expressions.Expression;

public interface Operation {
    Expression applyTo(Expression leftOperand, Expression rightOperand) throws TypeError;
}
