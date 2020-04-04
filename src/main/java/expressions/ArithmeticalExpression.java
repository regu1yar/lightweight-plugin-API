package expressions;

public class ArithmeticalExpression implements Expression {
    private final String representation;

    public ArithmeticalExpression(String representation) {
        this.representation = representation;
    }

    @Override
    public String getRepresentation() {
        return representation;
    }
}
