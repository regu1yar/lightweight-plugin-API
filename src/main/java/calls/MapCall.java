package calls;

import exceptions.TypeError;
import expressions.ArithmeticalExpression;
import expressions.Expression;
import utils.Representable;

public class MapCall implements Call, Representable {
    private final Expression argument;

    public MapCall(Expression argument) throws TypeError {
        if (!(argument instanceof ArithmeticalExpression)) {
            throw new TypeError("Expected arithmetical expression for filter argument, got: " + argument.getRepresentation());
        }

        this.argument = argument;
    }

    @Override
    public String getRepresentation() {
        return "map{" + argument.getRepresentation() + "}";
    }

    @Override
    public Expression getArgument() {
        return argument;
    }
}
