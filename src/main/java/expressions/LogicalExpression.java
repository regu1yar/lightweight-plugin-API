package expressions;

import utils.Representable;

public class LogicalExpression implements Expression {
    private final String representation;

    public LogicalExpression(String representation) {
        this.representation = representation;
    }

    @Override
    public String getRepresentation() {
        return representation;
    }
}
