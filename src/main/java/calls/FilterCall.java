package calls;

import exceptions.TypeError;
import expressions.Expression;
import expressions.LogicalExpression;
import utils.Representable;

public class FilterCall implements Call, Representable {
    private final Expression argument;

    public FilterCall(Expression argument) throws TypeError {
        if (!(argument instanceof LogicalExpression)) {
            throw new TypeError("Expected logical expression for filter argument, got: " + argument.getRepresentation());
        }

        this.argument = argument;
    }

    @Override
    public String getRepresentation() {
        return "filter{" + argument.getRepresentation() + "}";
    }

    @Override
    public Expression getArgument() {
        return argument;
    }
}
